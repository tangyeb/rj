package com.itheima.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.itheima.common.R;
import com.itheima.constant.TokenConstant;
import com.itheima.dto.MsgDto;
import com.itheima.dto.UserLoginDto;
import com.itheima.entity.User;
import com.itheima.handler.MyMetaObjecthandler;
import com.itheima.service.UserService;
import com.itheima.vo.UserVo;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author tang
 * @date 2022/5/7 19:21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private MyMetaObjecthandler mm;
    @Autowired
    private SendSms sendSms;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody MsgDto dto) throws Exception {
        //生成4为验证码 "%04d"：位数不够向左补0至4位数
        String code = String.format("%04d", new Random().nextInt(9999));
        //发送短信
        sendSms.msg(dto.getPhone(), code);
        redisTemplate.opsForValue().set(dto.getPhone(), code, 60, TimeUnit.SECONDS);

        return R.success("验证码发送成功");

    }

    /**
     * 用户登入
     *
     * @param dto 账号密码
     * @return 结果
     */
    @PostMapping("/login")
    public R<Object> login(@RequestBody UserLoginDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return R.error("参数为空");
        }

        String code = redisTemplate.opsForValue().get(dto.getPhone());
        if (code == null) {
            return R.error("验证码已失效");
        }
        if (!code.equals(dto.getCode())) {
            return R.error("验证码错误");
        }

        //查找用户
        User user = userService.login(dto);

        //用户不存在就新增用户
        if (user == null) {
            User newUser = new User();
            newUser.setPhone(dto.getPhone());
            newUser.setId(IdUtil.getSnowflakeNextIdStr());
            MetaObject metaObject = SystemMetaObject.forObject(newUser);
            mm.insertFill(metaObject);
            userService.saveUser(newUser);
        }

        //重新获取用户,才能给新增的用户添加token
        User login = userService.login(dto);

        //生成token
        String tokenStr = System.currentTimeMillis() + userService.login(dto).getId();
        String token = DigestUtils.md5DigestAsHex(tokenStr.getBytes());

        //将token记录到redis
        redisTemplate.opsForValue().set(TokenConstant.USER_TOKEN_PREFIX + token, JSON.toJSONString(user), 30, TimeUnit.DAYS);

        //返回带token的对象
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(login, vo);
        vo.setToken(token);
        return R.success(vo);

    }

    /**
     * 登出
     *
     * @param request 前端请求
     * @return 结果
     */
    @PostMapping("/loginout")
    public R<String> logout(HttpServletRequest request) {
        String token = request.getHeader("AuthorizationUser");
        redisTemplate.delete(TokenConstant.USER_TOKEN_PREFIX + token);
        return R.success("退出成功");
    }


}

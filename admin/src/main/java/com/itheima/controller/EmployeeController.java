package com.itheima.controller;

import com.alibaba.fastjson.JSON;
import com.itheima.common.R;
import com.itheima.constant.TokenConstant;
import com.itheima.dto.LoginDto;
import com.itheima.dto.SaveEmployeeDto;
import com.itheima.dto.UpdateEmployeeDto;
import com.itheima.entity.Employee;
import com.itheima.handler.MyMetaObjecthandler;
import com.itheima.service.EmployeeService;
import com.itheima.vo.EmployeeVo;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author tang
 * @date 2022/5/7 19:40
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MyMetaObjecthandler mm;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 新增用户
     *
     * @param dto 参数
     * @return 是否成功
     */
    @PostMapping("")
    public R<String> saveEmployee(@RequestBody SaveEmployeeDto dto) {
        if (dto == null) {
            return R.error("404");
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        MetaObject metaObject = SystemMetaObject.forObject(employee);
        mm.insertFill(metaObject);
        boolean flag = employeeService.saveEmployee(employee);
        if (flag) {
            return R.success(null);
        } else {
            return R.error("201");
        }
    }

    /**
     * 分页查询
     *
     * @param page     页码
     * @param pageSize 条数
     * @return 分页查询信息
     */
    @GetMapping("/page")

    public R<com.itheima.vo.GetEmployeeByPageVo> getEmployeeByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {

        return R.success(employeeService.getEmployeeByPage(page, pageSize));
    }

    /**
     * 更新用户
     *
     * @param dto 传入参数
     * @return 结果
     */
    @PutMapping
    public R<String> update(@RequestBody UpdateEmployeeDto dto) {
        if (dto == null) {
            return R.error("404");
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        MetaObject metaObject = SystemMetaObject.forObject(employee);
        mm.updateFill(metaObject);
        boolean flag = employeeService.update(employee);
        if (flag) {
            return R.success("员工信息修改成功");
        }
        return R.error("员工信息修改失败");
    }

    /**
     * 用户登入
     *
     * @param dto 账号密码
     * @return 结果
     */
    @PostMapping("/login")
    public R<Object> login(@RequestBody LoginDto dto) {
        //加密
        String password = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes());
        dto.setPassword(password);
        //获取对象
        Employee emp = employeeService.login(dto);
        int i = employeeService.selectEmployeeByUsername(dto.getUsername());
        if (i == 0) {
            return R.error("用户不存在");
        } else if (emp == null) {
            return R.error("密码错误或账号被停用");
        }
        //生成token
        String tokenStr = System.currentTimeMillis() + employeeService.login(dto).getId();
        String token = DigestUtils.md5DigestAsHex(tokenStr.getBytes());
        //将token记录到redis
        redisTemplate.opsForValue().set(TokenConstant.EMPLOYEE_TOKEN_PREFIX + token, JSON.toJSONString(emp), 7, TimeUnit.DAYS);

        EmployeeVo vo = new EmployeeVo();
        BeanUtils.copyProperties(emp, vo);
        vo.setToken(token);

        return R.success(vo);


    }

    /**
     * 登出
     *
     * @param request 前端请求
     * @return 结果
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        redisTemplate.delete(TokenConstant.EMPLOYEE_TOKEN_PREFIX + token);
        return R.success("退出成功");
    }

    /**
     * 根据ID查询
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public R<Object> getEmployeeById(@PathVariable String id) {
        return R.success(employeeService.getEmployeeById(id));
    }
}


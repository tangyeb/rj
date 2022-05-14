package com.itheima.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.itheima.common.R;
import com.itheima.common.TokenHolder;
import com.itheima.constant.TokenConstant;
import com.itheima.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author itheima
 * @since 2022-04-09
 */
@Slf4j
public class UserAuthInterceptor implements HandlerInterceptor {

    private RedisTemplate<Object, Object> redisTemplate;

    public UserAuthInterceptor() {

    }

    public UserAuthInterceptor(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("AuthorizationUser");
        if (StrUtil.isNotEmpty(token)) {
            Object o = redisTemplate.opsForValue().get(TokenConstant.USER_TOKEN_PREFIX + token);
            if (Objects.nonNull(o)) {
                User user = JSON.parseObject((String) o, User.class);
                if (Objects.nonNull(user)) {
                    String id = user.getId();
                    TokenHolder.setCurrentId(id);
                    return true;
                }
            }
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }
}


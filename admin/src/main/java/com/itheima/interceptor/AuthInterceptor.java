package com.itheima.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.itheima.common.R;
import com.itheima.common.TokenHolder;
import com.itheima.constant.TokenConstant;
import com.itheima.entity.Employee;
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
public class AuthInterceptor implements HandlerInterceptor {

    private RedisTemplate<Object, Object> redisTemplate;

    public AuthInterceptor() {

    }

    public AuthInterceptor(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if(StrUtil.isNotEmpty(token)){
            Object o = redisTemplate.opsForValue().get(TokenConstant.EMPLOYEE_TOKEN_PREFIX + token);
            if(Objects.nonNull(o)){
                Employee employee = JSON.parseObject((String) o, Employee.class);
                if(Objects.nonNull(employee)){
                    String id = employee.getId();
                    TokenHolder.setCurrentId(id);
                    return true;
                }
            }
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return false;
    }

}

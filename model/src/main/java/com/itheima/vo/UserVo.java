package com.itheima.vo;

import com.itheima.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tang
 * @date 2022/5/13 9:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends User {
    private String token;
}

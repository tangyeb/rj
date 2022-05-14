package com.itheima.service;


import com.itheima.dto.LoginDto;
import com.itheima.entity.Employee;
import com.itheima.vo.GetEmployeeByPageVo;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/7 19:23
 */
public interface EmployeeService {
    public boolean saveEmployee(Employee employee);

    public List<Employee> getAll();

    public GetEmployeeByPageVo getEmployeeByPage(Integer page, Integer pageSize);
    public boolean update(Employee employee);

    public Employee getEmployeeById(String id);

    public int selectEmployeeByUsername(String username);

    public Employee login(LoginDto dto);
}

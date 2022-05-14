package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.dto.LoginDto;
import com.itheima.entity.Employee;
import com.itheima.mapper.EmployeeMapper;
import com.itheima.service.EmployeeService;
import com.itheima.vo.GetEmployeeByPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/7 19:25
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public boolean saveEmployee(Employee employee){
        return employeeMapper.insert(employee)>0;
    }

    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectList(null);
    }

    @Override
    public GetEmployeeByPageVo getEmployeeByPage(Integer page, Integer pageSize) {
        IPage<Employee> p = new Page<>(page, pageSize);
        //2 执行分页查询
        employeeMapper.selectPage(p, null);
        GetEmployeeByPageVo getEmployeeByPageVo = new GetEmployeeByPageVo();
        getEmployeeByPageVo.setRecords(p.getRecords());
        getEmployeeByPageVo.setTotal(p.getTotal());
        getEmployeeByPageVo.setSize(pageSize);
        getEmployeeByPageVo.setCurrent(p.getCurrent());
        getEmployeeByPageVo.setOrders(p.orders());
        getEmployeeByPageVo.setOptimizeCountSql(true);
        getEmployeeByPageVo.setHitCount(false);
        getEmployeeByPageVo.setCountId(null);
        getEmployeeByPageVo.setMaxLimit(null);
        getEmployeeByPageVo.setSearchCount(true);
        getEmployeeByPageVo.setPages(p.getPages());
        return getEmployeeByPageVo;
    }

    @Override
    public boolean update(Employee employee) {
        return employeeMapper.updateById(employee)>0;
    }

    @Override
    public Employee getEmployeeById(String id) {
        return employeeMapper.selectById(id);
    }

    @Override
    public int selectEmployeeByUsername(String username) {
        QueryWrapper<Employee> qw=new QueryWrapper();
        qw.eq("username", username);
        return employeeMapper.selectCount(qw);
    }

    @Override
    public Employee login(LoginDto dto) {
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper();
        lqw.eq(Employee::getUsername, dto.getUsername()).eq(Employee::getPassword, dto.getPassword()).eq(Employee::getStatus,1);
        return employeeMapper.selectOne(lqw);

    }


}

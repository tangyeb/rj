package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.entity.Flavors;
import com.itheima.mapper.FlavorsMapper;
import com.itheima.service.FlavorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/10 9:01
 */
@Service
public class FlavorsServiceImpl implements FlavorsService {
    @Autowired
    private FlavorsMapper flavorsMapper;
    @Override
    public boolean saveFlavors(Flavors flavors) {
        return flavorsMapper.insert(flavors)>0;
    }

    @Override
    public List<Flavors> selectByDishId(String id) {
        QueryWrapper<Flavors> qw=new QueryWrapper<>();
        qw.eq("dish_id", id);
        return flavorsMapper.selectList(qw);
    }

    @Override
    public boolean update(Flavors flavors) {
        return flavorsMapper.updateById(flavors)>0;
    }

    @Override
    public boolean delete(String id) {
        return flavorsMapper.deleteById(id)>0;
    }

    @Override
    public String selectNameById(String id) {
        return flavorsMapper.selectById(id).getName();
    }
}

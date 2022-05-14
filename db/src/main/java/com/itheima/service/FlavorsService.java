package com.itheima.service;

import com.itheima.entity.Flavors;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/10 9:00
 */
public interface FlavorsService {
    public boolean saveFlavors(Flavors flavors);

    public List<Flavors> selectByDishId(String id);

    public boolean update(Flavors flavors);

    public boolean delete(String id);

    public String selectNameById(String id);
}

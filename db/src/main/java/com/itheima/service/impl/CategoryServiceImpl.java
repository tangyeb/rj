package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.entity.CategoryInfo;
import com.itheima.mapper.CategoryMapper;
import com.itheima.service.CategoryService;
import com.itheima.vo.GetCategoryByPageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author tang
 * @date 2022/5/4 16:31
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Boolean insert(CategoryInfo categoryInfo) {
        return categoryMapper.insert(categoryInfo) > 0;
    }

    @Override
    public Boolean update(CategoryInfo categoryInfo) {
        return categoryMapper.updateById(categoryInfo) > 0;
    }

    @Override
    public Boolean delete(String id) {
        return categoryMapper.deleteById(id) > 0;
    }


    @Override
    public List<CategoryInfo> getAll(Integer type) {
        QueryWrapper qw=new QueryWrapper();
        qw.eq("type", type);
        return categoryMapper.selectList(qw);
    }

    @Override
    public List<CategoryInfo> getAll() {
        return categoryMapper.selectList(null);
    }

    @Override
    public String getNameById(String id) {
        return categoryMapper.selectById(id).getName();
    }
    @Override
    public Integer getTypeById(String id) {
        return categoryMapper.selectById(id).getType();
    }
    @Override
    public Integer getSortById(String id) {
        return categoryMapper.selectById(id).getSort();
    }

    @Override
    public GetCategoryByPageVo getByPage(Integer page, Integer pageSize) {

        IPage<CategoryInfo> p = new Page<>(page, pageSize);
        //2 执行分页查询
        categoryMapper.selectPage(p, null);
        GetCategoryByPageVo getCategoryByPageVo = new GetCategoryByPageVo();
        getCategoryByPageVo.setRecords(p.getRecords());
        getCategoryByPageVo.setTotal(p.getTotal());
        getCategoryByPageVo.setSize(pageSize);
        getCategoryByPageVo.setCurrent(p.getCurrent());
        getCategoryByPageVo.setOrders(p.orders());
        getCategoryByPageVo.setOptimizeCountSql(true);
        getCategoryByPageVo.setHitCount(false);
        getCategoryByPageVo.setCountId(null);
        getCategoryByPageVo.setMaxLimit(null);
        getCategoryByPageVo.setSearchCount(true);
        getCategoryByPageVo.setPages(p.getPages());
        return getCategoryByPageVo;
    }
}

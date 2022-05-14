package com.itheima.service;

import com.itheima.entity.CategoryInfo;
import com.itheima.vo.GetCategoryByPageVo;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/4 16:31
 */
public interface CategoryService {
    public Boolean insert(CategoryInfo categoryInfo);

    public Boolean update(CategoryInfo categoryInfo);

    public Boolean delete(String id);

    public List<CategoryInfo> getAll(Integer type);
    public List<CategoryInfo> getAll();

    public Integer getSortById(String id);

    public String getNameById(String id);
    public Integer getTypeById(String id);

    public GetCategoryByPageVo getByPage(Integer page, Integer pageSize);


}

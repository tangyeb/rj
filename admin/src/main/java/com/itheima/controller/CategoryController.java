package com.itheima.controller;

import com.itheima.common.R;
import com.itheima.dto.SaveCategoryInfoDto;
import com.itheima.dto.UpdateCategoryDto;
import com.itheima.entity.CategoryInfo;
import com.itheima.handler.MyMetaObjecthandler;
import com.itheima.service.CategoryService;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.soap.Text;
import java.util.List;

/**
 * @author tang
 * @date 2022/5/4 17:01
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MyMetaObjecthandler mm;

    /**
     * 新增分类
     *
     * @param dto 传入参数
     * @return success：null error：错误信息
     */
    @PostMapping("")
    public R<String> saveSortInfo(@RequestBody SaveCategoryInfoDto dto) {
        if (dto == null) {
            return R.error("404");
        }
        CategoryInfo categoryInfo = new CategoryInfo();
        BeanUtils.copyProperties(dto, categoryInfo);
        //自动注入
        MetaObject metaObject = SystemMetaObject.forObject(categoryInfo);
        mm.insertFill(metaObject);

        Boolean flag = categoryService.insert(categoryInfo);
        if (flag) {
            return R.success(null);
        } else {
            return R.error("201");
        }

    }

    /**
     * 更新对象
     *
     * @param dto 传入参数
     * @return success：null error：错误信息
     */
    @PutMapping
    public R<String> update(@RequestBody UpdateCategoryDto dto) {
        if (dto == null) {
            return R.error("参数为空");
        }
        MetaObject metaObject = SystemMetaObject.forObject(dto);
        mm.insertFill(metaObject);
        Boolean flag = categoryService.update(dto);
        if (flag) {
            return R.success(null);
        } else {
            return R.error("修改失败");
        }
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return success：null error：错误信息
     */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable String id) {
        Boolean flag = categoryService.delete(id);
        if (flag) {
            return R.success(null);
        } else {
            return R.error("删除失败");
        }
    }


    /**
     * 分类下拉列表
     *
     * @return 结果
     */
    @GetMapping("/list")
    public R<Object> getAll(@RequestParam(value = "type",required = false) Integer type) {
        List<CategoryInfo> list = categoryService.getAll(type);
        return R.success(list);
    }

    /**
     * 分页查询
     *
     * @param page     页码
     * @param pageSize 页码条数
     * @return 分页查询结果
     */
    @GetMapping("page")
    public R<Object> getByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return R.success(categoryService.getByPage(page, pageSize));
    }
}
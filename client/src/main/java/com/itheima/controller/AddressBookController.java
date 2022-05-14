package com.itheima.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.common.R;
import com.itheima.common.TokenHolder;
import com.itheima.dto.SaveAddrDto;
import com.itheima.dto.SetDefaultDto;
import com.itheima.dto.UpdateAddrDto;
import com.itheima.entity.AddressBook;
import com.itheima.handler.MyMetaObjecthandler;
import com.itheima.service.AddressBookService;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author tang
 * @date 2022/5/13 10:36
 */
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private MyMetaObjecthandler mm;


    /**
     * 获取地址
     *
     * @return 地址集合
     */
    @GetMapping("/list")
    public R<Object> getList() {
        List<AddressBook> list = addressBookService.getAll();

        if (CollUtil.isNotEmpty(list)) {
            return R.success(list);
        }

        return R.error("没有数据");
    }

    /**
     * 新增地址
     *
     * @param dto 传入参数
     * @return addressBook 新增地址对象
     */
    @PostMapping("")
    public R<Object> saveAddr(@RequestBody SaveAddrDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return R.error("参数为空");
        }

        AddressBook addressBook = new AddressBook();
        BeanUtils.copyProperties(dto, addressBook);

        addressBook.setId(IdUtil.getSnowflakeNextIdStr());
        addressBook.setUserId(TokenHolder.getCurrentId());
        MetaObject metaObject = SystemMetaObject.forObject(addressBook);
        mm.insertFill(metaObject);

        boolean flag = addressBookService.save(addressBook);
        if (flag) {
            return R.success(addressBook);
        }
        return R.error("403");
    }

    /**
     * 修改地址
     *
     * @param dto 修改地址参数
     * @return success 成功，返回地址对象  error 错误信息
     */
    @PutMapping("")
    public R<Object> updateAddr(@RequestBody UpdateAddrDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return R.error("参数为空");
        }

        AddressBook addressBook = new AddressBook();
        BeanUtils.copyProperties(dto, addressBook);

        MetaObject metaObject = SystemMetaObject.forObject(addressBook);
        mm.updateFill(metaObject);

        boolean flag = addressBookService.update(addressBook);
        if (flag) {
            return R.success(addressBook);
        }

        return R.error("修改失败");
    }

    /**
     * 删除地址
     *
     * @param ids 需要删除的地址ID
     * @return success 成功信息  error 错误信息
     */
    @DeleteMapping("")
    public R<String> delete(@RequestParam String[] ids) {
        if (CollUtil.isEmpty(Arrays.asList(ids))) {
            return R.error("参数为空");
        }

        boolean flag = addressBookService.delete(ids);
        if (flag) {
            return R.success("删除成功");
        }

        return R.error("删除失败");
    }

    /**
     * 获取默认地址
     *
     * @return success 成功，返回地址对象  error 错误信息
     */
    @GetMapping("/default")
    public R<Object> getDefault() {
        AddressBook addressBook = addressBookService.getDefault();

        if (ObjectUtil.isNotEmpty(addressBook)) {
            return R.success(addressBook);
        }

        return R.error("没有数据");
    }

    /**
     * 设置默认地址
     *
     * @param dto 传入参数
     * @return success 成功，返回地址对象  error 错误信息
     */
    @PutMapping("/default")
    public R<Object> setDefault(@RequestBody SetDefaultDto dto) {
        if (ObjectUtil.isEmpty(dto)) {
            return R.error("参数为空");
        }
        String id = dto.getId();

        //设置默认地址未非默认
        addressBookService.setNotDefault();

        //设置成默认地址
        boolean flag = addressBookService.setDefault(id);
        if (flag) {
            AddressBook addressBook = new AddressBook();
            addressBook.setId(id);
            //填充修改信息
            MetaObject metaObject = SystemMetaObject.forObject(addressBook);
            mm.updateFill(metaObject);
            return R.success(addressBook);
        }

        return R.error("设置失败");
    }

    /**
     * 根据Id获取地址
     *
     * @param id 传入ID
     * @return success 成功，返回地址对象  error 错误信息
     */
    @GetMapping("/{id}")
    public R<Object> getById(@PathVariable String id) {
        if (StrUtil.isEmpty(id)) {
            return R.error("参数为空");
        }

        AddressBook addressBook = addressBookService.getById(id);

        if (ObjectUtil.isNotEmpty(addressBook)) {
            return R.success(addressBook);
        }

        return R.error("没有数据");
    }


}

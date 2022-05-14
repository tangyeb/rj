package com.itheima.service.impl;

import com.itheima.common.TokenHolder;
import com.itheima.entity.AddressBook;
import com.itheima.mapper.AddressBookMapper;
import com.itheima.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/13 10:46
 */
@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    @Override
    public List<AddressBook> getAll() {
        return addressBookMapper.selectAll(TokenHolder.getCurrentId());
    }

    @Override
    public boolean save(AddressBook addressBook) {
        addressBookMapper.save(addressBook);
        return true;
    }

    @Override
    public boolean update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(String[] ids) {
        return addressBookMapper.delete(ids) == ids.length;
    }

    @Override
    public AddressBook getDefault() {

        return addressBookMapper.selectDefault(TokenHolder.getCurrentId());
    }

    @Override
    public boolean setDefault(String id) {
        addressBookMapper.setDefault(id);
        return true;
    }

    @Override
    public boolean setNotDefault() {
        addressBookMapper.setNotDefault(TokenHolder.getCurrentId());
        return true;
    }

    @Override
    public AddressBook getById(String id) {
        return addressBookMapper.selectById(id);
    }

}

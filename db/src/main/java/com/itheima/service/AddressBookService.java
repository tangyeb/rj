package com.itheima.service;

import com.itheima.entity.AddressBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/13 10:46
 */
public interface AddressBookService {
    public List<AddressBook> getAll();

    public boolean save(AddressBook addressBook);

    public boolean update(AddressBook addressBook);
    public boolean delete(String[] ids);

    public AddressBook getDefault();

    public boolean setDefault(String id);

    public boolean setNotDefault();

    public AddressBook getById(String id);
}

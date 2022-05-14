package com.itheima.mapper;

import com.itheima.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tang
 * @date 2022/5/13 10:39
 */
@Mapper
public interface AddressBookMapper {
    public List<AddressBook> selectAll(@Param("userId") String userId);

    public void save(AddressBook addressBook);

    public void update(AddressBook addressBook);

    public int delete(@Param("ids")String[] ids);

    public AddressBook selectDefault(@Param("userId") String userId);

    public void setDefault(String id);

    public void setNotDefault(@Param("userId") String userId);

    public AddressBook selectById(@Param("id") String id);
}

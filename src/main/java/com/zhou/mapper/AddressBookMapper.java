package com.zhou.mapper;

import com.zhou.entity.AddressBook;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【address_book(地址管理)】的数据库操作Mapper
* @createDate 2022-08-19 14:13:30
* @Entity com.zhou.entity.AddressBook
*/
@Mapper
@Repository
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}





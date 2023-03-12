package com.zhou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.entity.AddressBook;
import com.zhou.service.AddressBookService;
import com.zhou.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author 54086
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-08-19 14:13:30
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}





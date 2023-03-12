package com.zhou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.entity.User;
import com.zhou.service.UserService;
import com.zhou.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 54086
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-08-19 10:48:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}





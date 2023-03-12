package com.zhou.mapper;

import com.zhou.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-08-19 10:48:09
* @Entity com.zhou.entity.User
*/
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}





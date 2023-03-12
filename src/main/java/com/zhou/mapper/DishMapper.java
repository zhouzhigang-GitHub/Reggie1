package com.zhou.mapper;

import com.zhou.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-08-14 15:29:12
* @Entity com.zhou.entity.Dish
*/
@Mapper
@Repository
public interface DishMapper extends BaseMapper<Dish> {

}





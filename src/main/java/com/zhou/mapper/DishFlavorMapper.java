package com.zhou.mapper;

import com.zhou.entity.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-08-14 18:33:52
* @Entity com.zhou.entity.DishFlavor
*/
@Mapper
@Repository
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}





package com.zhou.mapper;

import com.zhou.entity.SetmealDish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2022-08-18 12:12:57
* @Entity com.zhou.entity.SetmealDish
*/
@Mapper
@Repository
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

}





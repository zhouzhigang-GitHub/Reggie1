package com.zhou.service;

import com.zhou.dto.DishDto;
import com.zhou.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 54086
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-08-14 15:29:12
*/
public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void  updateWithFlavor(DishDto dish);

}

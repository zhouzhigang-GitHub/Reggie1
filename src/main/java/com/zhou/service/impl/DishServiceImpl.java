package com.zhou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.dto.DishDto;
import com.zhou.entity.Dish;
import com.zhou.entity.DishFlavor;
import com.zhou.service.DishFlavorService;
import com.zhou.service.DishService;
import com.zhou.mapper.DishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 54086
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-08-14 15:29:12
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{


    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId();//菜品id

        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();

        Dish byId = this.getById(id);

        BeanUtils.copyProperties(byId,dishDto);

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, byId.getId());

        dishDto.setFlavors(dishFlavorService.list(wrapper));
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dish) {
        this.updateById(dish);

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper();
        wrapper.eq(DishFlavor::getDishId,dish.getId());
        dishFlavorService.remove(wrapper);

        List<DishFlavor> flavors = dish.getFlavors();
        flavors.stream().map(item ->{
           item.setDishId(dish.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

}





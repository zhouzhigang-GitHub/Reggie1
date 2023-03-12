package com.zhou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.common.CustomException;
import com.zhou.entity.Category;
import com.zhou.entity.Dish;
import com.zhou.entity.Setmeal;
import com.zhou.service.CategoryService;
import com.zhou.mapper.CategoryMapper;
import com.zhou.service.DishService;
import com.zhou.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 54086
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-08-14 14:13:49
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，并判断分类是否有商品
     * @param id
     */
    @Override
    public void remove(Long id) {
        //菜品
        LambdaQueryWrapper<Dish> dish = new LambdaQueryWrapper<>();
        dish.eq(Dish::getCategoryId,id);
        //套餐
        LambdaQueryWrapper<Setmeal> setmael = new LambdaQueryWrapper<>();
        setmael.eq(Setmeal::getCategoryId ,id);
        long count1 = dishService.count(dish);
        long count = setmealService.count(setmael);
        if (count1 > 0 ){
            throw new CustomException("当前分类下菜品被关联了，无法删除");
        }
        if (count > 0 ){
            throw new CustomException("当前分类下套餐被关联了，无法删除");
        }
        super.removeById(id);

    }
}





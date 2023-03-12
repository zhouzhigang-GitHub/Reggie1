package com.zhou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.entity.DishFlavor;
import com.zhou.service.DishFlavorService;
import com.zhou.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

/**
* @author 54086
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
* @createDate 2022-08-14 18:33:52
*/
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor>
    implements DishFlavorService{

}





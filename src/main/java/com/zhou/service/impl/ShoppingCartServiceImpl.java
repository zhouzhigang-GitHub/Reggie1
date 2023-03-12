package com.zhou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.entity.ShoppingCart;
import com.zhou.service.ShoppingCartService;
import com.zhou.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

/**
* @author 54086
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-08-19 15:14:10
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart>
    implements ShoppingCartService{

}





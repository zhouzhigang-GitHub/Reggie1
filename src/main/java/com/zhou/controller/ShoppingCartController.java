package com.zhou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhou.common.BaseContext;
import com.zhou.common.R;
import com.zhou.entity.ShoppingCart;
import com.zhou.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author 周志刚
 * @Date 2022/8/19 15:14
 * @PackageName: com.zhou.controller
 * @ClassName: ShoppingCartController
 * @Description: TODO
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> List(){
        Long id = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,id);
        List<ShoppingCart> list = shoppingCartService.list(wrapper);

        return R.success(list);
    }

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);

        if(dishId != null){
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);

        }else{
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        //查询当前菜品或者套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if(cartServiceOne != null){
            //如果已经存在，就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);
        }else{
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }

        return R.success(cartServiceOne);
    }

    @DeleteMapping("/clean")
    public R<String> clean(){

        Long id = BaseContext.getId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId ,id);
        shoppingCartService.remove(wrapper);
        return R.success("删除成功");
    }

    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart){
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId ,BaseContext.getId());
        if (shoppingCart.getDishId() != null){
            wrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
            ShoppingCart cart = shoppingCartService.getOne(wrapper);
            Integer number = cart.getNumber();
            if (number > 1){
                cart.setNumber(number - 1);
                shoppingCartService.updateById(cart);
            }else {
                shoppingCartService.removeById(cart);
            }
        }else {
            wrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
            ShoppingCart cart = shoppingCartService.getOne(wrapper);
            Integer number = cart.getNumber();
            if (number > 1){
                cart.setNumber(number - 1);
                shoppingCartService.updateById(cart);
            }else {
                shoppingCartService.removeById(cart);
            }
        }
        return R.success("成功");
    }
}

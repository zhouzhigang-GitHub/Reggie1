package com.zhou.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.common.R;
import com.zhou.dto.DishDto;
import com.zhou.entity.Category;
import com.zhou.entity.Dish;
import com.zhou.entity.DishFlavor;
import com.zhou.service.CategoryService;
import com.zhou.service.DishFlavorService;
import com.zhou.service.DishService;
import com.zhou.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 周志刚
 * @Date 2022/8/14 15:33
 * @PackageName: com.zhou.controller
 * @ClassName: DishController
 * @Description: TODO
 */

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    public RedisService redisService;



    @GetMapping("/page")
    public R<IPage> getAll(@RequestParam(value = "page") Integer page ,
                                 @RequestParam(value = "pageSize") Integer pageSize ,
                                 @RequestParam(value = "name",required = false) String name){
        IPage<Dish> pageInfo = pageInfo(page, pageSize, name);
        IPage<DishDto> Page = new Page<>();
        //拷贝对象属性
        BeanUtils.copyProperties(pageInfo,Page,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> recordsDto =records.stream().map((item) ->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());
        Page.setRecords(recordsDto);

        return R.success(Page);

    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        Long categoryId = dishDto.getCategoryId();
        redisService.removeCache(categoryId);
        return R.success("添加菜品成功");
    }

    @GetMapping("/{id}")
    public R<DishDto> getDishById(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);

    }

    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        Long categoryId = dishDto.getCategoryId();
        redisService.removeCache(categoryId);
        return R.success("x修改成功");
    }

    /**
     * 停售和启用
     * @param statusId
     * @param ids
     * @return
     */
    @PostMapping("/status/{statusId}")
    public R<String> updateStatus(@PathVariable Integer statusId ,
                                  @RequestParam(value = "ids") List<Long> ids){
        Integer status = statusId;
        List<Dish> dishList = ids.stream().map(item -> {
            Dish dish = new Dish();
            dish.setStatus(status);
            dish.setId(item);
            return dish;
        }).collect(Collectors.toList());

        boolean update = dishService.updateBatchById(dishList);
        return R.success("状态修改成功");
    }

//    @DeleteMapping
//    public R<String> deleteDish(@RequestParam(value = "ids") Long id){
//        dishService.removeById(id);
//        return R.success("删除成功");
//    }

    /**
     * 删除和批量删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteDish(@RequestParam(value = "ids") List<Long> ids){
        dishService.removeBatchByIds(ids);
        return R.success("删除成功");
    }

//    @GetMapping("/list")
//    public R<List<Dish>> getDishByCategoryId(@RequestParam(value = "categoryId" ) Long id){
//        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(id != null ,Dish::getCategoryId,id);
//        wrapper.eq(Dish::getStatus,1);
//        List<Dish> list = dishService.list(wrapper);
//        return R.success(list);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> getDishByCategoryId(@RequestParam(value = "categoryId" ) String id){
        List<DishDto> dishDtoList = new ArrayList<>();
        //从redis中获取数据
//        dishDtoList = JSON.parseArray((String) redisService.get(id));
          dishDtoList = JSON.parseArray((String) redisService.getCache(id));

        if (dishDtoList != null){
            return R.success(dishDtoList);
        }

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null ,Dish::getCategoryId,id);
        wrapper.eq(Dish::getStatus,1);
        List<Dish> list = dishService.list(wrapper);
        dishDtoList=list.stream().map(item ->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Category byId = categoryService.getById(id);
            dishDto.setCategoryName(byId.getName());
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,item.getId());
            List<DishFlavor> dishFlavors = dishFlavorService.list(queryWrapper);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());

        //将查询出来的数据添加到redis缓存中
//        redisService.set(id,dishDtoList,1, TimeUnit.HOURS);
        redisService.putCache(id,dishDtoList);
        return R.success(dishDtoList);
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    private IPage<Dish> pageInfo(Integer page , Integer pageSize , String name){
        IPage<Dish> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(name), Dish::getName, name);
        dishService.page(pageInfo,wrapper);
        return pageInfo;
    }
}

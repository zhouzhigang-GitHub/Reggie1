package com.zhou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.common.R;
import com.zhou.dto.SetmealDto;
import com.zhou.entity.Category;
import com.zhou.entity.Setmeal;
import com.zhou.entity.SetmealDish;
import com.zhou.service.CategoryService;
import com.zhou.service.SetmealDishService;
import com.zhou.service.SetmealService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author 周志刚
 * @Date 2022/8/14 15:33
 * @PackageName: com.zhou.controller
 * @ClassName: SetmealController
 * @Description: 套餐
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<IPage> getAll(@RequestParam(value = "page") Integer page ,
                           @RequestParam(value = "pageSize") Integer pageSize ,
                           @RequestParam(value = "name",required = false) String name){
        IPage<Setmeal> pageInfo = pageInfo(page, pageSize, name);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        List<Setmeal> list = pageInfo.getRecords();

        List<SetmealDto> collect = list.stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Category byId = categoryService.getById(item.getCategoryId());
            String categoryName = byId.getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(collect);
        return R.success(setmealDtoPage);

    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.save(setmealDto);
        List<SetmealDish> dishes =
                setmealDto.getSetmealDishes().stream().map(item -> {
            item.setSetmealId(String.valueOf(setmealDto.getId()));
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(dishes);
        return R.success("添加成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> updateGetData(@PathVariable Long id){
        SetmealDto setmealDto = new SetmealDto();
        Setmeal byId = setmealService.getById(id);
        BeanUtils.copyProperties(byId ,setmealDto);
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> dishList = setmealDishService.list(wrapper);
        Category category = categoryService.getById(byId.getCategoryId());
        String categoryName = category.getName();
        setmealDto.setSetmealDishes(dishList);
        setmealDto.setCategoryName(categoryName);
        return R.success(setmealDto);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDto,setmeal);
        setmealService.updateById(setmeal);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishService.updateBatchById(setmealDishes);

        return R.success("更新成功");
    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,
                                  @RequestParam(value = "ids") List<Long> ids){
        List<Setmeal> setmealList = ids.stream().map(item -> {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(item);
            setmeal.setStatus(status);
            return setmeal;
        }).collect(Collectors.toList());
        setmealService.updateBatchById(setmealList);
        return R.success("状态修改成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam(value = "ids") List<Long> ids){
        List<Setmeal> setmealList = ids.stream().map(item -> {
            Setmeal setmeal = new Setmeal();
            setmeal.setId(item);
            setmeal.setIsDeleted(1);
            return setmeal;
        }).collect(Collectors.toList());
        setmealService.updateBatchById(setmealList);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId")
    public R<List<Setmeal>> getSetmealList(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId ,setmeal.getCategoryId());
        wrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus ,setmeal.getStatus());
        List<Setmeal> setmealList = setmealService.list(wrapper);
        return R.success(setmealList);

    }


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    private IPage<Setmeal> pageInfo(Integer page , Integer pageSize , String name){
        IPage<Setmeal> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        wrapper.eq(Setmeal::getIsDeleted,0);
        setmealService.page(pageInfo,wrapper);
        return pageInfo;
    }
}

package com.zhou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.common.R;
import com.zhou.entity.Category;
import com.zhou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 周志刚
 * @Date 2022/8/14 14:15
 * @PackageName: com.zhou.controller
 * @ClassName: CategoryController
 * @Description: TODO
 */

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        System.out.println(category.getName());
        boolean save = categoryService.save(category);
        if (save){
            return R.success("添加成功");
        }
        return R.error("添加失败");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */

    @GetMapping("/page")
    public R<IPage<Category>> page(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "pageSize") Integer pageSize){
        IPage<Category> iPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        categoryService.page(iPage,wrapper);
        return R.success(iPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam(value = "ids") Long id){
        categoryService.remove(id);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        boolean update = categoryService.updateById(category);
        if (update){
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }

    @GetMapping("/list")
    public R<List<Category>> getCategory(@RequestParam(value = "type" ,required = false) Integer type){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(type != null ,Category::getType,type);
        List<Category> list = categoryService.list(wrapper);
        if (!list.isEmpty()){
            return R.success(list);
        }
        return R.error("失败");
    }
}

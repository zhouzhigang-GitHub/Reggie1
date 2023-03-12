package com.zhou.service;

import com.zhou.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 54086
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-08-14 14:13:49
*/
public interface CategoryService extends IService<Category> {

    /**
     * 根据id删除分类
     * @param id
     */
    public void remove(Long id);

}

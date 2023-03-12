package com.zhou.mapper;

import com.zhou.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-08-14 14:13:49
* @Entity com.zhou.entity.Category
*/

@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}





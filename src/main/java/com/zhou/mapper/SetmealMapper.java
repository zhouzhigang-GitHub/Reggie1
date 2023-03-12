package com.zhou.mapper;

import com.zhou.entity.Setmeal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【setmeal(套餐)】的数据库操作Mapper
* @createDate 2022-08-14 15:30:02
* @Entity com.zhou.entity.Setmeal
*/
@Mapper
@Repository
public interface SetmealMapper extends BaseMapper<Setmeal> {

}





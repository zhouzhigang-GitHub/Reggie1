package com.zhou.mapper;

import com.zhou.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-08-19 16:14:17
* @Entity com.zhou.entity.Orders
*/
@Mapper
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {

}





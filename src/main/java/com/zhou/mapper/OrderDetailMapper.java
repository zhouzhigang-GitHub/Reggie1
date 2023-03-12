package com.zhou.mapper;

import com.zhou.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-08-19 16:14:20
* @Entity com.zhou.entity.OrderDetail
*/
@Mapper
@Repository
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}





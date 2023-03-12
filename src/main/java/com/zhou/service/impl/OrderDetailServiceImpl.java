package com.zhou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.entity.OrderDetail;
import com.zhou.service.OrderDetailService;
import com.zhou.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 54086
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-08-19 16:14:20
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}





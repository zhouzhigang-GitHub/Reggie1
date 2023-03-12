package com.zhou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.common.BaseContext;
import com.zhou.common.R;
import com.zhou.dto.OrdersDto;
import com.zhou.entity.*;
import com.zhou.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 周志刚
 * @Date 2022/8/19 16:23
 * @PackageName: com.zhou.controller
 * @ClassName: OrderController
 * @Description: TODO
 */

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;



    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);

        return R.success("成功");
    }

    @GetMapping ("/userPage")
    public R<IPage<OrdersDto>> userPage(@RequestParam(value = "page") Integer page ,
                                        @RequestParam(value = "pageSize") Integer pageSize){
        IPage<Orders> orders = new Page<>(page,pageSize);
        ordersService.page(orders);
        List<Orders> records = orders.getRecords();
        IPage<OrdersDto> ordersDtoPage = new Page<>();
        BeanUtils.copyProperties(orders,ordersDtoPage,"records");

        List<OrdersDto> ordersDtos = records.stream().map(item -> {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(item, ordersDto);
            Long addressBookId = item.getAddressBookId();
            AddressBook addressBook = addressBookService.getById(addressBookId);
            String userName = item.getUserName();
            String consignee = addressBook.getConsignee();
            String phone = addressBook.getPhone();
            String detail = addressBook.getDetail();
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(OrderDetail::getOrderId, item.getId());
            List<OrderDetail> orderDetails = orderDetailService.list(wrapper);
            ordersDto.setOrderDetails(orderDetails);
            ordersDto.setAddress(detail);
            ordersDto.setUserName(userName);
            ordersDto.setConsignee(consignee);
            ordersDto.setPhone(phone);
            return ordersDto;
        }).collect(Collectors.toList());

        ordersDtoPage.setRecords(ordersDtos);

        return R.success(ordersDtoPage);
    }

    @GetMapping("/page")
    public R<IPage<Orders>> page(@RequestParam(value = "page") Integer page ,
                                 @RequestParam(value = "pageSize") Integer pageSize,
                                 @RequestParam(value = "beginTime" ,required = false) String beginTime,
                                 @RequestParam(value = "endTime" ,required = false) String endTime,
                                 @RequestParam(value = "number" ,required = false) String number){
        IPage<Orders> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(number),Orders::getNumber ,number);
        wrapper.between((beginTime != null && endTime != null),Orders::getOrderTime,beginTime,endTime);
        ordersService.page(pageInfo,wrapper);
        List<Orders> records = pageInfo.getRecords();
        records = records.stream().map(item ->{
            Long userId = item.getUserId();
            User user = userService.getById(userId);
            String name = user.getName();
            item.setUserName(name);
            return item;
        }).collect(Collectors.toList());
        pageInfo.setRecords(records);
        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> setStatus(@RequestBody Orders orders){
        ordersService.updateById(orders);
        return R.success("状态修改成功");
    }
}

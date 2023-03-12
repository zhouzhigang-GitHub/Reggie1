package com.zhou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.entity.Employee;
import com.zhou.service.EmployeeService;
import com.zhou.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
* @author 54086
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-08-13 15:37:47
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

}





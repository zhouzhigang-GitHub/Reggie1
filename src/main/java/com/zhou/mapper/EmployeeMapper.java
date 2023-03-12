package com.zhou.mapper;

import com.zhou.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 54086
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-08-13 15:37:47
* @Entity generator.entity.Employee
*/
@Repository
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}





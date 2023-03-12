package com.zhou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhou.common.R;
import com.zhou.entity.Employee;
import com.zhou.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @Author 周志刚
 * @Date 2022/8/13 15:46
 * @PackageName: com.zhou.controller
 * @ClassName: EmployeeController
 * @Description: TODO
 */

@RestController
@RequestMapping("/employee")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        String password = employee.getPassword();
        //Apache DigestUtils线程安全的类来进行计算一个字符串的MD5值
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeService.getOne(wrapper);

        if (one == null){
            return R.error("用户名或密码不正确");
        }

        if (!one.getPassword().equals(password)){
            return R.error("用户名或密码不正确");
        }

        if (one.getStatus() == 0){
            return R.error("该账户已被禁用，请联系管理员！");
        }

        request.getSession().setAttribute("employee", one.getId());
        return R.success(one);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功！");
    }

    /*
    新增员工
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee){
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
          //检测用户名是否存在
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeService.getOne(wrapper);
        if (one != null){
            return R.error("用户名存在,请重新输入！");
        }
        //添加用户
        boolean save = employeeService.save(employee);
        //判断用户是否添加成功
        if (save){
            return R.success("添加成功！");
        }
        return R.error("添加失败！");

    }

    /**
     * 分页查询及搜索
     * @param page
     * @param pageSize
     * @param name
     * @return
     *
     * @RequestParam
     * value：指定参数名
     * required（true，false）指定是否为必传值
     * defaultValue 指定参数默认值
     */
    @GetMapping("/page")
    public R<IPage<Employee>> page(@RequestParam(value = "page") Integer page ,
                        @RequestParam(value = "pageSize") Integer pageSize ,
                        @RequestParam(value = "name",required = false) String name) {
//        Page pageInfo = new Page(page, pageSize);
        IPage<Employee> pageInfo = pageInfo(page, pageSize, name);
        return R.success(pageInfo);
    }


    @PutMapping
    public R<String> update(@RequestBody Employee employee){
        boolean b = employeeService.updateById(employee);
        if (b){
            return R.success("状态修改成功");
        }
        return R.error("状态修改失败");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee byId = employeeService.getById(id);
        if (byId != null) {
            return R.success(byId);
        }
        return R.error("查询失败");
    }

    /**
     * 分页查询功能封装
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    private IPage<Employee> pageInfo(Integer page , Integer pageSize , String name){
        IPage<Employee> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(name), Employee::getName, name);
        employeeService.page(pageInfo,wrapper);
        return pageInfo;
    }
}

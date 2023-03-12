package com.zhou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhou.common.R;
import com.zhou.entity.User;
import com.zhou.service.RedisService;
import com.zhou.service.UserService;
import com.zhou.utils.ValidateCodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author 周志刚
 * @Date 2022/8/19 11:00
 * @PackageName: com.zhou.controller
 * @ClassName: UserController
 * @Description: TODO
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){

        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(6).toString();
            System.out.println("验证码：" + code);
            //将生成的验证码写入session中
//            session.setAttribute(phone,code);

            //将验证码写入到redis中，并设置有效时间为5分钟
            redisService.set(phone,code,5, TimeUnit.MINUTES);
            return R.success("手机验证码发送成功");
        }
        return R.error("验证码发送失败");

    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String,String> map, HttpSession session){
        String phone = map.get("phone");

        //从session中取出生成的验证码
//        String str = session.getAttribute(map.get("phone")).toString();
        //从redis中取出验证码
        String str = (String) redisService.get(phone);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone,map.get("phone"));
        User one = userService.getOne(wrapper);
        if (map.get("code").equals(str) && StringUtils.isNotEmpty(str)){
            if (one == null){
                one = new User();
                one.setPhone(map.get("phone"));
                userService.save(one);
            }
            session.setAttribute("user",one.getId());

            redisService.delete(phone);
            return R.success(one);
        }
        return R.error("登录失败");
    }
}

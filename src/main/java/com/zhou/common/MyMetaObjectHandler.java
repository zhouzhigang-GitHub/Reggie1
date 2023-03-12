package com.zhou.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author 周志刚
 * @Date 2022/8/14 11:47
 * @PackageName: com.zhou.common
 * @ClassName: MyMateObjectHandler
 * @Description: 元数据自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler{
    /**
     *         employee.setCreateTime(LocalDateTime.now());
     *         employee.setUpdateTime(LocalDateTime.now());
     *         Long userId = (Long) request.getSession().getAttribute("employee");
     *         employee.setCreateUser(userId);
     *         employee.setUpdateUser(userId);
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getId());
        metaObject.setValue("updateUser", BaseContext.getId());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getId());
    }
}

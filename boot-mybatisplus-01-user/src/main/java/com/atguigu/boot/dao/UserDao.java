package com.atguigu.boot.dao;

import com.atguigu.boot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 自定义Mapper（Dao）接口
 * 1、实现BaseMapper
 * 2、指定泛型（实体对象）实体类
 *
 * BaseMapper是MP框架中的的对象，定义了18个操作方法来完成单表简单的CRUD的操作
 * MyBatis本身用的就是一种动态代理的方式来实现是接口对象的创建工作
 * 不需要写任何内容，也不需要写SQL映射文件
 */
public interface UserDao extends BaseMapper<User> {
}

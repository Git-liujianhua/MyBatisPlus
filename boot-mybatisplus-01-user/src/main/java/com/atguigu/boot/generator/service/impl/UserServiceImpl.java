package com.atguigu.boot.generator.service.impl;

import com.atguigu.boot.generator.pojo.User;
import com.atguigu.boot.generator.dao.UserDao;
import com.atguigu.boot.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 超级无敌最帅刘建华
 * @since 2021-12-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

package com.atguigu.boot.mycode.service.impl;

import com.atguigu.boot.mycode.entity.Student;
import com.atguigu.boot.mycode.mapper.StudentMapper;
import com.atguigu.boot.mycode.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 超级无敌最帅刘建华
 * @since 2021-12-31
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}

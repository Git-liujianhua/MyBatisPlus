package com.atguigu.boot.dao;

import com.atguigu.boot.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface StudentDao extends BaseMapper<Student> {

    int insertStudent(Student student);

    Student selectStudentById(Integer id);

    List<Student> selectStudentByName(String name);
}

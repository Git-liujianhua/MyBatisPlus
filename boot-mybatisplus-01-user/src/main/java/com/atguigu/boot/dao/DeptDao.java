package com.atguigu.boot.dao;

import com.atguigu.boot.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * DeptDao是不需要我们去调用的，MP需要使用DeptDao获取到数据库表的信息
 * 如果不定义DaptDao，MyBatisPlus会报错，找不到表的定义的信息
 */
public interface DeptDao extends BaseMapper<Dept> {
}

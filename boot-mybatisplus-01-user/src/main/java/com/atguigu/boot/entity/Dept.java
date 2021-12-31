package com.atguigu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 使用AR，要求实体类需要继承MP中的Model
 * Model中提供了对数据库的CRUD操作
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dept extends Model<Dept> {
    //定义属性，属性名和表的列名一样
    //设置主键id，type = IdType.AUTO主键的生成策略，自动增长
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String mobile;
    private Integer manager;
}

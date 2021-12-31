package com.atguigu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

//实体类
//@NoArgsConstructor      //无参构造器
//@AllArgsConstructor   //全参构造器
@Data                   //get和set方法
@ToString               //tostring方法
//@EqualsAndHashCode
public class User{
    //定义属性：属性名和表中的列名是一样的
    /**
     * 指定主键的方式
     * 两个属性：
     * 1、value：来指定主键字段的名称（如果是id，可以不用写value这个属性了）
     * 2、type：表示主键字段的类型（主键的值如何生成，IdType.AUTO 表示自动增长）
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
}

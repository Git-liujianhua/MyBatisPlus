package com.atguigu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Customer {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;
    private String custName;
    private Integer custAge;
    private String custEmail;
    /**
     * 1、查询：该字段在插入时可以插入任何值（字符串）
     * 2、修改：修改时该字段只做条件不作修改，故，该字段无法通过sql语句进行修改
     * 3、删除：删除时不是执行delete的sql语句而是执行update语句修改cust_flag字段设置delval的值，同时cust_flag字段也会作条件，默认值未value定义的值，也就是所说的逻辑删除，非物理删除操作
     * 4、查询：查询时，标有注解的TableLogic的custFlag字段会作为查询条件去查询
     * 如果不设置value为0（未删除），delval为1（已删除）
     */
    @TableLogic(value = "未删除",delval = "已删除")
    private String custFlag;
}

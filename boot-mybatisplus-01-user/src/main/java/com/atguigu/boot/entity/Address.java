package com.atguigu.boot.entity;

import ch.qos.logback.core.joran.action.IADataForComplexProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * @TableName(value = "数据库表名")
 * 位置：在类定义的上面
 */
@Data
@TableName(value = "user_address")
public class Address extends Model<Address> {

    //指定主键
    //@TableLogic
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    private String city;
    private String street;
    private String zipcode;
}

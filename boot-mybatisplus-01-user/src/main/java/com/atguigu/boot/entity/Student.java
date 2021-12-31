package com.atguigu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

@Data
public class Student {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    //如果使用自定义mapper的映射文件，自定义的sql语句，该注解不生效
//    @TableLogic(value = "0",delval = "1")
    private Integer status;
}

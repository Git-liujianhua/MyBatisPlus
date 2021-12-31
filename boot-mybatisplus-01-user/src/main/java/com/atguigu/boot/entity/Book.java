package com.atguigu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {

    @TableId(value = "book_id",type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * @TableField(value = "数据库字段名")：指定属性和列名的对应关系
     * 属性：value = "指定列名"
     */
    @TableField(value = "book_name")
    private String name;
    @TableField(value = "book_writer")
    private String writer;
    @TableField(value = "book_price")
    private BigDecimal price;
}

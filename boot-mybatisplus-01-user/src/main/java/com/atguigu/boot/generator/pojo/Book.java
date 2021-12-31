package com.atguigu.boot.generator.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 超级无敌最帅刘建华
 * @since 2021-12-30
 */
@Getter
@Setter
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "book_id", type = IdType.ASSIGN_UUID)
    private String bookId;

    private String bookName;

    private String bookWriter;

    private BigDecimal bookPrice;


}

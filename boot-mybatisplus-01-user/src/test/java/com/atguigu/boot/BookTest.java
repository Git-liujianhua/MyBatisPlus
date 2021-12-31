package com.atguigu.boot;

import com.atguigu.boot.dao.BookDao;
import com.atguigu.boot.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class BookTest {

    @Autowired
    BookDao bookDao;

    @Test
    void insertTest(){
        /**
         * ==>  Preparing: INSERT INTO book ( id, book_name, book_writer, book_price ) VALUES ( ?, ?, ?, ? )
         * ==> Parameters: 2e5d23489b641a4a3835291234c1ff2c(String), 水浒传(String), 施耐庵(String), 99999(BigDecimal)
         * <==    Updates: 1
         */
        Book book = new Book();
        book.setName("水浒传");
        book.setWriter("施耐庵");
        book.setPrice(new BigDecimal(99999));
        int insert = bookDao.insert(book);
        System.out.println(insert);
    }
}

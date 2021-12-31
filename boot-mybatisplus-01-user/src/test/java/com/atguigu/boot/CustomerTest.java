package com.atguigu.boot;

import com.atguigu.boot.dao.BookDao;
import com.atguigu.boot.dao.CustomerDao;
import com.atguigu.boot.entity.Book;
import com.atguigu.boot.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.sf.jsqlparser.parser.feature.Feature.insert;

@SpringBootTest
public class CustomerTest {

    @Autowired
    CustomerDao customerDao;


    @Test
    void insertTest(){
        /**
         ==>  Preparing: INSERT INTO customer ( id, cust_name, cust_age, cust_email, cust_flag ) VALUES ( ?, ?, ?, ?, ? )
         ==> Parameters: 1471000076863188993(String), 小明(String), 22(Integer), xiaoming@qq.com(String), 未删除(String)
         <==    Updates: 1
         */
        Customer customer = new Customer();
        customer.setCustName("小明");
        customer.setCustAge(22);
        customer.setCustEmail("xiaoming@qq.com");
        //该字段在插入时可以插入任何值（字符串）
        customer.setCustFlag("未删除");
        int insert = customerDao.insert(customer);
        System.out.println(insert);
    }

    @Test
    void updateTest(){
        /**
         * ==>  Preparing: UPDATE customer SET cust_name=?, cust_age=?, cust_email=? WHERE id=? AND cust_flag='未删除'
         * ==> Parameters: 小明(String), 22(Integer), xiaoming@qq.com(String), 1470994693608304642(String)
         * <==    Updates: 1
         */
        Customer customer = new Customer();
        customer.setCustName("小明");
        customer.setCustAge(22);
        customer.setCustEmail("xiaoming@qq.com");
        //修改时该字段只做条件不作修改，故，该字段无法通过sql语句进行修改
        customer.setCustFlag("3");
        customer.setId("1470994693608304642");
        int i = customerDao.updateById(customer);
        System.out.println(i);
    }

    @Test
    void deleteTest(){
        //删除时不是执行delete的sql语句而是执行update语句修改cust_flag字段设置delval的值，同时cust_flag字段也会作条件，默认值未value定义的值
        int i = customerDao.deleteById("1470994693608304642");
        /**
         * ==>  Preparing: UPDATE customer SET cust_flag='已删除' WHERE id=? AND cust_flag='未删除'
         * ==> Parameters: 1470994693608304642(String)
         * <==    Updates: 1
         */
        System.out.println(i);
    }


    @Test
    void selectTest(){
        //查询时，标有注解的TableLogic的custFlag字段会作为查询条件去查询
        Map<String,Object> map = new HashMap<>();
        map.put("cust_name","小明");
        List<Customer> customers = customerDao.selectByMap(map);
        /**
         * ==>  Preparing: SELECT id,cust_name,cust_age,cust_email,cust_flag FROM customer WHERE cust_name = ? AND cust_flag='未删除'
         * ==> Parameters: 小明(String)
         * <==    Columns: id, cust_name, cust_age, cust_email, cust_flag
         * <==        Row: 1470934435594563586, 小明, 22, xiaoming@qq.com, 未删除
         * <==        Row: 1470938817304301569, 小明, 22, xiaoming@qq.com, 未删除
         * <==        Row: 1470992075209166849, 小明, 22, xiaoming@qq.com, 未删除
         * <==        Row: 1470994192040734722, 小明, 22, xiaoming@qq.com, 未删除
         * <==        Row: 1471000076863188993, 小明, 22, xiaoming@qq.com, 未删除
         * <==      Total: 5
         * Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7219ac49]
         * Customer(id=1470934435594563586, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         * Customer(id=1470938817304301569, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         * Customer(id=1470992075209166849, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         * Customer(id=1470994192040734722, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         * Customer(id=1471000076863188993, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         */
        customers.forEach(customer -> {
            System.out.println(customer);
        });
        Long aLong = customerDao.selectCount(null);
        /**
         * ==>  Preparing: SELECT COUNT( * ) FROM customer WHERE cust_flag='未删除'
         * ==> Parameters:
         * <==    Columns: COUNT( * )
         * <==        Row: 5
         * <==      Total: 1
         */
        System.out.println(aLong);
        List<Customer> customers1 = customerDao.selectList(null);
        /**
         * ==>  Preparing: SELECT id,cust_name,cust_age,cust_email,cust_flag FROM customer WHERE cust_flag='未删除'
         * ==> Parameters:
         * <==    Columns: id, cust_name, cust_age, cust_email, cust_flag
         * <==        Row: 1470934435594563586, 小明, 22, xiaoming@qq.com, 未删除
         * <==        Row: 1470938817304301569, 小明, 22, xiaoming@qq.com, 未删除
         * <==        Row: 1470992075209166849, 小明, 22, xiaoming@qq.com, 未删除
         * <==        Row: 1470994192040734722, 小明, 22, xiaoming@qq.com, 未删除
         * <==        Row: 1471000076863188993, 小明, 22, xiaoming@qq.com, 未删除
         * <==      Total: 5
         * Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@433d9680]
         * Customer(id=1470934435594563586, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         * Customer(id=1470938817304301569, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         * Customer(id=1470992075209166849, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         * Customer(id=1470994192040734722, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         * Customer(id=1471000076863188993, custName=小明, custAge=22, custEmail=xiaoming@qq.com, custFlag=未删除)
         */
        customers1.forEach(customer1 -> {
            System.out.println(customer1);
        });
    }
}

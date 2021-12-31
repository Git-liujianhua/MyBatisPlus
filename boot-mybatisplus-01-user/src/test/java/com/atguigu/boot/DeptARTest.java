package com.atguigu.boot;

import com.atguigu.boot.entity.Dept;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * ar的CRUD操作出了查询操作其他返回值都是boolean类型的
 */
@SpringBootTest
public class DeptARTest {

    /**
     * AR自己本身可以把对象自己来完成一个持久化的操作是由实体对象低级的insert来完成一个数据库的操作，
     * 实际上dept.insert的实现调用的是实体类继承的父类Model中的insert方法，在Model中的insert方法是调用的sqlSession.insert操作（MyBatis的核心对象）
     */
    /**
     * AR值insert
     * INSERT INTO dept ( name, mobile, manager ) VALUES ( ?, ?, ? )
     */
    @Test
    void insertARTest(){
        //定义dept实体
        Dept dept = new Dept();
        dept.setName("产品部");
        dept.setMobile("010-22222222");
        dept.setManager(2);
        //调用实体对象自己的方法，完成对象自身到数据库的添加操作
        boolean insert = dept.insert();
        System.out.println("AR INSERT RESULT：" + insert);
    }

    /**
     * AR值update
     * UPDATE dept SET name=?, mobile=?, manager=? WHERE id=?
     */
    @Test
    void updateARTest(){
        //定义dept实体
        Dept dept = new Dept();
        dept.setName("市场部");
        dept.setMobile("010-88888888");
        dept.setManager(3);
        dept.setId(1);
        //调用实体对象自己的方法，完成对象自身到数据库的修改操作
        boolean update = dept.updateById();//根据主键id更新记录
        System.out.println("AR UPDATE RESULT：" + update);
    }

    /**
     * AR值update控制修改字段
     *UPDATE dept SET mobile=? WHERE id=?
     */
    @Test
    void updateARTest2(){
        //定义dept实体
        Dept dept = new Dept();
        dept.setMobile("010-22222222");
        dept.setId(1);
        //调用实体对象自己的方法，完成对象自身到数据库的修改操作、
        /**
         * name、manager是没有修改的
         * 根据主键id更新记录
         * null的属性值不做更新处理，在update中没有null的字段
         */
        boolean update = dept.updateById();//使用dept实体主键的值，作为where id = 1
        System.out.println("AR UPDATE RESULT：" + update);
    }

    /**
     * AR值delete直接传参的方式删除
     * DELETE FROM dept WHERE id=?
     */
    @Test
    void deleteARTest(){
        //定义dept实体
        Dept dept = new Dept();
        //调用实体对象自己的方法，完成对象自身到数据库的删除操作、
        /**
         * name、manager是没有修改的
         * 根据主键id更新记录
         * null的属性值不做更新处理，在update中没有null的字段
         */
        boolean delete = dept.deleteById(1);
        System.out.println("AR DELETE RESULT：" + delete);
    }

    /**
     * AR值delete给实体类对象主键id字段赋值的方式删除
     */
    @Test
    void deleteARTest2(){
        //定义dept实体
        Dept dept = new Dept();
        dept.setId(1);
        //调用实体对象自己的方法，完成对象自身到数据库的删除操作、
        /**
         * name、manager是没有修改的
         * 根据主键id更新记录
         * null的属性值不做更新处理，在update中没有null的字段
         */
        //使用dept实体自身的主键id的值作为删除条件的，作为where id = 2【如果实体对象没有赋值，调用deleteById方法也没有传入参数值会报错，主键为null】
        boolean delete = dept.deleteById();
        System.out.println("AR DELETE RESULT：" + delete);
    }

    /**
     * selectById()带参数的查询操作
     * 参数：Serializable id是一个可序列化的参数值【主键】
     * 如果参数给一个null值的话查询出来的则也是null没有该条数据
     * 1、主键有记录返回实体对象
     * 2、主键没有记录，返回的是null
     */
    @Test
    void selectARTest(){
        Dept dept = new Dept();
        Dept dept1 = dept.selectById(null);
        System.out.println("AR SELECT_BY_ID RESULT：" + dept1);
    }

    /**
     * 根据对象自己的id属性查询
     * 1、按实体主键能查询出数据，返回对象
     * 2、按实体的主键不能查出数据，是null，不报错，所以在作业务逻辑的时候要判空
     */
    @Test
    void selectARTest2(){
        Dept dept = new Dept();
        //设置主键的值
        dept.setId(2);
        Dept dept1 = dept.selectById();
        System.out.println("AR SELECT_BY_ID RESULT：" + dept1);
    }


    /**
     * selectAll
     */
    @Test
    void selectARTest3(){
        Dept dept = new Dept();
        List<Dept> depts = dept.selectAll();
        depts.forEach( dept1 -> {
            System.out.println("AR SELECT_BY_ID RESULT：" + dept1);
        });
    }

}

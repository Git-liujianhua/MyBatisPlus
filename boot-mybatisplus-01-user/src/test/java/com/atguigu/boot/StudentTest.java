package com.atguigu.boot;

import com.atguigu.boot.dao.StudentDao;
import com.atguigu.boot.entity.Student;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
@Log4j2
public class StudentTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    void insertTest(){
        Student student = new Student();
        student.setName("张三");
        student.setAge(20);
        student.setEmail("zhangsan@qq.com");
        student.setStatus(0);
        int i = studentDao.insertStudent(student);
        System.out.println(i);
    }

    @Test
    void selectByIdTest(){
        Student student = studentDao.selectStudentById(1);
        System.out.println(student);
    }

    @Test
    void selectByNameTest(){
        List<Student> students = studentDao.selectStudentByName("张三");
        students.forEach(student -> {System.out.println(student);});
    }


    private QueryWrapper<Student> qw = new QueryWrapper<>();

    private void print(QueryWrapper queryWrapper){
        List<Student> students = studentDao.selectList(queryWrapper);
        students.forEach(stu -> {
            log.info("查询的结果:{}",stu);
        });
    }
    /**
     * 一、
     * select查询语句的[allEq()]方法:将查询条件封装map查询，与（and）关系
     * 方法：default <V> Children allEq(Map<R, V> params) {}
     * 参数：Map<R, V> params
     * 个别参数说明:
     * params : key为数据库字段名,value为字段值
     * null2IsNull : 为true则在map的value为null时调用 isNull 方法,为false时则忽略value为null的
     * 个别参数说明:
     * filter : 过滤函数,是否允许字段传入比对条件中
     * params 与 null2IsNull : 同上
     */
    @Test
    void selectAllEqTest(){
        //组装条件
        Map<String,Object> param = new HashMap<>();
        //map<key,value>key是数据库表的列名，value：查询的字段值
        param.put("name","张三");
        param.put("age",22);
        param.put("status",1);
        qw.allEq(param);
        /**
         *  调用MyBatisPlus的自己的查询方法
         *  用map去封装条件，吧map中的所有值按照与的关系都加入到where条件后
         *  FROM student WHERE (name = ? AND age = ? AND status = ?)
         */
        print(qw);
    }

    /**
     * 二、
     * select查询语句的[allEq()]方法：将查询条件封装map查询，与（and）关系，且第二个参数是boolean
     * 方法：    default <V> Children allEq(Map<R, V> params, boolean null2IsNull) {
     * 参数：Map<R, V> params, boolean null2IsNull
     * 1、Map对象中有key的值是null
     *    使用：qw.allEq(param,true);
     *    查询结果的sql语句：SELECT id,name,age,email,status FROM student WHERE (name = ? AND age IS NULL)
     *
     * 2、Map对象中有key的值是null
     *   使用：qw.allEq(param,false);
     *   查询结果的sql语句：SELECT id,name,age,email,status FROM student WHERE (name = ? AND age IS NULL)
     *  也就是说第二个值是boolean类型是true和false是要判断要不要使用key的值为null去作sql语句的查询的where条件
     *  true：使用值为null的key，where条件加入字段 is null
     *  false：忽略值为null的key，不做查询的where条件
     */
    @Test
    void selectAllEqTest2(){
        //组装条件
        Map<String,Object> param = new HashMap<>();
        //map<key,value>key是数据库表的列名，value：查询的字段值
        param.put("name","张三");
        //age是一个null
        param.put("age",null);
        //allEq的第二个参数是true
        qw.allEq(param,true);
        /**
         *  调用MyBatisPlus的自己的查询方法
         *  用map去封装条件，吧map中的所有值按照与的关系都加入到where条件后
         *  1、qw.allEq(param,false);的时候FROM student WHERE (name = ?)
         *  Parameters: 张三(String)
         *  2、qw.allEq(param,true);的时候FROM student WHERE (name = ? AND age IS NULL)
         *  张三(String)
         */
        print(qw);
    }

    /**
     * 三、
     * select查询语句的[eq()]方法：查询单个列（字段）中【等于】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children eq(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,等于的值】
     */
    @Test
    void selectEqTest(){
        qw.eq("name","李四");
        //qw.eq("age",20);多个条件查询
        /**
         * FROM student WHERE (name = ?)
         * Parameters: 李四(String)
         */
        print(qw);
    }

    /**
     * 四、
     * select查询语句的[ne()]方法：查询单个列（字段）中【不等于】指定的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children ne(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,不等于的值】
     */
    @Test
    void selectNeTest(){
        qw.ne("name","张三");
        /**
         * FROM student WHERE (name <> ?)
         * 张三(String)
         */
        print(qw);
    }

    /**
     * 五、
     * select查询语句的[gt()]方法：查询单个列（字段）中【大于】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children gt(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,大于的值】
     */
    @Test
    void selectGtTest(){
        qw.gt("age",30);
        /**
         * FROM student WHERE (age > ?)
         * Parameters: 30(Integer)
         */
        print(qw);
    }

    /**
     * 六、
     * select查询语句的[ge()]方法：查询单个列（字段）中【大于等于】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children ge(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,大于等于的值】
     */
    @Test
    void selectGeTest(){
        qw.ge("age",31);
        /**
         *  FROM student WHERE (age >= ?)
         *  Parameters: 31(Integer)
         */
        print(qw);
    }

    /**
     * 七、
     * select查询语句的[lt()]方法：查询单个列（字段）中【小与】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children lt(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,小于的值】
     */
    @Test
    void selectLtTest(){
        qw.lt("age",31);
        /**
         *  FROM student WHERE (age < ?)
         *  Parameters: 31(Integer)
         */
        print(qw);
    }

    /**
     * 八、
     * select查询语句的[le()]方法：查询单个列（字段）中【小与等于】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children le(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,小于等于的值】
     */
    @Test
    void selectLeTest(){
        qw.le("age",31);
        /**
         *  FROM student WHERE (age <= ?)
         *  Parameters: 31(Integer)
         */
        print(qw);
    }

    /**
     * 九、
     * select查询语句的[between()]方法：查询单个列（字段）中的值【大与等于且小与等于这个范围】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children between(R column, Object val1, Object val2) {}
     * 参数：R column, Object val1, Object val2 【数据库表的列名,开始值，结束值】
     */
    @Test
    void selectBetweenTest(){
        qw.between("age",29,40);
        /**
         *  FROM student WHERE (age BETWEEN ? AND ?)
         *  Parameters:  29(Integer), 40(Integer)
         *  where age >= 29 and age <= 40
         */
        print(qw);
    }

    /**
     * 十、
     * select查询语句的[notBetween()]方法：查询单个列（字段）中的值【不在大与等于且小与等于这个范围（在大于或小与该范围的值）】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children notBetween(R column, Object val1, Object val2) {}
     * 参数：R column, Object val1, Object val2 【数据库表的列名,开始值，结束值】
     */
    @Test
    void selectNotBetweenTest(){
        qw.notBetween("age",29,40);
        /**
         *  FROM student WHERE (age NOT BETWEEN ? AND ?)
         *  Parameters:  29(Integer), 40(Integer)
         *  where age > 29 or age < 40
         */
        print(qw);
    }

    /**
     * 十一、
     * select查询语句的[like()]方法：查询单个列（字段）中的值【包含】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children like(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,字段值中包含的值】
     */
    @Test
    void selectLikeTest(){
        qw.like("name","张");
        /**
         *  FROM student WHERE (name LIKE ?)
         *  Parameters: %张%(String)
         */
        print(qw);
    }

    /**
     * 十二、
     * select查询语句的[notLike()]方法：查询单个列（字段）中的值【不包含】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children notLike(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,字段值中不包含的值】
     */
    @Test
    void selectNotLikeTest(){
        qw.notLike("name","张");
        /**
         *  FROM student WHERE (name NOT LIKE ?)
         *  Parameters: %张%(String)
         */
        print(qw);
    }

    /**
     * 十三、
     * select查询语句的[likeLeft()]方法：查询单个列（字段）中的值【以某个指定值结尾的】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children likeLeft(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,字段值中以指定值结尾的值】
     */
    @Test
    void selectLikeLeftTest(){
        qw.likeLeft("name","张");
        /**
         *  FROM student WHERE (name LIKE ?)
         *  Parameters: %张(String)
         */
        print(qw);
    }

    /**
     * 十四、
     * select查询语句的[likeRight()]方法：查询单个列（字段）中的值【以某个指定值开头的】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children likeRight(R column, Object val) {}
     * 参数：R column, Object val 【数据库表的列名,字段值中以指定值结尾的值】
     */
    @Test
    void selectLikeRightTest(){
        qw.likeRight("name","张");
        /**
         *  FROM student WHERE (name LIKE ?)
         *  Parameters: 张%(String)
         */
        print(qw);
    }

    /**
     * 十五、
     * select查询语句的[isNull()]方法：查询单个列（字段）中的值【为空】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children isNull(R column) {}
     * 参数：R column 【数据库表的列名,字段值为空的】
     */
    @Test
    void selectIsNullTest(){
        qw.isNull("email");
        /**
         *  FROM student WHERE (email IS NULL)
         *  Parameters:
         *  Columns: id, name, age, email, status
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         * <==      Total: 2
         */
        print(qw);
    }

    /**
     * 十六、
     * select查询语句的[isNotNull()]方法：查询单个列（字段）中的值【不为空】的值，也可以查询多个字段，与（and）的关系
     * 方法：default Children isNotNull(R column) {}
     * 参数：R column 【数据库表的列名,字段值为空的】
     */
    @Test
    void selectIsNotNullTest(){
        qw.isNotNull("email");
        /**
         *  FROM student WHERE (email IS NOT NULL)
         *  Parameters:
         *  Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==      Total: 4
         */
        print(qw);
    }

    /**
     * 十七、
     * select查询语句的[in()]方法：查询单个列（字段）中的值【后面的值列表，在列表中都是符合条件】的值，也可以查询多个字段，与（and）的关系
     * in(列名，多个值的列表)
     * 注意：后面的直接列表不要过多，因为如果过多会对查询查询的性能又比较大的影响，而且在有些数据库中，where in的子句对参数的值的数量是有限制的，并不是无限的
     * 所以还是建议封装一个集合去使用
     * 方法：default Children in(R column, Object... values) {}
     * 参数：R column, Object... values
     */
    @Test
    void selectInTest(){
        qw.in("name","张三","李四");
        /**
         * FROM student WHERE (name IN (?,?))
         * Parameters: 张三(String), 李四(String)
         * Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         */
        print(qw);
    }

    /**
     * 十七、
     * select查询语句的[in()]方法：查询单个列（字段）中的值【后面的值列表，在列表中都是符合条件】的值，也可以查询多个字段，与（and）的关系
     * in(列名，多个值的列表)
     * 注意：后面的直接列表不要过多，因为如果过多会对查询查询的性能又比较大的影响，而且在有些数据库中，where in的子句对参数的值的数量是有限制的，并不是无限的
     * 所以还是建议封装一个集合去使用
     * 方法：default Children in(R column, Collection<?> coll) {}
     * 参数：R column, Collection<?> coll
     */
    @Test
    void selectInTest2(){
        qw.in("name", Stream.of("张三","李四").collect(Collectors.toList()));
        /**
         * FROM student WHERE (name IN (?,?))
         * Parameters: 张三(String), 李四(String)
         * Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         */
        print(qw);
    }


    /**
     * 十八、
     * select查询语句的[notIn()]方法：查询单个列（字段）中的值【后面的值列表，不在列表中都是不符合条件】的值，也可以查询多个字段，与（and）的关系
     * 注意：后面的直接列表不要过多，因为如果过多会对查询查询的性能又比较大的影响，而且在有些数据库中，where in的子句对参数的值的数量是有限制的，并不是无限的
     *      * 所以还是建议封装一个集合去使用
     * 方法：default Children notIn(R column, Object... value) {}
     * 参数：R column, Object... value
     */
    @Test
    void selectNotInTest(){
        qw.notIn("name","张三","李四");
        /**
         * FROM student WHERE (name NOT IN (?,?))
         * Parameters: 张三(String), 李四(String)
         * Columns: id, name, age, email, status
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         */
        print(qw);
    }

    /**
     * 十八、
     * select查询语句的[notIn()]方法：查询单个列（字段）中的值【后面的值列表，不在列表中都是不符合条件】的值，也可以查询多个字段，与（and）的关系
     * 注意：后面的直接列表不要过多，因为如果过多会对查询查询的性能又比较大的影响，而且在有些数据库中，where in的子句对参数的值的数量是有限制的，并不是无限的
     *      * 所以还是建议封装一个集合去使用
     * 方法：default Children notIn(R column, Collection<?> coll) {}
     * 参数：R column, Collection<?> coll
     */
    @Test
    void selectNotInTest2(){
        qw.notIn("name",Stream.of("张三","李四").collect(Collectors.toList()));
        /**
         * FROM student WHERE (name NOT IN (?,?))
         * Parameters: 张三(String), 李四(String)
         * Columns: id, name, age, email, status
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         */
        print(qw);
    }

    /**
     * 十九、
     * select查询语句的[inSql()]方法：查询单个列（字段）中的值【常用来做子查询，类似in()】的值,sql语句where条件后边跟的是sql查询语句，也可以查询多个字段，与（and）的关系
     * 方法：default Children inSql(R column, String inValue) {}
     * 参数：R column, String inValue
     */
    @Test
    void selectInSqlTest(){
        qw.inSql("age","SELECT age FROM student WHERE (age > 30)");
        /**
         * FROM student WHERE (age IN (SELECT age FROM student WHERE (age > 30)))
         * Parameters:
         * Columns: id, name, age, email, status
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         */
        print(qw);
    }

    /**
     * 二十、
     * select查询语句的[notInSql()]方法：查询单个列（字段）中的值【常用来做子查询，类似notIn()】的值,sql语句where条件后边跟的是sql查询语句，也可以查询多个字段，与（and）的关系
     * 方法：default Children notInSql(R column, String inValue) {}
     * 参数：R column, String inValue
     */
    @Test
    void selectNotInSqlTest(){
        qw.notInSql("age","SELECT age FROM student WHERE (age > 30)");
        /**
         *  FROM student WHERE (age NOT IN (SELECT age FROM student WHERE (age > 30)))
         * Parameters:
         * Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 5, 周名明, 24, null, 2
         */
        print(qw);
    }


    /**
     * 二十一、聚合函数的支持
     * select查询语句的[分组groupBy()]方法：查询单个列（字段）中的值【基于多个字段分组】的值，可以基于多个字段来进行分组，也可以查询多个字段，与（and）的关系
     * 在分组的时候在查询数据库字段的时候就不能全部查询了，需要用到QueryWrapper的的一个select()方法，选择要查询（输出的字段），相当于select查询语句中select的字段名
     * 使用groupBy条件查询时，select的查询字段也需要跟着去变化，不能使用默认的查询了，默认是查询全部的字段，所以需要使用MyBatisPlus的select方法去进行一个字段列的声明
     *  select方法可以i使用链式的方式去使用
     */
    @Test
    void selectGroupByTest(){
        qw.select("name,count(*)").groupBy("name");
//        qw.groupBy("name");
        /**
         * SELECT name,count(*) FROM student GROUP BY name
         * Parameters:
         */
        print(qw);
    }


    /**
     * 二十二、
     * orderByAsc 按字段升序排序，可以指定多个字段同时都按照升序去排序
     * orderByDesc 按字段降序排序，可以指定多个字段同时都按照降序去排序
     * orderBy 每个字段指定排序的方向
     * 如果使用orderBy想做多列排序：
     *      1、该方法返回的是自己，相当于返回来还是查询条件对象可以使用像链式的方式去使用
     *      2、可以实现多条件组合的排序，可以指定不同字段，不同的排序规则
     * select查询语句的[orderByAsc()]方法：根据指定的某个字段进行【升序】的排序，可以基于多个字段来进行升序，与（and）的关系
     * 方法：default Children orderByAsc(R column) {}
     * 参数：R column
     */
    @Test
    void selectOrderByAscTest(){
        qw.orderByAsc("name");
        /**
         * FROM student ORDER BY name ASC
         *  Parameters:
         *  Columns: id, name, age, email, status
         * <==        Row: 6, 冯亮, 32, null, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         */
        print(qw);
    }

    /**
     * 二十二、
     * orderByAsc 按字段升序排序，可以指定多个字段同时都按照升序去排序
     * orderByDesc 按字段降序排序，可以指定多个字段同时都按照降序去排序
     * orderBy 每个字段指定排序的方向
     * 如果使用orderBy想做多列排序：
     *      1、该方法返回的是自己，相当于返回来还是查询条件对象可以使用像链式的方式去使用
     *      2、可以实现多条件组合的排序，可以指定不同字段，不同的排序规则
     * select查询语句的[orderByAsc()]方法：根据指定的某个字段进行【升序】的排序，可以基于多个字段来进行升序，与（and）的关系
     * 方法：default Children orderByAsc(List<R> columns) {},也可以传入list集合
     * 参数：List<R> columns
     */
    @Test
    void selectOrderByAscTest2(){
        qw.orderByAsc(Arrays.asList("name","age"));
        /**
         * FROM student ORDER BY name ASC,age ASC
         *  Parameters:
         *  Columns: id, name, age, email, status
         * <==        Row: 6, 冯亮, 32, null, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         */
        print(qw);
    }

    /**
     * 二十三、
     * orderByDesc 按字段降序排序，可以指定多个字段同时都按照降序去排序
     * select查询语句的[orderByDesc()]方法：根据指定的某个字段进行【降序】的排序，可以基于多个字段来进行降序，与（and）的关系
     * 方法：default Children orderByDesc(R column) {}
     * 参数：R column
     */
    @Test
    void selectOrderByDescTest(){
        qw.orderByDesc("age");
        /**
         * FROM student ORDER BY age DESC
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         */
        print(qw);
    }

    /**
     * 二十三
     * orderByDesc 按字段降序排序，可以指定多个字段同时都按照降序去排序
     * select查询语句的[orderByDesc()]方法：根据指定的某个字段进行【降序】的排序，可以基于多个字段来进行降序，与（and）的关系
     * 方法：default Children orderByDesc(List<R> columns) {},参数也可以传入list集合
     * 参数：List<R> columns
     */
    @Test
    void selectOrderByDescTest2(){
        qw.orderByDesc(Stream.of("name","age").collect(Collectors.toList()));
        /**
         * FROM student ORDER BY name DESC,age DESC
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         */
        print(qw);
    }

    /**
     * 二十四、
     * orderBy 每个字段指定排序的方向
     * select查询语句的[orderBy()]方法：根据指定的某个字段进行【自定义排序的方式（升序和降序可混合使用）】的排序，可以基于多个字段来进行自定义排序，与（and）的关系
     * 方法：public Children orderBy(boolean condition, boolean isAsc, R column) {}
     * 参数：boolean condition, boolean isAsc, R column
     * 如果使用orderBy想做多列排序：
     *      *      1、该方法返回的是自己，相当于返回来还是查询条件对象可以使用像链式的方式去使用
     *      *      2、可以实现多条件组合的排序，可以指定不同字段，不同的排序规则
     */
    @Test
    void selectOrderByTest(){
        qw.orderBy(true,true,"name");
        qw.orderBy(true,false,"age");
        qw.orderBy(false,true,"id");
        /**
         * FROM student ORDER BY name ASC,age DESC
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 6, 冯亮, 32, null, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         */
        print(qw);
    }

    /**
     * 二十四、
     * orderBy 每个字段指定排序的方向
     * select查询语句的[orderBy()]方法：根据指定的某个字段进行【自定义排序的方式（升序和降序可混合使用）】的排序，可以基于多个字段来进行自定义排序，与（and）的关系
     * 方法：public Children orderBy(boolean condition, boolean isAsc, List<R> columns) {}
     * 参数：boolean condition, boolean isAsc, List<R> columns
     * 如果使用orderBy想做多列排序：
     *      *      1、该方法返回的是自己，相当于返回来还是查询条件对象可以使用像链式的方式去使用
     *      *      2、可以实现多条件组合的排序，可以指定不同字段，不同的排序规则
     */
    @Test
    void selectOrderByTest2(){
        qw.orderBy(true,true,Arrays.asList("name","email"))
                .orderBy(true,false,Arrays.asList("age","id"))
                .orderBy(false,true,Arrays.asList("status"));
        /**
         * FROM student ORDER BY name ASC,email ASC,age DESC,id DESC
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 6, 冯亮, 32, null, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         */
        print(qw);
    }

    /**
     * 二十五、
     * or：连接条件用or，表示或的意思，默认是and
     * and：连接条件用and，表示且的意思
     * 多条件多字段的联合查询
     * myBatisPlus的所有查询条件的方法，返回的都是对象本身QueryWrapper，所以可以使用链式的方式来吧多个调节联合在一起
     * 多条件之间可以用QueryWrapper链式的操作来进行封装多个条件
     * select查询语句的[or()]方法：where后边的多个田间or连接【或】
     * 方法：default Children or() {}
     * 参数：boolean condition【可有可无】
     */
    @Test
    void selectOrTest(){
        qw.eq("name","张三")
                .or()
                .gt("age","22");
        /**
         * FROM student WHERE (name = ? OR age > ?)
         * Parameters: 张三(String), 22(String)
         * Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         */
        print(qw);
    }

    /**
     * 二十五、
     * or：连接条件用or，表示或的意思，默认是and
     * and：连接条件用and，表示且的意思
     * 多条件多字段的联合查询
     * myBatisPlus的所有查询条件的方法，返回的都是对象本身QueryWrapper，所以可以使用链式的方式来吧多个调节联合在一起
     * 多条件之间可以用QueryWrapper链式的操作来进行封装多个条件
     * select查询语句的[and()(AND 嵌套)]方法：where后边的多个田间and连接【与】
     * 方法：default Children and(Consumer<Param> consumer) {}
     * 参数：Consumer<Param> consumer【函数式接口】 Consumer<QueryWrapper<Student>> consumer
     *
     * 重点记忆
     * .and(i -> i.eq("status",1).isNotNull("email"))
     * i:代表的是连接上一层的QueryWrapper对象的查询的where条件，然后后边紧跟的一部分查询条件实现嵌套查询条件去进行查询
     */
    @Test
    void selectAndTest(){
        qw.eq("name","张三")
                .and(i -> i.eq("status",1).isNotNull("email"))
                .gt("age","22");
        /**
         * FROM student WHERE (name = ? AND (status = ? AND email IS NOT NULL) AND age > ?)
         * Parameters: 张三(String), 1(Integer), 22(String)
         */
        print(qw);
    }

    /**
     * 二十五、or和and混合使用
     * or：连接条件用or，表示或的意思，默认是and
     * and：连接条件用and，表示且的意思
     * 多条件多字段的联合查询
     * myBatisPlus的所有查询条件的方法，返回的都是对象本身QueryWrapper，所以可以使用链式的方式来吧多个调节联合在一起
     * 多条件之间可以用QueryWrapper链式的操作来进行封装多个条件
     * select查询语句的[and()(AND 嵌套)]方法：where后边的多个田间and连接【与】
     * 方法：default Children and(Consumer<Param> consumer) {}
     * 参数：Consumer<Param> consumer【函数式接口】 Consumer<QueryWrapper<Student>> consumer
     *
     * 重点记忆
     * .and(i -> i.eq("status",1).isNotNull("email"))
     * i:代表的是连接上一层的QueryWrapper对象的查询的where条件，然后后边紧跟的一部分查询条件实现嵌套查询条件去进行查询
     */
    @Test
    void selectAndOrTest2(){
        qw.eq("name","周丽")
                .or()
                .and(i -> i.eq("status",1).isNotNull("email"))
                .or()
                .gt("age","36");
        /**
         * FROM student WHERE (name = ? AND (status = ? AND email IS NOT NULL) OR age > ?)
         * Parameters: 周丽(String), 1(Integer), 36(String)
         */
        print(qw);
    }

    /**
     * 二十五、or和and混合使用
     * or：连接条件用or，表示或的意思，默认是and
     * and：连接条件用and，表示且的意思
     * 多条件多字段的联合查询
     * myBatisPlus的所有查询条件的方法，返回的都是对象本身QueryWrapper，所以可以使用链式的方式来吧多个调节联合在一起
     * 多条件之间可以用QueryWrapper链式的操作来进行封装多个条件
     * select查询语句的[and()(AND 嵌套)]方法：where后边的多个田间and连接【与】
     * 方法：default Children and(Consumer<Param> consumer) {}
     * 参数：Consumer<Param> consumer【函数式接口】 Consumer<QueryWrapper<Student>> consumer
     *
     * 重点记忆
     * .or(i -> i.eq("status",1).isNotNull("email"))
     * i:代表的是连接上一层的QueryWrapper对象的查询的where条件，然后后边紧跟的一部分查询条件实现嵌套查询条件去进行查询
     */
    @Test
    void selectAndOrTest3(){
        qw.eq("name","周丽")
                .or(i -> i.eq("status",1).isNotNull("email"))
                .or()
                .gt("age","36");
        /**
         * FROM student WHERE (name = ? OR (status = ? AND email IS NOT NULL) OR age > ?)
         * Parameters: 周丽(String), 1(Integer), 36(String)
         * Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         */
        print(qw);
    }

    /**
     * 二十五、or和and混合使用
     * or：连接条件用or，表示或的意思，默认是and
     * and：连接条件用and，表示且的意思
     * 多条件多字段的联合查询
     * myBatisPlus的所有查询条件的方法，返回的都是对象本身QueryWrapper，所以可以使用链式的方式来吧多个调节联合在一起
     * 多条件之间可以用QueryWrapper链式的操作来进行封装多个条件
     * select查询语句的[and()(AND 嵌套)]方法：where后边的多个田间and连接【与】
     * 方法：default Children and(Consumer<Param> consumer) {}
     * 参数：Consumer<Param> consumer【函数式接口】 Consumer<QueryWrapper<Student>> consumer
     *
     * 重点记忆
     * .and(i -> i.eq("status",1).or().isNotNull("email"))
     * i:代表的是连接上一层的QueryWrapper对象的查询的where条件，然后后边紧跟的一部分查询条件实现嵌套查询条件去进行查询
     * 类似于
     *      QueryWrapper<Student> qw2 = new QueryWrapper<>();
     *      qw2.eq("name","张三").eq("age",22);
 *          qw.and(qw2);
     */
    @Test
    void selectAndOrTest4(){
        qw.eq("name","周丽")
                .and(i -> i.eq("status",1).or().isNotNull("email"))
                .or()
                .gt("age","36");
        /**
         * FROM student WHERE (name = ? AND (status = ? OR email IS NOT NULL) OR age > ?)
         *  Parameters: 周丽(String), 1(Integer), 36(String)
         * Columns: id, name, age, email, status
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         */
        print(qw);
    }

    /**
     * 二十六、
     * last：拼接sql语句【添加一个条件放在原有myBatisPlus生成的sql语句的后面，自己添加一个拼接sql的sql语句】
     * 这条语句加入到整个sql语句的结尾，前边是拼接sql语句的where条件
     * limit 1，查询结果的第一条记录
     * 可以任意的拼接一条自定义sql语句到原有生成的sql语句的结尾部分
     * select查询语句的[last()]方法：添加到sql语句的末尾部分，也就是是说如果有where条件的话，方法是的参数【sql语句】添加到where条件之后
     * 方法：default Children last(String lastSql) {}
     * 参数：String lastSql
     * 注意事项:
     * 无视优化规则直接拼接到 sql 的最后
     * 只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用
     */
    @Test
    void selectLastTest(){
        qw.isNotNull("email").last("limit 2");
        /**
         * FROM student WHERE (email IS NOT NULL) limit 2
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         */
        print(qw);

    }

    /**
     * 二十七
     * exists 拼接 EXISTS(sql语句)，相当与if判断
     * notExists 是exists的相反操作
     * exists是用来做判断的：判断记录是否存在【对应的是sql语句中的exists的操作符】
     * exists：判断查询结果中是否有值，有则为真（true），就执行sql操作【执行主查询的sql语句】，反之不执行sql操作
     * notExists：判断查询结果中是否有值，没有则为真（true），就执行sql操作【执行主查询的sql语句】，反之不执行sql操作
     * exists和notExists常用在子查询之中
     * select查询语句的[exists()]方法：在sql语句后边拼接一个sql语句，做【判断】，为【true】执行主查询语句，【false】不执行
     * 方法：default Children exists(String existsSql, Object... values) {}
     * 参数：String existsSql, Object... values
     */
    @Test
    void selectExistsTest(){
        qw.exists("select age from student where age > 22");
        /**
         * exists为true
         * FROM student WHERE (EXISTS (select age from student where age > 22))
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         */
        print(qw);
    }

    /**
     * 二十七
     * exists 拼接 EXISTS(sql语句)，相当与if判断
     * notExists 是exists的相反操作
     * exists是用来做判断的：判断记录是否存在【对应的是sql语句中的exists的操作符】
     * exists：判断查询结果中是否有值，有则为真（true），就执行sql操作【执行主查询的sql语句】，反之不执行sql操作
     * notExists：判断查询结果中是否有值，没有则为真（true），就执行sql操作【执行主查询的sql语句】，反之不执行sql操作
     * exists和notExists常用在子查询之中
     * select查询语句的[exists()]方法：在sql语句后边拼接一个sql语句，做【判断】，为【true】执行主查询语句，【false】不执行
     * 方法：default Children exists(String existsSql, Object... values) {}
     * 参数：String existsSql, Object... values
     */
    @Test
    void selectExistsTest2(){
        qw.exists("select age from student where age > 100");
        /**
         * exists为false
         * FROM student WHERE (EXISTS (select age from student where age > 100))
         * Parameters:
         * <==      Total: 0
         */
        print(qw);
    }

    /**
     * 二十八、
     * exists 拼接 EXISTS(sql语句)，相当与if判断
     * notExists 是exists的相反操作
     * exists是用来做判断的：判断记录是否存在【对应的是sql语句中的exists的操作符】
     * exists：判断查询结果中是否有值，有则为真（true），就执行sql操作【执行主查询的sql语句】，反之不执行sql操作
     * notExists：判断查询结果中是否有值，没有则为真（true），就执行sql操作【执行主查询的sql语句】，反之不执行sql操作
     * exists和notExists常用在子查询之中
     * select查询语句的[exists()]方法：在sql语句后边拼接一个sql语句，做【判断】，为【true】执行主查询语句，【false】不执行
     * 方法：default Children exists(String existsSql, Object... values) {}
     * 参数：String existsSql, Object... values
     */
    @Test
    void selectNotExistsTest(){
        qw.notExists("select age from student where age > 100");
        /**
         * notExists为true,与exists相反
         * FROM student WHERE (NOT EXISTS (select age from student where age > 100))
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         */
        print(qw);
    }

    /**
     * 二十八、
     * exists 拼接 EXISTS(sql语句)，相当与if判断
     * notExists 是exists的相反操作
     * exists是用来做判断的：判断记录是否存在【对应的是sql语句中的exists的操作符】
     * exists：判断查询结果中是否有值，有则为真（true），就执行sql操作【执行主查询的sql语句】，反之不执行sql操作
     * notExists：判断查询结果中是否有值，没有则为真（true），就执行sql操作【执行主查询的sql语句】，反之不执行sql操作
     * exists和notExists常用在子查询之中
     * select查询语句的[exists()]方法：在sql语句后边拼接一个sql语句，做【判断】，为【true】执行主查询语句，【false】不执行
     * 方法：default Children exists(String existsSql, Object... values) {}
     * 参数：String existsSql, Object... values
     */
    @Test
    void selectNotExistsTest2(){
        qw.notExists("select age from student where age > 22");
        /**
         * notExists为false,与exists相反
         * FROM student WHERE (NOT EXISTS (select age from student where age > 22))
         * Parameters:
         * <==      Total: 0
         */
        print(qw);
    }

    /**
     * 二十九
     * select查询语句的[having(sql语句)]方法：需要与【select()】方法和【groupBy()】方法共同使用【无特定查询可以忽略不写select方法按照默认查询字段】是在where条件后使用的聚合函数的一个方法
     * 在 SQL 中增加 HAVING 子句原因是，WHERE 关键字无法与合计函数一起使用。
     * 方法：default Children having(String sqlHaving, Object... params) {
     * 参数：String sqlHaving, Object... params
     */
    @Test
    void selectHavingTest(){
        qw.select("name,sum(age)").groupBy("name").having("sum(age) > 22");
        /**
         * FROM student GROUP BY name HAVING sum(age) > 22
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==        Row: 3, 李蕾张, 31, lilei@163.com, 2
         * <==        Row: 4, 周丽, 36, zhouli@sina.com, 2
         * <==        Row: 5, 周名明, 24, null, 2
         * <==        Row: 6, 冯亮, 32, null, 2
         */
        print(qw);
    }

    /**
     * 三十、
     * select查询语句的[func()]方法：func 方法(主要方便在出现if...else下调用不同方法能不断链)相当于MyBatis中的when … otherwise
     * [主要方便在出现if...else下调用不同方法能不断链]根据条件去判断链接哪一个查询条件
     * 方法：default Children func(Consumer<Children> consumer) {
     * 参数：Consumer<Children> consumer
     */
    @Test
    void selectFuncTest(){
        qw.gt("age",20).func(i -> {
                                                if(true) {
                                                    i.eq("name", "张三");
                                                } else {
                                                    i.ne("id", 1);
                                                }
                                              });
        /**
         * FROM student WHERE (age > ? AND name = ?)
         * Parameters: 20(Integer), 张三(String)
         * <==    Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         */
        print(qw);
    }

    /**
     * 三十一、
     * select查询语句的[nested()]方法：正常嵌套 不带 AND 或者 OR,在where查询条件中嵌套一层查询【类似于and(),或or()方法】
     * 方法：default Children nested(Consumer<Param> consumer) {}
     * 参数：Consumer<Param> consumer 【Consumer<QueryWrapper<Student>>】
     */
    @Test
    void selectNestedTest(){
        qw.gt("age",20)
                .nested(i -> i.eq("name","李四")
                        .isNotNull("email"))
                .eq("status",1);
        /**
         * FROM student WHERE (age > ? AND (name = ? AND email IS NOT NULL) AND status = ?)
         * Parameters: 20(Integer), 李四(String), 1(Integer)
         * <==    Columns: id, name, age, email, status
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         */
        print(qw);
    }

    /**
     * 三十二、
     * select查询语句的[apply()]方法：动态添加sql查询条件，可以说sql语句也可以是字段条件
     * 注意事项:
     * 该方法可用于数据库函数 动态入参的params对应前面applySql内部的{index}部分.这样是不会有sql注入风险的,反之会有!
     */
    @Test
    void selectApplyTest(){
        qw.apply("id = 1");
        /**
         * FROM student WHERE (id = 1)
         * Parameters:
         * <==    Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         */
        print(qw);
    }


    /**
     * 分页
     *      1、统计记录数，使用count(1)
     *
     *      2、实现分页，在sql语句的末尾加上 limit 0,3
     *  一、创建configuration
     *  二、创建分页连接器，把拦截器对象注入到spring容器中
     *  三、调用selectPage实现分页操作
     */
    @Test
    void selectPageTest(){
        IPage<Student> page = new Page<>();
        page.setCurrent(1);
        page.setSize(2);
        qw.gt("age",20);
        IPage<Student> result = studentDao.selectPage(page, qw);
        /**
         * ==>  Preparing: SELECT COUNT(*) AS total FROM student WHERE (age > ?)
         * ==> Parameters: 20(Integer)
         * <==    Columns: total
         * <==        Row: 6
         * <==      Total: 1
         * ==>  Preparing: SELECT id,name,age,email,status FROM student WHERE (age > ?) LIMIT ?
         * ==> Parameters: 20(Integer), 2(Long)
         * <==    Columns: id, name, age, email, status
         * <==        Row: 1, 张三, 22, zs@sina.com, 1
         * <==        Row: 2, 李四, 29, lisi@163.com, 1
         * <==      Total: 2
         */
        log.info("获取每页显示条数：{}",result.getSize());
        log.info("当前分页总页数：{}",result.getPages());
        log.info("当前页：{}",result.getCurrent());
        log.info("分页记录列表：{}",result.getRecords());
        log.info("当前满足条件总行数(总条数)：{}",result.getTotal());

    }
}

package com.atguigu.boot;

import com.atguigu.boot.entity.User;
import com.atguigu.boot.dao.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//抑制警告
//@SuppressWarnings("all")
@SpringBootTest
class BootMybatisplus01UserApplicationTests {

    @Test
    void contextLoads() {
    }

    //使用自动注入，注入Mapper对象（Dao对象）
    //按类型的自动注入（只有程序执行过程中才有userDao这个对象）
    @Autowired
    UserDao userDao;



    /**
     *   定义测试方法
     *  测试添加：insert
     */
    @Test
    void testInsert(){
        //创建user对象
        User user = new User();
        user.setName("张大仙");
        user.setEmail("zhangdaxian@qq.com");
        user.setAge(30);
        //调用UserMapper的方法，也就是父接口BaseMapper中提供的方法
        //返回值是一个int数字表示受影响的行数
        int rows = userDao.insert(user);
        System.out.println("成功插入的行数是：" + rows);
    }

    @Test
    void testSelect(){
        System.out.println(("----- selectAll method test ------"));
        List<User> users = userDao.selectList(null);
        //Assertions.assertEquals(4,users.size());
        users.forEach(System.out::println);
    }

    /***
     * 添加数据后获取主键ID
     */
    @Test
    void testInsertGetId(){
        User user = new User();
        user.setName("吕德华");
        user.setEmail("lvdehua@qq.com");
        user.setAge(28);
        int rows = userDao.insert(user);
        System.out.println("成功插入的行数是：" + rows);
        //获取主键Id，刚添加数据库中的数据的id
        Long id = user.getId();//主键字段对应的get方法
        System.out.println("主键Id是："+id);
    }

    /**
     * update操作，修改数据
     */
    @Test
    void testUpdate(){
        User user = new User();
        user.setName("edit");
        user.setEmail("edit@qq.com");
        user.setAge(22);
        user.setId(10L);
        /**
         * 执行更新根据主键值更新
         * UPDATE user SET name=?, age=?, email=? WHERE id=?
         * 更新了所有非null属性值，条件where id = 主键值
         */
        int rows = userDao.updateById(user);
        System.out.println("修改的数据行数："+rows);
    }

    /**
     * update操作，修改数据，控制修改字段
     * 控制更新的属性
     */
    @Test
    void testUpdateNoNull(){
        User user = new User();
        user.setName("edit2");
        user.setId(10L);
        //更新数据
        //UPDATE user SET name=? WHERE id=?
        int rows = userDao.updateById(user);
        System.out.println("修改的数据行数："+rows);
    }

    /**
     * update操作，实体类的属性是基本类型 - int age
     * 在进行数据操作的时候，实体类最好定义成包装类型，尽量避免使用基本数据类型
     * 判断字段是否要修改，加入到set语句，是根据属性值是否为null去判断的
     */
    @Test
    void testUpdateNoNull2(){
        User user = new User();
        user.setName("edit2");
        user.setId(10L);
        //实体对象 user : [name = "edit2",email = null,age = 0]
        //==>  Preparing: UPDATE user SET name=?, age=? WHERE id=?
        //==> Parameters: edit2(String), 0(Integer), 10(Long)
        //<==    Updates: 1
        int rows = userDao.updateById(user);
        System.out.println("修改的数据行数："+rows);
    }

    /**
     * 按主键删除一条数据
     * 方法是：deleteById()
     * 参数：主键值
     * 返回值是：删除成功的记录数
     */
    @Test
    void testDeleteById(){
        /**
         * ==>  Preparing: DELETE FROM user WHERE id=?
         * ==> Parameters: 10(Integer)
         * <==    Updates: 1
         */
        int rows = userDao.deleteById(10);
        System.out.println(rows);
    }

    /**
     * 按实体类对象一条数据
     * 方法是：deleteById()
     * 参数：实体类对象
     * 返回值是：删除成功的记录数
     */
    @Test
    void testDeleteById2(){
        /**
         * ==>  Preparing: DELETE FROM user WHERE id=?
         * ==> Parameters: 10(Long)
         * <==    Updates: 1
         */
        User user = new User();
        user.setId(10L);
        int rows = userDao.deleteById(user);
        System.out.println(rows);
    }

    /**
     * 按条件删除数据，条件是封装到Map对象中的
     * 方法是：deleteByMap(Map对象)
     * 返回值是：删除成功的记录数
     */
    @Test
    void testDeleteByMap(){
        /**
         * ==>  Preparing: DELETE FROM user WHERE name = ? AND age = ?
         * ==> Parameters: 吕德华(String), 20(Integer)
         * <==    Updates: 0
         */
        /**
         * 创建map对象保存条件值
         */
        Map<String,Object> map = new HashMap<>();
        /**
         * put("表的字段名",条件值)，可以封装多个条件
         */
        map.put("name","吕德华");
        map.put("age",20);
        //调用删除方法
        int rows = userDao.deleteByMap(map);
        System.out.println(rows);
    }

    /**
     * 批量处理方式：使用多个主键值，删除数据
     * 方法名称：deleteBatchIds()
     * 参数：Collection<? extends Serializable> idList，可序列化的集合
     * 返回值：删除的记录数
     */
    @Test
    void testDeleteBatchIds(){
        //使用lambda表达式创建集合
        List<Integer> ids = Stream.of(5, 6, 7, 8, 9, 10).collect(Collectors.toList());
//        List<Integer> ids = Arrays.asList(5, 6, 7, 8, 9, 10);
        /**
         * ==>  Preparing: DELETE FROM user WHERE id IN ( ? , ? , ? , ? , ? , ? )
         * ==> Parameters: 5(Integer), 6(Integer), 7(Integer), 8(Integer), 9(Integer), 10(Integer)
         * <==    Updates: 2
         */
        int i = userDao.deleteBatchIds(ids);
        System.out.println(i);
    }

    /**
     * 实现查询：selectById，根据主键值查询
     * 参数：主键值
     * 返回值：实体对象(唯一的一个对象)
     */
    @Test
    void testSelectById(){
        /**
         * 生成的sql：
         * 如果根据主键没有查找到数据，得到的返回值是null
         * 如果使用userDao.selectById参数传一个null，查询出来的数据也是null
         */
        User user = userDao.selectById(null);
        System.out.println(user);
        //在使用对象之前，需要判断对象是否为null
        if (user != null){
            //业务方法的调用
        }
    }

    /**
     * 实现批处理查询：根据多个主键值查询，获取到List
     * 方法：selectBatchIds()
     * 参数：id的集合Collection<? extends Serializable> idList，可序列化的list集合
     * 返回值List<T>
     */
    @Test
     void testSelectBatchIds(){
        /**
         * ==>  Preparing: SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )
         * ==> Parameters: 1(Integer), 2(Integer), 3(Integer), 4(Integer), 5(Integer), 6(Integer), 7(Integer), 8(Integer), 9(Integer), 10(Integer)
         * <==    Columns: id, name, age, email
         * <==        Row: 1, Jone, 18, test1@baomidou.com
         * <==        Row: 2, Jack, 20, test2@baomidou.com
         * <==      Total: 2
         */
        List<User> users = userDao.selectBatchIds(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println(users);
        //遍历集合
        for (User u:users) {
            System.out.println(u);
        }
    }

    /**
     * 使用Lambda表达式查询数据
     */
    @Test
    void testSelectBatchIds2(){
        /**
         * ==>  Preparing: SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )
         * ==> Parameters: 1(Integer), 2(Integer), 3(Integer), 4(Integer), 5(Integer), 6(Integer), 7(Integer), 8(Integer), 9(Integer), 10(Integer)
         * <==    Columns: id, name, age, email
         * <==        Row: 1, Jone, 18, test1@baomidou.com
         * <==        Row: 2, Jack, 20, test2@baomidou.com
         * <==      Total: 2
         */
        List<Integer> ids = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).collect(Collectors.toList());
        List<User> users = userDao.selectBatchIds(ids);
        System.out.println(users);
        users.forEach(u -> {
            System.out.println(u);
        });
    }

    /**
     * 使用Map作多条件查询
     * 方法：selectByMap()
     * 参数：Map<String, Object> columnMap
     * 返回值List<T>
     */
    @Test
    void testSelectByMap(){
        //创建Map，封装查询条件
        Map<String,Object> map = new HashMap<>();
        //key是字段名，value是字段值，多个key使用and连接
        map.put("name","Jone");
        map.put("age",18);
        //根据Map去查询
        List<User> users = userDao.selectByMap(map);
        /**
         * 遍历list集合
         * ==>  Preparing: SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
         * ==> Parameters: Jone(String), 18(Integer)
         * <==    Columns: id, name, age, email
         * <==        Row: 1, Jone, 18, test1@baomidou.com
         * <==      Total: 1
         */
        users.forEach(user -> {
            System.out.println(user);
        });
    }

    @Test
    void testNum(){
        int index = 0;
        int speed = 0;
        int yan = 0;
        System.out.println(113/170);
        for (int i = 0;i < 340;i++){
            System.out.println("==========================="+i);
            yan++;
            if (yan % 3 == 0){
                System.out.println("yan===>"+yan);
                index++;
                System.out.println("index===>"+index);
                if ((index / 170) % 2 == 0){
                    speed = speed + 1;
                }else {
                    speed = speed - 1;
                }
                System.out.println("speed===>"+speed);
            }else {
            }
        }
    }

}

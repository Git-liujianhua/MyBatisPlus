package com.atguigu.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * @MapperScan：扫描器，指定Mapper类所在的包路径
 * 扫描器定义mapper文件位置来指定指定mapper所在的位置，
 * 就可以扫描到继承了BaseMapper的接口并可以调用其中的方法，且可以扫描到实体类
 * 还可以直接使用@Mapper注解就不需要在去加@MapperScan注解去指定扫描包路径了
 * 如果忘记配置那MP框架就找不到相关的接口和类了
 */
@MapperScan("com.atguigu.boot.dao")
public class BootMybatisplus01UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMybatisplus01UserApplication.class, args);
    }

}

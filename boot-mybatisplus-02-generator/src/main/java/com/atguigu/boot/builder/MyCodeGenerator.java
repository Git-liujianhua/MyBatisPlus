package com.atguigu.boot.builder;

import lombok.Getter;
import lombok.Setter;

/**
 * 测试构者模式代码生成器
 */
@Getter
@Setter
public class MyCodeGenerator {

    /**
     * 数据源配置
     */
    public String dataSource;

    /**
     * 全局配置
     */
    public String globalConfig;

    /**
     * 包配置
     */
    public String packageConfig;

    /**
     * 策略配置
     */
    public String strategyConfig;

    /**
     * 执行代码生成
     */
    public void execute(){
        System.out.println(dataSource + "===》" + globalConfig  + "===》" + packageConfig + "===》" + strategyConfig);
    }

}

package com.atguigu.boot.builder;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {
        MyCodeGenerator mcg = new MyCodeGenerator();
        mcg.setDataSource("数据库配置：mysql");
        mcg.setGlobalConfig("全局配置");
        mcg.setPackageConfig("包配置：com.atguigu");
        mcg.setStrategyConfig("策略配置：驼峰命名");
        mcg.execute();

        MyCodeGeneratorBuilder.create("数据库配置：mysql")
                .globalConfig("全局配置")
                .packageConfig("包配置：com.atguigu")
                .strategyConfig("策略配置：驼峰命名")
                .execute();
    }
}

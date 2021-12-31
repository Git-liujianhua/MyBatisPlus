package com.atguigu.boot.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyAutoGenerator {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/db_account";
        String path = System.getProperty("user.dir") + "/boot-mybatisplus-02-generator/src/main/java";
        String mapperXml = System.getProperty("user.dir") + "/boot-mybatisplus-02-generator/src/main/resources/mapperXml";
        FastAutoGenerator.create(url,"root","1234")
                .globalConfig(builder -> builder
                        .outputDir(path)
                        .author("超级无敌最帅刘建华")
                        .fileOverride()
                        .disableOpenDir())
                .packageConfig((scanner,builder) -> builder
                        //com.atguigu.boot
                        .parent(scanner.apply("请问要生成在哪个父包下："))
                        //mycode
                        .moduleName(scanner.apply("请问模块名是什么："))
                        //自定义输出某个层的类的输出目录，例如：mapperXml输出到resources下
//                        .pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperXml))
                        .pathInfo(new HashMap<OutputFile, String>() {
                            {
                                put(OutputFile.mapperXml, mapperXml);
                            }
                        }))
                .injectionConfig(builder -> builder
                        .beforeOutputFile((tableInfo,objectMap) -> {
                            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
                        })
                        .customMap(Collections.singletonMap("test","atguigu"))
                        .customFile(Collections.singletonMap("hi.txt","/templates/hi.java.vm"))
                )
                .strategyConfig(builder -> builder
                        .addInclude("student"))
                .templateEngine(new VelocityTemplateEngine(){
                    @Override
                    protected void outputCustomFile(@NotNull Map<String, String> customFile, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
//                        String entityName = tableInfo.getEntityName();
                        String otherPath = getPathInfo(OutputFile.other);
                        customFile.forEach((key, value) -> {
                            String fileName = String.format((otherPath + File.separator + "%s"), key);
                            outputFile(new File(fileName), objectMap, value);
                        });
                    }
                })
                .execute();
    }


}

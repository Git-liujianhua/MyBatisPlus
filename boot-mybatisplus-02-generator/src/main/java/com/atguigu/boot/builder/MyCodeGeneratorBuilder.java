package com.atguigu.boot.builder;

/**
 * 代码生成器构建者模式
 */
public class MyCodeGeneratorBuilder {
    /**
     * MyCodeGenerator属性
     */
    private MyCodeGenerator myCodeGenerator;

    /**
     * 创建有参构造器
     * @param myCodeGenerator
     */
    public MyCodeGeneratorBuilder(MyCodeGenerator myCodeGenerator) {
        this.myCodeGenerator = myCodeGenerator;
    }

    /**
     * 数据库配置
     * @param dataSource
     * @return
     */
    public static MyCodeGeneratorBuilder create(String dataSource){
        MyCodeGenerator mcg = new MyCodeGenerator();
        mcg.setDataSource(dataSource);
        return new MyCodeGeneratorBuilder(mcg);
    }

    /**
     * 全局配置
     * @param globalConfig
     * @return
     */
    public MyCodeGeneratorBuilder globalConfig(String globalConfig){
        myCodeGenerator.setGlobalConfig(globalConfig);
        return this;
    }

    /**
     * 包配置
     * @param packageConfig
     * @return
     */
    public MyCodeGeneratorBuilder packageConfig(String packageConfig){
        myCodeGenerator.setPackageConfig(packageConfig);
        return this;
    }

    /**
     * 策略配置
     * @param strategyConfig
     * @return
     */
    public MyCodeGeneratorBuilder strategyConfig(String strategyConfig){
        myCodeGenerator.setStrategyConfig(strategyConfig);
        return this;
    }

    /**
     * 输出
     */
    public void execute(){
        myCodeGenerator.execute();
    }

}

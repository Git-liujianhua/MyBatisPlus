package com.atguigu.boot.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

/**
 * 代码生成器【新】3.5.1版本的配置
 * 适用版本：mybatis-plus-generator 3.5.1 及其以上版本，对历史版本不兼容！3.5.1 以下的请参
 * 【CSDN】Mybatis-Plus新的版本（3.5.1+版本） https://blog.csdn.net/chencaw/article/details/121746402
 */
@Configuration
public class AutoGeneratorMapperConfiguration {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        //2、创建数据源配置DataSourceConfig
        DataSourceConfig.Builder ds = new DataSourceConfig
                //配置数据源  url、用户名username、密码password
                .Builder("jdbc:mysql://localhost:3306/db_account", "root", "1234");
                //创建代码生成器对象，加载配置
                //.build();
        //获取项目位置
        String property = System.getProperty("user.dir");
        //创建代码生成器，并将全局配置、数据源配置、包配置、策略配置、代码模板等添加进去
        FastAutoGenerator
                //创建数据源添加如代码生成器中
                .create(ds)
                //创建全局策略配置
                .globalConfig(builder -> {
                    builder
                            //覆盖已经生成的文件
                            .fileOverride()
                            //生成文件的输出目录
                            .outputDir(property + "/boot-mybatisplus-01-user/src/main/java")
                            //设置开发人员
                            .author("超级无敌最帅刘建华");
                })
                .packageConfig(builder -> {
                    builder
                            //设置父包名
                            .parent(scanner("父包名"))
                            //设置模块名
                            .moduleName(scanner("模块名"))
                            //设置实体类包名
                            .entity("pojo")
                            //设置包名（dao【mapper】）
                            .mapper("dao")
                            //设置包名（xml【mapperXml】）
                            .xml("dao.xml");
                })
                .strategyConfig(builder -> {
                    builder
                            //实体【entity】的策略配置
                            .entityBuilder()
                            //数据库表映射到实体的命名策略	默认下划线转驼峰命名:NamingStrategy.underline_to_camel
                            .naming(NamingStrategy.underline_to_camel)
                            //数据库表字段映射到实体的命名策略	默认为 null，未指定按照 naming 执行
                            .columnNaming(NamingStrategy.underline_to_camel)
                            //全局主键类型
                            .idType(IdType.ASSIGN_UUID)
                            //开启 lombok 模型
                            .enableLombok()
                            //持久层dao【mapper】的策略配置
                            .mapperBuilder()
                            //格式化 mapper【dao】 文件名称
                            .formatMapperFileName("%sDao")
                            //格式化 【dao（mapper）】xml 实现类文件名称
                            .formatXmlFileName("%sDao")
                            //业务层service的策略配置
                            .serviceBuilder()
                            //格式化 service 接口文件名称
                            .formatServiceFileName("%sService")
                            //格式化 service 实现类文件名称
                            .formatServiceImplFileName("%sServiceImpl")
                            //controller层的策略配置
                            .controllerBuilder()
                            //格式化文件名称
                            .formatFileName("%sController");
                })
                //开启使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                //执行代码生成器
                //方法：public void execute(AbstractTemplateEngine templateEngine) {}
                //参数：AbstractTemplateEngine templateEngine：模板引擎参数
                .execute();
    }
}

package com.atguigu.boot.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Scanner;

/**
 * 代码生成器【旧】3.5.0版本的配置，已经接近于3.5.1的配置了,经测试3.5.1版本兼容3.5.0版本
 * 【CSDN（1）】MyBatis Plus之代码生成器（3.5.0版本）https://blog.csdn.net/qq_43437874/article/details/117960013
 * 【博客园】mybatis-plus-generator代码生成器新版本3.5.0 https://www.cnblogs.com/sqhuang/p/15315156.html
 * 【CSDN（2）】mybatis-plus-generator 代码生成器 3.5.0 https://blog.csdn.net/zyy_2018/article/details/118177638
 */
public class AutoMapperConfiguration {

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

        /**
         * //创建AutoGenerator，MyBatis Plus中的对象
         *
         *         //1、设置全局配置
         *             //创建GlobalConfig对象
         *             //设置代码生成位置,磁盘的目录
         *             //设置生成的类的名称（命名规则）
         *                 //所有的（Dao）Mapper，都是以Mapper（Dao结尾的）【%sMapper（s相当于数据库表名），例如DeptMapper（DeptDao）】
         *                 //设置Service接口的名称
         *                 //所有的Service，都是以Service【%sService（s相当于数据库表名），例如DeptService】{全限定类名}
         *                 //设置Service实现类的名称
         *                 //所有的ServiceImpl，都是以ServiceImpl【%sServiceImpl（s相当于数据库表名），例如DeptServiceImpl】
         *                 //设置作者
         *                 //设置主键ID的配置【注意：如果数据库中设置了主键的类型是自动增长或其他方式则，数据的主键方式规则优先，代码配置其后】
         *                 //把全局设置赋值给AutoGenerator
         *         //2、设置数据源DataSource
         *                 //创建DataSourceConfig对象
         *                 //驱动
         *                 //设置url
         *                 //设置数据库的用户名
         *                 //设置密码
         *                 //把数据源配置DataSourceConfig赋值给AutoGenerator
         *                 //设置Package信息
         *         //3、创建PackageConfig对象
         *             //设置模块名称，相当于包名，在这个包下有，Entity、Mapper（Dao）、Service、Controller
         *             //设置父包名称，模块名称就这父包的下面生成【com.atguigu.order】
         *             //把包的信息PackageConfig赋值给AutoGenerator
         *         //4、设置策略（命名的策略）
         *             //创建StrategyConfig对象（策略配置器）
         *             //设置支持的命名规则（mybatisplus中选择驼峰命名规则）【数据库表的命名规则】
         *             //设置支持的命名规则（mybatisplus中选择驼峰命名规则）【数据库字段列的命名规则】
         *             //还可以自定义选择设置更多的策略.............
         *             //把策略的信息StrategyConfig赋值给AutoGenerator
         *         //执行代码的生成
         */

        //1、创建全局策略配置GlobalConfig
        //设置代码的生成位置，磁盘的目录
        String property = System.getProperty("user.dir");
        GlobalConfig gc = new GlobalConfig.Builder()
                //覆盖已经生成的文件
                .fileOverride()
                //生成文件的输出目录
                .outputDir(property + "/boot-mybatisplus-01-user/src/main/java")
                //开发人员
                .author("超级无敌最帅刘建华")
                //创建代码生成器对象，加载配置
                .build();

        //2、创建数据源配置DataSourceConfig
        DataSourceConfig ds = new DataSourceConfig
                //配置数据源  url、用户名username、密码password
                .Builder("jdbc:mysql://localhost:3306/db_account", "root", "1234")
                //创建代码生成器对象，加载配置
                .build();

        //3、创建包配置PackageConfig
        PackageConfig pc = new PackageConfig.Builder()
                //设置父包名
                .parent(scanner("父包名"))
                //.parent("com.atguigu.boot")
                //设置模块名
                .moduleName(scanner("模块名"))
                //.moduleName("generator")
                //设置包名（实体entity）
                .entity("pojo")
                //设置包名（dao【mapper】）
                .mapper("dao")
                //设置包名（xml【mapperXml】）在3.5.0版本中没有生效、3.5.1正确生效
                .xml("dao.xml")
                .build();

        //4、创建策略配置
        StrategyConfig sc = new StrategyConfig.Builder()
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
                //.formatFileName("%s")
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
                .formatFileName("%sController")
                .build();

        //5、创建代码生成器，并将1、2、3、4的配置注入添加到代码生成器中
        AutoGenerator strategy = new AutoGenerator(ds).global(gc).packageInfo(pc).strategy(sc);

        //6、执行代码生成器:
        //方法：public void execute(AbstractTemplateEngine templateEngine) {}
        //参数：AbstractTemplateEngine templateEngine：模板引擎参数
        strategy.execute(new FreemarkerTemplateEngine());


    }
}

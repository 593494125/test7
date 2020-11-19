package com.springboot;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


public class CodeGenerator {
    public static final String DB_URL = "jdbc:sqlserver://125.76.225.17;DatabaseName=A8ERP_CS";
//    public static final String DB_URL = "jdbc:sqlserver://125.76.225.17;DatabaseName=APP_ERP";
    public static final String USER_NAME = "sa";
//    public static final String PASSWORD = "Jjyerp191";
    public static final String PASSWORD = "jjyerp114";
    public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String AUTHOR = "zjq";
    //生成的文件输出到哪个目录
    public static final String OUTPUT_FILE = "D:\\Workspaces\\rfidAppService\\org-provide-8010\\src\\main\\java";
    public static final String OUTPUT_XML = "D:\\Workspaces\\rfidAppService\\org-provide-8010\\src\\main\\resources";
    //包名，会按照com/jjy/springclound这种形式生成类
    public static final String PACKAGE = "com.springboot";
    //TODO 更多配置请参考http://mp.baomidou.com/#/generate-code

    public void generateByTables(boolean serviceNameStartWithI, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.SQL_SERVER)
                .setUrl(DB_URL)
                .setUsername(USER_NAME)
                .setPassword(PASSWORD)
                .setDriverName(DRIVER);
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)
                .setEntityLombokModel(false)
                .setDbColumnUnderline(true)//指定表名，字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor(AUTHOR)
                .setOutputDir(OUTPUT_FILE)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(PACKAGE)
                                .setController("web.system")
                                .setService("service.system")
                                .setServiceImpl("service.impl.system")
                                .setMapper("dao.system")
                                .setEntity("model.goods")
                                .setXml("mapper.goods")
                ).execute();
    }
    public static void main(String[] args) {

    	CodeGenerator gse = new CodeGenerator();
    	gse.generateByTables(false, "da_qx_zyda");
	}
}
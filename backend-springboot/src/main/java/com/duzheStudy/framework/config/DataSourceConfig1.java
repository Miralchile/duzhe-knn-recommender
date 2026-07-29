package com.duzheStudy.framework.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * HikariCP连接池配置
 */
@Configuration
@MapperScan(basePackages = "com.duzheStudy.modules.**.mapper",sqlSessionFactoryRef="sqlSessionFactory")
public class DataSourceConfig1 {

    @Value("${spring.datasource.prod1.jdbc-url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.prod1.username}")
    private String user;

    @Value("${spring.datasource.prod1.password}")
    private String password;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    
    /*
     * @Bean(name="dataSource")//注入到这个容器
     * 
     * @ConfigurationProperties(prefix="spring.datasource.prod1")
     * 
     * @Primary public DataSource dataSource() {
     * 
     * return DataSourceBuilder.create().build(); }
     */

    @Bean(name="sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/braille/modules/user/mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name="transactionManager")//配置事务
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name="sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

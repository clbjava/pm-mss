package com.pm.mss.server.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class PmMssApplicationConfig {

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.user}")
    private String jdbcUser;

    @Value("${jdbc.initial-size}")
    private int jdbcInitialSize = 5;

    @Value("${jdbc.min-idle}")
    private int jdbcMinIdle;

    @Value("${jdbc.max-active}")
    private int jdbcMaxActive;

    @Value("${jdbc.max-wait}")
    private int jdbcMaxWait;

    @Value("${jdbc.time-between-eviction-runs-millis}")
    private int jdbcTimeBetweenEvictionRunsMillis;

    @Value("${jdbc.min-evictable-idle-time-millis}")
    private int jdbcMinEvictableIdleTimeMillis;

    @Value("${jdbc.validation-query}")
    private String jdbcValidationQuery;

    @Value("${jdbc.validation-query-timeout}")
    private int validationQueryTimeout = 2;

    @Value("${jdbc.test-while-idle:true}")
    private boolean jdbcTestWhileIdle = true;

    @Value("${jdbc.test-on-borrow:false}")
    private boolean jdbcTestOnBorrow = false;

    @Value("${jdbc.test-on-return:false}")
    private boolean jdbcTestOnReturn = false;

    @Value("${jdbc.prepared-statements:true}")
    private boolean jdbcPoolPreparedStatements = true;

    @Value("${jdbc.max-pool-prepared-statement-per-connection-size:20}")
    private int jdbcMaxPoolPreparedStatementPerConnectionSize = 20;

    @Value("${jdbc.filters:'stat'}")
    private String jdbcFilters;

    @Bean(name = "dataSource")
    public DataSource dataSource() throws Exception {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(jdbcUrl);
        ds.setUsername(jdbcUser);
        ds.setPassword(jdbcPassword);
        ds.setInitialSize(jdbcInitialSize);
        ds.setMinIdle(jdbcMinIdle);
        ds.setMaxActive(jdbcMaxActive);
        ds.setMaxWait(jdbcMaxWait);
        ds.setTimeBetweenEvictionRunsMillis(jdbcTimeBetweenEvictionRunsMillis);
        ds.setMinEvictableIdleTimeMillis(jdbcMinEvictableIdleTimeMillis);
        ds.setValidationQuery(jdbcValidationQuery);
        ds.setValidationQueryTimeout(validationQueryTimeout);
        ds.setTestWhileIdle(jdbcTestWhileIdle);
        ds.setTestOnBorrow(jdbcTestOnBorrow);
        ds.setTestOnReturn(jdbcTestOnReturn);
        ds.setPoolPreparedStatements(jdbcPoolPreparedStatements);
        ds.setMaxPoolPreparedStatementPerConnectionSize(jdbcMaxPoolPreparedStatementPerConnectionSize);
        ds.setFilters(jdbcFilters);
        return ds;
    }


    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager() throws Exception {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource());
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        factory.setConfigLocation(resourceLoader.getResource("classpath:mybatis-config.xml"));
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] res = resolver.getResources("classpath:mapper/*.xml");
        factory.setMapperLocations(res);
        return factory.getObject();
    }


}

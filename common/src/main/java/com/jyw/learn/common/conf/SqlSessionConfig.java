package com.jyw.learn.common.conf;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Configuration
public class SqlSessionConfig {
    /**
     * 配置数据源
     * @return
     */
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return new DataSource();
    }

    /**
     * 事务管理器
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(@Autowired MybatisPlusInterceptor interceptor) throws IOException {
        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(dataSource());
        mybatisPlus.setVfs(SpringBootVFS.class);
//        String configLocation = this.properties.getConfigLocation();
//        if (StringUtils.isNotBlank(configLocation)) {
//            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(configLocation));
//        }
//        mybatisPlus.setConfiguration(properties.getConfiguration());
        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setMapUnderscoreToCamelCase(true);// 数据库和java都是驼峰，就不需要
        mybatisPlus.setConfiguration(mc);
        //注入分页 显式调用
        Interceptor[] plugins={interceptor};
        mybatisPlus.setPlugins(plugins);
//
//        mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
//        mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
//        mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
        // 设置mapper.xml文件的路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resource = resolver.getResources("classpath:mybatis/*.xml");
        mybatisPlus.setMapperLocations(resource);
        return mybatisPlus;
    }

    /**
     * mybatis-plus分页插件
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}

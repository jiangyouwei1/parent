package com.jyw.learn.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.jyw.learn.listener.InitListener;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class ConfigureClas {

    @Value("#{'${Jedis.masterhost}'.split(',')}")
    private List<String> hosts;
    @Value("#{'${Jedis.masterport}'.split(',')}")
    private List<Integer> ports;

    @Bean
    public JedisCluster jedisCluster(){
        System.out.println("长度：======="+hosts);
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        if(hosts.size()>0){
            int i = 0;
            for (String host : hosts) {
                System.out.println("主机IP："+host +";端口:"+ports.get(i));
                jedisClusterNodes.add(new HostAndPort(host,ports.get(i)));
                i++;
            }
        }
//        jedisClusterNodes.add(new HostAndPort("192.168.1.101",7001));
//        jedisClusterNodes.add(new HostAndPort("192.168.1.103",7003));
//        jedisClusterNodes.add(new HostAndPort("192.168.1.105",7005));
        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
        return jedisCluster;
    }
    /**
     * 注册监听器
     */
    @Bean
    public ServletListenerRegistrationBean registrationBean(){
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
        registrationBean.setListener(new InitListener());
        return registrationBean;
    }


}

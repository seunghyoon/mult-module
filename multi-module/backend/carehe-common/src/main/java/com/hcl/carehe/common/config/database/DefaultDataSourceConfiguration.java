package com.hcl.carehe.common.config.database;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.hcl.carehe.common.exception.FrameworkException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.springframework.core.io.Resource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
/**
*
* DefaultDataSourceConfiguration Config 관련 설정
* <p>
*
* <pre>
* 개정이력(Modification Information)·
* 수정일   수정자    수정내용
* ------------------------------------
* 2022.12.05 seunghyoon 최초작성
* </pre>
*
* @author seunghyoon@jcft.co.kr
* @since 2023.02.09
* @version 1.0.0
* @see
*
*/
@Slf4j
@Configuration
@EnableConfigurationProperties
@EnableTransactionManagement
@MapperScan(basePackages = {"${spring.mybatis.base-packages[0]}"}, 
			sqlSessionFactoryRef="defaultDbSqlSessionFactory")
public class DefaultDataSourceConfiguration implements ApplicationListener<ApplicationStartedEvent> {
	
	
	
	/*
	 * Tomcat Jndi 
	 */
	
  	@Value("${spring.datasource.default-db.driver-class-name}") 
  	private String driverClassName;
    
    
    
    @Value("${spring.datasource.default-db.tomcat.initial-size}") 
  	private String initialSize;
    
    @Value("${spring.datasource.default-db.tomcat.max-total}") 
  	private String maxTotal;
    
    @Value("${spring.datasource.default-db.tomcat.min-idle}") 
  	private String minIdle;
    
    @Value("${spring.datasource.default-db.tomcat.max-idle}") 
  	private String maxIdle;
    
    @Value("${spring.datasource.default-db.tomcat.max-wait-millis}") 
  	private String maxWaitMillis;
    
    @Value("${spring.datasource.default-db.tomcat.validation-query}") 
  	private String validationQuery;
	
    @Value("${spring.datasource.default-db.tomcat.factory}") 
  	private String factory;
    
    @Value("${spring.datasource.default-db.jndi-name}") 
  	private String jndiName;
    
    @Value("${spring.datasource.default-db.resource-name}") 
  	private String resourceName;
    
	
	@Bean
	TomcatServletWebServerFactory tomcatFactory() {
		return new TomcatServletWebServerFactory() {
			
			@Override
			protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
				tomcat.enableNaming();
				return new TomcatWebServer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {
				ContextResource resource = new ContextResource();
				resource.setName(resourceName);
				resource.setType(DataSource.class.getName());
				resource.setProperty("driverClassName", driverClassName);
				resource.setProperty("url", jdbcUrl);
				resource.setProperty("username", username);
				resource.setProperty("password", password);
				resource.setProperty("initialSize", initialSize);
				resource.setProperty("maxTotal", maxTotal);
				resource.setProperty("minIdle", minIdle);
				resource.setProperty("maxIdle", maxIdle);
				resource.setProperty("maxWaitMillis", maxWaitMillis);
				resource.setProperty("validationQuery", validationQuery);
				resource.setProperty("factory", factory);
				context.getNamingResources().addResource(resource);
			}
		};
	}
    
    
    
	
	@Value("${spring.datasource.default-db.type}") 
	private String type;
	
	
	@Value("${spring.datasource.default-db.username}") 
	private String username;
	
	@Value("${spring.datasource.default-db.password}") 
	private String password;
	
	@Value("${spring.datasource.default-db.jdbc-url}") 
	private String jdbcUrl;
	
	
	@Value("${spring.datasource.default-db.hikari.maximum-pool-size}") 
	private Integer maximumPoolSize;
	
	@Value("${spring.datasource.default-db.hikari.minimum-idle}") 
	private Integer minimumIdle;
	
	@Value("${spring.datasource.default-db.hikari.connection-timeout}") 
	private Long connectionTimeout;
	
	@Value("${spring.datasource.default-db.hikari.idle-timeout}") 
	private Long idleTimeout;
	
	@Value("${spring.datasource.default-db.hikari.max-lifetime}") 
	private Long maxLifetime;
	

	@Primary
    @Bean(name="defaultDbDataSource")//, destroyMethod = "close")
    DataSource dataSource() {
		log.debug("DefaultDataSourceConfiguration.dataSource() type 3 => {} ", type);
		
		if("POOL".equals(type)) {
			HikariConfig hikariConfig = new HikariConfig();
			 
	        hikariConfig.setUsername(username);
	        hikariConfig.setPassword(password);
	        hikariConfig.setJdbcUrl(jdbcUrl);
	        hikariConfig.setMaximumPoolSize(maximumPoolSize); 
	        hikariConfig.setMinimumIdle(minimumIdle);
	        hikariConfig.setConnectionTimeout(connectionTimeout);
	        hikariConfig.setIdleTimeout(idleTimeout);
	        hikariConfig.setMaxLifetime(maxLifetime);
	        return new HikariDataSource(hikariConfig); 
		}else if("JNDI".equals(type)) {
			JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
			bean.setJndiName(jndiName);
			bean.setProxyInterface(DataSource.class);
			bean.setLookupOnStartup(true);
			try {
				bean.afterPropertiesSet();
			} catch (IllegalArgumentException e) {
				throw new FrameworkException(e);
			} catch (NamingException e) {
				throw new FrameworkException(e);
			}
			return (DataSource)bean.getObject();
		}else {
			return DataSourceBuilder.create()
					.driverClassName(driverClassName)
						.password(password)
							.url(jdbcUrl)
								.username(username)
									.build();
			
		}
		
    }
	    

	@Value("${spring.mybatis.config-location}") 
	private String mybatisConfigLocation;
	

	//@Value("${spring.mybatis.mapper-locations}") 
	//private String mybatisMapperLocations;
	@Value("#{'${spring.mybatis.mapper-locations}'.split(',')}") 
	private String[] mapperLocationArray;
	
	
	@Value("${spring.mybatis.type-aliases-package}") 
	private String mybatisTypeAliasesPackage;
	
	@Bean(name = "defaultDbSqlSessionFactory")
    SqlSessionFactory getDbSqlSessionFactory(@Qualifier("defaultDbDataSource") DataSource defaultDbDataSource, 
    		@Qualifier("resolveMapperLocations") Resource[] resolveMapperLocations, 
    		ApplicationContext applicationContext) { 
    	SqlSessionFactoryBean sqlSessionFactoryBean = null;
        try {
        	sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(defaultDbDataSource);
            sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource(mybatisConfigLocation));
            sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations);
            sqlSessionFactoryBean.setTypeAliasesPackage(mybatisTypeAliasesPackage);
            return sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
    }
	
	
	@Bean(name = "resolveMapperLocations")
    public Resource[] resolveMapperLocations() {
    	ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>();
        if (mapperLocationArray != null && mapperLocationArray.length > 0) {
        	for(int i = 0; i < mapperLocationArray.length; i++) {
                try {
                    Resource[] mappers = (Resource[]) resourceResolver.getResources(mapperLocationArray[i]);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                	throw new FrameworkException(e);
                }
            }
        }
        
        return resources.toArray(new Resource[resources.size()]);
    }
	
	
	
	
	
    @Bean(name = "defaultDbSqlSessionTemplate", destroyMethod = "clearCache")
    SqlSessionTemplate defaultDbSqlSessionTemplate(SqlSessionFactory sqldbSqlSessionFactory){ 
    	try {
    		return new SqlSessionTemplate(sqldbSqlSessionFactory);
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
    	
    }
    
    @Value("#{'${spring.jpa.packages-to-scan}'.split(',')}") 
	private String[] jpaPackagesToScan;
    
    @Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("defaultDbDataSource") DataSource defaultDbDataSource) {
	   
    	LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
    	entityManagerFactory.setDataSource(defaultDbDataSource);
    	entityManagerFactory.setPersistenceUnitName("defaultDbDataSource");
    	entityManagerFactory.setPackagesToScan(jpaPackagesToScan);
    	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    	entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
    	entityManagerFactory.setJpaProperties(additionalProperties());
	   
	   return entityManagerFactory;
	}
    
	@Bean
	PlatformTransactionManager transactionManager(@Qualifier("defaultDbDataSource") DataSource defaultDbDataSource) {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(entityManagerFactory(defaultDbDataSource).getObject());
	    
	    return transactionManager;
	}
	
	@Bean
	PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	    return new PersistenceExceptionTranslationPostProcessor();
	}
    
    
    @Value("${spring.jpa.hibernate.ddl-auto}") 
	private String jpaHibernateDdlAuto;
	
	@Value("${spring.jpa.show-sql}") 
	private String jpaShowSql;
	
	@Value("${spring.jpa.properties.hibernate.format_sql}") 
	private String jpaFormatSql;
	
	@Value("${spring.jpa.properties.hibernate.highlight_sql}") 
	private String jpaHighlightSql;
	
	@Value("${spring.jpa.database-platform}") 
	private String jpaDatabasePlatform;
	
	@Value("${spring.jpa.properties.hibernate.allow_update_outside_transaction}") 
	private String jpaAllowUpdateOutsideTransaction;
	
	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("spring.jpa.hibernate.hbm2ddl.auto", jpaHibernateDdlAuto);
	    properties.setProperty("spring.jpa.database-platform", jpaDatabasePlatform);
		properties.setProperty("spring.jpa.show-sql", jpaShowSql);
		properties.setProperty("spring.jpa.properties.hibernate.format_sql", jpaFormatSql);
		properties.setProperty("spring.jpa.properties.hibernate.highlight_sql", jpaHighlightSql);
		properties.setProperty("spring.jpa.properties.hibernate.allow_update_outside_transaction", jpaAllowUpdateOutsideTransaction);
		
		log.debug("DefaultDataSourceConfiguration.getJpaProperties properties => {}", properties);
		return properties;
	}

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
}

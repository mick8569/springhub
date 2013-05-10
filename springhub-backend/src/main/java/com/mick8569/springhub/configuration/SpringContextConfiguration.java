package com.mick8569.springhub.configuration;

import com.mick8569.springhub.commons.context.SpringApplicationContext;
import com.mick8569.springhub.dao.GenericDao;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

public abstract class SpringContextConfiguration {

	public abstract String persistenceUnitName();

	@Bean
	public abstract DataSource dataSource();

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactory.setPersistenceUnitName(persistenceUnitName());
		entityManagerFactory.setDataSource(dataSource());
		return entityManagerFactory;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}

	@Bean
	public ApplicationContextAware applicationContext() {
		return new SpringApplicationContext();
	}

	@Bean
	public GenericDao genericDao() {
		return new GenericDao();
	}
}

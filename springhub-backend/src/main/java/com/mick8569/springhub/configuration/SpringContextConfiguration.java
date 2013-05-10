package com.mick8569.springhub.configuration;

import com.mick8569.springhub.commons.context.SpringApplicationContext;
import com.mick8569.springhub.dao.GenericDao;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

public abstract class SpringContextConfiguration {

	/**
	 * Configure sql debugging.
	 *
	 * @return True to show sql, false otherwise.
	 */
	protected boolean showSql() {
		return false;
	}

	/**
	 * Return root package of jpa entities.
	 *
	 * @return Root package of jpa entities.
	 */
	protected abstract String packagesToScan();

	/**
	 * Get custom jpa properties added to configuration.
	 *
	 * @return JPA Properties added to configuration.
	 */
	protected Map<String, Object> jpaProperties() {
		return null;
	}

	/**
	 * Get dialect class to use with hibernate.
	 *
	 * @return Dialect class.
	 */
	protected Class dialect() {
		return null;
	}

	@Bean
	public abstract DataSource dataSource();

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setPackagesToScan(packagesToScan());

		Class dialect = dialect();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(showSql());
		if (dialect != null) {
			vendorAdapter.setDatabasePlatform(dialect.getName());
		}

		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

		// Set custom JPA properties
		Map<String, Object> properties = jpaProperties();
		if (properties != null && !properties.isEmpty()) {
			Properties props = new Properties();
			for (Map.Entry<String, Object> e : properties.entrySet()) {
				props.setProperty(e.getKey(), e.getValue().toString());
			}
			entityManagerFactory.setJpaProperties(props);
		}

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

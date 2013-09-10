package com.mjeanroy.springhub.test.configuration;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public abstract class DaoConfiguration {

	public boolean showSql() {
		return false;
	}

	public abstract String packagesToScan();

	public Map<String, Object> jpaProperties() {
		return null;
	}

	/**
	 * Test datasource.
	 *
	 * @return Datasource used for tests database.
	 */
	@Bean(destroyMethod = "shutdown")
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactory.setDataSource(dataSource());

		// Scan entities
		entityManagerFactory.setPackagesToScan(packagesToScan());

		// Set hibernate properties
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(showSql());
		vendorAdapter.setDatabase(Database.H2);
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

		// Set custom JPA properties
		Properties props = new Properties();
		props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		props.setProperty("hibernate.connection.tinyInt1isBit", "true");
		props.setProperty("hibernate.connection.transformedBitIsBoolean", "true");
		props.setProperty("hibernate.default_batch_fetch_size", "100");

		Map<String, Object> properties = jpaProperties();
		if (properties != null && !properties.isEmpty()) {
			for (Map.Entry<String, Object> e : properties.entrySet()) {
				props.setProperty(e.getKey(), e.getValue().toString());
			}
		}

		entityManagerFactory.setJpaProperties(props);

		return entityManagerFactory;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setDataSource(dataSource());
		return txManager;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
}

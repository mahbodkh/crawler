package app.finology.conf;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Ebrahim Kh.
 */


//@Configuration
//@EnableJpaRepositories("app.finology.repository")
//@EnableTransactionManagement
public class StandaloneDataJpaConfig {

//	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
//				.addScript("classpath:db/schema.sql")
//				.addScript("classpath:db/import-users.sql")
				.build();
	}

//	@Bean
//	public PlatformTransactionManager transactionManager() {
//
//		JpaTransactionManager txManager = new JpaTransactionManager();
//		txManager.setEntityManagerFactory(entityManagerFactory());
//		return txManager;
//	}

//	@Bean
//	public HibernateExceptionTranslator hibernateExceptionTranslator() {
//		return new HibernateExceptionTranslator();
//	}
//
//	@Bean
//	public EntityManagerFactory entityManagerFactory() {
//
//		// will set the provider to 'org.hibernate.ejb.HibernatePersistence'
//		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		// will set hibernate.show_sql to 'true'
//		vendorAdapter.setShowSql(true);
//		// if set to true, will set hibernate.hbm2ddl.auto to 'update'
//		vendorAdapter.setGenerateDdl(false);
//
//		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//		factory.setJpaVendorAdapter(vendorAdapter);
//		factory.setPackagesToScan("app.finology.domain");
//		factory.setDataSource(dataSource());
//
//		// This will trigger the creation of the entity manager factory
//		factory.afterPropertiesSet();
//
//		return factory.getObject();
//	}

}

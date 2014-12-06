package pl.edu.agh.pp.hitchhiker.webservice.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.repository.context.AnnotatedHandlerRepositoryEventListener;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import pl.edu.agh.pp.hitchhiker.service.gcm.SendingNotificationsService;
import pl.edu.agh.pp.hitchhiker.service.gcm.SendingNotificationsServiceImpl;
import pl.edu.agh.pp.hitchhiker.webservice.api.HitchhikerResourceAssembler;
import pl.edu.agh.pp.hitchhiker.webservice.handlers.DriverEventHandler;
import pl.edu.agh.pp.hitchhiker.webservice.handlers.HitchhikerEventHandler;
import pl.edu.agh.pp.hitchhiker.webservice.handlers.UserEventHandler;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Main application configuration class, it involves configuring database, data source, entity manager factory,
 * transaction manager and configuring all Spring beans
 * @author patrykkurczyna
 *
 */
@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
@EnableWebMvc
@ImportResource("classpath:applicationContext.xml")
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "pl.edu.agh.pp.hitchhiker.webservice.controllers")
public class AppConfiguration {

	private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
	private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
	private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
	private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";

	private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

	@Resource
	private Environment environment;
	
	/**
	 * Data source configuration
	 * @return {@link DataSource}
	 */
	@Bean
	public DataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();

		dataSource.setDriverClass(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setJdbcUrl(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(environment
				.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));

		return dataSource;
	}
	
	/**
	 * Entity manager factory configuration
	 * @return {@link EntityManagerFactory}
	 * @throws ClassNotFoundException
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean()
			throws ClassNotFoundException {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setGenerateDdl(true);

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean
				.setPackagesToScan(environment
						.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
		entityManagerFactoryBean
				.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

		Properties jpaProterties = new Properties();
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
		jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment
				.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

		entityManagerFactoryBean.setJpaProperties(jpaProterties);

		return entityManagerFactoryBean;
	}
	
	/**
	 * Notification service config
	 * @return {@link SendingNotificationsService}
	 */
	@Bean
	public SendingNotificationsService sendingNotificationsService() {
		return new SendingNotificationsServiceImpl();
	}

	/**
	 * Hitchhiker resource assembler bean initialization
	 * @return {@link HitchhikerResourceAssembler}
	 */
	@Bean
	public HitchhikerResourceAssembler hitchhikerResourceAssembler() {
		return new HitchhikerResourceAssembler();
	}
	
	/**
	 * Transaction manager configuration
	 * @return {@link TransactionManager}
	 * @throws ClassNotFoundException
	 */
	@Bean
	public PlatformTransactionManager transactionManager()
			throws ClassNotFoundException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();

		transactionManager.setEntityManagerFactory(entityManagerFactoryBean()
				.getObject());

		return transactionManager;
	}
	
	/**
	 * Hitchhiker event handler init
	 * @return {@link HitchhikerEventHandler}
	 */
	@Bean
	HitchhikerEventHandler hitchhikerEventHandler() {
		return new HitchhikerEventHandler();
	}
	
	/**
	 * User event handler init
	 * @return {@link UserEventHandler}
	 */
	@Bean
	UserEventHandler userEventHandler() {
		return new UserEventHandler();
	}
	
	/**
	 * Driver event handler init
	 * @return {@link DriverEventHandler}
	 */
	@Bean
	DriverEventHandler driverEventHandler() {
		return new DriverEventHandler();
	}
	
	/**
	 * Configuration for listening handlers
	 * @return {@link AnnotatedHandlerRepositoryEventListener}
	 */
	@Bean
	AnnotatedHandlerRepositoryEventListener repositoryEventListener() {
		return new AnnotatedHandlerRepositoryEventListener(
				"pl.edu.agh.pp.hitchhiker.webservice.handlers");
	}
}

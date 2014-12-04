package pl.edu.agh.pp.hitchhiker.service.gcm;
import java.util.Properties;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.repository.context.AnnotatedHandlerRepositoryEventListener;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import pl.edu.agh.pp.hitchhiker.service.gcm.SendingNotificationsService;
import pl.edu.agh.pp.hitchhiker.webservice.handlers.DriverEventHandler;
import pl.edu.agh.pp.hitchhiker.webservice.handlers.HitchhikerEventHandler;
import pl.edu.agh.pp.hitchhiker.webservice.handlers.UserEventHandler;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
@EnableWebMvc
@ImportResource({"classpath:spring-mvc.xml","classpath:embeddedDBapplicationContext.xml"})
@ComponentScan(basePackages = "pl.edu.agh.pp.hitchhiker.webservice.controllers")
public class TestConfiguration {
	
	@Resource
	private Environment environment;
	
	private static final String H2_JDBC_URL_TEMPLATE = "jdbc:h2:%s/target/db/sample;AUTO_SERVER=TRUE";

	@Value("classpath:geolocationData.sql")
	private org.springframework.core.io.Resource H2_DATA_SCRIPT;

	@Bean
	public DataSource dataSource(Environment env) throws Exception {
	        return createH2DataSource();
	}

	@Autowired
	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {

	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
	}


	private DatabasePopulator databasePopulator() {
	    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(H2_DATA_SCRIPT);
	    return populator;
	}

	private DataSource createH2DataSource() {
	    String jdbcUrl = String.format(H2_JDBC_URL_TEMPLATE, System.getProperty("user.dir"));
	    JdbcDataSource ds = new JdbcDataSource();       
	    ds.setURL(jdbcUrl);
	    ds.setUser("sa");
	    ds.setPassword("");

	    return ds;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(entityManagerFactory);
	    return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(Environment env) throws Exception {
	    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(Boolean.TRUE);
	    vendorAdapter.setShowSql(Boolean.TRUE);     

	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setPersistenceUnitName("sample");
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("pl.edu.agh.pp.hitchhiker.webservice.repository");
	    factory.setPackagesToScan("pl.edu.agh.pp.hitchhiker.webservice.model");
	    factory.setDataSource(dataSource(env));     

	    factory.setJpaProperties(jpaProperties());
	    factory.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());

	    return factory;
	}

	Properties jpaProperties() {
	    Properties props = new Properties();
	    props.put("hibernate.query.substitutions", "true 'Y', false 'N'");
	    props.put("hibernate.hbm2ddl.auto", "create-drop");
	    props.put("hibernate.show_sql", "false");
	    props.put("hibernate.format_sql", "true");

	    return props;
	}


	@Bean
	public SendingNotificationsService sendingNotificationsService(){
		return new SendingNotificationsService();
	}

	@Bean
	HitchhikerEventHandler hitchhikerEventHandler() {
		return new HitchhikerEventHandler();
	}
	
	@Bean
	UserEventHandler userEventHandler() {
		return new UserEventHandler();
	}
	
	@Bean
	DriverEventHandler driverEventHandler() {
		return new DriverEventHandler();
	}

	@Bean
	AnnotatedHandlerRepositoryEventListener repositoryEventListener() {
		return new AnnotatedHandlerRepositoryEventListener(
				"pl.edu.agh.pp.hitchhiker.webservice.handlers");
	}
}

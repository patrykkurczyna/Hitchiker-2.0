package pl.edu.agh.pp.hitchhiker.webservice.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * Configuration class for populating database
 * @author patrykkurczyna
 *
 */
@Configuration
public class DatabasePopulatorConfig {

	@Value("classpath:data.sql")
	private Resource dataScript;
	
	/**
	 * Initialize database 
	 * @param dataSource data source which is going to be initialized
	 * @return {@link DataSourceInitializer}
	 */
	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
	    final DataSourceInitializer initializer = new DataSourceInitializer();
	    initializer.setDataSource(dataSource);
	    initializer.setDatabasePopulator(databasePopulator());
	    return initializer;
	}
	
	/**
	 * Populates database with data retrieved from script file
	 * @return {@link DatabasePopulator}
	 */
	private DatabasePopulator databasePopulator() {
	    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(dataScript);
	    return populator;
	}
}

package pl.edu.agh.pp.hitchhiker.webservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.rest.webmvc.RepositoryRestMvcConfiguration;


/**
 * Configuration of web application
 * @author patrykkurczyna
 *
 */
@Configuration
public class WebConfiguration extends RepositoryRestMvcConfiguration {
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
package loanplatform.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class BeanConfigs {
	private static final Logger logger = LoggerFactory.getLogger(BeanConfigs.class);

	@Autowired
	private Environment env;
}

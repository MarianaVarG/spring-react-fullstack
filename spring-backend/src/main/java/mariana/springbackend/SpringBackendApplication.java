package mariana.springbackend;

import mariana.springbackend.models.responses.UserRest;
import mariana.springbackend.security.AppProperties;
import mariana.springbackend.share.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBackendApplication {

	static final Logger LOGGER = LoggerFactory.getLogger(SpringBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBackendApplication.class, args);
		LOGGER.info("Working c:");
	}

	/**
	 * A single instance is creates for the ENTIRE project
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

	@Bean
	public SpringApplicationContext springApplicationContext() { return new SpringApplicationContext(); }

	@Bean(name = "AppProperties")
	public AppProperties getAppProperties() { return new AppProperties(); }

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		// In order to not mapping posts and not create a buckle
		mapper.typeMap(UserDto.class, UserRest.class).addMappings(m->m.skip(UserRest::setPosts));
		return mapper;
	}

}

package com.pingidentity.oidclogin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = { ErrorMvcAutoConfiguration.class })
public class OIDCLoginApplication {

	Logger logger = LoggerFactory.getLogger(OIDCLoginApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OIDCLoginApplication.class, args);
	}
	
	// This template is used to make anonymous calls to the P14C authentication APIs	
	@Bean
	public RestTemplate defaultRestTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	// This template is used to inject a client credential token to the P14C APIs (for user creation and other admin tasks)
	@Bean
	public RestTemplate customRestTemplate(ClientRegistrationRepository clientRegistrationRepository) {
		logger.info("Starting customRestTemplate");
		return new RestTemplateBuilder()
				.interceptors(new OAuth2HttpRequestInterceptor(clientRegistrationRepository.findByRegistrationId(Consts.WORKER_APP_ID))).build();
	}

}

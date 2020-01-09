package com.cg.rms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cg.rms.configuration.AuditorAwareImpl;

/**
 * @author Saurabh
 *
 */

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class RMSConfiguration {
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

}

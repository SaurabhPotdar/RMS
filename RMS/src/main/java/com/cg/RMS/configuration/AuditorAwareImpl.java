package com.cg.rms.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

/**
 * @author Saurabh
 *
 */
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of("Saurabh");
	}

}

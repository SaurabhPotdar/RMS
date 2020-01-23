package com.cg.rms.configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;


import org.springframework.data.domain.AuditorAware;

/**
 * @author Saurabh
 *
 */
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		try {
			return Optional.of(InetAddress.getLocalHost().toString());
		} catch (UnknownHostException exception) {
		exception.printStackTrace();
		}
		return null;
	}

}

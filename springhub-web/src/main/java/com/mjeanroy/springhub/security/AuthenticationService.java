package com.mjeanroy.springhub.security;

import java.io.Serializable;

public interface AuthenticationService<PK extends Serializable> {

	/**
	 * Check if value encrypted in
	 *
	 * @param value Value stored in session than must be used
	 * @return Authentication id, null if authentication cannot be parsed.
	 */
	PK parseAuthenticationId(char[] value);

	/**
	 * Find authenticated user by its authentication id.
	 *
	 * @param authenticationId Authenticatin id.
	 * @return AuthenticatedUser user, null if user does not exist.
	 */
	AuthenticatedUser<PK> findByAuthenticationId(PK authenticationId);
}

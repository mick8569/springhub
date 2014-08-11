package com.mjeanroy.springhub.security;

import java.io.Serializable;

/**
 * User that can be authenticated.
 *
 * @param <PK> Type of authentication Id.
 */
public interface AuthenticatedUser<PK extends Serializable> {

	/**
	 * Get authentication id of authenticated user.
	 *
	 * @return AuthenticatedUser Id.
	 */
	PK getAuthenticationId();

}

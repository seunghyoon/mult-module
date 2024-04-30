package com.hcl.carehe.app.api.user.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Builder
@Data
public class CustomUserDetailEntity implements UserDetails, CredentialsContainer{

	private Collection<? extends GrantedAuthority> authorities;

	/**
	 * Returns the password used to authenticate the user.
	 * @return the password
	 */
	private String password;

	/**
	 * Returns the username used to authenticate the user. Cannot return
	 * <code>null</code>.
	 * @return the username (never <code>null</code>)
	 */
	private String username;

	/**
	 * Indicates whether the user's account has expired. An expired account cannot be
	 * authenticated.
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	private boolean accountNonExpired;

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */
	private boolean accountNonLocked;

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 * @return <code>true</code> if the user's credentials are valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
	private boolean credentialsNonExpired;

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	private boolean enabled;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	public void eraseCredentials() {
		this.password = null;
	}
	
}

package edu.ucdavis.ucdh.stu.core.security;

import java.security.Principal;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import edu.ucdavis.ucdh.stu.core.manager.UserManager;

/**
 * <p>Loads user-specific data using the specified UserManager.</p>
 */
@SuppressWarnings("rawtypes")
public class SpringUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService {
	private Log log = LogFactory.getLog(getClass());
	private UserManager userManager;
	private String context;

	public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
		UserDetails user = null;
		String principal = null;
		if (authentication != null) {
			try {
				principal = (String) authentication.getPrincipal();
				if (log.isDebugEnabled()) {
					log.debug("Using principal String " + principal);
				}
			} catch (ClassCastException e) {
				try {
					principal = ((Principal) authentication.getPrincipal()).getName();
					if (log.isDebugEnabled()) {
						log.debug("Using principal.getName(): " + principal);
					}
				} catch (Exception e1) {
					log.error("Unable to obtain principal name from " + authentication.getPrincipal(), e1);
				}
			}
		}
		if (StringUtils.isNotBlank(principal)) {
			user = loadUserByUsername(principal);
		}
		return user;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		UserDetails user = null;
		try {
			user = userManager.findByContextAndName(context, username);
		} catch (Throwable t) {
			log.error("Exception encountered attempting to obtain user " + username + " in context " + context, t);
		}
		if (user != null) {
			if (log.isDebugEnabled()) {
				log.debug("Returning user " + user);
			}
		} else {
			if (log.isDebugEnabled()) {
				log.debug("User " + username + " not found in context " + context);
			}
			throw new UsernameNotFoundException("User " + username + " not found in context " + context);
		}
		return user;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
}

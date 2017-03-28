package com.playground.phoneword.util;

import java.security.Permission;

/**
 * This allows us to catch the system exit status when testing command line functionality. 
 * 
 * @author	Justin Taylor
 * @version	%I%, %G%
 * @since 1.0
 */
public class ExitDeniedSecurityManager extends SecurityManager {
	 
    public static final class ExitSecurityException extends SecurityException {
		private static final long serialVersionUID = 1L;
		private final int status;
 
        public ExitSecurityException(final int status) {
            this.status = status;
        }
 
        public int getStatus() {
            return this.status;
        }
    }
 
    @Override
    public void checkExit(final int status) {
        throw new ExitSecurityException(status);
    }
 
    @Override
    public void checkPermission(final Permission perm) {}
}
/*******************************************************************************
 * Software Name : RCS IMS Stack
 * Version : 2.0
 * 
 * Copyright � 2010 France Telecom S.A.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.orangelabs.rcs.core.ims.service;

import javax.sip.header.ProxyAuthenticateHeader;
import javax.sip.header.ProxyAuthorizationHeader;

import com.orangelabs.rcs.core.CoreException;
import com.orangelabs.rcs.core.ims.ImsModule;
import com.orangelabs.rcs.core.ims.protocol.sip.SipRequest;
import com.orangelabs.rcs.core.ims.protocol.sip.SipResponse;
import com.orangelabs.rcs.core.ims.security.HttpDigestMd5Authentication;
import com.orangelabs.rcs.utils.logger.Logger;

/**
 * HTTP Digest MD5 authentication agent for sessions
 * 
 * @author JM. Auffret
 */
public class SessionAuthenticationAgent {
	/**
     * The logger
     */
    private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * HTTP Digest MD5 agent
	 */
	private HttpDigestMd5Authentication digest = new HttpDigestMd5Authentication();

	/**
	 * Constructor
	 */
	public SessionAuthenticationAgent() {
		super();
	}

	/**
	 * Set the proxy authorization header on the INVITE request
	 * 
	 * @param request SIP request
	 * @throws CoreException
	 */
	public void setProxyAuthorizationHeader(SipRequest request) throws CoreException {
		if ((digest.getRealm() == null) || (digest.getNextnonce() == null)) {
			return;
		}

		try {
	   		// Update nonce parameters
			digest.updateNonceParameters();
	
			// Calculate response
			String user = ImsModule.IMS_USER_PROFILE.getPrivateID();
			String password = ImsModule.IMS_USER_PROFILE.getPassword();
	   		String response = digest.calculateResponse(user,
	   				password,
	   				request.getMethod(),
	   				request.getRequestURI(),
					digest.buildNonceCounter(),
					request.getContent());			
	   		
			// Build the Authorization header
			String auth = "Digest username=\"" + ImsModule.IMS_USER_PROFILE.getPrivateID() +
				"\",uri=\"" + request.getRequestURI() +
				"\",algorithm=MD5,realm=\"" + digest.getRealm() +
				"\",nc=" + digest.buildNonceCounter() +
				",nonce=\"" + digest.getNonce() +
				"\",response=\"" + response +
				"\",cnonce=\"" + digest.getCnonce() + "\"";
			String qop = digest.getQop();
			if (qop != null) {
				auth += ",qop=" + qop;
			}
			
			// Set header in the SIP message 
			request.addHeader(ProxyAuthorizationHeader.NAME, auth);

		} catch(Exception e) {
			if (logger.isActivated()) {
				logger.error("Can't create the proxy authorization header", e);
			}
			throw new CoreException("Can't create the proxy authorization header");
		}
    }

	/**
	 * Read parameters of the Proxy-Authenticate header
	 * 
	 * @param response SIP response
	 */
	public void readProxyAuthenticateHeader(SipResponse response) {
		ProxyAuthenticateHeader header = (ProxyAuthenticateHeader)response.getHeader(ProxyAuthenticateHeader.NAME);
		if (header != null) {
	   		// Get domain name
			digest.setRealm(header.getRealm());

			// Get qop
			digest.setQop(header.getQop());
	   		
	   		// New nonce to be used
			digest.setNextnonce(header.getNonce());
		}
	}	
}

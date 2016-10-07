package com.afkl.cases.df.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Component/**
 * responsible for obtaining oauth2 token
 * @author megha
 *
 */

public class AccessTokenHelper {

	@Value("${token.url}")
	private String uri;
	
	@Value("${token.username}")
	private String username;
	
	@Value("${token.password}")
	private String password;
	
	@Value("${grant.type}")
	private String grantType;
	
	private static final String GRANT_TYPE = "grant_type";
	
	BasicAuthRestTemplate restTemplate;
	
	/**
	 * obtain access token for token service of mock api
	 * @return
	 */
	public  AccessTokenResponse obtainAccessToken(){
		restTemplate = new BasicAuthRestTemplate();
		MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
		form.set(GRANT_TYPE, grantType);
		
		restTemplate.addAuthentication(username, password);
		
		
		ResponseEntity<AccessTokenResponse> response =  restTemplate.postForEntity(uri, form, AccessTokenResponse.class);
		return response.getBody();
	} 
}

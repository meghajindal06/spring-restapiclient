package com.afkl.cases.df.oauth;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.security.crypto.codec.Base64;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Service
public class BasicAuthRestTemplate extends RestTemplate implements RestOperations {
	
	

	public void addAuthentication(String username, String password) {
		if (username == null) {
			return;
		}
		List<ClientHttpRequestInterceptor> interceptors = Collections
				.<ClientHttpRequestInterceptor> singletonList(
						new BasicAuthorizationInterceptor(username, password));
		setRequestFactory(new InterceptingClientHttpRequestFactory(getRequestFactory(),
				interceptors));
	}

	private static class BasicAuthorizationInterceptor implements
			ClientHttpRequestInterceptor {

		private final String username;

		private final String password;

		public BasicAuthorizationInterceptor(String username, String password) {
			this.username = username;
			this.password = (password == null ? "" : password);
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			byte[] token = Base64.encode(
					(this.username + ":" + this.password).getBytes());
			request.getHeaders().add("Authorization", "Basic " + new String(token));
			
			return execution.execute(request, body);
		}

	}
	
}
package com.afkl.cases.df.oauth;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class OAuth2RestTemplate extends RestTemplate implements RestOperations{

	public void addAuthentication(String accessToken) {
		if (accessToken == null) {
			return;
		}
		List<ClientHttpRequestInterceptor> interceptors = Collections
				.<ClientHttpRequestInterceptor> singletonList(
						new OAuth2AuthorizationInterceptor(accessToken));
		setRequestFactory(new InterceptingClientHttpRequestFactory(getRequestFactory(),
				interceptors));
	}

	private class OAuth2AuthorizationInterceptor implements
			ClientHttpRequestInterceptor {

		private String accessToken;

		
		public OAuth2AuthorizationInterceptor(String accessToken) {
			this.accessToken = accessToken;
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			request.getHeaders().add("Authorization", "Bearer " + accessToken);
			
			return execution.execute(request, body);
		}

	}
}

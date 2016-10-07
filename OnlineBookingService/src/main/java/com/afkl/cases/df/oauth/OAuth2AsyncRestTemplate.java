package com.afkl.cases.df.oauth;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.AsyncClientHttpRequestExecution;
import org.springframework.http.client.AsyncClientHttpRequestFactory;
import org.springframework.http.client.AsyncClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.InterceptingAsyncClientHttpRequestFactory;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

public class OAuth2AsyncRestTemplate extends AsyncRestTemplate {

	public void addAuthentication(String accessToken) {
		if (accessToken == null) {
			return;
		}
		List<AsyncClientHttpRequestInterceptor> interceptors = Collections
				.<AsyncClientHttpRequestInterceptor> singletonList(
						new OAuth2AuthorizationInterceptor(accessToken));
		setAsyncRequestFactory(new InterceptingAsyncClientHttpRequestFactory(getAsyncRequestFactory(), interceptors));
	}

	private class OAuth2AuthorizationInterceptor implements
	AsyncClientHttpRequestInterceptor {

		private String accessToken;

		
		public OAuth2AuthorizationInterceptor(String accessToken) {
			this.accessToken = accessToken;
		}

		

		@Override
		public ListenableFuture<ClientHttpResponse> intercept(HttpRequest request, byte[] body,
				AsyncClientHttpRequestExecution execution) throws IOException {
			request.getHeaders().add("Authorization", "Bearer " + accessToken);
			
			return execution.executeAsync(request,body);
		}

	}
}

package com.afkl.cases.df.airport.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.afkl.cases.df.airport.resource.Location;
import com.afkl.cases.df.oauth.AccessTokenHelper;
import com.afkl.cases.df.oauth.AccessTokenResponse;
import com.afkl.cases.df.oauth.OAuth2RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AirportService {
	
	@Value("${airports.url}")
	private String airport_uri;
	
	@Value("${token.url}")
	private String uri;
	
	@Value("${token.username}")
	private String username;
	
	@Value("${token.password}")
	private String password;
	
	@Value("${grant.type}")
	private String grantType;
	
	AccessTokenHelper tokenHelper;
	
	OAuth2RestTemplate oAuthRestTemplate;
	
	
public List<Location> getAirportList(){
		
		tokenHelper = new AccessTokenHelper();
		AccessTokenResponse accessToken = tokenHelper.obtainAccessToken(uri,username,password,grantType);
		
		List<Location> airports = retrieveAirportList( accessToken);
		return airports;
	}



	/**
	 * call mock api to retrieve airport list
	 * @param accessToken
	 * @return
	 */
	private List<Location> retrieveAirportList( AccessTokenResponse accessToken) {
		
		List<Location> airports = new ArrayList<>();
		
		oAuthRestTemplate = (OAuth2RestTemplate)getRestTemplateWithHalMessageConverter();
		oAuthRestTemplate.addAuthentication(accessToken.getAccess_token());
		ResponseEntity<PagedResources<Resource<Location>>> response = oAuthRestTemplate.exchange(airport_uri,HttpMethod.GET, null,new TypeReferences.PagedResourcesType<Resource<Location>>(){});
		
		for (Resource<Location> resource : response.getBody().getContent()) {
			Location airport = resource.getContent();
			airports.add(airport);
         }
		
		return airports;
	}
	
	
	
	/**
	 * get an instance of Rest template with converters to parse paged resources
	 * @return
	 */
	public RestTemplate getRestTemplateWithHalMessageConverter() {
		 RestTemplate restTemplate = new OAuth2RestTemplate();
		 List<HttpMessageConverter<?>> existingConverters = restTemplate.getMessageConverters();
		 List<HttpMessageConverter<?>> newConverters = new ArrayList<>();
		 newConverters.add(getHalMessageConverter());
		 newConverters.addAll(existingConverters);
		 restTemplate.setMessageConverters(newConverters);
		 return restTemplate;
		}

		private HttpMessageConverter getHalMessageConverter() {
		 ObjectMapper objectMapper = new ObjectMapper();
		 objectMapper.registerModule(new Jackson2HalModule());
		 MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
		 halConverter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{MediaTypes.HAL_JSON}));
		 halConverter.setObjectMapper(objectMapper);
		 return halConverter;
		}
	
	
	
}

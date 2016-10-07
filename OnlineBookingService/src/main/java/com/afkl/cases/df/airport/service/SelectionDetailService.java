package com.afkl.cases.df.airport.service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.afkl.cases.df.airport.resource.Fare;
import com.afkl.cases.df.airport.resource.Location;
import com.afkl.cases.df.airport.resource.SelectionDetail;
import com.afkl.cases.df.exception.ApiCallException;
import com.afkl.cases.df.oauth.AccessTokenHelper;
import com.afkl.cases.df.oauth.AccessTokenResponse;
import com.afkl.cases.df.oauth.OAuth2AsyncRestTemplate;

@Service
public class SelectionDetailService {

	AccessTokenHelper tokenHelper;
	
	OAuth2AsyncRestTemplate oAuthRestTemplate;
	
	@Value("${airportbase.url}")
	private String airportbase_uri;
	
	@Value("${fare.url}")
	private String fare_uri;
	
	
	
	@Value("${token.url}")
	private String uri;
	
	@Value("${token.username}")
	private String username;
	
	@Value("${token.password}")
	private String password;
	
	@Value("${grant.type}")
	private String grantType;
	
	
	
	private final String SLASH = "/";
	
	public SelectionDetail getJourneyDetails(String originCode , String destinationCode) {
		tokenHelper = new AccessTokenHelper();
		AccessTokenResponse accessToken = tokenHelper.obtainAccessToken(uri,username,password,grantType);
		
		SelectionDetail detail = retrieveAndPopulateJourneyDetails(originCode, destinationCode, accessToken);
		return detail;
		
	}

	private SelectionDetail retrieveAndPopulateJourneyDetails(String originCode, String destinationCode,
			AccessTokenResponse accessToken) {
		
		SelectionDetail detail  = null;
		try{
			oAuthRestTemplate = new OAuth2AsyncRestTemplate();
			oAuthRestTemplate.addAuthentication(accessToken.getAccess_token());
		
			ListenableFuture<ResponseEntity<Location>> future1 = oAuthRestTemplate
		        .getForEntity(airportbase_uri+ originCode , Location.class);
		    ListenableFuture<ResponseEntity<Location>> future2 = oAuthRestTemplate
		        .getForEntity(airportbase_uri+ destinationCode, Location.class);
		    ListenableFuture<ResponseEntity<Fare>> future3 = oAuthRestTemplate
		        .getForEntity(fare_uri+ originCode + SLASH + destinationCode, Fare.class);

		    ResponseEntity<Location> response1 = future1.get();
		    ResponseEntity<Location> response2 = future2.get();
		    ResponseEntity<Fare> response3 = future3.get();
		
		
		    detail = new SelectionDetail();
		    detail.setOrigin(response1.getBody());
		    detail.setDstination(response2.getBody());
		    detail.setFare(response3.getBody());
		}catch(InterruptedException | ExecutionException e){
			throw new ApiCallException();
		}
		return detail;
	}
	
	
	
}

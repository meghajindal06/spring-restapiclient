package com.afkl.cases.df.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Error while calling mock api")

public class ApiCallException extends RuntimeException{

}

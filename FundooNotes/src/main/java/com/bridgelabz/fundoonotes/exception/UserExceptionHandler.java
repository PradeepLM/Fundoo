package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(UserException.class)
	public final ResponseEntity<ExceptionResponse> allException(UserException ex) {
		ExceptionResponse exp=new ExceptionResponse();
		exp.setMessage(ex.getMessage());
		exp.setCode(ex.getHttpstatus());
		return ResponseEntity.status(exp.getCode()).body(new ExceptionResponse(ex.getMessage(),exp.getCode()));
	}

}

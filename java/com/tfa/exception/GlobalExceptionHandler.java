package com.tfa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetail> globalException(HttpServletRequest request,Exception ex) {
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorDetail  detail = ErrorDetail.builder()
				.methode(request.getMethod())
				.message(ex.getMessage())
				.endPoint(request.getRequestURI())
				.errorCode(status.toString())
				.build();
		return new ResponseEntity<>(detail, status);
		
		
	}
}

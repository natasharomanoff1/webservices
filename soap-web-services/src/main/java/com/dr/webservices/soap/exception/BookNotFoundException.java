package com.dr.webservices.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode=FaultCode.CUSTOM, customFaultCode="{http://dr.com/books}001_BOOK_NOT_FOUND")
public class BookNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -433427064651946056L;

	public BookNotFoundException(String message) {
		super(message);
	}

}

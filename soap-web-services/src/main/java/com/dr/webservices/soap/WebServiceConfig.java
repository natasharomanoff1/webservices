package com.dr.webservices.soap;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


//Configure wsdl
//Enable Spring Web Services
@EnableWs
//Spring Configuration
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	
	//MessageDispatcherServlet
	//ApplicationContext
	//url -> /ws/*
	
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		//Map MessageDispatcherServlet to url
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServlet,"/ws/*");
	}
		
	// wsdl definiton
	@Bean(name="books")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema booksSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		
		//PortType - BookPort
		definition.setPortTypeName("BookPort");
		//Namespace - http://dr.com/books
		definition.setTargetNamespace("http://dr.com/books");
		//ws
		definition.setLocationUri("/ws");
		//schema
		definition.setSchema(booksSchema);
		
		return definition;
	}
	
	@Bean
	public XsdSchema booksSchema() {
		return new SimpleXsdSchema(new ClassPathResource("book-details.xsd"));
	}
	
	//XwsSecurityInterceptor to enable security in WS
	@Bean
	public XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor securityInterceptor = new XwsSecurityInterceptor();
		
		//Callback Handler -> SimplePasswordValidationCallbackHandler
		securityInterceptor.setCallbackHandler(callbackHandler());
		//Security Policy -> securityPolicy.xml
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		return securityInterceptor;
	}
	
	@Bean
	public SimplePasswordValidationCallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		handler.setUsersMap(Collections.singletonMap("user","password"));
		return handler;
	}

	//interceptors.add -> XwsSecurityInterceptor
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		interceptors.add(securityInterceptor());
	}

}

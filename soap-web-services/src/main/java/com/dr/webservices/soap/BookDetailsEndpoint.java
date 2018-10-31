package com.dr.webservices.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.dr.books.BookDetails;
import com.dr.books.DeleteBookDetailRequest;
import com.dr.books.DeleteBookDetailResponse;
import com.dr.books.GetAllBookDetailRequest;
import com.dr.books.GetAllBookDetailResponse;
import com.dr.books.GetBookDetailRequest;
import com.dr.books.GetBookDetailResponse;
import com.dr.webservices.soap.exception.BookNotFoundException;
import com.dr.webservices.soap.service.BookDetailsService;
import com.dr.webservices.soap.service.BookDetailsService.Status;

@Endpoint
public class BookDetailsEndpoint {
	
	@Autowired
	BookDetailsService service;

	// method
	// input - GetBookDetailRequest
	// output - GetBookDetailResponse

	//http://dr.com/books
	//GetBookDetailRequest
	@PayloadRoot(namespace="http://dr.com/books",localPart="GetBookDetailRequest")
	@ResponsePayload
	public GetBookDetailResponse processBookDetailRequest(@RequestPayload GetBookDetailRequest request) {
		
		Book book = service.findById(request.getId());
		
		if(book == null) {
			throw new BookNotFoundException("Invalid book id: "+request.getId());
		}
		
		return mapBookDetails(book);
	}
	
	@PayloadRoot(namespace="http://dr.com/books",localPart="GetAllBookDetailRequest")
	@ResponsePayload
	public GetAllBookDetailResponse processAllBookDetailRequest(@RequestPayload GetAllBookDetailRequest request) {
		
		List<Book> books = service.findAll();
		return mapAllBookDetails(books);
	}
	
	@PayloadRoot(namespace="http://dr.com/books",localPart="DeleteBookDetailRequest")
	@ResponsePayload
	public DeleteBookDetailResponse processDeleteBookDetailRequest(@RequestPayload DeleteBookDetailRequest request) {
		
		Status status = service.deleteById(request.getId());
		
		DeleteBookDetailResponse response = new DeleteBookDetailResponse();
		response.setStatus(mapStatus(status));
		return response;
	}

	private com.dr.books.Status mapStatus(Status status) {
		if(status == Status.FAILURE)
			return com.dr.books.Status.FAILURE;
		return com.dr.books.Status.SUCCESS;
	}

	private GetBookDetailResponse mapBookDetails(Book book) {
		GetBookDetailResponse response = new GetBookDetailResponse();
		
		response.setBookDetails(mapBook(book));
		return response;
	}
	
	private GetAllBookDetailResponse mapAllBookDetails(List<Book> books) {
		GetAllBookDetailResponse response = new GetAllBookDetailResponse();
		
		for ( Book book: books) {
			BookDetails mapBook = mapBook(book) ;
			response.getBookDetails().add(mapBook);
		}
		return response;
	}

	private BookDetails mapBook(Book book) {
		BookDetails bookDetails = new BookDetails();
		
		bookDetails.setId(book.getId());
		bookDetails.setName(book.getName());
		bookDetails.setAuthor(book.getAuthor());
		return bookDetails;
	}
}

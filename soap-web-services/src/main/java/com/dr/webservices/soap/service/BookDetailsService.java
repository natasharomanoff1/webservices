package com.dr.webservices.soap.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dr.webservices.soap.Book;

@Component
public class BookDetailsService {
	
	public enum Status {
		SUCCESS, FAILURE
	}
	
	private static List<Book> books = new ArrayList<>();
	
	static {
		Book book1 = new Book(1,"The Devil", "Leo Tolstoy");
		books.add(book1);
		
		Book book2 = new Book(2,"Watchmen", "Alan More");
		books.add(book2);
		
		Book book3 = new Book(3,"Stories of your life and others", "Ted Chiang");
		books.add(book3);
		
		Book book4 = new Book(4,"Steve Jobs", "Walter Isaacson");
		books.add(book4);
	}
	
	public Book findById(int id) {
		for(Book book: books) {
			if(book.getId()==id) return book;
		}
		return null;
	}
	
	public List<Book> findAll() {
		return books;
	}
	
	public Status deleteById(int id) {
		
		Iterator<Book> iterator = books.iterator();
		while(iterator.hasNext()) {
			Book book = iterator.next();
			if(book.getId() == id) { 
				iterator.remove();
				return Status.SUCCESS;
			}
		}
		return Status.FAILURE;
	}
}

package Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
	
	private List<Book> catalog;
	private List<User> users;
	private HashMap<Book, List<User>> rented = new HashMap<Book, List<User>>();
	
	public HashMap<Book, List<User>> getRented() {
		return rented;
	}

	public void setRented(HashMap<Book, List<User>> rented) {
		this.rented = rented;
	}

	public List<Book> getCatalog() {
		return catalog;
	}

	public void setCatalog(List<Book> catalog) {
		this.catalog = catalog;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Library() {
        this.catalog = new ArrayList<>();
        this.users = new ArrayList<>();
	}
	
	public void addBookToCatalog(Book book) {
		catalog.add(book);
	}
	
	public void addBookToCatalog(List<Book> books) {
		for(Book book : books) {
			catalog.add(book);			
		}
	}
	
	public void registerUser(User user) {
		users.add(user);
	}
	
	public void showCatalog(){
		System.out.println("CATÁLOGO DE LIVROS:");
		for(Book book: catalog) {
			System.err.println(book.toString());
		}
	}

	
	
	
	
}

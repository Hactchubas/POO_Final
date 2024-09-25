package interfacesAndAbstracts;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.Book;
import Entities.BookStatus;
import Entities.Library;
import Entities.User;

public abstract class LibraryHandler {
	List<Book> sampleBooks;

	public LibraryHandler() {
		super();
		List<String> authors = new ArrayList<String>();
		authors.add("Kauã");
		authors.add("Levi");
		authors.add("Bianka");
		LocalDate localDate = new Date(System.currentTimeMillis()).toLocalDate();
		Book newBook1 = new Book("Kauã", authors, BookStatus.DISPONIVEL, "isbn", "Kasoma", localDate, 2);
		Book newBook2 = new Book("Levi", authors, BookStatus.DISPONIVEL, "isbn", "Lema", localDate, 2);
		Book newBook3 = new Book("Bianka", authors, BookStatus.DISPONIVEL, "isbn", "Biesli", localDate, 2);
		List<Book> books = new ArrayList<Book>();
		books.add(newBook1);
		books.add(newBook2);
		books.add(newBook3);
		
		this.sampleBooks = books;
	}	
	public List<Book> getSampleBooks(){
		return this.sampleBooks;
	}
	
	public abstract void showCatalog(Library library, User user);
	public abstract void showCatalog(Library library);
	public abstract void searchBook(Library library, User user);
	public abstract void searchUser(Library library);
	public abstract Boolean addBooks(Library library);
	public abstract HashMap<Book, String> rentBook(Library library, User user,  List<Long> ids);
	public abstract void showRented(Library library, User librarian);
	public abstract void returnBook(Library library, User user, List<Long> ids);
	public abstract List<Book> rentedByUser(Library library, User user);
	public abstract void updateBookById(Long id, Library library);
	public abstract void deleteBookById(Long id, Library library);
	public abstract void showUsers(Library bookstore);
	public abstract void deleteUserById(Long id, Library library); 
}

package interfacesAndAbstracts;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Entities.Book;
import Entities.BookStatus;
import Entities.Category;
import Entities.Library;
import Entities.User;

public abstract class LibraryHandler {
	List<Book> sampleBooks;

	public LibraryHandler() {
		super();
		
		List<String> authorsb1 = new ArrayList<>(Arrays.asList("Kauã Maia"));
		List<String> authorsb2 = new ArrayList<>(Arrays.asList("Bianka Costa", "Matheus Costa"));
		List<String> authorsb3 = new ArrayList<>(Arrays.asList("Levi Martins"));
		
		LocalDate localDate = new Date(System.currentTimeMillis()).toLocalDate();
		
		List<Category> categoriesb1 = new ArrayList<>(Arrays.asList(new Category("Ficção"), new Category("Romance")));
		List<Category> categoriesb2 = new ArrayList<>(Arrays.asList(new Category("Ficção")));
		List<Category> categoriesb3 = new ArrayList<>(Arrays.asList(new Category("Horror")));
		
		Book newBook1 = new Book("O morro dos ventos uivantes", authorsb1, BookStatus.DISPONIVEL, "8594318235", "Kasoma", categoriesb1, localDate, 2);
		Book newBook2 = new Book("O jardim secreto", authorsb2, BookStatus.DISPONIVEL, "6589678480", "Lema", categoriesb2, localDate, 2);
		Book newBook3 = new Book("O médico e o monstro", authorsb3, BookStatus.DISPONIVEL, "isbn", "Biesli", categoriesb3, localDate, 2);
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

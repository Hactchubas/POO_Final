package Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interfacesAndAbstracts.LibraryHandler;

public class LibraryHandlerImpl extends LibraryHandler {

	@Override
	public void showCatalog(Library library, User user) {
		List<Book> catalog = library.getCatalog();

		System.out.println("Lista de livros:");
		if(user instanceof Librarian) {
			showAdmCatalog(catalog);
		} else {
			showUserCatalog(catalog);
		}

	}

	private void showAdmCatalog(List<Book> catalog) {
		for (Book book : catalog) {
			System.out.println(book.toString() + "\n\n");
		}
	}

	private void showUserCatalog(List<Book> catalog) {
		for (Book book : catalog) {
			if (book.getStatus() == BookStatus.DISPONIVEL) {
				System.out.println(book.toUserString() + "\n\n");
			}
		}
	}

	@Override
	public Boolean addBooks(Library library) {
		library.addBookToCatalog();
		return true;
	}

	@Override
	public void searchBook(Library library, User user) {
		library.searchBookTitle(user);
	}

	public Book searchBook(Library library, Long id) {
		for (Book book : library.getCatalog()) {
			if (book.getId() == id)
				return book;
		}
		return null;
	}

	public HashMap<Book, String> rentBook(Library library, List<Book> books, User user) {
		HashMap<Book, List<User>> rented = library.getRented();
		HashMap<Book, String> success = new HashMap<Book, String>();
		for (Book book : books) {
			Boolean available = true;
			if (rented.get(book) != null) {
				available = rented.get(book).size() < book.getQuantity();
			}
			book.setStatus(available ? BookStatus.DISPONIVEL : BookStatus.ALUGADO);

			if (book.getStatus() == BookStatus.DISPONIVEL) {
				if (rented.get(book) == null) {
					rented.put(book, new ArrayList<User>());
				}
				rented.get(book).add(user);

				success.put(book, "(Alugado)");
				book.setStatus(rented.get(book).size() < book.getQuantity() ? BookStatus.DISPONIVEL : BookStatus.ALUGADO); 
			} else {
				success.put(book, "(Indisponível)");
			}
		}
		return success;
	}

	@Override
	public HashMap<Book, String> rentBook(Library library, User user, List<Long> ids) {
		List<Book> books = new ArrayList<Book>();
		for (Long id : ids) {
			Book book = searchBook(library, id);
			if (book != null)
				books.add(book);
		}
		return this.rentBook(library, books, user);
	}

	@Override
	public void showRented(Library library, User librarian) {
		if (librarian instanceof Librarian) {
			HashMap<Book, List<User>> rented = library.getRented();
			for (Book book : rented.keySet()) {
				List<User> users = rented.get(book);
				if(users.size() < 1) continue;
				System.out.println(book.getTitle() + ":");
				for (User user : users) {
					System.out.println("\t" + user.getName());
				}
			}
		}

	}

	@Override
	public void showCatalog(Library library) {
		for(Book book : library.getCatalog()) {
			System.out.println(book.toSimpleString());
		}
	}

	@Override
	public void returnBook(Library library, User user, List<Long> ids) {
		List<Book> books = new ArrayList<Book>();
		for (Long id : ids) {
			Book book = searchBook(library, id);
			if (book != null)
				books.add(book);
		}
		this.returnBook(library, books, user);
	}

	private void returnBook(Library library, List<Book> books, User user) {
		HashMap<Book, List<User>> allRented = library.getRented();
		
		for(Book book : books) {
			List<User> users = allRented.get(book);
			if(users.remove(user)) {
				book.setStatus(BookStatus.DISPONIVEL);				
			}
		}
	}

	@Override
	public List<Book> rentedByUser(Library library, User user) {
		HashMap<Book, List<User>> rented = library.getRented();
		List<Book> userRented = new ArrayList<Book>();
		
		for(Book book : rented.keySet()) {
			List<User> users  = rented.get(book);
			for( User u : users) {
				if(u.equals(user)) {
					userRented.add(book);
				}
			}
		}		
		return userRented;
	}

	@Override
	public void updateBookById(Long id, Library library) {
		library.updateBook(id);		
	}

	@Override
	public void deleteBookById(Long id, Library library) {
		library.deleteBookById(id);		
	}

	@Override
	public void showUsers(Library library) {
		library.showUsers();		
	}

}

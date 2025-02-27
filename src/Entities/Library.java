package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Library {
	
	private List<Book> catalog;
	private List<User> users;
	private Scanner scanner;
	private HashMap<Book, List<User>> rented = new HashMap<Book, List<User>>();
	private List<Category> existingCategories = new ArrayList<>();
	
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
        this.scanner = new Scanner(System.in);
	}
	
	public void addBookToCatalog() {
		System.out.println("Adicionando livro...");
		catalog.add(getBookInfos());
		System.out.println("Livro adicionado com sucesso.");
	}
	
	public Book searchBookById(long id) {
        Optional<Book> bookOptional = catalog.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
        
        return bookOptional.orElse(null);
    }
	
	public void searchBookTitle(User user) {
        System.out.println("Digite o nome do livro: ");
        String search = scanner.nextLine();

        boolean found = false;

        for (Book book : getCatalog()) {
            if (book.getTitle().equalsIgnoreCase(search)) {
                if (user instanceof Librarian) {
                    System.out.println(book.toString() + "\n");
                    found = true;
                } else {
                    System.out.println(book.toUserString() + "\n");
                    found = true;
                }
            }
        }

        if (!found) {
            System.err.println("Livro não encontrado \n \n");
            
        }
    }
	
    public void viewBookDetailsById(long id) {
    	Book book = searchBookById(id);

        if (book != null) {
            System.out.println("Detalhes do Livro:\n" + book.toString());
        } else {
            System.out.println("Livro não encontrado com o ID: " + id);
        }
    }
    
    public void updateBook(long id) {
        Book book = searchBookById(id);

        if (book != null) {
            System.out.println("Atualizando informações do livro: " + book.getTitle());
            Book updatedBook = getBookInfos();

            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setStatus(updatedBook.getStatus());
            book.setIsbn(updatedBook.getIsbn());
            book.setPublisher(updatedBook.getPublisher());
            book.setPublishDate(updatedBook.getPublishDate());
            book.setQuantity(updatedBook.getQuantity());

            System.out.println("Informações do livro atualizadas com sucesso.");
        } else {
            System.out.println("Livro não encontrado com o ID: " + id);
        }
    }
    
    public void deleteBookById(long id) {
        Book book = searchBookById(id);

        if (book != null) {
            catalog.remove(book);
            System.out.println("Livro com ID " + id + " foi deletado com sucesso.");
        } else {
            System.out.println("Livro não encontrado com o ID: " + id);
        }
    }
    
    public Book getBookInfos() {
        System.out.println("Digite o título do livro: ");
        String title = scanner.nextLine();

        System.out.println("Digite os autores do livro (separados por vírgula): ");
        String authorsInput = scanner.nextLine();
        List<String> authors = Arrays.asList(authorsInput.split("\\s*,\\s*"));

        BookStatus status = InputHandler.getEnumInput("Digite o status do livro (DISPONIVEL, INDISPONIVEL): ");

        System.out.println("Digite o ISBN do livro: ");
        String isbn = scanner.nextLine();

        System.out.println("Digite o nome da editora: ");
        String publisher = scanner.nextLine();
        
        System.out.println("Digite as categorias do livro (separadas por vírgula): ");
        String categoriesInput = scanner.nextLine();
        List<String> categoryNames = Arrays.asList(categoriesInput.split("\\s*,\\s*"));
        List<Category> bookCategories = new ArrayList<>();

        LocalDate publishDate = InputHandler.getDateInput("Digite a data de publicação (formato AAAA-MM-DD): ");
        
        int quantity = InputHandler.getIntInput("Digite a quantidade de cópias do livro: ");
        
        for (String categoryName : categoryNames) {
            categoryName = categoryName.trim();
            Category foundCategory = findCategoryByName(categoryName);

            if (foundCategory != null) {
                bookCategories.add(foundCategory);
            } else {
                Category newCategory = new Category(categoryName);
                existingCategories.add(newCategory);
                bookCategories.add(newCategory);
            }
        }

        return new Book(title, authors, status, isbn, publisher, bookCategories, publishDate, quantity);
    }
    
    public Category findCategoryByName(String name) {
        for (Category category : existingCategories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
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
	
	public void showUsers(){
		System.out.println("Lista de Usuários:");
		for(User user: users) {
			System.err.println(user.toString());
		}
	}
	
	public void searchUserName() {
		System.out.println("Digite o nome do usuário: ");
        String search = scanner.nextLine();
        
        boolean found = false;
        
        for(User user : getUsers()) {
        	if(user.getName().equalsIgnoreCase(search)) {
                System.out.println(user.toString() + "\n");
                found = true;
        	}
        }
        
        if (!found) {
            System.err.println("Usuário não encontrado \n \n");
            
        }
	}
	
	public User searchUserById(long id) {
        Optional<User> userOptional = users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
        
        return userOptional.orElse(null);
    }
	
    public void deleteUserById(long id) {
        User user = searchUserById(id);

        if (user != null) {
            users.remove(user);
            System.out.println("Usuário com ID " + id + " foi deletado com sucesso.");
        } else {
            System.out.println("Usuário não encontrado com o ID: " + id);
        }
    }
	
}

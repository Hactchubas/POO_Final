import Entities.Book;
import Entities.Library;
import Entities.LibraryHandlerImpl;
import Entities.Librarian;
import Entities.Student;
import Entities.User;
import interfacesAndAbstracts.LibraryHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

	static private User logged = null;
	

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Library bookstore = new Library();
		LibraryHandler libraryManager = new LibraryHandlerImpl();
		
		

		libraryManager.addBooks(bookstore, libraryManager.getSampleBooks());

		bookstore.registerUser(new Student("kaua", "Kaua", "1234", "S123"));
		bookstore.registerUser(new Librarian("kauaAdm", "Kaua - Adm", "1234", "1234"));

		Boolean running = true;
		

		while (running) {
			if (logged == null) {
				System.out.println("Bem-vindo à biblioteca! Escolha uma opção:");
				System.out.println("1. Entrar no sistema");
				System.out.println("2. Criar nova conta\n");
			} else {
				System.out.println("Escolha uma opção:");
				System.out.println("1. Ver catálogo de livros");
				System.out.println("2. Alugar livro");
				System.out.println("3. Devolver livro");
				System.out.println("4. Procurar livro");
				
				if(logged instanceof Librarian) {
					System.out.println("5. Cadastrar livro");					
					System.out.println("6. Editar livro");					
					System.out.println("7. Deletar livro");					
					System.out.println("8. Listar Alugados");					
				}
				
				System.out.println("9. Deslogar");
				
			}
			System.out.println("0. Sair");
			
			
			
			int choice = scanner.nextInt();
			scanner.nextLine();

			if (logged == null) {
				switch (choice) {
				case 1: {
					logged = login(scanner, bookstore);
					break;
				}
				case 2: {
					createUser(scanner, bookstore);
					break;
				}
				case 3: {
					running = false;
					break;
				}
				default:
					break;
				}
			} else {
				switch (choice) {
				case 1: {
					libraryManager.showCatalog(bookstore, logged);
					break;
				}
				case 2:
					rentBook(scanner, bookstore, libraryManager);
					break;
				case 3:
					returnBook(scanner, bookstore, libraryManager);
					break;
				case 8:
					libraryManager.showRented(bookstore, logged);
					break;
				case 9:
					logged = null;
					break;
				case 5:
					running = false;
					break;
				default:
					break;
				}
			}
		}

		scanner.close();
	}

	

	private static User login(Scanner scanner, Library bookstore) {
		System.out.println("Escolha o tipo de usuário:");
		System.out.println("1. Bibliotecário");
		System.out.println("2. Estudante");

		User logged = null;

		int role = scanner.nextInt();
		scanner.nextLine();

		if (role == 1) {
			System.out.println("Digite seu CPF: ");
			String cpf = scanner.nextLine();

			System.out.println("Digite sua senha: ");
			String password = scanner.nextLine();

			boolean authenticated = false;
			for (User user : bookstore.getUsers()) {
				if (user instanceof Librarian) {
					Librarian librarian = (Librarian) user;
					if (librarian.authenticateByCpf(cpf, password)) {
						authenticated = true;
						System.out.println("Login bem-sucedido como Bibliotecário: " + librarian);
						logged = librarian;
						break;
					}
				}
			}

			if (!authenticated) {
				System.out.println("CPF ou senha inválidos.");
			}

		} else if (role == 2) {
			System.out.println("Digite seu email: ");
			String email = scanner.nextLine();

			System.out.println("Digite sua senha: ");
			String password = scanner.nextLine();

			boolean authenticated = false;
			for (User user : bookstore.getUsers()) {
				if (user instanceof Student) {
					if (user.authenticate(email, password)) {
						authenticated = true;
						System.out.println("Login bem-sucedido como Estudante: \n" + user);
						logged = user;
						break;
					}
				}
			}

			if (!authenticated) {
				System.out.println("Email ou senha inválidos.");
			}
		}

		return logged;
	}

	private static void createUser(Scanner scanner, Library bookstore) {
		System.out.println("Escolha o tipo de usuário para criar conta:");
		System.out.println("1. Bibliotecário");
		System.out.println("2. Estudante");
		int role = scanner.nextInt();
		scanner.nextLine();

		System.out.println("Digite seu nome: ");
		String name = scanner.nextLine();

		System.out.println("Digite seu email: ");
		String email = scanner.nextLine();

		System.out.println("Digite uma senha: ");
		String password = scanner.nextLine();

		if (role == 1) {
			System.out.println("Digite seu CPF: ");
			String cpf = scanner.nextLine();
			bookstore.registerUser(new Librarian(email, name, password, cpf));
			System.out.println("Conta de Bibliotecário criada com sucesso!");

		} else if (role == 2) {
			System.out.println("Digite sua matrícula: ");
			String registration = scanner.nextLine();
			bookstore.registerUser(new Student(email, name, password, registration));
			System.out.println("Conta de Estudante criada com sucesso!");
		}
	}
	
	private static void rentBook(Scanner scanner, Library bookstore, LibraryHandler libraryManager) {
		System.out.println("Digite os códigos dos livros que deseja alugar [ex: '1,3,5']:");
		for(Book book : bookstore.getCatalog()) {
			System.out.println(book.toSimpleString());
		}
		
		
		String ids = scanner.nextLine();
		List<Long> idsLong = new ArrayList<Long>();
		for(String id : ids.split(",")) {
			idsLong.add(Long.parseLong(id));
		}
		
		HashMap<Book, String> success = libraryManager.rentBook(bookstore, logged, idsLong);
		for(Book book : success.keySet()) {
			System.out.println(book.getTitle() + ":" + success.get(book));
		}
	}
	
	private static void returnBook(Scanner scanner, Library bookstore, LibraryHandler libraryManager) {
		
		List<Book> books = libraryManager.rentedByUser(bookstore, logged);
		if(books.size() < 1) {
			System.out.println("\nNenhum livro alugado!\n");
			return;
		}
		System.out.println("Digite os códigos dos livros que deseja devolver [ex: '1,3,5']:");
		for(Book book : books ){
			System.out.println(book.toSimpleString());
		}
		
		String ids = scanner.nextLine();
		List<Long> idsLong = new ArrayList<Long>();
		for(String id : ids.split(",")) {
			idsLong.add(Long.parseLong(id));
		}
		
		libraryManager.returnBook(bookstore, logged, idsLong);		
	}

}

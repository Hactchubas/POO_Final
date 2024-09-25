import Entities.Book;
import Entities.Library;
import Entities.LibraryHandlerImpl;
import Entities.Librarian;
import Entities.Student;
import Entities.User;
import interfacesAndAbstracts.LibraryHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

	static private User logged = null;

	public static void main(String[] args) {
		Library bookstore = new Library();
		consoleInterface(bookstore);		
	}

	private static User login(Scanner scanner, Library bookstore) {
		boolean validRole = false;
		User logged = null;
		while (!validRole) {
			System.out.println("Escolha o tipo de usuário:");
			System.out.println("1. Bibliotecário");
			System.out.println("2. Estudante");
			System.out.println("3. Voltar");

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
							validRole = true;
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
							validRole = true;
							break;
						}
					}
				}

				if (!authenticated) {
					System.out.println("Email ou senha inválidos.");
				}
			} else if (role == 3) {
				validRole = true;
			} else {
				System.out.println("Entrada inválida. Por favor, tente novamente.");
			}
		}

		return logged;
	}

	private static void createUser(Scanner scanner, Library bookstore) {
		boolean validRole = false;
		while (!validRole) {
			System.out.println("Escolha o tipo de usuário para criar conta:");
			System.out.println("1. Bibliotecário");
			System.out.println("2. Estudante");
			System.out.println("3. Voltar");
			int role;
			try {
				role = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
				scanner.nextLine();
				continue;
			}
			if (role == 1) {
				System.out.println("Digite seu CPF: ");
				String cpf = scanner.nextLine();
				System.out.println("Digite seu nome: ");
				String name = scanner.nextLine();
				System.out.println("Digite seu email: ");
				String email = scanner.nextLine();
				System.out.println("Digite uma senha: ");
				String password = scanner.nextLine();
				bookstore.registerUser(new Librarian(email, name, password, cpf));
				System.out.println("Conta de Bibliotecário criada com sucesso!");
				validRole = true;
			} else if (role == 2) {
				System.out.println("Digite sua matrícula: ");
				String registration = scanner.nextLine();
				System.out.println("Digite seu nome: ");
				String name = scanner.nextLine();
				System.out.println("Digite seu email: ");
				String email = scanner.nextLine();
				System.out.println("Digite uma senha: ");
				String password = scanner.nextLine();
				bookstore.registerUser(new Student(email, name, password, registration));
				System.out.println("Conta de Estudante criada com sucesso!");
				validRole = true;
			} else if (role == 3) {
				validRole = true;
			} else {
				System.out.println("Entrada inválida. Por favor, tente novamente com uma das opções a seguir.");
			}
			continue;
		}
	}

	private static void rentBook(Scanner scanner, Library bookstore, LibraryHandler libraryManager) {
		System.out.println("Digite os códigos dos livros que deseja alugar [ex: '1,3,5']:");
		for (Book book : bookstore.getCatalog()) {
			System.out.println(book.toSimpleString());
		}

		String ids = scanner.nextLine();
		List<Long> idsLong = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			idsLong.add(Long.parseLong(id));
		}

		HashMap<Book, String> success = libraryManager.rentBook(bookstore, logged, idsLong);
		for (Book book : success.keySet()) {
			System.out.println(book.getTitle() + ":" + success.get(book));
		}
	}

	private static void returnBook(Scanner scanner, Library bookstore, LibraryHandler libraryManager) {

		List<Book> books = libraryManager.rentedByUser(bookstore, logged);
		if (books.size() < 1) {
			System.out.println("\nNenhum livro alugado!\n");
			return;
		}
		System.out.println("Digite os códigos dos livros que deseja devolver [ex: '1,3,5']:");
		for (Book book : books) {
			System.out.println(book.toSimpleString());
		}

		String ids = scanner.nextLine();
		List<Long> idsLong = new ArrayList<Long>();
		for (String id : ids.split(",")) {
			idsLong.add(Long.parseLong(id));
		}

		libraryManager.returnBook(bookstore, logged, idsLong);
	}

	private static void consoleInterface(Library bookstore) {
		Scanner scanner = new Scanner(System.in);
		LibraryHandler libraryManager = new LibraryHandlerImpl();

		bookstore.registerUser(new Student("alicemedeiros@alu.uf.br", "Alice Medeiros", "1234", "S123"));
		bookstore.registerUser(new Librarian("josefarias@serv.uf.br", "José Farias", "5678", "12345978900"));
		
		for(Book book: libraryManager.getSampleBooks()) {
            bookstore.getCatalog().add(book);
        }
		
		Boolean running = true;
		while (running) {
			if (logged == null) {
				System.out.println("Bem-vindo à biblioteca! Escolha uma opção:");
				System.out.println("1. Entrar no sistema");
				System.out.println("2. Criar nova conta");
			} else {
				System.out.println("Escolha uma opção:");
				System.out.println("1. Ver catálogo de livros");
				System.out.println("2. Alugar livro");
				System.out.println("3. Devolver livro");
				System.out.println("4. Procurar livro");

				if (logged instanceof Librarian) {
					System.out.println("5. Cadastrar livro");
					System.out.println("6. Editar livro");
					System.out.println("7. Deletar livro");
					System.out.println("8. Listar Alugados");
					System.out.println("9. Buscar usuário");
					System.out.println("10. Listar usuários");
					System.out.println("11. Deletar usuário");
				}

				System.out.println("12. Deslogar");

			}
			System.out.println("0. Sair");

			int choice;
			try {
				choice = scanner.nextInt();
				scanner.nextLine();
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
				scanner.nextLine();
				continue;
			}

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
					case 0: {
						running = false;
						break;
					}
					default:
						System.out.println("Entrada inválida. Por favor, tente novamente.");
						break;
				}
			} else {
				switch (choice) {
					case 1: {
						showCatalog(bookstore, libraryManager, logged);
						break;
					}
					case 2:
						rentBook(scanner, bookstore, libraryManager);
						break;
					case 3:
						returnBook(scanner, bookstore, libraryManager);
						break;
					case 4:
                        searchBook(bookstore, libraryManager, logged);
                        break;
					case 5:
						if(logged instanceof Librarian) {
							addBook(scanner, bookstore, libraryManager);
						}
						break;
					case 6:
						if(logged instanceof Librarian) {
							updateBook(scanner, bookstore, libraryManager);
						}
						break;
					case 7:
						if(logged instanceof Librarian) {
							deleteBook(scanner, bookstore, libraryManager);
						}
						break;
					case 8:
						if(logged instanceof Librarian) {
							showRented(bookstore, libraryManager, logged);
						}
						break;
					case 9:
						if(logged instanceof Librarian) {
							searchUser(bookstore, libraryManager);
						}
						break;
					case 10:
						if(logged instanceof Librarian) {
							listUsers(bookstore, libraryManager);
						}
						break;
					case 11:
						if(logged instanceof Librarian) {
							deleteUser(scanner, bookstore, libraryManager);
						}
						break;
					case 12:
						logged = null;
						break;
					case 0:
						running = false;
						break;
					default:
						break;
				}
			}
		}

		scanner.close();
	}
	
	private static void deleteUser(Scanner scanner, Library bookstore, LibraryHandler libraryManager) {
		System.out.println("Digite o ID do usuário que deseja deletar: ");
		Long id = scanner.nextLong();
		libraryManager.deleteUserById(id, bookstore);
		
	}

	private static void searchUser(Library bookstore, LibraryHandler libraryManager) {
		libraryManager.searchUser(bookstore);
	}
	
	private static void searchBook(Library bookstore, LibraryHandler libraryManager, User user) {
        libraryManager.searchBook(bookstore, user);
    }

	private static void listUsers(Library bookstore, LibraryHandler libraryManager) {
		System.out.println("Lista de usuários cadastrados: ");
		libraryManager.showUsers(bookstore);
		
	}

	private static void showRented(Library bookstore, LibraryHandler libraryManager, User user) {
		libraryManager.showRented(bookstore, user);
		
	}

	private static void showCatalog(Library bookstore, LibraryHandler libraryManager, User user) {
		libraryManager.showCatalog(bookstore, user);
	}

	private static void updateBook(Scanner scanner, Library bookstore, LibraryHandler libraryManager) {
		System.out.println("Digite o ID do livro que deseja atualizar: ");
		Long id = scanner.nextLong();
		libraryManager.updateBookById(id, bookstore);
		
	}

	private static void addBook(Scanner scanner, Library bookstore, LibraryHandler libraryManager) {
		libraryManager.addBooks(bookstore);		
	}
	
	private static void deleteBook(Scanner scanner, Library bookstore, LibraryHandler libraryManager) {
		System.out.println("Digite o ID do livro que deseja deletar: ");
		Long id = scanner.nextLong();
		libraryManager.deleteBookById(id, bookstore);
	}

}

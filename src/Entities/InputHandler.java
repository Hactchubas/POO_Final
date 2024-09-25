package Entities;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String prompt) {
        int value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println(prompt);
                value = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
                scanner.nextLine();
            }
        }
        return value;
    }

    public static long getLongInput(String prompt) {
        long value = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println(prompt);
                value = scanner.nextLong();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                scanner.nextLine();
            }
        }
        return value;
    }

    public static BookStatus getEnumInput(String prompt) {
        BookStatus status = null;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println(prompt);
                String value = scanner.nextLine();
                status = BookStatus.valueOf(value.toUpperCase());
                validInput = true;
            } catch (Exception e) {
                System.out.println("Status inválido. Por favor, insira um status válido [DISPONIVEL, INDISPONIVEL].");
            }
        }

        return status;
    }
    
    public static LocalDate getDateInput(String prompt) {
        LocalDate publishDate = null;
        boolean validDate = false;
        
        while (!validDate) {
            try {
                System.out.println(prompt);
                String publishDateInput = scanner.nextLine();
                publishDate = LocalDate.parse(publishDateInput);
                validDate = true;
            } catch (Exception e) {
                System.out.println("Formato de data inválido. Por favor, insira a data no formato AAAA-MM-DD.");
            }
        }
       return publishDate;
    }
}

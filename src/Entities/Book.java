package Entities;

import java.time.LocalDate;
import java.util.List;

public class Book extends BaseModel {
	
	private String title;
	private List<String> author;
	private BookStatus status;
	private String isbn;
	private String publisher;
	private LocalDate publishDate;
	
	public Book(String title, List<String> author, BookStatus status, String isbn, String publisher,
			LocalDate publishDate, Integer quantity) {
		super();
		this.title = title;
		this.author = author;
		this.status = status;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.quantity = quantity;
	}

	private Integer quantity;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setPublishDate(LocalDate publishDate) {
		this.publishDate = publishDate;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getTitle() {
        return title;
    }

    public List<String> getAuthor() {
        return author;
    }

    public BookStatus getStatus() {
        return status;
    }
    public void setStatus(BookStatus status) {
    	this.status = status;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }
    
	public Integer getQuantity() {
		return quantity;
	}
	
	@Override
	public String toString() {
		return title + " (" + status +") | ID: " + this.getId() + '\n' +
                "\tAutores: " + String.join(", ", author) + '\n' +
                "\tIsbn: " + isbn + '\n' +
                "\tEditora: " + publisher + '\n' +
                "\tData de publicação: " + publishDate;
	}
	
	public String toUserString() {
		return title + "\n" +
                "\tAutores: " + String.join(", ", author) + '\n' +
                "\tIsbn: " + isbn + '\n' +
                "\tEditora: " + publisher + '\n' +
                "\tData de publicação: " + publishDate;
	}
	
	public String toSimpleString() {
		return title + " | ID: " + this.getId();
	}
	

}

package LibraryManagementSystem;

import java.util.*;

//Book class
class Book {
	private int bookId;
	private String title;
	private boolean isIssued;

	public Book(int bookId, String title) {
		this.bookId = bookId;
		this.title = title;
		this.isIssued = false;
	}

	public int getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public boolean isIssued() {
		return isIssued;
	}

	public void issue() {
		isIssued = true;
	}

	public void returned() {
		isIssued = false;
	}

	@Override
	public String toString() {
		return "Book ID: " + bookId + ", Title: " + title + ", Issued: " + isIssued;
	}
}

//User class
class User {
	private int userId;
	private String name;
	private List<Book> issuedBooks;

	public User(int userId, String name) {
		this.userId = userId;
		this.name = name;
		this.issuedBooks = new ArrayList<>();
	}

	public int getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void issueBook(Book book) {
		issuedBooks.add(book);
	}

	public void returnBook(Book book) {
		issuedBooks.remove(book);
	}

	public List<Book> getIssuedBooks() {
		return issuedBooks;
	}

	@Override
	public String toString() {
		return "User ID: " + userId + ", Name: " + name + ", Books Issued: " + issuedBooks.size();
	}
}

//Library class
class Library {
	private List<Book> books;
	private Map<Integer, User> users;

	public Library() {
		books = new ArrayList<>();
		users = new HashMap<>();
	}

	public void addBook(Book book) {
		books.add(book);
	}

	public void addUser(User user) {
		users.put(user.getUserId(), user);
	}

	public void issueBook(int bookId, int userId) {
		Book book = findBookById(bookId);
		User user = users.get(userId);

		if (book == null || user == null) {
			System.out.println(" Book or User not found.");
			return;
		}

		if (book.isIssued()) {
			System.out.println(" Book is already issued.");
		} else {
			book.issue();
			user.issueBook(book);
			System.out.println(" Book issued to " + user.getName());
		}
	}

	public void returnBook(int bookId, int userId) {
		Book book = findBookById(bookId);
		User user = users.get(userId);

		if (book == null || user == null || !book.isIssued()) {
			System.out.println(" Invalid return operation.");
			return;
		}

		book.returned();
		user.returnBook(book);
		System.out.println(" Book returned by " + user.getName());
	}

	private Book findBookById(int bookId) {
		for (Book book : books) {
			if (book.getBookId() == bookId)
				return book;
		}
		return null;
	}

	public void displayBooks() {
		System.out.println(" All Books:");
		for (Book book : books) {
			System.out.println(book);
		}
	}

	public void displayUsers() {
		System.out.println(" All Users:");
		for (User user : users.values()) {
			System.out.println(user);
		}
	}
}

//Main class with Scanner-based menu
public class LibraryManagementSystem {
	public static void main(String[] args) {
		Library library = new Library();
		Scanner sc = new Scanner(System.in);
		int choice;

		System.out.println("Welcome to Library Management System");

		do {
			System.out.println("\n-------- Menu --------");
			System.out.println("1. Add Book");
			System.out.println("2. Add User");
			System.out.println("3. Issue Book");
			System.out.println("4. Return Book");
			System.out.println("5. Display All Books");
			System.out.println("6. Display All Users");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();

			switch (choice) {
			case 1:
				System.out.print("Enter Book ID: ");
				int bookId = sc.nextInt();
				sc.nextLine(); // consume newline
				System.out.print("Enter Book Title: ");
				String title = sc.nextLine();
				library.addBook(new Book(bookId, title));
				System.out.println(" Book added.");
				break;

			case 2:
				System.out.print("Enter User ID: ");
				int userId = sc.nextInt();
				sc.nextLine(); // consume newline
				System.out.print("Enter User Name: ");
				String name = sc.nextLine();
				library.addUser(new User(userId, name));
				System.out.println(" User added.");
				break;

			case 3:
				System.out.print("Enter Book ID to issue: ");
				int issueBookId = sc.nextInt();
				System.out.print("Enter User ID to issue to: ");
				int issueUserId = sc.nextInt();
				library.issueBook(issueBookId, issueUserId);
				break;

			case 4:
				System.out.print("Enter Book ID to return: ");
				int returnBookId = sc.nextInt();
				System.out.print("Enter User ID returning the book: ");
				int returnUserId = sc.nextInt();
				library.returnBook(returnBookId, returnUserId);
				break;

			case 5:
				library.displayBooks();
				break;

			case 6:
				library.displayUsers();
				break;

			case 0:
				System.out.println(" Exiting Library System. Thank you!");
				break;

			default:
				System.out.println("Invalid choice. Try again.");
			}

		} while (choice != 0);

		sc.close();
	}
}

import java.util.*;

abstract class LibraryItem {
    private String title;
    private boolean isAvailable = true;
    private String issuedTo;

    public LibraryItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void issue(String userName) {
        if (isAvailable) {
            isAvailable = false;
            issuedTo = userName;
            System.out.println(userName + " successfully issued: " + title);
        } else {
            System.out.println("Already issued to " + issuedTo);
        }
    }

    public void returnItem(String userName) {
        if (!isAvailable && userName.equals(issuedTo)) {
            isAvailable = true;
            issuedTo = null;
            System.out.println(userName + " returned: " + title);
        } else {
            System.out.println("Return failed. Item not issued to you.");
        }
    }

    public abstract void displayInfo();
}


class Book extends LibraryItem {
    private String author;

    public Book(String title, String author) {
        super(title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public void displayInfo() {
        System.out.print("- " + getTitle() + " by " + getAuthor());
        if (isAvailable()) {
            System.out.println(" (Available)");
        } else {
            System.out.println(" (Issued to: " + getIssuedTo() + ")");
        }
    }
}


class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void issueItem(Library library, String title) {
        LibraryItem item = library.findItem(title);
        if (item != null) {
            item.issue(name);
        } else {
            System.out.println("Item not found.");
        }
    }
    public void returnItem(Library library, String title) {
        LibraryItem item = library.findItem(title);
        if (item != null) {
            item.returnItem(name);
        } else {
            System.out.println("Item not found.");
        }
    }
    
}

class Library {
    private List<LibraryItem> items = new ArrayList<>();
    private Map<String, User> users = new HashMap<>();

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public LibraryItem findItem(String title) {
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) return item;
        }
        return null;
    }

    public User getUser(String name) {
        return users.computeIfAbsent(name.toLowerCase(), k -> new User(name));
    }

    public void displayItems() {
        System.out.println("Library Collection:");
        for (LibraryItem item : items) {
            item.displayInfo();
        }
    }
}


public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

     
        library.addItem(new Book("The Alchemist", "Paulo Coelho"));
        library.addItem(new Book("Harry Potter", "J.K. Rowling"));
        library.addItem(new Book("1984", "George Orwell"));
        library.addItem(new Book("Srimad Bhagavad Gita", "Srila Prabupada"));

        while (true) {
            System.out.println("\nLibrary Menu:");
            System.out.println("1. Display Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1 -> library.displayItems();
                case 2 -> {
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    library.getUser(name).issueItem(library, title);
                }
                case 3 -> {
                    System.out.print("Enter your name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    library.getUser(name).returnItem(library, title);
                }
                case 4 -> {
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}

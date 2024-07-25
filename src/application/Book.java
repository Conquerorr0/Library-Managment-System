package application;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Book extends LibraryItem implements Manageable {

    private String author;
    private String ISBN;
    private static final String FILE_NAME = "books.dat";

    public Book(String title, String type, String author, String ISBN, int yearPublished, int pageQuantity) {
        super(title, type, yearPublished, pageQuantity);
        this.author = author;
        this.ISBN = ISBN;
    }

    @Override
    public void save() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            file.seek(file.length());
            file.writeBytes(formatBookDetails() + "\n");
        }
    }

    @Override
    public void create() {
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book read() {
        Book foundBook = null;
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 6 && details[0].equals(this.title)) {
                    String title = details[0];
                    String type = details[1];
                    String author = details[2];
                    String ISBN = details[3];
                    int yearPublished = Integer.parseInt(details[4]);
                    int pageQuantity = Integer.parseInt(details[5]);
                    foundBook = new Book(title, type, author, ISBN, yearPublished, pageQuantity);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foundBook;
    }


    @Override
    public void update() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            String line;
            long pointer = 0;
            boolean found = false;
            while ((line = file.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length > 0 && details[0].equals(this.title)) { 
                    found = true;
                    break;
                }
                pointer = file.getFilePointer();
            }

            if (found) {
                file.seek(pointer);
                file.writeBytes(formatBookDetails() + "\n");
                System.out.println("Book updated successfully.");
            } else {
                System.out.println("Book not found for update.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
    	File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp_books.dat");

        try (RandomAccessFile file = new RandomAccessFile(inputFile, "rw");
             RandomAccessFile temp = new RandomAccessFile(tempFile, "rw")) {

            String line;
            boolean found = false;

            while ((line = file.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length > 0 && details[0].equals(this.title)) {
                    found = true;
                    continue; // Skip writing this line to temp file
                }
                temp.writeBytes(line + System.lineSeparator());
            }

            if (found) {
                // Copy temp file back to original file
                file.setLength(0); // Clear the original file
                temp.seek(0);
                while ((line = temp.readLine()) != null) {
                    file.writeBytes(line + System.lineSeparator());
                }
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book not found for delete.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Clean up the temporary file
            tempFile.delete();
        }
    }

    @Override
    public String getDetails() {
        return formatBookDetails();
    }

    private String formatBookDetails() {
        return getTitle() + "," + getType() + "," + author + "," + ISBN + "," + getYearPublished() + "," + getPageQuantity();
    }

    public static Book findBookByISBN(String ISBN) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] details = line.split(",");
                if (details[3].equals(ISBN)) {
                    return new Book(details[0], details[1], details[2], details[3], Integer.parseInt(details[4]), Integer.parseInt(details[5]));
                }
            }
        }
        return null;
    }

    public static ObservableList<Book> getAllBooks() throws IOException {
        ObservableList<Book> books = FXCollections.observableArrayList();
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 6 && !details[0].equals("DELETED")) {
                    String title = details[0];
                    String type = details[1];
                    String author = details[2];
                    String ISBN = details[3];
                    int yearPublished = Integer.parseInt(details[4]);
                    int pageQuantity = Integer.parseInt(details[5]);
                    books.add(new Book(title, type, author, ISBN, yearPublished, pageQuantity));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Dosyadan veri okunurken bir hata oluştu.");
        }
        return books;
    }

    
    public static Book findBookByName(String name) throws IOException, ClassNotFoundException {
        List<Book> books = getAllBooks();
        for (Book book : books) {
            if (book.getTitle().equals(name)) {
                return book;
            }
        }
        return null; // Kitap bulunamadı
    }
    

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}

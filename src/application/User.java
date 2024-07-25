package application;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User extends UserDetails implements Manageable {
    private static final String FILE_NAME = "users.dat";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public User(String fullName, String password, String mail, LocalDate registrationDate) {
        super(fullName, password, mail, registrationDate);
    }

    @Override
    public void save() throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            file.seek(file.length()); // Dosyanın sonuna git
            file.writeBytes(formatUserDetails() + "\n");
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
    public User read() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] details = line.split(",");
                if (details[2].equals(getMail())) {
                    System.out.println("User Found: " + line);
                    return new User(details[0], details[1], details[2], LocalDate.parse(details[3]));
                }
            }
            System.out.println("User not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Kullanıcı bulunamadı
    }


    @Override
    public void update() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            String line;
            long pointer;
            while ((line = file.readLine()) != null) {
                pointer = file.getFilePointer();
                String[] details = line.split(",");
                if (details[2].equals(getMail())) {
                    file.seek(pointer - line.length() - 1);
                    file.writeBytes(formatUserDetails() + "\n");
                    return;
                }
            }
            System.out.println("User not found for update.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            String line;
            long pointer;
            while ((line = file.readLine()) != null) {
                pointer = file.getFilePointer();
                String[] details = line.split(",");
                if (details[2].equals(getMail())) {
                    file.seek(pointer - line.length() - 1);
                    file.writeBytes("DELETED\n");
                    return;
                }
            }
            System.out.println("User not found for deletion.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDetails() {
        return formatUserDetails();
    }

    private String formatUserDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return getFullName() + "," + getPassword() + "," + getMail() + "," + getRegistrationDate().format(formatter);
    }
    
    public static User authenticateUser(String email, String password) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] details = line.split(",");
                if (details[2].equals(email) && details[1].equals(password)) {
                    LocalDate registrationDate = LocalDate.parse(details[3], DateTimeFormatter.ofPattern(DATE_FORMAT));
                    return new User(details[0], details[1], details[2], registrationDate);
                }
            }
        }
        return null;
    }
    
    public static ObservableList<User> getAllUsers() throws IOException {
        ObservableList<User> user = FXCollections.observableArrayList();
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] details = line.split(",");
                if (details.length == 4 && !details[0].equals("DELETED")) {
                    String name = details[0];
                    String password = details[1];
                    String email = details[2];
                    String registrationDate = details[3];
                    user.add(new User(name, password, email, LocalDate.parse(registrationDate)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Dosyadan veri okunurken bir hata oluştu.");
        }
        return user;
    }

    public static User findUserByFullName(String fullName) throws IOException, ClassNotFoundException {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getFullName().equals(fullName)) {
                return user;
            }
        }
        return null; // Kullanıcı bulunamadı
    }

}

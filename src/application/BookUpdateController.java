package application;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BookUpdateController {
    @FXML
    private ComboBox<String> cbBookName;
    @FXML
    private TextField txtBookName;
    @FXML
    private TextField txtBookType;
    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtISBN;
    @FXML
    private TextField txtPublishedYear;
    @FXML
    private TextField txtPageQuantity;

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnBack;

    @FXML
    private void initialize() {
        try {
            // Tüm kitapları al
            List<Book> books = Book.getAllBooks();
            
            // Kitap isimlerini combobox'a ekle
            ObservableList<String> bookNames = FXCollections.observableArrayList();
            for (Book book : books) {
                bookNames.add(book.getTitle());
            }
            
            // Combobox'a kitap isimlerini ekle
            cbBookName.setItems(bookNames);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Hata", "Kitap listesi alınırken bir hata oluştu.");
        }
    }


    @FXML
    private void actionBookUpdate(MouseEvent event) {
        // Seçilen kitabın bilgilerini güncelleme işlemi
        String selectedBookName = cbBookName.getValue();
        if (selectedBookName != null) {
            // Seçilen kitabın diğer bilgilerini alarak güncelleme işlemi gerçekleştirilebilir
            String type = txtBookType.getText();
            String author = txtAuthor.getText();
            String ISBN = txtISBN.getText();
            int publishedYear = Integer.parseInt(txtPublishedYear.getText());
            int pageQuantity = Integer.parseInt(txtPageQuantity.getText());
            
            // Güncelleme işlemini yapmak için bir method çağrılabilir
            try {
                Book book = Book.findBookByName(selectedBookName); // Kitabı ismine göre bul
                if (book != null) {
                    book.setType(type);
                    book.setAuthor(author);
                    book.setISBN(ISBN);
                    book.setYearPublished(publishedYear);
                    book.setPageQuantity(pageQuantity);
                    book.update(); // Kitabı güncelle
                    showAlert("Başarılı", "Kitap güncelleme işlemi başarıyla tamamlandı.");
                } else {
                    showAlert("Hata", "Kitap bulunamadı.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Hata", "Kitap güncelleme işlemi sırasında bir hata oluştu.");
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
            showAlert("Hata", "Lütfen güncellenecek kitabı seçin.");
        }
    }
    
    @FXML
    private void writeInfos() {
    	try {
			Book book = Book.findBookByName(cbBookName.getValue());
			txtBookName.setText(book.getTitle());
			txtBookType.setText(book.getType());
			txtAuthor.setText(book.getAuthor());
			txtISBN.setText(book.getISBN());
			txtPublishedYear.setText(book.getYearPublished()+"");
			txtPageQuantity.setText(book.getPageQuantity()+"");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    private void actionBack(MouseEvent event) {
        openWindow(event, "MainMenu.fxml", "Ana Menü");
    }

    private void openWindow(MouseEvent event, String fxmlFile, String title) {
        Parent root = null;
        Stage newStage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource(fxmlFile));
            IconHelper.setIcon(newStage);
            newStage.setTitle(title);
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

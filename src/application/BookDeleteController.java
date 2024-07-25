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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BookDeleteController {
	@FXML ComboBox<String> cbBookName;
	@FXML TextField txtBookType;
	@FXML TextField txtAuthor;
	@FXML TextField txtISBN;
	@FXML TextField txtPublishedYear;
	@FXML TextField txtPageQuantity;
	
	@FXML Button btnDelete;
	@FXML Button btnBack;
	
	
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
    private void actionBookDelete(MouseEvent event) {
        String selectedBookName = cbBookName.getValue();
        if (selectedBookName != null) {
            try {
                Book book = Book.findBookByName(selectedBookName);
                if (book != null) {
                    book.delete();
                    showAlert("Başarılı", "Kitap silme işlemi başarıyla tamamlandı.");
                    cbBookName.getItems().remove(selectedBookName);
                    txtBookType.setText("");
                    txtAuthor.setText("");
                    txtISBN.setText("");
                    txtPublishedYear.setText("");
                    txtPageQuantity.setText("");
                } else {
                    showAlert("Hata", "Kitap bulunamadı.");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                showAlert("Hata", "Kitap silme işlemi sırasında bir hata oluştu: " + e.getMessage());
            }
        } else {
            showAlert("Hata", "Lütfen silinecek kitabı seçin.");
        }
    }
	
	@FXML
    private void writeInfos() {
    	try {
			Book book = Book.findBookByName(cbBookName.getValue());
			
			if(book != null) {
				txtBookType.setText(book.getType());
				txtAuthor.setText(book.getAuthor());
				txtISBN.setText(book.getISBN());
				txtPublishedYear.setText(book.getYearPublished()+"");
				txtPageQuantity.setText(book.getPageQuantity()+"");
			}
			
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
		openWindow(event);
	}
	
	private void openWindow(MouseEvent event) {
		Parent root = null;
		Stage newStage = new Stage();
		try {
			 root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
			 IconHelper.setIcon(newStage);
			 newStage.setTitle("Ana Menü");
			 newStage.setScene(new Scene(root));
			 newStage.show();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}
	
	private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

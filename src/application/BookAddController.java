package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BookAddController {

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
    private Button btnAdd;

    @FXML
    private Button btnBack;

    @FXML
    void actionBookAdd(MouseEvent event) {
        try {
            String title = txtBookName.getText();
            String type = txtBookType.getText();
            String author = txtAuthor.getText();
            String ISBN = txtISBN.getText();
            int yearPublished = Integer.parseInt(txtPublishedYear.getText());
            int pageQuantity = Integer.parseInt(txtPageQuantity.getText());

            Book newBook = new Book(title, type, author, ISBN, yearPublished, pageQuantity);
            newBook.create();
            showAlert("Başarılı", "Kitap başarılı bir şekilde eklendi");
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void actionBack(MouseEvent event) {
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
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

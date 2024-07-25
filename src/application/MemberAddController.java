package application;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MemberAddController {
	
	@FXML TextField txtMemberName;
	@FXML TextField txtMemberPassword;
	@FXML TextField txtMemberMail;
	
	@FXML Button btnAdd;
	
	@FXML
	private void actionMemberAdd(MouseEvent event) {
		try {
			String fullName = txtMemberName.getText();
			String password = txtMemberPassword.getText();
			String eMail = txtMemberMail.getText();
			
			User user = new User(fullName, password, eMail, LocalDate.now());
			user.create();
			showAlert("Başarılı", "Kullanıcı başarılı bir şekilde eklendi");
		} catch (Exception e) {
			showAlert("Hata", e.getMessage());
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
	
	private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

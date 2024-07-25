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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MemberUpdateController {
	@FXML ComboBox<String> cbMemberName;
	@FXML TextField txtMemberPassword;
	@FXML TextField txtMemberEmail;
	
	@FXML Button btnUpdate;
	@FXML Button btnBack;
	
	@FXML
    private void initialize() {
        try {
            List<User> user = User.getAllUsers();
            
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User users: user) {
                userNames.add(users.getFullName());
            }
            
            cbMemberName.setItems(userNames);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Hata", "Üye listesi alınırken bir hata oluştu.");
        }
    }
	
	@FXML
	private void actionMemberUpdate(MouseEvent event) {
	    // Seçilen kullanıcının bilgilerini güncelleme işlemi
	    String selectedUserName = cbMemberName.getValue();
	    if (selectedUserName != null) {
	        // Seçilen kullanıcının diğer bilgilerini alarak güncelleme işlemi gerçekleştirilebilir
	        String newPassword = txtMemberPassword.getText();
	        String newEmail = txtMemberEmail.getText();
	        
	        // Güncelleme işlemini yapmak için bir method çağrılabilir
	        try {
	            User user = User.findUserByFullName(selectedUserName); // Kullanıcıyı tam adına göre bul
	            if (user != null) {
	                user.setPassword(newPassword);
	                user.setMail(newEmail);
	                user.update(); // Kullanıcıyı güncelle
	                showAlert("Başarılı", "Kullanıcı güncelleme işlemi başarıyla tamamlandı.");
	            } else {
	                showAlert("Hata", "Kullanıcı bulunamadı.");
	            }
	        } catch (IOException | ClassNotFoundException e) {
	            e.printStackTrace();
	            showAlert("Hata", "Kullanıcı güncelleme işlemi sırasında bir hata oluştu.");
	        }
	    } else {
	        showAlert("Hata", "Lütfen güncellenecek kullanıcıyı seçin.");
	    }
	}

	
	@FXML
    private void writeInfos() {
    	try {
			User user = User.findUserByFullName(cbMemberName.getValue());
			txtMemberPassword.setText(user.getPassword());
			txtMemberEmail.setText(user.getMail());
			
			
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
	
	private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

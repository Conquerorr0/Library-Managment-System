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
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MemberDeleteController {
	@FXML ComboBox<String> cbMemberName;
	@FXML TableView<String> tblMember;
	
	@FXML Button btnDelete;
	@FXML Button btnBack;
	
	@FXML
    private void initialize() {
        try {
            // Tüm kitapları al
            List<User> users = User.getAllUsers();

            // Kitap isimlerini combobox'a ekle
            ObservableList<String> userNames = FXCollections.observableArrayList();
            for (User user: users) {
                userNames.add(user.getFullName());
            }

            // Combobox'a kitap isimlerini ekle
            cbMemberName.setItems(userNames);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Hata", "Kitap listesi alınırken bir hata oluştu.");
        }
    }
	
	@FXML
	private void actionMemberDelete(MouseEvent event) {
		String selectedUser = cbMemberName.getValue();
        if (selectedUser != null) {
            try {
                User user = User.findUserByFullName(selectedUser);
                if (user != null) {
                    user.delete();
                    showAlert("Başarılı", "Üye silme işlemi başarıyla tamamlandı.");
                    cbMemberName.getItems().remove(selectedUser);
                } else {
                    showAlert("Hata", "Üye bulunamadı.");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                showAlert("Hata", "Üye silme işlemi sırasında bir hata oluştu: " + e.getMessage());
            }
        } else {
            showAlert("Hata", "Lütfen silinecek Üye seçin.");
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

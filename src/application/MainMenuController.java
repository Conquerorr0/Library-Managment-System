package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainMenuController {
	@FXML Button btnBookList;
	@FXML Button btnBookAdd;
	@FXML Button btnBookUpdate;
	@FXML Button btnBookDelete;
	@FXML Button btnMemberList;
	@FXML Button btnMemberAdd;
	@FXML Button btnMemberUpdate;
	@FXML Button btnMemberDelete;
	
	@FXML ImageView imageBookList;
	@FXML ImageView imageBookAdd;
	@FXML ImageView imageBookUpdate;
	@FXML ImageView imageBookDelete;
	@FXML ImageView imageMemeberList;
	@FXML ImageView imageMemeberAdd;
	@FXML ImageView imageMemeberUpdate;
	@FXML ImageView imageMemeberDelete;
	
	@FXML
	private void actionBookList(MouseEvent event) {
		openWindow(event, "BookList", "Kitaplar");
	}
	@FXML
	private void actionBookAdd(MouseEvent event) {
		openWindow(event, "BookAdd", "Kitap Ekleme");
	}
	@FXML
	private void actionBookUpdate(MouseEvent event) {
		openWindow(event, "BookUpdate", "Kitap Güncelleme");
	}
	@FXML
	private void actionBookDelete(MouseEvent event) {
		openWindow(event, "BookDelete", "Kitap Silme");
	}
	@FXML
	private void actionMemberList(MouseEvent event) {
		openWindow(event, "MemberList", "Üyeler");
	}
	@FXML
	private void actionMemberAdd(MouseEvent event) {
		openWindow(event, "MemberAdd", "Üye Ekleme");
	}
	@FXML
	private void actionMemberUpdate(MouseEvent event) {
		openWindow(event	, "MemberUpdate", "Üye Güncelleme");
	}
	@FXML
	private void actionMemberDelete(MouseEvent event) {
		openWindow(event, "MemberDelete", "Üye Silme");
	}
	
	
	public void handleImageViewClick(MouseEvent event) {
	    actionBookList(event);
	}
	
	private void openWindow(MouseEvent event, String windowName, String windowTitle) {
		Parent root = null;
		Stage newStage = new Stage();
		try {
			 root = FXMLLoader.load(getClass().getResource(windowName + ".fxml"));
			 IconHelper.setIcon(newStage);
			 newStage.setTitle(windowTitle);
			 newStage.setScene(new Scene(root));
			 newStage.show();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.close();
	}

}


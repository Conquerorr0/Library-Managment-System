package application;

import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.time.LocalDate;

public class SampleController {
	 @FXML TextField txtFullName;
	    @FXML TextField txtPassword;
	    @FXML TextField txtEmail;
	    @FXML Button btnChooseSignUp;
	    @FXML Button btnChooseSignIn;
	    @FXML Button btnSignIn;
	    @FXML Label lblFullName;
	    @FXML Line lineName;

	    private boolean isSignIn = true;

	    private String signInName;
	    private String signInPassword;
	    private String signInEmail;
	    private String registerName;
	    private String registerPassword;
	    private String registerEmail;

	    @FXML
	    private void actionChooseSingUp() {
	        btnChooseSignUp.setStyle("-fx-background-radius: 20 0 0 20; -fx-background-color: #248f3e;");
	        btnChooseSignIn.setStyle("-fx-background-radius: 0 20 20 0; -fx-background-color: #28658a;");
	        btnSignIn.setText("KAYIT OL");
	        isSignIn = false;
	        lblFullName.setVisible(true);
	        txtFullName.setVisible(true);
	        lineName.setVisible(true);
	        getSignIn();
	        setRegister();
	    }

	    @FXML
	    private void actionChooseSingIn() {
	        btnChooseSignIn.setStyle("-fx-background-radius: 0 20 20 0; -fx-background-color: #248f3e;");
	        btnChooseSignUp.setStyle("-fx-background-radius: 20 0 0 20; -fx-background-color: #28658a;");
	        btnSignIn.setText("GİRİŞ YAP");
	        isSignIn = true;
	        lblFullName.setVisible(false);
	        txtFullName.setVisible(false);
	        lineName.setVisible(false);
	        getRegister();
	        setSignIn();
	    }
	    
	    @FXML
	    private void actionSignIn(MouseEvent event) {
	    	if (isSignIn) {
	    	    try {
	    	        User user = User.authenticateUser(txtEmail.getText(), txtPassword.getText());
	    	        if (user != null) {
	    	            if (user.getMail().equals("admin@outlook.com")) {
	    	                openWindow(event, "MainMenu", "Ana Menü");
	    	            } else {
	    	                openWindow(event, "MemberBookList", "Kitaplar");
	    	            }
	    	        } else {
	    	            showAlert("Giriş Başarısız", "Email veya şifre yanlış.");
	    	        }
	    	    } catch (IOException e) {
	    	        e.printStackTrace();
	    	        showAlert("Dosya Hatası", "Kullanıcı bilgileri okunamadı.");
	    	    }
	    	} else {
	    	    User newUser = new User(txtFullName.getText(), txtPassword.getText(), txtEmail.getText(), LocalDate.now());
	    	    try {
	    	        newUser.create();
	    	        showAlert("Kayıt Başarılı", "Kullanıcı başarıyla kaydedildi.");
	    	        actionChooseSingIn();
	    	    } catch (Exception e) {
	    	        e.printStackTrace();
	    	        showAlert("Dosya Hatası", "Kullanıcı bilgileri kaydedilemedi.");
	    	    }
	    	}
	    }

	    private void setSignIn() {
	        txtFullName.setText(signInName);
	        txtPassword.setText(signInPassword);
	        txtEmail.setText(signInEmail);
	    }

	    private void getSignIn() {
	        signInName = txtFullName.getText();
	        signInPassword = txtPassword.getText();
	        signInEmail = txtEmail.getText();
	    }

	    private void setRegister() {
	        txtFullName.setText(registerName);
	        txtPassword.setText(registerPassword);
	        txtEmail.setText(registerEmail);
	    }

	    private void getRegister() {
	        registerName = txtFullName.getText();
	        registerPassword = txtPassword.getText();
	        registerEmail = txtEmail.getText();
	    }

	    private void openWindow(MouseEvent event, String windowName, String windowTitle) {
	        Parent root = null;
	        Stage newStage = new Stage();
	        try {
	            root = FXMLLoader.load(getClass().getResource(windowName + ".fxml"));
	            newStage.setTitle(windowTitle);
	            newStage.setScene(new Scene(root));
	            newStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
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

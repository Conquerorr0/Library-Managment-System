package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MemberListController implements Initializable{
	
	@FXML TextField txtMemberSearch;
	@FXML TableView<User> tblMember;
	@FXML Button btnBack;
	
    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colPassword;

    @FXML
    private TableColumn<User, String> colMail;

	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColumns();
        loadMemberData();
    }

    private void loadMemberData() {
        try {
            ObservableList<User> users = User.getAllUsers();
            setMemberList(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMemberList(ObservableList<User> users) {
        userList.clear(); 
        userList.addAll(users);
        tblMember.setItems(userList);
    }
    
    private void initializeColumns() {
        colName.setCellValueFactory(new PropertyValueFactory<User, String>("fullName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        colMail.setCellValueFactory(new PropertyValueFactory<User, String>("mail"));
    }


	
	private ObservableList<User> userList = FXCollections.observableArrayList();
	public void initialize() {
        // Verileri tabloya ekleme işlemi
        tblMember.setItems(userList);
    }
	
	@FXML
	private void actionSearchMember(KeyEvent event) {
		String searchText = txtMemberSearch.getText().toLowerCase();

        ObservableList<User> filteredList = FXCollections.observableArrayList();

        for (User user: userList) {
            if (user.getFullName().toLowerCase().contains(searchText) ||
            		user.getMail().toLowerCase().contains(searchText)) {
                filteredList.add(user);
            }
        }

        tblMember.setItems(filteredList);
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
	
}

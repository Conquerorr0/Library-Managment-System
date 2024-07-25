package application;

import java.io.IOException;
import java.net.URL;
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

public class BookListController  implements Initializable{

	@Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColumns();
        loadBookData();
    }
	
    private void initializeColumns() {
        colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        colType.setCellValueFactory(new PropertyValueFactory<Book, String>("type"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        colISBN.setCellValueFactory(new PropertyValueFactory<Book, String>("ISBN"));
        colYearPublished.setCellValueFactory(new PropertyValueFactory<Book, Integer>("yearPublished"));
        colPageQuantity.setCellValueFactory(new PropertyValueFactory<Book, Integer>("pageQuantity"));
    }

    private void loadBookData() {
        try {
            ObservableList<Book> books = Book.getAllBooks();
            setBookList(books);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file loading error
        }
    }
    
    @FXML
    private TextField txtSearchBook;

    @FXML
    private TableView<Book> tblBook;

    @FXML
    private Button btnBack;

    private ObservableList<Book> bookList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colType;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colISBN;

    @FXML
    private TableColumn<Book, Integer> colYearPublished;

    @FXML
    private TableColumn<Book, Integer> colPageQuantity;
    
    @FXML
    private void actionSearchBook(KeyEvent event) {
        String searchText = txtSearchBook.getText().toLowerCase();

        ObservableList<Book> filteredList = FXCollections.observableArrayList();

        for (Book book : bookList) {
            if (book.getTitle().toLowerCase().contains(searchText) ||
                book.getType().toLowerCase().contains(searchText) ||
                book.getAuthor().toLowerCase().contains(searchText)) {
                filteredList.add(book);
            }
        }

        tblBook.setItems(filteredList);
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

    public void setBookList(ObservableList<Book> books) {
        bookList.addAll(books);
        tblBook.setItems(bookList);
    }

	
}
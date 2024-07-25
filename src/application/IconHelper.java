package application;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class IconHelper {

    public static void setIcon(Stage stage) {
        try {
            Image icon = new Image("file:///C:/Users/User/eclipse-workspace/LibraryManagmentSystem/Images/AppImage/Library.png");
            stage.getIcons().add(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

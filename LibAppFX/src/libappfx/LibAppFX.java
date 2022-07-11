package libappfx;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * LibAppFX.java
 *
 * 
 *
 * @author Orien Goulding
 * @version 1.0
 *
 * Date Written: 22nd August 2014
 *
 * Subject: Object Oriented Application Development 
 * Assignment: 1 
 * Tutor: Mary Martin
 *
 */
public class LibAppFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // This line to resolve keys against properties for English language
        ResourceBundle i18nBundle = ResourceBundle.getBundle("libappfx.view.resources.Bundle", new Locale("en", "US"));
        //This line to resolve keys against properties for French language
        //ResourceBundle i18nBundle = ResourceBundle.getBundle("libappfx.view.resources.Bundle", new Locale("fr", "FR"));
        Parent root = FXMLLoader.load(getClass().getResource("view/LibAppView.fxml"), i18nBundle);

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

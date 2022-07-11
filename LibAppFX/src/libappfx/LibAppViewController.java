package libappfx;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
import libappfx.model.DVD;
import libappfx.model.IOManager;

/**
 * LibAppViewController.java
 *
 * Controller class to process the object interaction for a personal DVD
 * library.
 *
 * @author Orien Goulding
 * @version 1.0
 *
 * Date Written: 22nd August 2014
 *
 * Subject: Object Oriented Application Development Assignment: 1 Tutor: Mary
 * Martin
 *
 */
public class LibAppViewController implements Initializable {

    @FXML
    private MenuButton menuFile;
    @FXML
    private MenuItem menuItemOpen;
    @FXML
    private MenuItem menuItemSave;
    @FXML
    private MenuItem menuItemSaveAs;
    @FXML
    private MenuItem menuItemQuit;
    @FXML
    private TableView<DVD> myDVDTableView;
    @FXML
    private TableColumn<DVD, String> genreColumn;
    @FXML
    private TableColumn<DVD, String> titleColumn;
    @FXML
    private TabPane dvdTabPane;
    @FXML
    private Tab dvdTab;
    @FXML
    private Tab detailsTab;
    @FXML
    private Tab imageTab;
    @FXML
    private ImageView dvdImage;
    @FXML
    private TextField messageField;
    @FXML
    private TextField imagePathField;
    @FXML
    private TextField directorField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField durationField;
    @FXML
    private Button okButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button cancelButton;
    @FXML
    private MenuItem menuItemNew;
    @FXML
    private MenuItem menuItemDvd;
    @FXML
    private MenuItem menuItemDelete;
    private Label titleLabel;
    private Label durationLabel;
    private Label datePurchasedLabel;
    private Label nameLabel;
    @FXML
    private MenuButton menuDvd;
    @FXML
    private TableView<String> genreTableView;
    @FXML
    private TableColumn<String, String> genreSelectionColumn;
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private Button allGenreButton;
    @FXML
    private DatePicker datePurchasedDatePicker;

    //controller data... 
    private ObservableList<DVD> listViewDVDData;// for myDVDTableView
    private ArrayList<DVD> dvdDatabase;// for listViewDVDData
    private String currentFileName = "dvd.dat";// Initial file to be loaded
    private boolean genreDisplayed = false;// Indicates whether genre list is desplayed
    private ObservableList<String> listViewDVDGenre;// for genreTableView
    private ArrayList<String> dvdDatabaseGenres;// for listViewDVDGenre
    private static final String genreFileName = "genre.dat";// file name for genres
    private static final String pattern = "d-MMM-yyyy";// format of the date purchased
    private static final String pattern2 = "dd-MMM-yyyy";// format datePicker will display date purchased

    // Values to populate the status field
    private static final HashMap<String, String> messages = new HashMap<String, String>() {
        {
            put("Loaded", "DVD File Loaded");
            put("Saved", "DVD File Saved");
            put("Adding", "Adding New DVD");
            put("Editing", "Editing Selected DVD");
            put("Deleting", "Deleting Selected DVD");
            put("Deleted", "DVD deleted!!!");
            put("Select", "Please select a DVD!!!");
            put("Empty", "No DVD!!!");
            put("Cancel", "Operation cancelled");
            put("Reset", "DVD Reset");
            put("Done", "Adding/Editing DVD completed");
            put("GenreNull", "Genre must not be empty");
            put("TitleNull", "Title must not be empty");
            put("DirectorNull", "Director must not be empty");
            put("DurationNull", "Duration must not be empty");
            put("PathNull", "Image Path must not be empty");
        }
    };

    /**
     * Loads DVDs into myDVDTableView from a file. Files can be either ".dat",
     * ".csv", or ".xml"
     */
    private void loadDVDs() {
        //Read File contents into ArrayList
        if (currentFileName.endsWith(".dat") || currentFileName.endsWith(".csv")) {
            dvdDatabase = IOManager.readTextDatabase(new File(currentFileName));
        }
        if (currentFileName.endsWith(".xml")) {
            dvdDatabase = IOManager.readXMLDatabase(currentFileName);
        }
        //Create a new empty observable list that is backed by an arraylist.
        listViewDVDData = FXCollections.observableArrayList();
        //Set contents of ArrayList in Observable List
        listViewDVDData.setAll(dvdDatabase);
        //Set contents of observable list in Table View 
        myDVDTableView.setItems(listViewDVDData);
    }

    /**
     * Loads genres into genreTableView from a file. Files can be either ".dat"
     * or ".csv"
     */
    private void loadGenres() {
        //Read File contents into ArrayList
        if (currentFileName.endsWith(".dat") || currentFileName.endsWith(".csv")) {
            dvdDatabaseGenres = IOManager.readGenreList(genreFileName);

        }

        //Create a new empty observable list that is backed by an arraylist.
        listViewDVDGenre = FXCollections.observableArrayList();
        //Set contents of ArrayList in Observable List
        listViewDVDGenre.setAll(dvdDatabaseGenres);
        //Set contents of observable list in Table View 
        genreTableView.setItems(listViewDVDGenre);
    }

    /**
     * Display details of a selected DVD
     *
     * @param dvd a DVD that has been selected from myDVDTableView
     */
    private void displayDVDDetails(DVD dvd) {
        if (dvd != null) {

            titleField.setText(dvd.getTitle());
            if (dvd.getGenre() != null) {
                String genre = dvd.getGenre().toLowerCase().trim();
                genre = genre.substring(0, 1).toUpperCase() + genre.substring(1);
                if (listViewDVDGenre.contains(genre)) {
                    genreComboBox.setValue(genre);
                } else {
                    genreComboBox.setValue(null);
                }
            } else {
                genreComboBox.setValue(null);
            }
            directorField.setText(dvd.getDirector());
            durationField.setText(dvd.getDuration());
            if (dvd.getDatePurchased() != null && isValidDate(dvd.getDatePurchased())
                    && (LocalDate.parse(dvd.getDatePurchased(), DateTimeFormatter.ofPattern(pattern)).isBefore(LocalDate.now())
                    || LocalDate.parse(dvd.getDatePurchased(), DateTimeFormatter.ofPattern(pattern)).isEqual(LocalDate.now()))) {
                datePurchasedDatePicker.setValue(LocalDate.parse(dvd.getDatePurchased(), DateTimeFormatter.ofPattern(pattern)));
            } else {
                datePurchasedDatePicker.setValue(null);
            }
            imagePathField.setText(dvd.getImageURL());
        } else {
            clearDVDDetails();
        }
    }

    /**
     * Checks if a DVD's datePurcahsed is a valid date. Returns true if date is
     * valid.
     *
     * @param query a string from DVD's date purchased
     * @return boolean indicating validity of the date string
     */
    private static boolean isValidDate(String query) {
        boolean valid;
        try {
            LocalDate.parse(query, DateTimeFormatter.ofPattern(pattern));
            valid = true;
        } catch (DateTimeParseException e) {
            valid = false;
        }
        return valid;
    }

    /**
     * Removes displayed DVD details
     */
    private void clearDVDDetails() {
        titleField.setText("");
        genreComboBox.setValue(null);
        directorField.setText("");
        durationField.setText("");
        datePurchasedDatePicker.setValue(null);
        imagePathField.setText("");
    }

    /**
     * Sets the format of the datePicker & prevents future dates from being
     * selected
     */
    private void datePicker() {

        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter
                    = DateTimeFormatter.ofPattern(pattern2);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isAfter(
                                        LocalDate.now())) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        datePurchasedDatePicker.setDayCellFactory(dayCellFactory);
        datePurchasedDatePicker.setConverter(converter);
        datePurchasedDatePicker.setPromptText(pattern2.toLowerCase());
        datePurchasedDatePicker.requestFocus();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //invoke method to get the dvd data and add to an observable list  
        loadDVDs();
        loadGenres();
        datePicker();

        //Get the cellData and set the value of the table cells to first and last names of patients
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        genreColumn.setCellValueFactory(cellData -> cellData.getValue().genreProperty());
        genreSelectionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        genreComboBox.setItems(listViewDVDGenre);
        // Auto resize columns 
        myDVDTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        genreTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Listen for selection changes and update selected dvd details with new selected dvd details
        myDVDTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends DVD> observable, DVD oldValue, DVD newValue) -> {
                    switch (dvdTabPane.getSelectionModel().getSelectedItem().getText()) {
                        case "Details":
                            displayDVDDetails(newValue);
                            break;
                        case "Image":
                            displayDvdImage(newValue);
                            break;
                    }
                });

        genreTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    createListGenre(newValue);
                });

        //Select first entry
        myDVDTableView.getSelectionModel().selectFirst();

    }

    /**
     * Creates a FileChooser so a new file can be loaded. Allows ".dat", ".csv,
     * and ".xml" to be selected.
     *
     * @param event from menuItemOpen
     */
    @FXML
    private void handleFileOpen(ActionEvent event) {
        //Create a FileChooser object
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterD = new FileChooser.ExtensionFilter("TXT files (*.dat)", "*.dat");
        FileChooser.ExtensionFilter extFilterC = new FileChooser.ExtensionFilter("TXT files (*.csv)", "*.csv");
        FileChooser.ExtensionFilter extFilterX = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilterC);
        fileChooser.getExtensionFilters().add(extFilterD);
        fileChooser.getExtensionFilters().add(extFilterX);
        //Set the current directory
        fileChooser.setInitialDirectory(new File("."));
        //Show open file dialog and load contents of selected file
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            currentFileName = file.getName();
            loadDVDs();
        }
    }

    /**
     * Writes DVD objects within listViewDVDData to a file. File can be either
     * ".dat", ".csv, or ".xml".
     *
     * @param file a file to be written too.
     */
    private void writeDvdDatabase(File file) {
        ArrayList<DVD> currentData = new ArrayList();
        currentData.addAll(listViewDVDData);
        if (file.getName().endsWith(".dat") || file.getName().endsWith(".csv")) {
            IOManager.writeTextDatabase(file, currentData);
        } else if (file.getName().endsWith(".xml")) {
            IOManager.writeXMLDatabase(file.getName(), currentData);
        }
    }

    /**
     * Creates a FileChooser so a new file can be saved.
     *
     * @param event from menuItemSaveAs
     */
    @FXML
    private void handleFileSaveAs(ActionEvent event) {
        //Create a FileChooser object
        FileChooser fileChooser = new FileChooser();
        //Set the current directory, Dialog title, and initial filename
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.setTitle("Save File as...");
        fileChooser.setInitialFileName(currentFileName);
        //Show save file dialog and save contents of dvdDatabase
        File file = fileChooser.showSaveDialog(null);
        if (null != file) {
            writeDvdDatabase(file);
        }
    }

    /**
     * Saves to the current file.
     *
     * @param event from menuItemSave
     */
    @FXML
    private void handleFileSave(ActionEvent event) {
        File file = new File(currentFileName);
        writeDvdDatabase(file);
    }

    /**
     * Exits the application
     *
     * @param event from menuItemQuit
     */
    @FXML
    private void handleQuit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Displays the relevant DVD image. Default image displayed if there is an
     * error.
     *
     * @param dvd a DVD to be displayed
     */
    private void displayDvdImage(DVD dvd) {
        Image image;
        try {
            image = new Image(getClass().getResourceAsStream(dvd.getImageURL()));
        } catch (Exception e) {
            image = new Image(getClass().getResourceAsStream("view/images/myDvds.png"));
        }
        dvdImage.setImage(image);
    }

    /**
     * Displays DVD details upon selection
     *
     * @param event from detailsTab
     */
    @FXML
    private void handleDetailsTabSelected(Event event) {
        DVD dvd = myDVDTableView.getSelectionModel().getSelectedItem();
        displayDVDDetails(dvd);
    }

    /**
     * Displays image relevant to DVD upon selection
     *
     * @param event from imageTab
     */
    @FXML
    private void handleImageTabSelected(Event event) {
        DVD dvd = myDVDTableView.getSelectionModel().getSelectedItem();
        displayDvdImage(dvd);
    }

    /**
     * Allows a new DVD to be created.
     *
     * @param event
     */
    @FXML
    private void handleNewDvd(ActionEvent event) {
        messageField.setText((String) messages.get("Adding"));
        setDvdDetailFieldsEditable(true);
        setEditControlsVisible(true);
        dvdTabPane.getSelectionModel().select(detailsTab);
        DVD dvd = new DVD();
        listViewDVDData.add(dvd);
        myDVDTableView.getSelectionModel().selectLast();
        datePurchasedDatePicker.setValue(LocalDate.now());
    }

    /**
     * Allows the current DVD to be edited.
     *
     * @param event
     */
    @FXML
    private void handleEditDvd(ActionEvent event) {
        editDvd();
    }

    private void editDvd() {
        messageField.setText(messages.get("Editing"));
        setDvdDetailFieldsEditable(true);
        setEditControlsVisible(true);
        dvdTabPane.getSelectionModel().select(detailsTab);
    }

    /**
     * Allows the current DVD to be deleted.
     *
     * @param event
     */
    @FXML
    private void handleDeleteDvd(ActionEvent event) {
        messageField.setText(messages.get("Deleting"));
        try {
            int selectedIndex = myDVDTableView.getSelectionModel().getSelectedIndex();
            DVD currentDvd = listViewDVDData.get(selectedIndex);
            myDVDTableView.getItems().remove(selectedIndex);
            messageField.setText(messages.get("Deleted"));

            if (genreDisplayed) {
                dvdDatabase.remove(currentDvd);
            }
        } catch (Exception e) {
            messageField.setText(messages.get("Select"));
        }
        if (myDVDTableView.getSelectionModel().isEmpty()) {
            messageField.setText(messages.get("Empty"));
        } else {
            myDVDTableView.getSelectionModel().selectFirst();
        }
        dvdTabPane.getSelectionModel().select(dvdTab);
    }

    /**
     * Cancels a new DVD from being created or the current DVD from being
     * modified.
     *
     * @param event
     */
    @FXML
    private void handleCancelButton(ActionEvent event) {
        if (messageField.getText().equals(messages.get("Adding"))) {
            myDVDTableView.getSelectionModel().selectLast();
            int index = myDVDTableView.getSelectionModel().getSelectedIndex();
            myDVDTableView.getItems().remove(index);
        }
        setDvdDetailFieldsEditable(false);
        setEditControlsVisible(false);
        dvdTabPane.getSelectionModel().select(dvdTab);
        messageField.setText(messages.get("Cancel"));
    }

    /**
     * Resets the current DVD to null.
     *
     * @param event
     */
    @FXML
    private void handleResetButton(ActionEvent event) {
        DVD dvd = new DVD();
        int selectedIndex = myDVDTableView.getSelectionModel().getSelectedIndex();
        if (genreDisplayed) {
            DVD currentDvd = listViewDVDData.get(selectedIndex);
            if (dvdDatabase.contains(currentDvd)) {
                dvdDatabase.set(dvdDatabase.indexOf(currentDvd), dvd);
            }
        }
        listViewDVDData.set(selectedIndex, dvd);
        messageField.setText(messages.get("Reset"));
        editDvd();
    }

    /**
     * Sets the values of the current DVD's data members. Title, Genre,
     * Director, Duration must be set
     *
     * @param event
     */
    @FXML
    private void handleOkButton(ActionEvent event) {
        if (titleField.getText() == null || titleField.getText().equals("")) {
            messageField.setText(messages.get("TitleNull"));
            titleField.getStyleClass().add("error");
        } else if (genreComboBox.getValue() == null) {
            messageField.setText(messages.get("GenreNull"));
            genreComboBox.getStyleClass().add("error");
        } else if (directorField.getText() == null || directorField.getText().equals("")) {
            messageField.setText(messages.get("DirectorNull"));
            directorField.getStyleClass().add("error");
        } else if (durationField.getText() == null || durationField.getText().equals("")) {
            messageField.setText(messages.get("DurationNull"));
            durationField.getStyleClass().add("error");
        } else if (imagePathField.getText() == null || imagePathField.getText().equals("")) {
            messageField.setText(messages.get("PathNull"));
            imagePathField.getStyleClass().add("error");
        } else {
            int selectedIndex = myDVDTableView.getSelectionModel().getSelectedIndex();
            if (genreDisplayed) {
                DVD currentDvd = listViewDVDData.get(selectedIndex);
                if (dvdDatabase.contains(currentDvd)) {
                    dvdDatabase.set(dvdDatabase.indexOf(currentDvd), getDvd());
                } else {
                    dvdDatabase.add(getDvd());
                }
            }

            listViewDVDData.set(selectedIndex, getDvd());
            refreshDvdTable();
            setDvdDetailFieldsEditable(false);
            setEditControlsVisible(false);

            messageField.setText(messages.get("Done"));
        }
    }

    // Validates the digit fields
    @FXML
    private void handleDigitField(KeyEvent event) {
        if (event.getCharacter().matches("[a-zA-z\\s]*")) {
            durationField.getStyleClass().add("error");
            event.consume();
        } else {
            durationField.getStyleClass().remove("error");
        }
    }

    // Validates the string field
    @FXML
    private void handleStringField(KeyEvent event) {
        TextField inputField = (TextField) event.getSource();
        if (event.getCharacter().matches("[0-9]*")) {
            inputField.getStyleClass().add("error");
            event.consume();
        } else {
            inputField.getStyleClass().remove("error");
        }
    }

    // Removes css style "error" from text field
    @FXML
    private void handleMouseMoved(MouseEvent event) {
        TextField inputField = (TextField) event.getSource();
        inputField.getStyleClass().remove("error");
    }

    // Removes css style "error" from combo box
    @FXML
    private void handleMouseMovedGenre(MouseEvent event) {
        genreComboBox.getStyleClass().remove("error");
    }

    /**
     * Enables & disables detail input
     *
     * @param state a boolean signals fields editable or not
     */
    private void setDvdDetailFieldsEditable(boolean state) {
        titleField.setEditable(state);
        genreComboBox.setDisable(!state);
        directorField.setEditable(state);
        durationField.setEditable(state);
        datePurchasedDatePicker.setDisable(!state);
        imagePathField.setEditable(state);
    }

    /**
     * Sets the visibility of the control buttons.
     *
     * @param state a boolean toggles the visibility
     */
    private void setEditControlsVisible(boolean state) {
        okButton.setVisible(state);
        cancelButton.setVisible(state);
        resetButton.setVisible(state);
    }

    /**
     * Creates a DVD using details from the input fields
     *
     * @return a DVD
     */
    private DVD getDvd() {
        DVD dvd = new DVD(titleField.getText(), genreComboBox.getValue(),
                directorField.getText(), imagePathField.getText(),
                durationField.getText(), DateTimeFormatter.ofPattern(pattern).format(datePurchasedDatePicker.getValue()));
        return dvd;
    }

    // Refreshes myDVDTableView so changes can be displayed
    private void refreshDvdTable() {
        int selectedIndex = myDVDTableView.getSelectionModel().getSelectedIndex();
        myDVDTableView.setItems(null);
        myDVDTableView.layout();
        myDVDTableView.setItems(listViewDVDData);
        myDVDTableView.getSelectionModel().select(selectedIndex);
    }

    /**
     * Creates a list of a specific genre and displays it in myDVDTableView.
     *
     * @param genre a string which is the target genre
     */
    private void createListGenre(String genre) {
        if (!genreDisplayed) {
            menuItemSave.setDisable(true);
            dvdDatabase.clear();
            dvdDatabase.addAll(listViewDVDData);
            genreDisplayed = true;
        }
        ArrayList<DVD> dvdDatabaseGenre = new ArrayList();
        dvdDatabase.stream().filter((dvd) -> (dvd.getGenre().trim().equalsIgnoreCase(genre))).forEach((dvd) -> {
            dvdDatabaseGenre.add(dvd);
        });

        listViewDVDData.setAll(dvdDatabaseGenre);
        myDVDTableView.getSelectionModel().selectFirst();
    }

    // Display all genres of DVDs.
    @FXML
    private void handleAllGenre(ActionEvent event) {
        if (genreDisplayed) {
            menuItemSave.setDisable(false);
            listViewDVDData.setAll(dvdDatabase);
            genreDisplayed = false;
            myDVDTableView.getSelectionModel().selectFirst();
        }
    }

}

package simple.course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;



public class TestTableController extends Connect{

    private String actualLogin;
    private int actualId;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Phrase, Integer> author_idColumn;

    @FXML
    private TableColumn<Phrase, String> dateColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Phrase, Integer> idColumn;

    @FXML
    private Button insertButton;

    @FXML
    private TableColumn<Phrase, String> lessonColumn;

    @FXML
    private Button logOutButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<Phrase> tablePhrases;

    @FXML
    private TableColumn<Phrase, String> teacherColumn;

    @FXML
    private TableColumn<Phrase, String> textColumn;

    @FXML
    private TextField tfAuthor_id;

    @FXML
    private TextField tfDate;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfLesson;

    @FXML
    private TextField tfTeacher;

    @FXML
    private TextField tfText;

    @FXML
    private TextArea tfTextArea;

    @FXML
    private Button updateButton;

    @FXML
    private Label actualUser;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == insertButton) {
            insertRecord();
        } else if (event.getSource() == updateButton) {
            updateRecord();
        } else if (event.getSource() == deleteButton) {
            deleteRecord();
            tfId.clear();
        } else if (event.getSource() == profileButton){
            try {
                profileButton.getScene().getWindow().hide(); // убирает прошлое окно

                Parent root = FXMLLoader.load(getClass().getResource("profilePage.fxml"));// NEW PAGE
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (event.getSource() == logOutButton){
            try {
                logOutButton.getScene().getWindow().hide(); // убирает прошлое окно

                User.actualLogin = "";
                User.actualId = 0;

                Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        } else if (event.getSource() == refreshButton){
            showPhrases();
        }
    }

    public ObservableList<Phrase> getPhrasesList() {
        ObservableList<Phrase> phraselist = FXCollections.observableArrayList();
        Connection conn = getConnect();
        String query = "SELECT * FROM Phrases";
        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Phrase phrase;
            while (rs.next()) {
                phrase = new Phrase(rs.getInt("id"), rs.getString("text"), rs.getString("date"), rs.getString("teacher"), rs.getString("lesson"), rs.getInt("author_id"));
                phraselist.add(phrase);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return phraselist;
    }

    public void showPhrases() {
        ObservableList<Phrase> list = getPhrasesList();

        idColumn.setCellValueFactory(new PropertyValueFactory<Phrase, Integer>("id"));
        textColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("text"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("date"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("teacher"));
        lessonColumn.setCellValueFactory(new PropertyValueFactory<Phrase, String>("lesson"));
        author_idColumn.setCellValueFactory(new PropertyValueFactory<Phrase, Integer>("author_id"));

        tablePhrases.setItems(list);
//
    }

    private void insertRecord() {
        String query = "INSERT INTO Phrases(text, date, teacher, lesson, author_id) " +
                "VALUES ('" + tfText.getText() + "','" + tfDate.getText() + "','" + tfTeacher.getText() + "','" + tfLesson.getText() + "','" + actualId + "')";
        executeQuery(query);
        showPhrases();
    }

    private void updateRecord() {
        String query = "UPDATE Phrases SET text = '" + tfTeacher.getText() + "', date = '" + tfDate.getText() + "', teacher = '" + tfTeacher.getText() + "', lesson = '" + tfLesson.getText() + "' WHERE id = " + tfId.getText();
        executeQuery(query);
        showPhrases();
    }

    private void deleteRecord() {
        String query = "DELETE FROM Phrases WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showPhrases();
    }

    private void executeQuery(String query) {
        Connection conn = getConnect();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        showPhrases();
        actualLogin = User.actualLogin;
        actualId = User.actualId;

        actualUser.setText(actualLogin); // отображение на поле
    }
}



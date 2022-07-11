package simple.course;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MainPageController extends Connect{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Phrase> tablePhrases;

    @FXML
    private TableColumn<Phrase, String> teacherColumn;

    @FXML
    private TableColumn<Phrase, String> textColumn;

    @FXML
    private TableColumn<Phrase, String> lessonColumn;

    @FXML
    private TableColumn<Phrase, Integer> authorIdColumn;

    @FXML
    private TableColumn<Phrase, Integer> idColumn;

    @FXML
    private TableColumn<Phrase, String> dateColumn;

    @FXML
    private Button logInButton;

    @FXML
    private ScrollBar scroll;

    @FXML
    private Button signUpButton;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == signUpButton){
            try {
                signUpButton.getScene().getWindow().hide(); // убирает прошлое окно

                Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if(event.getSource() == logInButton){
            try {
                logInButton.getScene().getWindow().hide(); // убирает прошлое окно

                Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public ObservableList<Phrase> getPhrasesList(){
        ObservableList<Phrase> phraselist = FXCollections.observableArrayList();
        Connection conn = getConnect();
        String query = "SELECT * FROM Phrases";
        PreparedStatement st;
        ResultSet rs;

        try {
            st  = conn.prepareStatement(query);
            rs = st.executeQuery()
            ;
            Phrase phrase;
            while (rs.next()){
                phrase = new Phrase(rs.getInt("id"), rs.getString("text"), rs.getString("date"), rs.getString("teacher"), rs.getString("lesson"), rs.getInt("author_id"));
                phraselist.add(phrase);
            }

        }catch (Exception e){
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
        authorIdColumn.setCellValueFactory(new PropertyValueFactory<Phrase, Integer>("author_id"));

        tablePhrases.setItems(list);
    }


    @FXML
    void initialize() {
        showPhrases();
    }
}

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

import javax.xml.stream.events.StartElement;
import java.net.URL;
import java.sql.*;
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

//    @FXML
//    private TextField tfAuthor_id;

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

//    @FXML
//    private TextArea tfTextArea;

    @FXML
    private Button updateButton;

    @FXML
    private Label actualUser;

    @FXML
    void handleButtonAction(ActionEvent event) throws Exception{
        if (event.getSource() == insertButton) {
            insertRecord();
            tfLesson.clear();
            tfTeacher.clear();
            tfText.clear();
            tfDate.clear();

        } else if (event.getSource() == updateButton) {
            String checkSuper = "SELECT superUser from Users where id ='" + actualId + "'";
            String checkAuthor = "SELECT author_id from Phrases where id='" + actualId + "'";
            String checkTableLaws = "SELECT user_id FROM Laws WHERE phrase_id ='" + tfId.getText() + "'";
            String checkVerifier = "SELECT a.verifier_id FROM VerifierUsers as a where a.user_id IN (SELECT Phrases.author_id from Phrases where Phrases.id ='" + tfId.getText() + "')";

            if (getValue(checkSuper, "superUser") == 1){
                updateRecord();

            } else if (getValue(checkAuthor, "author_id") == actualId) {
                updateRecord();

            } else if (getValue(checkTableLaws, "user_id") == actualId){
                updateRecord();

            } else if (checkVelifier(checkVerifier)){
                updateRecord();

            } else {
                tfId.clear();
                tfLesson.clear();
                tfTeacher.clear();
                tfText.clear();
                tfDate.clear();
            }

        } else if (event.getSource() == deleteButton) {
            String checkSuper = "SELECT superUser from Users where id ='" + actualId + "'";
            String checkAuthor = "SELECT author_id from Phrases where id='" + actualId + "'";
            String checkVerifier = "SELECT a.verifier_id FROM VerifierUsers as a where a.user_id IN (SELECT Phrases.author_id from Phrases where Phrases.id ='" + tfId.getText() + "')";


            if (getValue(checkSuper, "superUser") == 1) {
                deleteRecord();
            } else if (getValue(checkAuthor, "author_id") == actualId) {
                deleteRecord();
            }  else if (checkVelifier(checkVerifier)){
                deleteRecord();
            }
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
        PreparedStatement st;
        ResultSet rs;

        try {
            st = conn.prepareStatement(query);
            rs = st.executeQuery();
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
        String insert = "INSERT INTO Phrases(text, date, teacher, lesson, author_id) VALUES (?,?,?,?,'" + actualId + "')";
//        PreparedStatement pr =
        try {
            testik(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

//        String query = "INSERT INTO Phrases(text, date, teacher, lesson, author_id) " +
//                "VALUES ('" + tfText.getText() + "','" + tfDate.getText() + "','" + tfTeacher.getText() + "','" + tfLesson.getText() + "','" + actualId + "')";
//        executeQuery(query);
        showPhrases();
    }

    private void updateRecord() {
        String query = "UPDATE Phrases SET text = '" + tfText.getText() + "', date = '" + tfDate.getText() + "', teacher = '" + tfTeacher.getText() + "', lesson = '" + tfLesson.getText() + "' WHERE id = " + tfId.getText();
        executeQuery(query);
        showPhrases();
    }

    private void deleteRecord() {
        String query = "DELETE FROM Phrases WHERE id =" + tfId.getText() + "";
        executeQuery(query);
        showPhrases();
    }

    private void testik(String in) throws SQLException{
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(in);
        pr.setString(1, tfText.getText());
        pr.setString(2, tfDate.getText());
        pr.setString(3, tfTeacher.getText());
        pr.setString(4, tfLesson.getText());

        pr.executeUpdate();

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

    private int getValue(String query, String column) throws Exception{
        Connection conn = getConnect();
        Statement st = conn.createStatement();
        int count = 0;

        ResultSet result = st.executeQuery(query);
        if (result.next())
            count = result.getInt(column);


        return count;
    }

    private boolean checkVelifier(String query) throws Exception{
        Connection conn = getConnect();
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(query);
        int idUserControlled = 0;

        while(result.next()){
            idUserControlled = result.getInt("verifier_id");
            if (idUserControlled == actualId)
                return true;
        }


        return false;
    }

    @FXML
    private void initialize() {
        showPhrases();
        actualLogin = User.actualLogin;
        actualId = User.actualId;

        actualUser.setText(actualLogin); // отображение на поле
    }
}



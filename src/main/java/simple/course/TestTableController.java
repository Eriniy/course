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
import org.w3c.dom.Text;

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
    private Button updateButton;

    @FXML
    private Label actualUser;

    @FXML
    void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == insertButton) {
            if(!tfText.getText().equals("") && !tfDate.getText().equals("") && !tfTeacher.getText().equals("") && !tfLesson.getText().equals("")){
                insertRecord();
                tfLesson.clear();
                tfTeacher.clear();
                tfText.clear();
                tfDate.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Не заполнены обязательные поля!");
                alert.showAndWait();
            }


        } else if (event.getSource() == updateButton) {
            String checkSuper = "SELECT superUser from Users where id = ?";
            String checkAuthor = "SELECT author_id from Phrases where id=?";
            String checkTableLaws = "SELECT user_id FROM Laws WHERE phrase_id =?";
            String checkVerifier = "SELECT a.verifier_id FROM VerifierUsers as a where a.user_id IN (SELECT Phrases.author_id from Phrases where Phrases.id =?)";
            if (!tfText.getText().equals("") && !tfDate.getText().equals("") && !tfTeacher.getText().equals("") && !tfLesson.getText().equals("") && !tfId.getText().equals("")){
                if (getValue(checkSuper, "superUser", actualId) == 1){
                    updateRecord();

                } else if (getValue(checkAuthor, "author_id", tfId) == actualId) {
                    updateRecord();

                } else if (getValue(checkTableLaws, "user_id", tfId) == actualId){
                    updateRecord();

                } else if (checkVelifier(checkVerifier, tfId)){
                    updateRecord();

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Недостаточно прав!");
                    alert.showAndWait();

                    tfId.clear();
                    tfLesson.clear();
                    tfTeacher.clear();
                    tfText.clear();
                    tfDate.clear();
                }
            } else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Не заполнены обязательные поля!");
                alert.showAndWait();
            }


        } else if (event.getSource() == deleteButton) {
            String checkSuper = "SELECT superUser from Users where id =?";
            String checkAuthor = "SELECT author_id from Phrases where id=?";
            String checkVerifier = "SELECT a.verifier_id FROM VerifierUsers as a where a.user_id IN (SELECT Phrases.author_id from Phrases where Phrases.id =?)";

            if(!tfId.getText().equals("")){
                if (getValue(checkSuper, "superUser", actualId) == 1) {
                    deleteRecord();
                } else if (getValue(checkAuthor, "author_id", tfId) == actualId) {
                    deleteRecord();
                } else if (checkVelifier(checkVerifier, tfId)){
                    deleteRecord();
                } else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Недостаточно прав!");
                    alert.showAndWait();
                }
                tfId.clear();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Не заполнено обязательное поле!");
                alert.showAndWait();
            }

        } else if (event.getSource() == profileButton){
            try {
                profileButton.getScene().getWindow().hide(); // убирает прошлое окно

                Parent root = FXMLLoader.load(getClass().getResource("profilePage.fxml"));
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
    }

    private void insertRecord() {
        String insert = "INSERT INTO Phrases(text, date, teacher, lesson, author_id) VALUES (?,?,?,?,'" + actualId + "')";
        try {
            insertPhrase(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        showPhrases();
    }

    private void updateRecord() throws SQLException {
        String query = "UPDATE Phrases SET text =?, date =?, teacher =?, lesson =? WHERE id =? ";
        updatePhrase(query);

        showPhrases();
    }

    private void deleteRecord() throws SQLException {
        String query = "DELETE FROM Phrases WHERE id =?";
        deletePhrase(query);

        showPhrases();
    }

    private void insertPhrase(String in) throws SQLException{
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(in);
        pr.setString(1, tfText.getText());
        pr.setString(2, tfDate.getText());
        pr.setString(3, tfTeacher.getText());
        pr.setString(4, tfLesson.getText());

        pr.executeUpdate();
    }

    private void updatePhrase(String in) throws SQLException{
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(in);
        pr.setString(1, tfText.getText());
        pr.setString(2, tfDate.getText());
        pr.setString(3, tfTeacher.getText());
        pr.setString(4, tfLesson.getText());
        pr.setInt(5, Integer.parseInt(tfId.getText()));

        pr.executeUpdate();
    }

    private void deletePhrase(String in) throws SQLException{
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(in);
        pr.setInt(1, Integer.parseInt(tfId.getText()));

        pr.executeUpdate();
    }

    private int getValue(String query, String column, TextField tf) throws SQLException{
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(query);
        pr.setString(1, tf.getText());
        int count = 0;

        ResultSet result = pr.executeQuery();

        if (result.next())
            count = result.getInt(column);

        return count;
    }

    private int getValue(String query, String column, int value) throws SQLException{
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(query);
        pr.setInt(1, value);
        int count = 0;

        ResultSet result = pr.executeQuery();

        if (result.next())
            count = result.getInt(column);

        return count;
    }

    private boolean checkVelifier(String query, TextField tf) throws Exception{
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(query);
        pr.setInt(1, Integer.parseInt(tf.getText()));
        ResultSet result = pr.executeQuery();
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



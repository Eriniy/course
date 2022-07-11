package simple.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

public class ProfilePageController extends Connect{

    private String actualLogin;
    private int actualId;

    private int countPhrase;

    @FXML
    private Label actual_login;

    @FXML
    private Label counter;

    @FXML
    private Button addLawButton;

    @FXML
    private Button changeLoginButton;

    @FXML
    private Button changeNameButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Button returnButton;

    @FXML
    private TextField tfIdPhrase;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfLoginNewUser;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPass;

    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException{
//        actual_login.setText(const_user);
        if (event.getSource() == returnButton){
            try {
                returnButton.getScene().getWindow().hide(); // убирает прошлое окно

                Parent root = FXMLLoader.load(getClass().getResource("testTable.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        else if (event.getSource() == changeNameButton){
            if(!tfName.equals("")){
                updateUser("name", tfName);
            }
        }

        else if (event.getSource() == changeLoginButton){
            if(!tfLogin.equals("")) {
                updateUser("login", tfLogin);
                actualLogin = tfLogin.getText();
                actual_login.setText(tfLogin.getText());
                User.actualLogin = actualLogin;
                tfLogin.clear();
            }
        }

        else if (event.getSource() == changePasswordButton){
            if(!tfName.equals("")){
                updateUser("password", tfPass);
            }
        }

        else if (event.getSource() == addLawButton){
            insertLaw();
        }
    }

    public void updateUser(String column, TextField tf){
        String query = "UPDATE Users SET " + column + "='" + tf.getText() + "' WHERE login ='" + actualLogin + "'";
        executeQuery(query);

    }

    public void insertLaw() throws SQLException{
        String query = "INSERT INTO Laws(user_id, phrase_id) VALUES('" + tfLoginNewUser.getText() + "','" + tfIdPhrase.getText() + "')";
//        PreparedStatement st = connection.prepareStatement("INSERT INTO Laws(user_id, phrase_id) VALUES(?,?)");
//        st.setString(1, tfLogin.getText());
//        st.setString(   2, tfIdPhrase.getText());
//        st.executeUpdate();



        executeQuery(query);
    }

    private void executeQuery(String query) {
        Connection conn = getConnect();
        PreparedStatement st;
        try {
            st = conn.prepareStatement(query);
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCounterValue() throws Exception{ // способ 1.2
        String countValue = "SELECT COUNT(*) as count FROM Phrases WHERE author_id=?";
        countPhrase = getValue(countValue, "count");
        counter.setText(Integer.toString(countPhrase));

    }

    private int getValue(String query, String column) throws Exception{ // способ 1.1
        Connection conn = getConnect();
        PreparedStatement st = conn.prepareStatement(query);
        st.setInt(1, actualId);
        int count = 0;

        ResultSet result = st.executeQuery();
        result.next();

        return result.getInt(1);
    }

    private void getCounterValue_2(){
        try {
            Connection conn = getConnect();
            PreparedStatement st = connection.prepareStatement("SELECT * FROM Phrases WHERE author_id =?");
//            String query = "SELECT * FROM Phrases WHERE author_id ='" + actualId + "'";
            st.setInt(1, actualId);
            int freeCount = 0;
            ResultSet res = st.executeQuery();

            while(res.next()){
                freeCount++;
            }
            countPhrase = freeCount;
            counter.setText(Integer.toString(countPhrase));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void initialize() {
        actualLogin = User.actualLogin;
        actualId = User.actualId;

        actual_login.setText(actualLogin);
        try {
            getCounterValue(); // 1 способ
//            getCounterValue_2(); // 2 способ
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

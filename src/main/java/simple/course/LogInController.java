package simple.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LogInController extends Connect {
    public String const_user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logInButton;

    @FXML
    private TextField tfLogLogin;

    @FXML
    private TextField tfLogPassword;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == logInButton){
            if(!tfLogLogin.equals("") && !tfLogPassword.equals("")){
                try {
                    String check = "SELECT COUNT(*) as count FROM Users WHERE login=? AND password=?";
                    String checkLogPass = "SELECT COUNT(*) as count FROM Users WHERE login='" + tfLogLogin.getText() + "' AND password = '" + tfLogPassword.getText() + "'";
                    //чекаем на совпадение пары в бд
//                    if (executeQuery(checkLogPass, "count") == 1){
                    if (testik(check) == 1){
                        User.actualLogin = tfLogLogin.getText(); // актуальный логин
                        String lookId = "SELECT id FROM Users WHERE login='" + tfLogLogin.getText() + "'";
                        User.actualId = executeQuery(lookId, "id"); // актуальный пароль


                        logInButton.getScene().getWindow().hide(); // убирает прошлое окно

                        Parent root = FXMLLoader.load(getClass().getResource("testTable.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }
                    else {
                        tfLogPassword.clear();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    private int executeQuery(String query, String column) throws Exception{
        Connection conn = getConnect();
        Statement st = conn.createStatement();
        int count = 0;

        ResultSet result = st.executeQuery(query);
        if (result.next())
            count = result.getInt(column);


        return count;
    }

    private int testik(String str) throws SQLException {
        Connection conn = getConnect();
        PreparedStatement pr = conn.prepareStatement(str);
        pr.setString(1, tfLogLogin.getText());
        pr.setString(2, tfLogPassword.getText());
        ResultSet resultSet = pr.executeQuery();
        int i = 0;

        if (resultSet.next()){
            i = resultSet.getInt("count");
        }

        return i;

//        return pr.executeQuery();

    }

    @FXML
    void initialize() {

    }
}

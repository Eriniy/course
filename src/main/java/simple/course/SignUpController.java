package simple.course;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUpController extends Connect{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button signUpButton;

    @FXML
    private Button returnButton;

    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPass;

    @FXML
    void handleButtonAction(ActionEvent event) {
        if(event.getSource() == signUpButton){
            String login = tfLogin.getText().trim();
            String password = tfPass.getText().trim();
            String name = tfName.getText().trim();

            if (!login.equals("") && !password.equals("") && !name.equals("")){
                try {
                    String query = "INSERT INTO Users(name, login, password) " +
                            "VALUES ('" + name + "','" + login + "','" + password + "')";

                    executeQuery(query);

                    signUpButton.getScene().getWindow().hide(); // убирает прошлое окно

                    Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Не все обязатльные поля заполены!");
            }
        }else if(event.getSource() == returnButton){
            try {
                returnButton.getScene().getWindow().hide(); // убирает прошлое окно

                Parent root = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void executeQuery(String query) {
        Connection conn = getConnect();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }


//    public void setSignUpBotton(ActionEvent event) throws IOException{
//        try {
////            Connect connect = new Connect();
////            Statement statement = connect.getConnect().createStatement();
////            statement.execute("INSERT INTO " + USERS_TABLE + "(" + USERS_NAME +", " + USERS_LOGIN + ", " + USERS_PASSWORD + ") " +
////                    "VALUES ('" + name_field.getText() + "', '" + login_field.getText() + "', '" + password_field.getText() + "')");
//
//            Parent root = FXMLLoader.load(getClass().getResource("testTable.fxml"));
//            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//            stage.setScene(scene);
//            stage.show();
//        } catch (Exception e){
//            System.out.println(e);
//        }
//    }

//    @FXML
//    void initialize() {
//        Connect connect = new Connect();
//       signUpBotton.setOnAction(actionEvent -> {
//           try {
////               Statement statement = connect.getConnect().createStatement();
////               statement.execute("INSERT INTO " + USERS_TABLE + "(" + USERS_NAME +", " + USERS_LOGIN + ", " + USERS_PASSWORD + ") " +
////                       "VALUES ('" + name_field.getText() + "', '" + login_field.getText() + "', '" + password_field.getText() + "')");
//
////               signUpBotton.getScene().getWindow().hide(); // убирает прошлое окно
//
//               Parent root = FXMLLoader.load(getClass().getResource("testTable.fxml"));
//               Scene scene = new Scene(root);
//               Stage stage = new Stage();
//               stage.setScene(scene);
//               stage.show();


//               signUpBotton.getScene().getWindow().hide(); // убирает прошлое окно

//               Parent root = FXMLLoader.load(Application.class.getResource("testTable.fxml"));
//               Stage primaryStage = new Stage();
//               primaryStage.setTitle("Hello!");
//               primaryStage.setScene(new Scene (root, 700, 400));
//               primaryStage.show();

               // отображение для нового окна
//               FXMLLoader loader = new FXMLLoader();
//               loader.setLocation(getClass().getResource("testTable.fxml"));
//
//               try {
//                   loader.load();
//               } catch (IOException e) {
//                   throw new RuntimeException(e);
//               }
//
//               Parent root = loader.getRoot();
//               Stage stage = new Stage();
//               stage.setScene(new Scene(root));
//               stage.show();
//           }
//           catch (Exception e){
//               System.out.println(e);
//           }
//       });
//    }
}

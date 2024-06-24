import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {
//    public static void main(String[] args) {
//        launch();
//
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
////        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/Login.fxml"))));
////        stage.show();
////        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/Registration.fxml"))));
////        stage.show();
////        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/PasswordReset.fxml"))));
////        stage.show();
////        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/AddProductForm.fxml"))));
////        stage.show();
////        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/AddSupplierForm.fxml"))));
////        stage.show();
////        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/PlaceOrderForm.fxml"))));
////        stage.show();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/AdminDashboard.fxml"))));
//        stage.show();
////        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/EmployeeDashboard.fxml"))));
////        stage.show();
//    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
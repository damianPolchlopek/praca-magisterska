package controller;

import entity.Users;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.*;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginPanel.fxml"));
        primaryStage.setTitle("Pomiary");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(700);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();



        // create session factory
        SessionFactory factory = new Configuration()
                .configure("/hibernate.cfg.xml")
                .addAnnotatedClass(Users.class)
                .buildSessionFactory();

        // create sesion
        Session session = factory.getCurrentSession();


        // start a transaction
        session.beginTransaction();

        // get the instructor detail object
        int theId = 3;
        Users tempiInstructorDetails =
                session.get(Users.class, theId);

        //print the instructor detail
        System.out.println("tempInstructor: " + tempiInstructorDetails);

        // commit transaction
        session.getTransaction().commit();

        System.out.println("Done!");

        session.close();

        factory.close();


//        VBox vBox = new VBox();
//
//        StackPane root = new StackPane();
//        root.getChildren().add(vBox);
//
//        Scene scene = new Scene(root, 300, 250);
//
//        primaryStage.setTitle("Hello World!");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);


    }


    @Override
    public void stop() throws Exception {

    }

    @Override
    public void init() throws Exception {

    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }
}

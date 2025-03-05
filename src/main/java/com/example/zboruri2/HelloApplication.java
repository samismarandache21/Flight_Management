package com.example.zboruri2;

import com.example.zboruri2.controller.LoginController;
import com.example.zboruri2.repository.ClientDBRepository;
import com.example.zboruri2.repository.FlightDBRepository;
import com.example.zboruri2.repository.TicketDBRepository;
import com.example.zboruri2.service.ClientService;
import com.example.zboruri2.service.FlightService;
import com.example.zboruri2.service.TicketService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String url = "jdbc:postgresql://localhost:5432/zboruri2";
        String user = "postgres";
        String password = "postgres";

        ClientDBRepository clientDBRepository = new ClientDBRepository(url, user, password);
        ClientService clientService = new ClientService(clientDBRepository);
        TicketDBRepository ticketDBRepository = new TicketDBRepository(url, user, password);
        TicketService ticketService = new TicketService(ticketDBRepository);
        FlightDBRepository flightDBRepository = new FlightDBRepository(url, user, password);
        FlightService flightService = new FlightService(flightDBRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        fxmlLoader.setControllerFactory(param -> new LoginController(clientService, ticketService, flightService, stage));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
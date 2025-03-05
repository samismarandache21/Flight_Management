package com.example.zboruri2.controller;

import com.example.zboruri2.HelloApplication;
import com.example.zboruri2.service.ClientService;
import com.example.zboruri2.service.FlightService;
import com.example.zboruri2.service.TicketService;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Button userTicketsButton;
    @FXML
    private Button dateTicketsButton;
    @FXML
    private Button filterFlightsButton;

    private String username;
    private ClientService clientService;
    private TicketService ticketService;
    private FlightService flightService;

    private Stage stage;

    public MainController(String username, ClientService clientService, TicketService ticketService,
                          FlightService flightService ,Stage stage) {
        this.username = username;
        this.clientService = clientService;
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.stage = stage;
    }

    @FXML
    public void initialize() {}

    @FXML
    public void userTicketsClick() {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("user-ticktes-view.fxml"));
        fxmlLoader.setControllerFactory(param -> new UserTicketsController(username, clientService, ticketService, stage1));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 400, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage1.setTitle("User Tickets");
        stage1.setScene(scene);
        stage1.show();
    }

    @FXML
    public void dateTicketsClick() {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("date-tickets-view.fxml"));
        fxmlLoader.setControllerFactory(param -> new DateTicketsController(username, clientService, ticketService, stage1));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 400, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage1.setTitle("Date Tickets");
        stage1.setScene(scene);
        stage1.show();
    }

    @FXML
    public void filterFlightsClick() {
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("filter-flights-view.fxml"));
        fxmlLoader.setControllerFactory(param -> new FilterFlightsController(username,
                clientService,
                ticketService, flightService, stage1));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 400, 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage1.setTitle("Filter Flights");
        stage1.setScene(scene);
        stage1.show();
    }
}

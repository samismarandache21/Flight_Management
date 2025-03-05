package com.example.zboruri2.controller;

import com.example.zboruri2.HelloApplication;
import com.example.zboruri2.model.Client;
import com.example.zboruri2.service.ClientService;
import com.example.zboruri2.service.FlightService;
import com.example.zboruri2.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class LoginController {
    @FXML
    private VBox loginBox;
    @FXML
    private TextField usernameInput;
    @FXML
    private Button loginButton;

    private ClientService clientService;
    private TicketService ticketService;
    private FlightService flightService;
    private Stage stage;

    public LoginController(ClientService clientService,
                           TicketService ticketService,
                           FlightService flightService, Stage stage) {
        this.clientService = clientService;
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.stage = stage;
    }

    @FXML
    public void initialize() {

    }

    @FXML
    public void loginClick(ActionEvent event)
    {
        Optional<Client> loginRequest = clientService.findOneByUsername(usernameInput.getText());
        if (loginRequest.isPresent()) {
            Stage stage1 = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
            fxmlLoader.setControllerFactory(param -> new MainController(usernameInput.getText(),
                    clientService,
                    ticketService,
                    flightService,
                    stage1));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 320, 240);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage1.setTitle("Login");
            stage1.setScene(scene);
            stage1.show();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username");
            alert.setContentText("Please try again");
            alert.showAndWait();
        }
    }
}

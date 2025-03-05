package com.example.zboruri2.controller;

import com.example.zboruri2.model.Ticket;
import com.example.zboruri2.service.ClientService;
import com.example.zboruri2.service.TicketService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class UserTicketsController {
    @FXML
    private VBox ticketsBox;

    private String username;
    private ClientService clientService;
    private TicketService ticketService;

    private TableView<Ticket> ticketsView;

    private Stage stage;

    public UserTicketsController(String username, ClientService clientService, TicketService ticketService,
                                 Stage stage) {
        this.username = username;
        this.clientService = clientService;
        this.ticketService = ticketService;
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        TableColumn<Ticket, Long> flightIdColumn = new TableColumn<>();
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        TableColumn<Ticket, LocalDateTime> purchaseTimeColumn = new TableColumn<>();
        purchaseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseTime"));

        ticketsView = new TableView<>();
        ticketsView.getColumns().add(flightIdColumn);
        ticketsView.getColumns().add(purchaseTimeColumn);
        ticketsView.setItems(FXCollections.observableArrayList(ticketService.findUserTickets(username)));

        ticketsBox.getChildren().add(ticketsView);
    }
}

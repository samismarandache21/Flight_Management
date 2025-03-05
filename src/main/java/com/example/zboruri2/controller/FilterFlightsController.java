package com.example.zboruri2.controller;

import com.example.zboruri2.model.Flight;
import com.example.zboruri2.model.Ticket;
import com.example.zboruri2.service.ClientService;
import com.example.zboruri2.service.FlightService;
import com.example.zboruri2.service.TicketService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class FilterFlightsController {
    @FXML
    private VBox filterBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> fromBox;
    @FXML
    private ComboBox<String> toBox;
    @FXML
    private Button filterButton;
    @FXML
    private Button buyTicketButton;

    private String username;
    private ClientService clientService;
    private TicketService ticketService;
    private FlightService flightService;

    private Stage stage;

    private TableView<Flight> filterView = new TableView<>();

    public FilterFlightsController(String username, ClientService clientService, TicketService ticketService,
                                   FlightService flightService ,Stage stage) {
        this.username = username;
        this.clientService = clientService;
        this.ticketService = ticketService;
        this.flightService = flightService;
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        fromBox.setItems(FXCollections.observableArrayList(flightService.findLocations()));
        toBox.setItems(FXCollections.observableArrayList(flightService.findLocations()));

        TableColumn<Flight, String> fromColumn = new TableColumn<>();
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        TableColumn<Flight, String> toColumn = new TableColumn<>();
        toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        TableColumn<Flight, LocalDateTime> departureColumn = new TableColumn<>();
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        TableColumn<Flight, LocalDateTime> landingColumn = new TableColumn<>();
        landingColumn.setCellValueFactory(new PropertyValueFactory<>("landingTime"));
        TableColumn<Flight, Integer> seatsColumn = new TableColumn<>();
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));

        filterView.getColumns().add(fromColumn);
        filterView.getColumns().add(toColumn);
        filterView.getColumns().add(departureColumn);
        filterView.getColumns().add(landingColumn);
        filterView.getColumns().add(seatsColumn);
    }

    @FXML
    public void filterClick(ActionEvent event) {
        filterBox.getChildren().remove(filterView);
        filterBox.getChildren().remove(buyTicketButton);

        LocalDateTime start = datePicker.getValue().atStartOfDay();
        LocalDateTime end = datePicker.getValue().atTime(LocalTime.MAX);

        Timestamp startTimestamp = Timestamp.valueOf(start);
        Timestamp endTimestamp = Timestamp.valueOf(end);

        List<Flight> filtered = flightService.findFilteredFlights(
                fromBox.getSelectionModel().getSelectedItem(),
                toBox.getSelectionModel().getSelectedItem(),
                startTimestamp, endTimestamp
        );

        filterView.getItems().clear();
        filterView.setItems(FXCollections.observableArrayList(filtered));
        filterBox.getChildren().add(filterView);

        filterBox.getChildren().add(buyTicketButton);
    }

    @FXML
    public void buyClick() {
        Flight flight = filterView.getSelectionModel().getSelectedItem();
        if(flight == null)
            return;
        if(flight.getSeats() == 0)
            return; //todo alert
        Ticket ticket = new Ticket(username, flight.getId(), LocalDateTime.now());
        ticket.setId(ticketService.getLastId() + 1);
        ticketService.save(ticket);
    }
}

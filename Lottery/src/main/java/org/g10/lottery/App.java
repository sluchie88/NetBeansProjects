package org.g10.lottery;

import org.g10.lottery.data.CustomerDao;
import org.g10.lottery.data.CustomerFileDao;
import org.g10.lottery.data.TicketDao;
import org.g10.lottery.data.TicketFileDao;
import org.g10.lottery.service.CustomerService;
import org.g10.lottery.service.TicketService;
import org.g10.lottery.ui.ConsoleIO;
import org.g10.lottery.ui.Controller;
import org.g10.lottery.ui.View;

public class App {

    /*
    Pretty straightforward App class. Will need to refactor using Spring and DI
    */
    
    public static final String CUSTOMER_FILE_PATH = "customers.txt";
    public static final String TICKET_FILE_PATH = "tickets.txt";

    public static void main(String[] args) {

        ConsoleIO io = new ConsoleIO();
        View view = new View(io);

        CustomerDao customerDao = new CustomerFileDao(CUSTOMER_FILE_PATH);
        TicketDao ticketDao = new TicketFileDao(TICKET_FILE_PATH);

        CustomerService customerService = new CustomerService(customerDao);
        TicketService ticketService = new TicketService(customerDao, ticketDao);

        Controller controller = new Controller(view, customerService, ticketService);

        controller.run();
    }
}

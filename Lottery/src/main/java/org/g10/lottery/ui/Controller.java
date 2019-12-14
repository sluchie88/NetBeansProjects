package org.g10.lottery.ui;

import java.util.List;
import org.g10.lottery.models.Customer;
import org.g10.lottery.models.Match;
import org.g10.lottery.models.Ticket;
import org.g10.lottery.service.CustomerService;
import org.g10.lottery.service.Response;
import org.g10.lottery.service.Result;
import org.g10.lottery.service.TicketService;

public class Controller {

    private final View view;
    private final CustomerService customerService;
    private final TicketService ticketService;

    public Controller(View view, CustomerService customerService, TicketService ticketService) {
        this.view = view;
        this.customerService = customerService;
        this.ticketService = ticketService;
    }

    public void run() {

        view.welcome();

        MainMenuOption selection;

        do {
            selection = view.selectFromMainMenu();
            switch (selection) {
                case ENTER_PICK:
                    enterPick();
                    break;
                case FIND_PICKS_BY_LAST_NAME:
                    findPicksByLastName();
                    break;
                case QUICK_PICK:
                    quickPick();
                    break;
                case RUN_LOTTERY:
                    runLottery();
                    break;
                case GENERATE_TICKETS:
                    superSecretHiddenEasterEgg();
                    break;
            }
        } while (selection != MainMenuOption.EXIT);

        view.goodbye();
        //superSecretHiddenEasterEgg();
    }

    /*
    calls to view and takes in user input for picking numbers
    creates the customer (either a new one or existing), then 
    user puts in picks for PBall ticket, saves both to respective text file
    */
    private void enterPick() {

        Customer customer = findOrCreateCustomer("Enter a pick.");
        if (customer == null) {
            return;
        }

        Ticket ticket;
        Result<Ticket> result;
        do {
            ticket = view.makeTicket(customer);
            result = ticketService.add(ticket);
        } while (!validateTicket(ticket));
        if (result.hasError()) {
            view.displayErrors(result);
        } else {
            view.displaySuccess();
        }
    }

    /*
    let's user generate picks rather than entering.
    calls to view, user can either enter new customer or pick an existing
    generates a ticket and asks if it's okay, then adds to DB
    */
    private void quickPick() {

        Customer customer = findOrCreateCustomer("Quick pick");
        if (customer == null) {
            return;
        }

        Ticket ticket;
        do {
            ticket = ticketService.quickPick(customer);
        } while (!view.accept(ticket));

        Result<Ticket> result = ticketService.add(ticket);
        if (result.hasError()) {
            view.displayErrors(result);
        } else {
            view.displaySuccess();
        }
    }

    /*
    method for running the lottery
    asks for approval first, then runs the runLotteryMethod() in TicketService and 
    displays results afterwards
    */
    private void runLottery() {

        if (!view.confirm("Are you sure you want to run the lottery?")) {
            return;
        }

        List<Match> matches = ticketService.runLottery();
        view.displayWinners(matches);

    }

    /*
    allows for searching by last name via the ticket service
    */
    private void findPicksByLastName() {
        String lastName = view.getLastNameSearch();
        List<Ticket> tickets = ticketService.findByCustomerLastName(lastName);

        view.displayTickets("Tickets by Last Name", tickets);
    }

    /*
    allows for creating or finding a customer, calls to another method
    */
    private Customer findOrCreateCustomer(String header) {

        boolean shouldCreate = view.shouldCreateCustomer(header);
        if (shouldCreate) {
            return createCustomer();
        }

        return findCustomer();
    }

    /*
    calls to view to take in new customer information
    passes new customer in to the Service layer, checks for errors and adds,
    then returns the new customer.
    seems like any new customer doesn't get added and just returns an error
    */
    private Customer createCustomer() {
        Customer c = view.makeCustomer();
        Response r = customerService.add(c);
        if (r.hasError()) {
            view.displayErrors(r);
            return null;
        }
        return c;
    }

    /*
    only allows for searching by email
    */
    private Customer findCustomer() {
        String search = view.getEmailSearch();
        List<Customer> customers = customerService.findByEmailStartingWith(search);
        if (customers.size() <= 0) {
            view.displayNoCustomerWarning(search);
            return null;
        }

        return view.chooseCustomer(customers);
    }

    /*
    reveals hidden menu for generating many tickets at once
    */
    private void superSecretHiddenEasterEgg() {
        int ticketCount = view.getAutoGeneratedTicketCount();
        ticketService.autoGenerateTickets(ticketCount);
    }
    
    /*
    checks to make sure the first five picks are not the same. returns
    an error message if any are, and has the user pick all numbers again
    */
    private boolean validateTicket(Ticket ticket){
        boolean validTicket = true;
        if(ticket.getPickOne() == ticket.getPickFour() 
                || ticket.getPickOne() == ticket.getPickTwo() || ticket.getPickOne() == ticket.getPickThree() || ticket.getPickOne() == ticket.getPickFive() ||
                ticket.getPickTwo() == ticket.getPickThree() || ticket.getPickTwo() == ticket.getPickFour() || ticket.getPickTwo() == ticket.getPickFive() ||
                ticket.getPickThree() == ticket.getPickFour() || ticket.getPickThree() == ticket.getPickFive() ||
                ticket.getPickFour() == ticket.getPickFive()
                ){
            Response error = new Response();
            error.addError("Picks [1-5] cannot contain duplicate numbers. Please pick again");
            view.displayErrors(error);
            validTicket = false;
        }
        return validTicket;
    }
}

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
            }
        } while (selection != MainMenuOption.EXIT);

        view.goodbye();
        superSecretHiddenEasterEgg();
    }

    private void enterPick() {

        Customer customer = findOrCreateCustomer("Enter a pick.");
        if (customer == null) {
            return;
        }

        Ticket ticket = view.makeTicket(customer);
        Result<Ticket> result = ticketService.add(ticket);
        // TODO: Something is wrong here. I'm able to enter duplicate
        // picks and sometimes my Powerball is too big.
        if (result.hasError()) {
            view.displayErrors(result);
        } else {
            view.displaySuccess();
        }
    }

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

    private void runLottery() {

        if (!view.confirm("Are you sure you want to run the lottery?")) {
            return;
        }

        List<Match> matches = ticketService.runLottery();
        view.displayWinners(matches);

    }

    private void findPicksByLastName() {
        String lastName = view.getLastNameSearch();
        List<Ticket> tickets = ticketService.findByCustomerLastName(lastName);

        view.displayTickets("Tickets by Last Name", tickets);
    }

    private Customer findOrCreateCustomer(String header) {

        boolean shouldCreate = view.shouldCreateCustomer(header);
        if (shouldCreate) {
            return createCustomer();
        }

        return findCustomer();
    }

    private Customer createCustomer() {
        Customer c = view.makeCustomer();
        Response r = customerService.add(c);
        if (r.hasError()) {
            view.displayErrors(r);
            return null;
        }
        return c;
    }

    private Customer findCustomer() {
        String search = view.getEmailSearch();
        List<Customer> customers = customerService.findByEmailStartingWith(search);
        if (customers.size() <= 0) {
            view.displayNoCustomerWarning(search);
            return null;
        }

        return view.chooseCustomer(customers);
    }

    private void superSecretHiddenEasterEgg() {
        int ticketCount = view.getAutoGeneratedTicketCount();
        ticketService.autoGenerateTickets(ticketCount);
    }
}

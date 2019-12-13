package org.g10.lottery.ui;

import java.util.List;
import java.util.stream.Collectors;
import org.g10.lottery.models.Customer;
import org.g10.lottery.models.Match;
import org.g10.lottery.models.Ticket;
import org.g10.lottery.service.Response;

public class View {

    private final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectFromMainMenu() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption mmo : MainMenuOption.values()) {
            io.print(String.format("%s. %s", mmo.getValue(), mmo.getName()));
            min = Math.min(mmo.getValue(), min);
            max = Math.max(mmo.getValue(), max);
        }
        int value = io.readInt(String.format("Select [%s-%s]:", min, max), min, max);
        return MainMenuOption.fromValue(value);
    }

    public boolean confirm(String message) {
        return io.readBoolean(String.format("%s [y/n]:", message));
    }

    public void welcome() {
        printHeader("Powerball!");
        io.print("====================\n");
    }

    public void goodbye() {
        printHeader("Good Bye!");
    }

    boolean shouldCreateCustomer(String header) {
        printHeader(header);
        io.print("1. Create a new customer.");
        io.print("2. Search for an existing customer.");
        return io.readInt("Select [1-2]: ", 1, 2) == 1;
    }

    // create =============
    Customer makeCustomer() {
        printHeader("New customer");
        Customer result = new Customer();
        result.setEmailAddress(io.readRequiredString("Email Address:"));
        result.setFirstName(io.readRequiredString("First Name:"));
        result.setLastName(io.readRequiredString("Last Name:"));
        return result;
    }

    Ticket makeTicket(Customer customer) {
        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setPickOne(io.readInt("Pick 1 [1-69]:", 1, 69));
        ticket.setPickTwo(io.readInt("Pick 2 [1-69]:", 1, 69));
        ticket.setPickThree(io.readInt("Pick 3 [1-69]:", 1, 69));
        ticket.setPickFour(io.readInt("Pick 4 [1-69]:", 1, 69));
        ticket.setPickFive(io.readInt("Pick 5 [1-69]:", 1, 69));
        ticket.setPowerBall(io.readInt("Powerball [1-26]:", 1, 69));
        return ticket;
    }

    // displays =======
    void displayErrors(Response r) {
        io.print("");
        printHeader("ERROR");
        for (String message : r.getErrors()) {
            io.print(message);
        }
        io.print("");
    }

    void displayTicket(Ticket t) {
        io.print(t.getCustomer().getEmailAddress());
        io.print(String.format("Pick 1   : %02d", t.getPickOne()));
        io.print(String.format("Pick 2   : %02d", t.getPickTwo()));
        io.print(String.format("Pick 3   : %02d", t.getPickThree()));
        io.print(String.format("Pick 4   : %02d", t.getPickFour()));
        io.print(String.format("Pick 5   : %02d", t.getPickFive()));
        io.print(String.format("Powerball: %02d", t.getPowerBall()));
    }

    void displayTickets(String header, List<Ticket> tickets) {

        printHeader(header);

        if (tickets.size() <= 0) {
            io.print("No tickets found.\n");
        } else {
            for (Ticket t : tickets) {

                io.print(String.format(
                        "Customer: %s, %s %s",
                        t.getCustomer().getEmailAddress(),
                        t.getCustomer().getFirstName(),
                        t.getCustomer().getLastName()
                ));

                io.print(String.format(
                        "ID: %s | Picks: %s, %s, %s, %s, %s | Powerball: %s%n",
                        t.getId(),
                        t.getPickOne(),
                        t.getPickTwo(),
                        t.getPickThree(),
                        t.getPickFour(),
                        t.getPickFive(),
                        t.getPowerBall()
                ));
            }
        }
    }

    void displaySuccess() {
        printHeader("Success.");
    }

    void displayNoCustomerWarning(String prefix) {
        io.print("No customers with an email starting with: " + prefix + "\n");
    }

    void displayWinners(List<Match> matches) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    String getEmailSearch() {
        printHeader("Search for a customer.");
        return io.readRequiredString("Email starts with:");
    }

    String getLastNameSearch() {
        printHeader("Search tickets by customer last name.");
        return io.readRequiredString("Last Name: ");
    }

    Customer chooseCustomer(List<Customer> customers) {

        if (customers.size() > 25) {
            io.print("Too many customers. Showing the top 25.\n");
        }

        customers = customers.stream()
                .sorted((a, b) -> a.getEmailAddress().compareTo(b.getEmailAddress()))
                .limit(25)
                .collect(Collectors.toList());

        int index = 1;
        for (Customer c : customers) {
            io.print(String.format("%s: %s %s %s",
                    index++,
                    c.getEmailAddress(),
                    c.getFirstName(),
                    c.getLastName()));
        }

        io.print(""); // empty newline

        int record = io.readInt("Enter a row number or 0 to exit.", 0, index - 1);
        if (record > 0) {
            return customers.get(record - 1);
        }

        return null;
    }

    private void printHeader(String message) {
        io.print(String.format("=== %s ===", message));
    }

    boolean accept(Ticket ticket) {
        printHeader("Review your picks");
        displayTicket(ticket);
        return confirm("Okay?");
    }

    int getAutoGeneratedTicketCount() {
        if (confirm("[[Activate Super Secret Admin Option?]]")) {
            printHeader("AUTO GENERATE TICKETS!");
            return io.readInt(
                    "How many tickets should I auto-generated? [0-500]", 0, 500);
        }
        return 0;
    }

}

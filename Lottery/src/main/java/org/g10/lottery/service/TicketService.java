package org.g10.lottery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.g10.lottery.data.CustomerDao;
import org.g10.lottery.data.StorageException;
import org.g10.lottery.data.TicketDao;
import org.g10.lottery.models.Customer;
import org.g10.lottery.models.Match;
import org.g10.lottery.models.Ticket;

public class TicketService {

    private final CustomerDao customerDao;
    private final TicketDao ticketDao;

    public TicketService(CustomerDao customerDao, TicketDao ticketDao) {
        this.customerDao = customerDao;
        this.ticketDao = ticketDao;
    }

    public Result<Ticket> add(Ticket ticket) {

        Result<Ticket> result = new Result<>();

        if (!Validations.inRange(ticket.getPickOne(), 1, 69)) {
            result.addError("Pick 1 must be between 1 and 69.");
        }
        if (!Validations.inRange(ticket.getPickTwo(), 1, 69)) {
            result.addError("Pick 2 must be between 1 and 69.");
        }
        if (!Validations.inRange(ticket.getPickThree(), 1, 69)) {
            result.addError("Pick 3 must be between 1 and 69.");
        }
        if (!Validations.inRange(ticket.getPickFour(), 1, 69)) {
            result.addError("Pick 4 must be between 1 and 69.");
        }
        if (!Validations.inRange(ticket.getPickFive(), 1, 26)) {
            result.addError("Pick 5 must be between 1 and 69.");
        }

        if (result.hasError()) {
            return result;
        }

        try {
            ticket = ticketDao.add(ticket);
            result.setValue(ticket);
        } catch (StorageException ex) {
            result.addError(ex.getMessage());
        }

        return result;
    }

    public Ticket quickPick(Customer customer) {
        Ticket result = Drawing.draw();
        result.setCustomer(customer);
        return result;
    }

    public List<Ticket> findByCustomerLastName(String lastName) {
        List<Customer> customers = customerDao.findByLastName(lastName);
        List<Ticket> result = new ArrayList<>();
        for (Customer c : customers) {
            for (Ticket t : ticketDao.findByEmailAddress(c.getEmailAddress())) {
                t.setCustomer(c);
                result.add(t);
            }
        }
        return result;
    }

    public List<Match> runLottery() {

        ArrayList<Match> matches = new ArrayList<>();
        Ticket winner = Drawing.draw();

        // TODO: Determine which tickets best match the winner.
        // Sort by https://en.wikipedia.org/wiki/Powerball#Prizes_and_odds
        for (Ticket t : ticketDao.findAll()) {

        }

        return matches;
    }

    public void autoGenerateTickets(int count) {
        try {
            if (count > 0 && count <= 500) {
                Random rand = new Random();
                List<Customer> customers = customerDao.findAll();
                ArrayList<Ticket> tickets = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Ticket t = Drawing.draw();
                    t.setCustomer(customers.get(rand.nextInt(customers.size())));
                    tickets.add(t);
                }
                ticketDao.addAll(tickets);
            }
        } catch (StorageException ex) {

        }
    }

}

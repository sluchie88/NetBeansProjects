package org.g10.lottery.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
    
    /*
    checks the values of a ticket and validates them before adding them to the 
    ticket DB
    changed validation check for pick 5, range was 1 to 26
    added validation for Powerball as there was none
    */
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
        if (!Validations.inRange(ticket.getPickFive(), 1, 69)) {
            result.addError("Pick 5 must be between 1 and 69.");
        }
        if(!Validations.inRange(ticket.getPowerBall(), 1, 26)){
            result.addError("Powerball must be between 1 and 26.");
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
    
    /*
    takes in a customer, creates a ticket using the draw method and adds the 
    customer as the owner of the ticket. Working
    */
    public Ticket quickPick(Customer customer) {
        Ticket result = Drawing.draw();
        result.setCustomer(customer);
        return result;
    }
    
    /*
    returns a list of all tickets associated with a particular last name
    seems to be working properly
    */
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

    /*
    chooses the winning ticket, then compares all customer tickets by putting winning
    numbers into a new HashSet and then customer tickets into the same hashset.
    Checks the size of the hashset. If 5, all match, 6 means 4 matches...etc
    if any match, including the powerball, adds it to the list of matches to be returned
    
    needs refactoring. this is ugly, but dammit if it doesn't work
     */
    public List<Match> runLottery() {
        
        ArrayList<Match> matches = new ArrayList<>();
        Ticket winner = Drawing.draw();

        // TODO: Determine which tickets best match the winner.
        // Sort by https://en.wikipedia.org/wiki/Powerball#Prizes_and_odds
        for (Ticket t : ticketDao.findAll()) {
            Set<Integer> winningNums = new HashSet<>();
            boolean pBallMatch = (t.getPowerBall() == winner.getPowerBall());
            
            winningNums.add(winner.getPickOne());
            winningNums.add(winner.getPickTwo());
            winningNums.add(winner.getPickThree());
            winningNums.add(winner.getPickFour());
            winningNums.add(winner.getPickFive());
            
            winningNums.add(t.getPickOne());
            winningNums.add(t.getPickTwo());
            winningNums.add(t.getPickThree());
            winningNums.add(t.getPickFour());
            winningNums.add(t.getPickFive());
            
            Match currTicket = new Match(t);
            if (winningNums.size() == 5) {
                currTicket.setNumberMatchs(5);
                currTicket.setPowerBallMatch(pBallMatch);
                if (pBallMatch) {
                    currTicket.setAmountWon(250000000);
                } else {
                    currTicket.setAmountWon(1000000);
                }
                matches.add(currTicket);
            } else if (winningNums.size() == 6) {
                currTicket.setNumberMatchs(4);
                currTicket.setPowerBallMatch(pBallMatch);
                if (pBallMatch) {
                    currTicket.setAmountWon(50000);
                } else {
                    currTicket.setAmountWon(100);
                }
                matches.add(currTicket);
            } else if (winningNums.size() == 7) {
                currTicket.setNumberMatchs(3);
                currTicket.setPowerBallMatch(pBallMatch);
                if (pBallMatch) {
                    currTicket.setAmountWon(100);
                } else {
                    currTicket.setAmountWon(7);
                }
                matches.add(currTicket);
            } else if (winningNums.size() == 8) {
                currTicket.setNumberMatchs(2);
                currTicket.setPowerBallMatch(pBallMatch);
                if (pBallMatch) {
                    currTicket.setAmountWon(7);
                } else {
                    currTicket.setAmountWon(4);
                }
                matches.add(currTicket);
            } else if (winningNums.size() == 9) {
                currTicket.setNumberMatchs(1);
                currTicket.setPowerBallMatch(pBallMatch);
                if (pBallMatch) {
                    currTicket.setAmountWon(4);
                } else {
                    currTicket.setAmountWon(4);
                }
                matches.add(currTicket);
            } else if (pBallMatch) {
                currTicket.setPowerBallMatch(pBallMatch);
                currTicket.setAmountWon(4);
                matches.add(currTicket);
            }
            
        }
        
        return matches;
    }
    
    /*
    generates and adds the given number of tickets to the DB. attaches
    customers to generated tickets randomly
    working, don't touch
    */
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

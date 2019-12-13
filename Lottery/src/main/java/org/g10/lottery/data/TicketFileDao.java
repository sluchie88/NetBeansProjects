package org.g10.lottery.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.g10.lottery.models.Customer;
import org.g10.lottery.models.Ticket;

public class TicketFileDao
        extends FileDao<Ticket>
        implements TicketDao {

    public TicketFileDao(String path) {
        super(path, 9, false);
    }

    @Override
    public List<Ticket> findAll() {
        try {
            return read(this::mapToTicket).stream()
                    .collect(Collectors.toList());
        } catch (StorageException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public Ticket findById(int id) {

        return findAll().stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);

    }

    @Override
    public List<Ticket> findByEmailAddress(String emailAddress) {
        return findAll().stream()
                .filter(t -> t.getCustomer().getEmailAddress().equals(emailAddress))
                .collect(Collectors.toList());
    }

    @Override
    public Ticket add(Ticket ticket) throws StorageException {

        int maxId = findAll().stream()
                .mapToInt(t -> t.getId())
                .max()
                .orElse(0);

        ticket.setId(maxId + 1);
        ticket.setTimestamp(LocalDateTime.now());

        append(ticket, this::mapToString);
        return ticket;
    }

    @Override
    public void addAll(List<Ticket> tickets) throws StorageException {
        List<Ticket> all = findAll();

        int nextId = all.stream()
                .mapToInt(t -> t.getId())
                .max()
                .orElse(0) + 1;

        for (Ticket t : tickets) {
            t.setId(nextId++);
            t.setTimestamp(LocalDateTime.now());
            all.add(t);
        }

        write(all, this::mapToString);
    }

    private Ticket mapToTicket(String[] tokens) {

        Customer customer = new Customer();
        customer.setEmailAddress(tokens[1]);

        return new Ticket()
                .setId(Integer.parseInt(tokens[0]))
                .setCustomer(customer) // tokens[1]
                .setPickOne(Integer.parseInt(tokens[2]))
                .setPickTwo(Integer.parseInt(tokens[3]))
                .setPickThree(Integer.parseInt(tokens[4]))
                .setPickFour(Integer.parseInt(tokens[5]))
                .setPickFive(Integer.parseInt(tokens[6]))
                .setPowerBall(Integer.parseInt(tokens[7]))
                .setTimestamp(LocalDateTime.parse(tokens[8]));

    }

    private String mapToString(Ticket ticket) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                ticket.getId(),
                ticket.getCustomer().getEmailAddress(),
                ticket.getPickOne(),
                ticket.getPickTwo(),
                ticket.getPickThree(),
                ticket.getPickFour(),
                ticket.getPickFive(),
                ticket.getPowerBall(),
                ticket.getTimestamp()
        );
    }

}

package org.g10.lottery.data;

import java.util.List;
import org.g10.lottery.models.Ticket;

public interface TicketDao {

    List<Ticket> findAll();

    Ticket findById(int id);

    List<Ticket> findByEmailAddress(String emailAddress);

    Ticket add(Ticket ticket) throws StorageException;

    void addAll(List<Ticket> tickets) throws StorageException;
}

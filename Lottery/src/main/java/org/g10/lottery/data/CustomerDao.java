package org.g10.lottery.data;

import java.util.List;
import org.g10.lottery.models.Customer;

public interface CustomerDao {

    List<Customer> findAll();

    List<Customer> findByEmailStartingWith(String prefix);

    Customer findByEmail(String emailAddress);

    List<Customer> findByLastName(String lastName);
    
    List<Customer> findByZipCode(String zipCode);

    void add(Customer customer) throws StorageException;

    boolean update(Customer customer) throws StorageException;

}

package org.g10.lottery.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.g10.lottery.models.Customer;

public class CustomerFileDao
        extends FileDao<Customer>
        implements CustomerDao {

    public CustomerFileDao(String path) {
        super(path, 3, false);
    }

    @Override
    public List<Customer> findAll() {
        try {
            return read(this::mapToCustomer).stream()
                    .collect(Collectors.toList());
        } catch (StorageException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Customer> findByEmailStartingWith(String prefix) {

        return findAll().stream()
                .filter(c -> c.getEmailAddress().startsWith(prefix))
                .collect(Collectors.toList());
    }

    @Override
    public Customer findByEmail(String emailAddress) {
        return findAll().stream()
                .filter(c -> c.getEmailAddress().equals(emailAddress))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Customer> findByLastName(String lastName) {
        return findAll().stream()
                .filter(c -> c.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public void add(Customer customer) throws StorageException {
        append(customer, this::mapToString);
    }

    @Override
    public boolean update(Customer customer) throws StorageException {

        List<Customer> customers = findAll();

        int index = 0;
        for (; index < customers.size(); index++) {
            if (customers.get(index).getEmailAddress()
                    .equalsIgnoreCase(customer.getEmailAddress())) {
                break;
            }
        }

        if (index < customers.size()) {
            customers.set(index, customer);
            write(customers, this::mapToString);
            return true;
        }

        return false;
    }

    private String mapToString(Customer customer) {
        return String.format("%s,%s,%s",
                customer.getEmailAddress(),
                customer.getFirstName(),
                customer.getLastName());
    }

    private Customer mapToCustomer(String[] tokens) {
        return new Customer(
                tokens[0],
                tokens[1],
                tokens[2]);
    }

}

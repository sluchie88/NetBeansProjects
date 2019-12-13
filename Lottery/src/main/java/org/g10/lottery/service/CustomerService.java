package org.g10.lottery.service;

import java.util.List;
import org.g10.lottery.data.CustomerDao;
import org.g10.lottery.data.StorageException;
import org.g10.lottery.models.Customer;

public class CustomerService {

    private final CustomerDao dao;

    public CustomerService(CustomerDao dao) {
        this.dao = dao;
    }

    public Response add(Customer customer) {

        Response response = new Response();

        if (Validations.isEmail(customer.getEmailAddress())) {
            response.addError(
                    String.format("Email '%s' is not valid.", customer.getEmailAddress())
            );
        }

        if (Validations.isNullOrWhitespace(customer.getFirstName())) {
            response.addError("First name is required.");
        }
        if (Validations.isNullOrWhitespace(customer.getLastName())) {
            response.addError("Last name is required.");
        }

        if (response.hasError()) {
            return response;
        }

        // duplicate
        Customer existing = dao.findByEmail(customer.getEmailAddress());
        if (existing != null) {
            response.addError("Duplicate email address: " + customer.getEmailAddress());
            return response;
        }

        try {
            dao.add(customer);
        } catch (StorageException ex) {
            response.addError(ex.getMessage());
        }
        return response;
    }

    public List<Customer> findByEmailStartingWith(String prefix) {
        return dao.findByEmailStartingWith(prefix);
    }

}

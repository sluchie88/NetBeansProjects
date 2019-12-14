package org.g10.lottery.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.g10.lottery.models.Customer;

public class CustomerFileDao
        extends FileDao<Customer>
        implements CustomerDao {

    /*
    constructor. not sure what the path argument is doing
    */
    public CustomerFileDao(String path) {
        super(path, 3, false);
    }

    @Override
    /*
    uses a stream to find all customers in the file and returns them in an ArrayList
    if none are found, returns an empty list
    */
    public List<Customer> findAll() {
        try {
            return read(this::mapToCustomer).stream()
                    .collect(Collectors.toList());
        } catch (StorageException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    /*
    uses a stream to find Email addresses starting with a particular prefix
    */
    public List<Customer> findByEmailStartingWith(String prefix) {

        return findAll().stream()
                .filter(c -> c.getEmailAddress().startsWith(prefix))
                .collect(Collectors.toList());
    }

    @Override
    /*
    uses a stream to find a specific Email. if not found, returns null
    */
    public Customer findByEmail(String emailAddress) {
        return findAll().stream()
                .filter(c -> c.getEmailAddress().equals(emailAddress))
                .findFirst()
                .orElse(null);
    }

    @Override
    /*
    uses a stream to find a customer according to their last name. Returns all
    matching last names in an array list
    */
    public List<Customer> findByLastName(String lastName) {
        return findAll().stream()
                .filter(c -> c.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    @Override
    /*
    uses a stream to find a customer according to their zipcode. Returns all customers
    with the matching zip code
    */
    public List<Customer> findByZipCode(String zipCode) {
        return findAll().stream().filter(z -> z.getZipCode().equals(zipCode))
                .collect(Collectors.toList());
    }

    @Override
    /*
    takes in a customer and appends them to the customer file. 
    Added in the call to update() myself in an attempt to add zip codes to all customers,
    but only sort of worked
    */
    public void add(Customer customer) throws StorageException {
        //update(customer);
        append(customer, this::mapToString);
    }

    /*
    I probably made this harder on myself than I should have, but I wanted to make it
    so the zipCodes got generated rather than making a new set of data on Mockaroo.
    The hardest part was tracking down where update got called
     */
    @Override
    public boolean update(Customer customer) throws StorageException {

        List<Customer> customers = findAll();

        int index = 0;
        for (; index < customers.size(); index++) {
            if (customers.get(index).getZipCode() == null) {
                customers.get(index).setZipCode(generateZip());
            }

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

    /*
    Takes in a customer and maps it to a string. it's in the name
    */
    private String mapToString(Customer customer) {
        return String.format("%s,%s,%s,%s",
                customer.getEmailAddress(),
                customer.getZipCode(),
                customer.getFirstName(),
                customer.getLastName());
    }

    /*
    takes in a string array and calls to customer constructor
    */
    private Customer mapToCustomer(String[] tokens) {
        return new Customer(
                tokens[0],
                tokens[1],
                tokens[2]);
//                tokens[3]);
    }

    /*
    self-made method for generating zip codes. generates using random numbers and
    returns them in a string. Can make more realistic.
    Lowest possible zip: 00501
    Highest possibl zip: 99950
    */
    private String generateZip() {
        String zipCode = "";
        Random randy = new Random();
        int counter = randy.nextInt(100) + 1;
        for (int i = 0; i < 5; i++) {
            zipCode += Integer.toString(randy.nextInt(10));
        }
        if (counter % 10 == 0) {
                zipCode += "-";
                for (int j = 0; j < 4; j++) {
                    zipCode += Integer.toString(randy.nextInt(9) + 1);
                }
            }
        return zipCode;
    }

}

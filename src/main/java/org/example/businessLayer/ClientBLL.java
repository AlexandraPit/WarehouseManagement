package org.example.businessLayer;

import org.example.businessLayer.validators.EmailValidator;
import org.example.businessLayer.validators.Validator;
import org.example.dataAccessLayer.ClientDAO;
import org.example.model.Client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) for managing clients.
 */
public class ClientBLL {
    private List<Validator<Client>> validators;

    private ClientDAO clientDAO;

    /**
     * Constructs a new ClientBLL with the specified database connection.
     *
     * @param connection the database connection
     */
    public ClientBLL(Connection connection) {

        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());

        clientDAO=new ClientDAO();
    }


    /**
     * Retrieves a client by ID.
     *
     * @param id the ID of the client to retrieve
     * @return the retrieved client, or null if not found
     * @throws SQLException if a database access error occurs
     */
    public Client getClientById(int id) throws SQLException {
        Client client = clientDAO.getById(id);
        if(client==null)
        {
            throw new NoSuchElementException("The client with id "+id+" was not found!");
        }
        return client;
    }

    public int insertClient(Client client) throws SQLException {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return clientDAO.insert(client);
    }

    public void editClient(Client client){
        for(Validator<Client> item : validators)
            item.validate(client);
        clientDAO.update( client);
    }

    public void deleteClient(int id){
        clientDAO.delete(id);
    }

    public List<Client> viewAllClients(){
        return clientDAO.viewAll();
    }

}

package org.example.model;

/**
 * Represents a client entity.
 */
public class Client {
    private int id;
    private String name;
    private String phone;
    private String email;

    /**
     * Constructs a new client with the specified attributes.
     *
     * @param id    the ID of the client
     * @param name  the name of the client
     * @param phone the phone number of the client
     * @param email the email address of the client
     */
    public Client(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Constructs a new client with the specified attributes.
     *
     * @param name  the name of the client
     * @param phone the phone number of the client
     * @param email the email address of the client
     */
    public Client(String name, String phone, String email) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Gets the ID of the client.
     *
     * @return the ID of the client
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the email address of the client.
     *
     * @return the email address of the client
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the name of the client.
     *
     * @return the name of the client
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the phone number of the client.
     *
     * @return the phone number of the client
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the ID of the client.
     *
     * @param id the ID of the client
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the email address of the client.
     *
     * @param email the email address of the client
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the name of the client.
     *
     * @param name the name of the client
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the phone number of the client.
     *
     * @param phone the phone number of the client
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}

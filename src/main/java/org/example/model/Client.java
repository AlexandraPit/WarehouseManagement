package org.example.model;



public class Client {
    private  int id ;
    private String name ;
    private String phone;
    private String email;

    public Client(int id, String name, String phone, String email) {
       this.id=id;
       this.name=name;
       this.email=email;
       this.phone=phone;
    }
    public Client( String name, String phone, String email) {

        this.name=name;
        this.email=email;
        this.phone=phone;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {

    }
}

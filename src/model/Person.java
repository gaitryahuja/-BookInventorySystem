package model;

//Abstract class
public abstract class Person {

    private String name;
    private String contact;
    private String address;

    //Contructor
    public Person(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    //Setters and Getters
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }

    public String getContact() { 
        return contact; 
    }
    
    public void setContact(String contact) { 
        this.contact = contact; 
    }

    public String getAddress() { 
        return address; 
    }
    
    public void setAddress(String address) { 
        this.address = address; 
    }

    //Subclasses can call this using super.toString() to include these details.
    @Override
    public String toString() {
        return "Name: " + name + ", Contact: " + contact + ", Address: " + address;
    }
}
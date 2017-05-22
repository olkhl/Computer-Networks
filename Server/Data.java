package Server;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Borikhankozha Azimkhan && Zhandos Seraliyev && Chingiskhan Tangirbergenov
 */
public class Data {

    private String name;
    private String type;
    private String size;
    private String last_date;
    private String address;
    private String port;
    private String username;



    public Data(String username, String name, String type, String size, String last_date, String address, String port) {
        this.username = username;
        this.name = name;
            this.type = type;
        this.size = size;
        this.last_date = last_date;
        this.address = address;
        this.port = port;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSize() {
        return size;
    }

    public String getLast_date() {
        return last_date;
    }

    public String getAddress() {
        return address;
    }

    public String getPort() {
        return port;
    }
    

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "<" + name + ", " + type + ", " + size + ", " + last_date + ", " + address + ", " + port + '>';
    }




}
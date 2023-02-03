package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String id;
    
    public Room() {
        super();
    }
    private String name;
    private int status;
    private int quantity;
    private String owner;
    private int checkforload;
    public Room(String id, String name, String owner, List<User> user_List,int checkforload) {
        super();
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.user_List = user_List;
        this.status=0;
        this.checkforload=checkforload;
    }
    
    public int getCheckforload() {
        return checkforload;
    }
    public void setCheckforload(int checkforload) {
        this.checkforload = checkforload;
    }
    private List<User> user_List=new ArrayList<User>();
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public List<User> getUser_List() {
        return user_List;
    }
    public void setUser_List(List<User> user_List) {
        this.user_List = user_List;
    }
    public void printRoom(){
        System.out.println("id: "+id+".name: ."+name+".owner: "+owner+". Number: "+user_List.size());
    }
}

package model;

public class User {
    private Address addr;
    private String name;
    private String id;
    private int idScene;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdScene() {
        return idScene;
    }

    public void setIdScene(int idScene) {
        this.idScene = idScene;
    }

    private int status;
    private String currentRoom;
    private String token;
   
    public User(String id,String name) {
    	super();
    	this.id=id;
        this.name=name;
    }
}

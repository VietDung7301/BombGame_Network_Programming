package controller;

import java.util.List;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.User;
import netwoork.Connect;

public class MyTask extends TimerTask{
    private Connect connect;
    private List<ViewRoom> listroommap;
    private User user;
    public MyTask(Connect connect,List<ViewRoom> listroommap){
        this.connect=connect;
        this.listroommap=listroommap;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public void run() {
        Platform.runLater(()->{for(ViewRoom view:listroommap){
			view.loadingWaitroom(connect);

		}
        for(ViewRoom view:listroommap){
			ImageView startbtn=(ImageView) view.getViewWaitRoom().lookup("#start");
			startbtn.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					//if(view.getRoom().getOwner().equals(user.getId())){
						System.out.println("Owner:"+view.getRoom().getOwner()+".Start room Id: "+view.getRoom().getId()+".Name: "+view.getRoom().getName()+".Number of user: "+view.getRoom().getUser_List().size());
					//}
					
				}
				
			});
		}
    });
       
        
    }
    
}

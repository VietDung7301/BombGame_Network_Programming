package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;
import netwoork.Connect;

public class MyTask extends TimerTask{
    private Connect connect;
    private List<ViewRoom> listroommap;
    private User user;
    private Stage mainStage;
    public MyTask(Connect connect,List<ViewRoom> listroommap,Stage mainStage){
        this.connect=connect;
        this.listroommap=listroommap;
        this.mainStage=mainStage;
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
            System.out.println("Room id:"+view.getRoom().getId()+"."+"room status:"+view.getRoom().getStatus()+".Check for load:"+view.getRoom().getCheckforload());
            if(view.getRoom().getCheckforload()==1){
            if(view.getRoom().getStatus()==0){
			view.loadingWaitroom(connect,mainStage);
            }else{
             view.getRoom().setCheckforload(0);
            }
        }

		}
        for(ViewRoom view:listroommap){
            if(view.getRoom().getStatus()==0){
			ImageView startbtn=(ImageView) view.getViewWaitRoom().lookup("#start");
			startbtn.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					try {
                        connect.SendAndRecvData("#c006#",5500);
                       
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
					
				}
				
			});
		}
    }
    });
       
        
    }
    
}

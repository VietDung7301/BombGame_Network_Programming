package controller;

import java.util.List;

import netwoork.Connect;

public class MuiltiThread extends Thread {
    private List<ViewRoom> viewRoom;
    private Connect connect;
    
    public MuiltiThread(List<ViewRoom> room,Connect connect){
        this.viewRoom=room;
        this.connect=connect;
    }
    public void run(List<ViewRoom> listroommap){
           while(true){
            for(ViewRoom view:listroommap){
                view.loadingWaitroom(connect);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
}

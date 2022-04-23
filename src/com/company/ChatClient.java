package com.company;

import java.io.IOException;
import java.net.Socket;

public class ChatClient implements MessageHandler{
    private static final String SERVER_HOST = "localhost";
    private CommThread commThread;
    private ChatFrame chatFrame;

    public ChatClient() {
        chatFrame = new ChatFrame(this);
        try { connect(); }
        catch (IOException e){
            Message errormsg = new Message("System", "Server nicht erreichbar");
            chatFrame.addMessage(errormsg);
        }
        chatFrame.setVisible(true);
    }

    private void connect() throws IOException {
        Socket socket = new Socket(SERVER_HOST,CommRes.SERVER_PORT);
        commThread = new CommThread(socket,this);
        commThread.start();
        System.out.println("System connected");
    }


    @Override
    public void treatMessage(Message message) {
        chatFrame.addMessage(message);
    }
    public void treatUIMessage(Message message){
        try {
            if(commThread == null) connect();
            commThread.sendMessage(message);
        }catch (IOException e){
            commThread = null;
            Message errormsg = new Message("System", "Server nicht erreichbar");
            chatFrame.addMessage(errormsg);
        }
    }

    public static void main(String[] args){
        new ChatClient();
        new ChatClient();
    }
}

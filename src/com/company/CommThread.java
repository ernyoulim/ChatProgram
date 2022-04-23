package com.company;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommThread extends Thread{
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private MessageHandler messageHandler;

    public CommThread(Socket socket, MessageHandler mh) throws IOException {
        this.socket = socket;
        messageHandler = mh;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.flush();
        inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void run()  {
        try {
            while(true){
                Message message = receiveMessage();
                messageHandler.treatMessage(message);
            }
        }catch (Exception e){

        }
    }

    private Message receiveMessage() throws  IOException {
        Object message = null;
        do {
            try {
                message = inputStream.readObject();
            }catch (IOException ioe){
                disconnect();
                throw ioe;
            }catch (ClassNotFoundException cne) {

            }
        }while (! (message instanceof Message)) ;
        return (Message) message;
    }

    public void sendMessage(Message message) throws IOException{
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        }catch (IOException ioe) {
            disconnect();
            throw ioe;
        }
    }

    private void disconnect(){
        try{
            inputStream.close();
        }catch(IOException ex){

        }

        try{
            outputStream.close();
        }catch(IOException ex){

        }

        try{
            socket.close();
        }catch(IOException ex){

        }
    }

}

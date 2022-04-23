package com.company;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer implements MessageHandler {
    private int x = 0;
    private Object sema = new Object();
    private ServerSocket serverSocket;
    private Distributor distributor;

    public ChatServer() throws IOException {
        serverSocket = new ServerSocket(CommRes.SERVER_PORT);
        distributor = new Distributor();
    }

    public void run() throws IOException {
        while(true) {
            Socket socket = serverSocket.accept();
            CommThread ct = new CommThread(socket,this);
            ct.start();
            synchronized (sema) {distributor.addCommThreads(ct);
            System.out.println(x);}
        }
    }

    @Override
    public void treatMessage(Message message) {
        synchronized (sema) {distributor.pollMessage(message);
        System.out.println("Polling");}
    }

    public static void main(String args[]) throws IOException {
        // write your code here
        ChatServer chatServer = new ChatServer();
        chatServer.run();

    }

}

package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatFrame extends JFrame {
    private MessageListModel messageListModel;
    private JList lstMessages;
    private String nickname;
    private JTextField txtMessages;
    private ChatClient chatClient;
    class MessageAdder implements Runnable{
        private Message message;

        public MessageAdder(Message message){
            this.message = message;
        }

        @Override
        public void run() {
            messageListModel.addMessage(message);
            lstMessages.ensureIndexIsVisible(messageListModel.getSize()-1);
        }
    }
    public ChatFrame (ChatClient chatClient){
            nickname = JOptionPane.showInputDialog(this,"Bitte Nickname eingeben: ",
                    "Anmeldung", JOptionPane.QUESTION_MESSAGE);
            if(nickname==null || nickname.trim().equals("") )
                nickname = "Annonym";

            messageListModel = new MessageListModel();
            lstMessages = new JList(messageListModel);
            txtMessages = new JTextField();
            setTitle("Chat");
            setSize(500,500);
            setLayout(new GridLayout(2,1));
            add(lstMessages);
            add(txtMessages);
            pack();
            setVisible(true);
            txtMessages.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(txtMessages.getText().equals(""))
                        return;
                    Message message = new Message(nickname, txtMessages.getText());
                    chatClient.treatUIMessage(message);
                    txtMessages.setText("");
                }

            });
            txtMessages.requestFocusInWindow();
    }

    public void addMessage(Message message){
        MessageAdder messageAdder = new MessageAdder(message);
        SwingUtilities.invokeLater(messageAdder);
    }
}

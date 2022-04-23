package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MessageListModel extends AbstractListModel {
    private List<Message> messages;

    public MessageListModel(){
        messages = new ArrayList<Message>();
    }

    public void addMessage(Message message){
        messages.add(message);
        fireIntervalAdded(this,messages.size()-1, messages.size()-1);
    }

    @Override
    public int getSize() {
        return messages.size();
    }

    @Override
    public Object getElementAt(int index) {
        return messages.get(index);
    }
}

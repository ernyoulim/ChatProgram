package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Distributor {
    private List<CommThread> commThreads;

    public Distributor() {
        commThreads = new ArrayList<CommThread>();
    }

    public void addCommThreads(CommThread commThread){
        commThreads.add(commThread);
    }

    public void pollMessage(Message message) {
        Iterator<CommThread> it = commThreads.iterator();
        while (it.hasNext()) {
            CommThread ct = it.next();
            try { ct.sendMessage(message); }
            catch (IOException e){it.remove();}
        }
    }
}

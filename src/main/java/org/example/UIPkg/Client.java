package org.example.UIPkg;

import org.example.MessagePkg.Message;
import org.example.MessagePkg.MessageGenerator;

import java.rmi.RemoteException;
import java.util.List;

public interface Client {

    void insertName(String name);

    void registerName(List<String> names);

    void sendMessage(Message message) throws RemoteException;

    MessageGenerator getMessageGenerator();

    UI getUserInterface();
}

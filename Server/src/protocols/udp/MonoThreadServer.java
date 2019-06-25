package protocols.udp;

import com.s251437.KarlsonAdventures.journey.Kid;
import com.s251437.KarlsonAdventures.net.Message;
import com.s251437.KarlsonAdventures.control.CollectionManager;
import db.DatabaseManager;

import static com.s251437.KarlsonAdventures.net.SerializationUtils.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;


public class MonoThreadServer extends Thread {
    private DatagramChannel channel;
    private ByteBuffer in;
    private SocketAddress addr;
    private ServerCommandHandler commandHandler;
    private CollectionManager manager;


    MonoThreadServer(ByteBuffer in, CollectionManager manager, DatabaseManager dbmg, Map sessions, DatagramChannel channel, SocketAddress addr) {
        this.manager = manager;
        this.in = in;
        this.addr = addr;
        this.channel = channel;
        commandHandler = new ServerCommandHandler(manager, dbmg, sessions);
    }


    public void run() {
        if (!manager.isStopped()) {
            try {
                Message message = messageDeserializator(in.array());
                if (message.isKids()) {
                    ConcurrentSkipListSet<Kid> collection = message.getKids();
                    int length = collection.size();
                    int added = 0;
                    for (Kid kid : collection) {
                        if (manager.getCollection().add(kid)) {
                            added++;
                        }
                    }
                    String answer = String.format("Добавлено %1$d/%2$d элементов.", added, length);
                    sendAnswer(answer);
                } else {
                    String command = message.getCommand();
                    if (command.equals("load")) {
                        Message collectionMsg = new Message("collection");
                        collectionMsg.setKids(manager.getCollection());
                        sendMessage(new Message("Отправка коллекции..."));
                        sendMessage(collectionMsg);
                    } else {
                        System.out.println("Команда " + command + " от " + addr.toString());
                        String answer = commandHandler.control(command, message.getLogin(), message.getSID());
                        System.out.println(answer + addr.toString());
                        sendAnswer(answer);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Ошибка чтения команды: " + e);
            }
        }
    }

    private void sendAnswer(String answer) {
        Message response = new Message(answer);
        sendMessage(response);
    }

    public void sendMessage(Message message) {
        try {
            byte[] serializedResponse = serializator(message);
            ByteBuffer buffer = ByteBuffer.wrap(serializedResponse);
            channel.send(buffer, addr);
            System.out.println("Ответ отправлен клиенту " + addr.toString());
        } catch (IOException e) {
            System.out.println();
        }
    }

}
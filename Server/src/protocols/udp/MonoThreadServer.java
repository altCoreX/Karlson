package protocols.udp;

import com.s251437.KarlsonAdventures.Kid;
import com.s251437.KarlsonAdventures.Message;
import com.s251437.KarlsonAdventures.control.CollectionManager;
import com.s251437.KarlsonAdventures.control.CommandHandler;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentSkipListSet;


public class MonoThreadServer extends Thread {
    private DatagramChannel channel;
    private ByteBuffer in;
    private SocketAddress addr;
    private CommandHandler commandHandler;
    private CollectionManager manager;



    MonoThreadServer(ByteBuffer in, CollectionManager manager, DatagramChannel channel, SocketAddress addr){
        this.manager = manager;
        this.in = in;
        this.addr = addr;
        this.channel = channel;
        commandHandler = new CommandHandler(manager);
    }


    public void run() {
        if (!manager.isStopped()) {
            try {
                Message message = deserializator(in.array());
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
                    if(command.equals("load")){
                        Message collectionMsg = new Message("collection");
                        collectionMsg.setKids(manager.getCollection());
                        sendMessage(new Message("Отправка коллекции..."));
                        sendMessage(collectionMsg);
                    }
                    else {
                        System.out.println("Команда " + command + " от " + addr.toString());
                        String answer = commandHandler.control(command);
                        System.out.println(answer + addr.toString());
                        sendAnswer(answer);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Ошибка чтения команды: " + e);
            }
        }
    }

    private void sendAnswer(String answer){
        Message response = new Message(answer);
        sendMessage(response);
    }

    public void sendMessage(Message message){
        try {
            byte[] serializedResponse = serializator(message);
            ByteBuffer buffer = ByteBuffer.wrap(serializedResponse);
            channel.send(buffer, addr);
            System.out.println("Ответ отправлен клиенту " + addr.toString());
        }
        catch (IOException e){
            System.out.println();
        }
    }

    private byte[] serializator(Serializable object) throws IOException{
            ByteArrayOutputStream byteAOS = new ByteArrayOutputStream();
            ObjectOutputStream objOS = new ObjectOutputStream(byteAOS);
            objOS.writeObject(object);
            objOS.flush();
            objOS.close();
            byte[] data = byteAOS.toByteArray();
            return data;
    }

    private Message deserializator(byte[] response) throws IOException, ClassNotFoundException{
        ByteArrayInputStream byteAIS = new ByteArrayInputStream(response);
        ObjectInputStream objIS = new ObjectInputStream(byteAIS);
        Message command = (Message) objIS.readObject();
        return command;
    }
}
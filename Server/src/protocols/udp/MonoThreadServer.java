package protocols.udp;

import com.s251437.KarlsonAdventures.Message;
import com.s251437.KarlsonAdventures.control.CollectionManager;
import com.s251437.KarlsonAdventures.control.CommandHandler;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class MonoThreadServer implements Runnable {
    private DatagramChannel channel;
    private ByteBuffer in;
    private SocketAddress addr;
    private CommandHandler commandHandler;



    MonoThreadServer(ByteBuffer in, CollectionManager manager, DatagramChannel channel, SocketAddress addr){
        commandHandler = new CommandHandler(manager);
        this.in = in;
        this.addr = addr;
        this.channel = channel;
    }


    public void run() {
        try {
            Message message = deserializator(in.array());
            String command = message.getCommand();
            System.out.println("Команда " + command + " от " + addr.toString());
            String answer = commandHandler.control(command);
            System.out.println(answer + addr.toString());
            Message response = new Message(answer);
            byte[] serializedResponse = serializator(response);
            ByteBuffer buffer = ByteBuffer.wrap(serializedResponse);
            channel.send(buffer, addr);
            System.out.println("Ответ отправлен клиенту " + addr.toString());
        }
        catch (IOException | ClassNotFoundException e){
            System.out.println("Ошибка чтения команды: " + e);
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
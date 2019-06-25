package protocols.udp;

import com.s251437.KarlsonAdventures.net.Message;

import protocols.Client;
import static com.s251437.KarlsonAdventures.net.SerializationUtils.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DatagramSocketClient implements Client  {
    private DatagramSocket client;
    private int port;
    private int selfPort;
    private String SID;
    private String login;
    private InetAddress selfAddr;
    private InetAddress addr;

    public DatagramSocketClient(int port, InetAddress addr) {
        try {
            client = new DatagramSocket();
        } catch (java.net.SocketException e) {
            System.out.println("Ошибка создания сокета: " + e);
        }
        this.port = port;
        this.addr = addr;
        SID = null;
        login = null;
        selfPort = client.getPort();
        selfAddr = client.getInetAddress();
    }

    public void setPort(int port) {
        this.port = port;
    }

    /*
    private void sendArrays(byte[] array){
        int a = 0;
        int b = packetSize;
        int length = array.length;
        while (b < length){
            byte[] sliceArray = Arrays.copyOfRange(array, a, b);
            sendArray(sliceArray);
            a += packetSize;
            b += packetSize;
        }
        sendArray(Arrays.copyOfRange(array, a, length));
    }
    */

    /*
    private void sendArray(byte[] data) {
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
            client.send(packet);
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }
*/

    public Message send(Message message) {
        message.setLogin(login);
        message.setSID(SID);
        try {
            byte[] serializedMessage = serializator(message);
            DatagramPacket packet = new DatagramPacket(serializedMessage, serializedMessage.length, addr, port);
            try {
                client.send(packet);
                System.out.println("Отправка... ");
                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                client.setSoTimeout(1000);
                client.receive(response);
                try {
                    Message msg = messageDeserializator(buffer);
                    System.out.println(msg.getCommand());
                    return msg;
                } catch (ClassNotFoundException e) {
                    System.out.println("Ошибка в сериализации: " + e);
                }
            } catch (java.net.SocketTimeoutException e) {
                System.out.println("Время ожидания вышло.");
            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода: " + e);
            }
        }
        catch (IOException e){
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        return new Message("Ошибка");
    }

/*
    public void send(Message message){
        try {
            byte[] serializedMessage = serializator(message);
            int numberOfPackets = (int) Math.ceil(serializedMessage.length / (double) packetSize);
            String command = String.format("send %d", numberOfPackets);
            Message info = new Message(command);
            byte[] serializedInfo = serializator(info);
            sendArray(serializedInfo);
            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            client.receive(response);
        }
        catch (IOException e) {
            System.out.println("Ошибка потока: " + e);
        }
    }
    */

    public Message recieve() {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            client.setSoTimeout(10000);
            client.receive(response);
            try {
                Message msg = messageDeserializator(buffer);
                return msg;

            } catch (ClassNotFoundException e) {
                System.out.println("Ошибка в сериализации: " + e);
            }
        } catch (java.net.SocketTimeoutException e) {
            System.out.println("Время ожидания вышло.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода: " + e);
        }
        return new Message("collection");
    }

    public void bind(int port) {
        this.port = port;
    }

    public void setSID(String SID) {
        this.SID = SID;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}

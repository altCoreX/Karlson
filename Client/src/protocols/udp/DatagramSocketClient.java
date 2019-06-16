package protocols.udp;

import com.s251437.KarlsonAdventures.Message;
import protocols.Client;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class DatagramSocketClient implements Client {
    private DatagramSocket client;
    private int port;
    private int selfPort;
    private InetAddress selfAddr;
    private InetAddress addr;
    int packetSize;

    public DatagramSocketClient(int port, InetAddress addr){
        try{
            client = new DatagramSocket();
        }
        catch(java.net.SocketException e){
            System.out.println("Ошибка создания сокета: " + e);
        }
        this.port = port;
        this.addr = addr;
        selfPort = client.getPort();
        selfAddr = client.getInetAddress();
        packetSize = 1024;
    }

    public void setPort(int port){
        this.port = port;
    }

    private byte[] serializator(Serializable object){
        try {
            ByteArrayOutputStream byteAOS = new ByteArrayOutputStream();
            ObjectOutputStream objOS = new ObjectOutputStream(byteAOS);
            objOS.writeObject(object);
            objOS.flush();
            objOS.close();
            byte[] data = byteAOS.toByteArray();
            return data;
        }
        catch (IOException e) {
            System.out.println("Ошибка потока: " + e);
        }
        return null;
    }

    private Message deserializator(byte[] response) throws IOException, ClassNotFoundException{
            ByteArrayInputStream byteAIS = new ByteArrayInputStream(response);
            ObjectInputStream objIS = new ObjectInputStream(byteAIS);
            Message command = (Message) objIS.readObject();
            return command;
    }

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

    private void sendArray(byte[] data){
        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, addr, port);
            client.send(packet);
        }
        catch (IOException e){
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }

    public void send(String command){
        Message message = new Message(command);
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
                Message msg = deserializator(buffer);
                System.out.println(msg.getCommand());
            }
            catch (ClassNotFoundException e){
                System.out.println("Ошибка в сериализации: " + e);
            }
        }
        catch (java.io.IOException e){
            System.out.println("Ошибка ввода-вывода: " + e);
        }
    }

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

    public void recieve(){}

    public void bind(int port){
        this.port = port;
    }
}

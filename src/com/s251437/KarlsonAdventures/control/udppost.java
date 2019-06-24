package com.s251437.KarlsonAdventures.control;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.stream.Stream;

public class udppost {

    public static void main(String[] args) {
    /*
        DatagramSocket client;
        try {
            byte[] buf = new byte[256];
            client = new DatagramSocket(4445);
            String message = "Hello";
            byte[] msg = message.getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, InetAddress.getByName("localhost"), 4444);
            client.send(packet);
            packet = new DatagramPacket(buf, buf.length);
            client.receive(packet);
            String qt = "OK ";
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println(qt + received);
        }
        catch (Exception e){}
*/
        Stream<String> s = Stream.of("a", "b", "c");
        synchronized (s) {int a;}

    }

}

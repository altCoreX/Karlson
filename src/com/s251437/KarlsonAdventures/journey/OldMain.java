package com.s251437.KarlsonAdventures.journey;

import com.s251437.KarlsonAdventures.control.CollectionManager;
import com.s251437.KarlsonAdventures.control.CommandHandler;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

//import com.s251437.KarlsonAdventures.server.tcp.TcpServer;

public class OldMain {
    private class A implements Runnable{
        public void run(){
            try {
                DatagramSocket a = new DatagramSocket();
            }
            catch (Exception e){}
        }
    }
    private class B implements Runnable{
        public void run(){

        }
    }
    public static void main(String[] args){

        //Thread s = new Thread(new Server());
        //Thread c = new Thread(new Client());
        //s.start();
        //c.start();
        //CommandHandler handler = new CommandHandler(new CollectionManager());
        //TcpServer server = new TcpServer();
        //handler.control();
        //server.setPort(3342);

        //server.start();

    }
}

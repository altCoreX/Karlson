package protocols.udp;

import com.s251437.KarlsonAdventures.control.CollectionManager;
import com.s251437.KarlsonAdventures.control.CommandHandler;
import db.DatabaseManager;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiThreadServer {
    private DatagramChannel channel;
    private int port;
    private ByteBuffer in;
    private ByteBuffer out;
    private SocketAddress address;
    private DatagramSocket socket;
    private CollectionManager manager;
    private DatabaseManager dbmg;
    private Map sessions;

    public MultiThreadServer(int port) throws java.io.IOException{
        this.port = port;
        this.port = port;
        this.manager = new CollectionManager("labFile");
        channel = DatagramChannel.open();
        address = new InetSocketAddress(port);
        socket = channel.socket();
        socket.bind(address);
        sessions = new ConcurrentHashMap<String, String>();
        dbmg = new DatabaseManager(sessions);
    }

    public void setPort(int port){
        this.port = port;
    }

    public void start(){
        while (!manager.isStopped()) {
            try {
                in = ByteBuffer.allocate(1024);
                SocketAddress addr = channel.receive(in);
                System.out.println("Получена команда.");
                MonoThreadServer thread = new MonoThreadServer(in, manager, dbmg, sessions, channel, addr);
                thread.start();
            }
            catch (IOException e){
                System.out.println(e);
            }

        }

    }

}

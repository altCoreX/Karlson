package protocols.udp;

import com.s251437.KarlsonAdventures.control.CollectionManager;
import com.s251437.KarlsonAdventures.control.CommandHandler;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class MultiThreadServer {
    private DatagramChannel channel;
    private int port;
    private ByteBuffer in;
    private ByteBuffer out;
    private SocketAddress address;
    private DatagramSocket socket;
    private CollectionManager manager;

    public MultiThreadServer(int port) throws java.io.IOException{
        this.port = port;
        this.port = port;
        this.manager = new CollectionManager("labFile");
        channel = DatagramChannel.open();
        address = new InetSocketAddress(port);
        socket = channel.socket();
        socket.bind(address);
    }

    public void setPort(int port){
        this.port = port;
    }

    public void start(){
        while (!manager.isStopped()) {
            try {
                in = ByteBuffer.allocate(1024);
                SocketAddress addr = channel.receive(in);
                System.out.println("Получено.");
                MonoThreadServer thread = new MonoThreadServer(in, manager, channel, addr);
                thread.start();
            }
            catch (IOException e){
                System.out.println(e);
            }

        }

    }

}

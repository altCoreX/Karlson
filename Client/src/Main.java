import com.s251437.KarlsonAdventures.net.Message;
import com.s251437.KarlsonAdventures.control.CollectionManager;
import protocols.Client;
import protocols.udp.DatagramSocketClient;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0){
            switch (args[0]){
                case "spammerQueue":
                    startSpammer(true);
                    break;
                case "spammer":
                    startSpammer(false);
                    break;
            }
        }
        startClient();
    }

    private static void startSpammer(boolean isQueue){
        class Spammer extends Thread{
            public void run(){
                try {
                    InetAddress host = InetAddress.getByName("localhost");
                    Client client = new DatagramSocketClient(3434, host);
                    Message message = new Message("wait");
                    client.send(message);
                }
                catch (Exception e){}
            }
        }
            long start = System.currentTimeMillis();
            for (int i = 0; i<100; i++){
                Spammer spammer = new Spammer();
                if(isQueue) {
                    spammer.run();
                }
                else{
                    spammer.start();
                }
            }
            long finish = System.currentTimeMillis();
            long timeConsumedMillis = finish - start;
            System.out.println("Время выполнения: " +timeConsumedMillis);
    }

    private static void startClient(){
        try {
            System.out.println("Client... ");
            InetAddress host = InetAddress.getByName("localhost");
            CollectionManager manager = new CollectionManager("labFile2");
            Client client = new DatagramSocketClient(3434, host);
            ClientCommandHandler handler = new ClientCommandHandler(manager, client);
            handler.control();
        }
        catch (UnknownHostException e){
            System.out.println("Неизвестный хост: " + e);
            System.exit(0);
        }
    }
}

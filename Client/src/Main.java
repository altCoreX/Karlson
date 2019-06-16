import com.s251437.KarlsonAdventures.control.CollectionManager;
import protocols.Client;
import protocols.udp.DatagramSocketClient;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0){
            switch (args[0]){
                case "client":
                    startClient();
                case "spammer":
            }
        }

    }
    private static void startSpammer(){
    }

    private static void startClient(){
        try {
            System.out.println("Client... ");
            InetAddress host = InetAddress.getByName("localhost");
            CollectionManager manager = new CollectionManager("labFile2");
            Client client = new DatagramSocketClient(3434, host);
            CommandHandler handler = new CommandHandler(manager, client);
            handler.control();
        }
        catch (UnknownHostException e){
            System.out.println("Неизвестный хост: " + e);
            System.exit(0);
        }
    }
}

import protocols.udp.MultiThreadServer;

public class Main {
    public static void main(String[] args){
        try {
            System.out.println("Server... ");
            MultiThreadServer server = new MultiThreadServer(3434);
            server.start();
        }
        catch (Exception e){}
    }
}

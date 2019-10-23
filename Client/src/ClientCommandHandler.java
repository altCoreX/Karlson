import com.s251437.KarlsonAdventures.control.CommandHandler;
import com.s251437.KarlsonAdventures.journey.Kid;
import com.s251437.KarlsonAdventures.net.Message;
import com.s251437.KarlsonAdventures.control.CollectionManager;
import protocols.Client;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

public class ClientCommandHandler extends CommandHandler {
    private boolean isStopped;
    private Client client;

    public ClientCommandHandler(CollectionManager collectionManager, Client client) {
        super(collectionManager);
        this.client = client;
        isStopped = false;
    }

    private String readCommand() {
        Scanner scanner = new Scanner(System.in);
        String command;

        try {
            System.out.println();
            System.out.println("Введите команду:");
            System.out.println();
            command = scanner.nextLine();
        } catch (NoSuchElementException ex) {
            command = "stop";
        }
        return command;

    }

    public void control() {
        while (!isStopped) {
            String fullCommand = readCommand();
            String[] keyWords = fullCommand.trim().split(" ", 3);

            switch (keyWords[0]) {
                case "import":
                    ConcurrentSkipListSet<Kid> collection = manager.getCollection();
                    Message message = new Message("collection");
                    message.setKids(collection);
                    client.send(message);
                    break;
                case "show":
                    System.out.println("Коллекция на клиенте: " + manager.getCollection());
                    client.send(new Message(fullCommand));
                    break;
                case "stop":
                    isStopped = true;
                    break;
                case "login":
                    Message answ = client.send(new Message(fullCommand));
                    if (answ.getSID() != null) {
                        client.setLogin(keyWords[1]);
                        client.setSID(answ.getSID());
                    }
                    break;
                case "load":
                    client.send(new Message(fullCommand));
                    Message msg = client.recieve();
                    ConcurrentSkipListSet<Kid> kids = msg.getKids();
                    int length = kids.size();
                    int added = 0;
                    for (Kid kid : kids) {
                        if (manager.getCollection().add(kid)) {
                            added++;
                        }
                    }
                    System.out.println(String.format("Добавлено %1$d/%2$d.", added, length));
                    break;
                default:
                    client.send(new Message(fullCommand));
                    break;

            }


        }
    }
}
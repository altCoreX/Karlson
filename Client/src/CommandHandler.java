import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s251437.KarlsonAdventures.Kid;
import com.s251437.KarlsonAdventures.Message;
import com.s251437.KarlsonAdventures.control.CollectionManager;
import protocols.Client;

import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListSet;

public class CommandHandler {
    private CollectionManager manager;
    private boolean isStopped;
    private Client client;

    public CommandHandler(CollectionManager collectionManager, Client client) {
        manager = collectionManager;
        this.client = client;
        isStopped = false;
        if (collectionManager != null) manager = collectionManager;
    }

    private String readCommand() {
        Scanner scanner = new Scanner(System.in);
        String command;

        try {
            System.out.println("Введите команду");
            command = scanner.nextLine();
        } catch (NoSuchElementException ex) {
            command = "stop";
        }
        return command;

    }

    public void control() {
        while (!isStopped) {
            String fullCommand = readCommand();
            String[] keyWords = fullCommand.trim().split(" ", 2);

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
                case "load":
                    client.send(new Message(fullCommand));
                    Message msg = client.recieve();
                    ConcurrentSkipListSet<Kid> kids = msg.getKids();
                    int length = kids.size();
                    int added = 0;
                    for(Kid kid:kids){
                        if(manager.getCollection().add(kid)){
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
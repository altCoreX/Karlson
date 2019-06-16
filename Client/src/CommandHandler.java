import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s251437.KarlsonAdventures.Kid;
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
                    for(Kid kid:collection) {
                        String command = String.format("add %1$s", kid.toJson());
                        client.send(command);
                        continue;
                    }
                    break;
                case "stop":
                    isStopped = true;
                    client.send(fullCommand);
                    break;
                default:
                    client.send(fullCommand);

            }


        }
    }
}
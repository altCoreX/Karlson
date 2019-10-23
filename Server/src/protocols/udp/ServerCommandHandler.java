package protocols.udp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.s251437.KarlsonAdventures.control.CollectionManager;
import com.s251437.KarlsonAdventures.control.CommandHandler;
import com.s251437.KarlsonAdventures.control.ReadElementFromJsonException;
import com.s251437.KarlsonAdventures.control.Utils;
import com.s251437.KarlsonAdventures.journey.Kid;
import db.DatabaseManager;

import java.util.HashMap;
import java.util.Map;

public class ServerCommandHandler extends CommandHandler {
    DatabaseManager dbmg;
    Map sessions;

    ServerCommandHandler(CollectionManager collectionManager, DatabaseManager dbmg, Map sessions){
        super(collectionManager);
        this.sessions = sessions;
        this.dbmg = dbmg;

    }

    public String control(String command, String login, String SID) {
        String[] fullCommand = command.trim().split(" ", 3);
        if(login == null || SID == null){
            return authControl(fullCommand);//авторизация
        } else if(sessions.size() != 0 && sessions.get(SID).equals(login)) {
            return commandSwitcher(fullCommand, login);//выполнение комманд.
        } else{
            return "Авторизируйтесь!";
        }
    }

    protected String commandSwitcher(String[] fullCommand, String login) {
        String collectionAnswer = collectionControl(fullCommand, login);
        synchronized (this) {
            if (collectionAnswer != "null") {
                return collectionAnswer;
            } else {
                return serverControl(fullCommand);
            }
        }
    }

    private String collectionControl(String[] fullCommand, String login) {
        Gson gson = new Gson();
        try {
            switch (fullCommand[0]) {
                case "remove_lower":
                case "remove":
                case "add":
                case "add_if_min":
                    try {
                        Kid element = Utils.getElementFromJSON(gson, fullCommand[1]);
                        element.setOwner(login);
                        switch (fullCommand[0]) {
                            case "remove":
                                return manager.remove(element);
                            case "remove_lower":
                                return manager.removeLower(element);
                            case "add_if_min":
                                return manager.addIfMin(element);
                            case "add":
                                return manager.add(element);
                        }
                    } catch (JsonSyntaxException ex) {
                        return "Ошибка, элемент задан неверно. Используйте формат JSON.";
                    } catch (ReadElementFromJsonException ex) {
                        return ex.toString();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        return "Неверный аргумент.";
                    }
                    break;
                case "import":
                    return manager.importItem(fullCommand[1]);
                case "info":
                    return manager.info();
                case "show":
                    return manager.show();
                case "stop":
                    return manager.finishWork();
                case "save":
                    return manager.save();


            }
            return "null";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Неверный аргумент.";
        }
    }

    private String authControl(String[] fullCommand) {
        try {
            switch (fullCommand[0]) {
                case "login":
                    return dbmg.login(fullCommand[1], fullCommand[2]);
                case "register":
                    return dbmg.register(fullCommand[1], fullCommand[2]);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Неверный аргумент.";
        }
        return "Ошибка авторизации";
    }

    private String serverControl(String[] fullCommand){
        try {
            switch (fullCommand[0]) {
                case "wait":
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Waited";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
        return "Неверный аргумент.";
        }
        return "Ошибка, Неизвестная команда.";
    }
}

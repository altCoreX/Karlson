package protocols.udp;

import com.s251437.KarlsonAdventures.control.CollectionManager;
import com.s251437.KarlsonAdventures.control.CommandHandler;
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
            return commandSwitcher(fullCommand);//выполнение комманд.
        } else{
            return "Авторизируйтесь.";
        }
    }

    @Override
    protected String commandSwitcher(String[] fullCommand) {
        String collectionAnswer = collectionControl(fullCommand);
        synchronized (this) {
            if (collectionAnswer != "null") {
                return collectionAnswer;
            } else {
                return serverControl(fullCommand);
            }
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

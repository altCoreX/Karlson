package db;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import static java.lang.System.currentTimeMillis;

public class DatabaseManager {
    private Map sessions;
    private PostgreDatabase db;
    private EmailClient emailClient = new EmailClient("labaratorybot@yandex.ru", "1337Bot");

    public DatabaseManager(Map<String, String> sessions){
        this.sessions = sessions;
        try {
            db = new PostgreDatabase();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public String login(String login, String password){
        try {
            ResultSet rs = getUserInfo(login);
            if(rs.next()){
                String loginDB = rs.getString("login");
                String passwordDB = rs.getString("password");
                System.out.println();
                if (loginDB.equals(login) && passwordDB.equals(password)){
                    String SID = generateSessionId();
                    sessions.put(SID, login);
                    return SID;
                }
            } else {
                return "Для начала зарегистрируйтесь.";
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    public String register(String email, String login) {
        if (email.matches(".+@.+\\..+")) {
            try {
                ResultSet rs = getUserInfo(login);
                if (rs.next()) {
                    return "Учетная запись существует.";
                } else {
                    String password = generatePassword();
                    String addUser = String.format("insert into account (password, email, login) values (%1$s,%2$s,%3$s);",
                            password, email, login);
                    db.execute(addUser);
                    String subject = "Registration in BOT";
                    String text = String.format("Hello, %1$s, your password is %2$s.", login, password);
                    //emailClient.send(subject, text, email);
                    return "Пароль выслан вам на почту.";
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return "Неверный формат email";
    }

    public void exec(){

    }

    private ResultSet getUserInfo(String login) throws SQLException{
            String loginSQL = String.format("select * from account where login like '%s';", login);
        return db.get(loginSQL);
    }

    private String getSHA512(String passwordToHash, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private String generatePassword(){
        Random random = new Random();
        int saltLength = 10 + random.nextInt(3);
        char[] passwordArray = new char[saltLength];
        for (int i = 0; i < saltLength; i++){
            passwordArray[i] = (char)(random.nextInt(26) + 'a');
        }
        String password = new String(passwordArray);
        return password;

    }

    private String generateSessionId(){
        Random random = new Random();
        long time = currentTimeMillis();
        int saltLength = 8;
        char[] head = new char[saltLength];
        char[] tail = new char[saltLength];
        for (int i = 0; i < saltLength; i++){
            head[i] = (char)(random.nextInt(26) + 'a');
            tail[i] = (char)(random.nextInt(26) + 'a');
        }
        String sessionID = String.format("%1$s%2$d%3$s", new String(head), time, new String(tail));
        return sessionID;

    }
}

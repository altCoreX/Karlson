package protocols;

import com.s251437.KarlsonAdventures.net.Message;

public interface Client {

    void setPort(int port);
    void bind(int port);
    Message send(Message message);
    void setSID(String SID);
    void setLogin(String login);

    Message recieve();
}

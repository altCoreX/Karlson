package protocols;

import com.s251437.KarlsonAdventures.Message;

import java.io.Serializable;

public interface Client {

    void setPort(int port);
    void bind(int port);
    void send(String command);
    void recieve();
}

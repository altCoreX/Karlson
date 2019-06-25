package com.s251437.KarlsonAdventures.net;

import java.io.*;

public class SerializationUtils {
    public static byte[] serializator(Serializable object) throws IOException {
        ByteArrayOutputStream byteAOS = new ByteArrayOutputStream();
        ObjectOutputStream objOS = new ObjectOutputStream(byteAOS);
        objOS.writeObject(object);
        objOS.flush();
        objOS.close();
        byte[] data = byteAOS.toByteArray();
        return data;
    }

    private static Object deserializator(byte[] array) throws IOException, ClassNotFoundException{
        ByteArrayInputStream byteAIS = new ByteArrayInputStream(array);
        ObjectInputStream objIS = new ObjectInputStream(byteAIS);
        Object object = objIS.readObject();
        return object;
    }

    public static Message messageDeserializator(byte[] array) throws IOException, ClassNotFoundException{
        Message message = (Message) deserializator(array);
        return message;
    }

}

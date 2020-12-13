package sample;
import Shared.User;
import javafx.scene.Parent;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class Client {
    private static Socket clientSocket;
    private static ServerSocket serverSocket;
    private static ObjectInputStream ois = null;
    private static ObjectOutputStream oos = null;

    static IConnection connector = () -> {
        try {
            clientSocket = new Socket("localhost", 4022);
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    };

    static void SendMessage(Object ToSend) {
        try {
            oos.writeObject(ToSend);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static Object AcceptMessage() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    static User Authorize(String login, String password) throws IOException, ClassNotFoundException {
        SendMessage("authorization");
        oos.writeObject(new User(login, password, null));
        oos.flush();
        User user = (User) ois.readObject();
        return user;
    }

    static void EditUser(User initialData, User finalData) throws IOException {
        oos.writeObject(initialData);
        oos.flush();
        oos.writeObject(finalData);
        oos.flush();
    }
}

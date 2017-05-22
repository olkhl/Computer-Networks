package Server;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Borikhankozha Azimkhan && Zhandos Seraliyev && Chingiskhan Tangirbergenov
 */
public class main_server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        ArrayList<Data> myData = new ArrayList<Data>();
 
        ServerSocket welcomeSocket = new ServerSocket(6788);

        while (true) {

            Socket connectionSocket = welcomeSocket.accept();

            Server_Socket_1 thread = new Server_Socket_1(welcomeSocket, connectionSocket, myData);
            thread.start();

        }
    }

}


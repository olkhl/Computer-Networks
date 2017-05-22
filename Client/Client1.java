package Client;

import Client.Client_Socket_1;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 *
 * 
 * @authors Kaisar Kassenov & Olzhas Sutemgenov
 * 
 * 
 */
public class Client1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String Receiver, Sender;
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));

        int myServerPort = 6796;
        ServerSocket s = new ServerSocket(myServerPort);
        Client_Socket_1 thread = new Client_Socket_1(s);
        thread.start();

        Socket clientSocket = new Socket("localhost", 6788);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        outToServer.writeBytes("HELLO" + '\n');
        Receiver = inFromServer.readLine();
        System.out.println("FROM SERVER: " + Receiver);
    
        System.out.println("Write your username:");
        String usr = buf.readLine();
               
        outToServer.writeBytes("Connect to server with username:" + usr + "\n");

        System.out.println("Log in is completed!");

        File folder = new File(System.getProperty("user.dir") + "/src/Client/");
        File[] files = folder.listFiles();
        String initial = "UPLOAD: ";
        initial += usr + " ";
        initial += myServerPort;
        for (File file : files) {
            if (file.isFile()) {
                Date myDate = new Date(file.lastModified());
                String temp = "";
                String[] e = myDate.toString().split(" ");
                temp += (e[2] + "-" + e[1] + "-" + e[5]);

                initial += ": " + file.getName();
                initial += " | " + file.length(); 
                initial += " | " + temp;
            }
        }
        outToServer.writeBytes(initial + '\n');

        while (true) {
            Sender = buf.readLine();
            String[] segment = Sender.split(" ");
            if (!segment[0].equals("SEARCH:")) {

                if (Receiver.equals("BYE")) {
                    thread.exiting();
                    clientSocket.close();
                    s.close();
                    break;
                }
            } 
            else {
                
                outToServer.writeBytes(Sender + '\n');
                boolean used = false;
                String workWith = "";
                while (!Receiver.equals("<")) {
                    Receiver = inFromServer.readLine();
                    if (!used) {
                        workWith = Receiver;
                        used = true;
                    }
                } 

                if (used) {
                    String[] sfiles = workWith.split(",");
                    String Address = sfiles[4].substring(2);
                    String name = sfiles[0].substring(1) + sfiles[1].substring(1);
                    int prt = Integer.parseInt(sfiles[5].substring(1, sfiles[5].length() - 1));

                    System.out.println("PEER: " + Address + " " + prt + " " + name);

                    Socket clSocket = new Socket(Address, prt);

                    DataOutputStream outToCl = new DataOutputStream(clSocket.getOutputStream());

                    outToCl.writeBytes("DOWNLOAD:" + name + "\n");

                    DataInputStream din = new DataInputStream(clSocket.getInputStream());

                    String filename = din.readUTF();
                    System.out.println("Receving file: " + filename);

                    long sz = Long.parseLong(din.readUTF());
                    System.out.println("File Size: " + (sz / 1048576) + " MB");

                    byte b[] = new byte[1024];

                    FileOutputStream fos = new FileOutputStream(new File(System.getProperty("user.dir") + "/src/Client/" + filename), true);
                    
                    long bytesRead;
                    do {
                        bytesRead = din.read(b, 0, b.length);
                        fos.write(b, 0, b.length);
                    } while (!(bytesRead < 1024));
                    System.out.println("Completed");
                    fos.close();
                    din.close();
                    outToCl.close();
                }
            } 
        }
    }
}


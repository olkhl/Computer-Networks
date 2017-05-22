package Client;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
 *
 * 
 * @author Kaisar Kassenov & Olzhas Sutemgenov
 * 
 * 
 */
public class Client_Socket_1 extends Thread {
    
    ServerSocket s;
    Integer a = 0;
    
    public  Client_Socket_1 (ServerSocket s) {
        this.s = s;
        a = 1;
    }
    
    public void exiting() {
        a = 0;
    }

    public void run() {
        
    while (a == 1) {
            try {
                Socket connectionSocket = s.accept();
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

                DataInputStream din=new DataInputStream(connectionSocket.getInputStream());
                DataOutputStream dout=new DataOutputStream(connectionSocket.getOutputStream());

                String rec = inFromClient.readLine();
                String el[] = rec.split("[:]");

                System.out.println("FROM ANOTHER PEER: " + rec);
                if (el[0].equals("DOWNLOAD")){

                    String filename = el[1];
                    System.out.println("Sending File: "+filename);
                    dout.writeUTF(filename);
                    dout.flush();

                    File fileR=new File(System.getProperty("user.dir") + "/src/MyClient/MyFiles/" + filename);
                    FileInputStream fin=new FileInputStream(fileR);
                    long sz=(int)fileR.length();

                    byte b[]=new byte [1024];

                    int read;

                    dout.writeUTF(Long.toString(sz));

                    System.out.println ("Size: "+sz);
                    System.out.println ("Buf size: " + s.getReceiveBufferSize());

                    while((read = fin.read(b)) != -1){
                        dout.write(b, 0, read);
                        dout.flush();
                    }

                    dout.close();

                }
            } catch (IOException ex) {

               System.out.println(ex.getMessage());
            }

        }

    }

}

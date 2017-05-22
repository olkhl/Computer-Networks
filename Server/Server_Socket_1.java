package Server;



import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Borikhankozha Azimkhan && Zhandos Seraliyev && Chingiskhan Tangirbergenov
 */
public class Server_Socket_1 extends Thread {

    ServerSocket welcomeSocket;
    Socket connectionSocket;
    String rec;
    String cap;
    String send;
    ArrayList<Data> myData;

    Server_Socket_1(ServerSocket welcomeSocket, Socket connectionSocket, ArrayList<Data> myData) {
        this.connectionSocket = connectionSocket;
        this.welcomeSocket = welcomeSocket;
        this.myData = myData;
        
    }

    public boolean contains(Data u){
        for (int i = 0; i < myData.size(); i++){
            if (myData.get(i).getUsername().equals(u.getUsername())){
                return true;
            }
        }
        return false;
    }

    public void run() {
        try {
            String address = connectionSocket.getLocalAddress() + "";
            String port = "";

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            while (true) {
                try {
                    rec = inFromClient.readLine();
                    String e1[] = rec.split(" ");
//                    System.out.println("e1[0]" + e1[0]);
                    System.out.println();
                    if (rec.equals("BYE")) {
                        System.out.println("FROM ONE PEER: " + rec);
                        outToClient.writeBytes("BYE\n");
                    } else if (e1[0].equals("UPLOAD:")) {
                        port = e1[1];
                        System.out.println("PRESENT PEER DATA: ");
                        for (int i = 2; i < e1.length; i++) {
                            String e2[] = e1[i].split("/");
                            String e3[] = e2[0].split("[.]");
                            String username = "";
                            String name = e3[0];
                            String type = "." + e3[1];
                            String size = e2[1];
                            String last_date = e2[2];
                            Data data = new Data(username, name, type, size, last_date, address, port);
                            myData.add(data);
                            System.out.println(data);
                        }

                    } else if (e1[0].equals("SEARCH:") && e1.length > 1) {

                        String fname = e1[1];
                        String ans = "";
                        for (int i = 0; i < myData.size(); i++) {
                            if (!port.equals(myData.get(i).getPort())
                                && (fname.toLowerCase().contains(myData.get(i).getName().toLowerCase()) 
                                || myData.get(i).getName().toLowerCase().contains(fname.toLowerCase()))) {
                               
                                    ans += myData.get(i).toString() + '\n';
                                
                            }
                            }
                        
                        if (ans.equals("")) {
                            ans += "NOT FOUND" + '\n';
                        } else {
                            ans = "FOUND:\n" + ans;
                        }
                        ans += "\n";
                        outToClient.writeBytes(ans);
                    } else if (rec.equals("HELLO")) {
                        System.out.println("ONE PEER HAS CONNECTED");
                        System.out.println("PEER: " + rec);
                        String hi = "HI" + '\n';
                        outToClient.writeBytes(hi);
                    }  else  {
                        System.out.println("FROM ONE PEER: " + rec);
                        outToClient.writeBytes("BAD REQUEST!!!\n");
                    }

                } catch (Exception e) {
                    System.out.println("ONE PEER HAS GONE!!!");

                    for (int i = 0; i < myData.size(); i++) {
                        if (myData.get(i).getPort().equals(port)) {
                            myData.remove(i);
                            i--;
                        }
                    }
                    break;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Server_Socket_1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
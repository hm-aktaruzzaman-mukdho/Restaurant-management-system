package s2105064.client;

import s2105064.CommonClass.Food;
import s2105064.CommonClass.SocketWrapper;

import java.util.HashMap;

public class CustomerClient {
    private final SocketWrapper serversocket;
    //private final String hostname;
    //private final int port;
    private String username, password;
    private int isaccepted;
    private HashMap<Food,Integer> orderpoolcustomer;

    public HashMap<Food, Integer> getOrderpoolcustomer() {
        return orderpoolcustomer;
    }

    public String getUsername()
    {
        return username;
    }
    public int isAccepted() {
        return isaccepted;
    }

    public CustomerClient(String hostname, int port) throws Exception {
//        this.hostname = hostname;
//        this.port = port;
        serversocket = new SocketWrapper(hostname, port);
        orderpoolcustomer=new HashMap<>();
        System.out.println("server connected");
    }

    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
        //this.isaccepted = true;
    }


    public void connectAndCommunicate() {
        try {
            //serversocket.write("customer");//This work is done elsewere to optimize the code.

            //For login controlling write username and password here.And write corresponding instructions in server.
            serversocket.write(username);
            serversocket.write(password);


            //server will check the credentials and send the handshake status.
            String status = (String) serversocket.read();

//            isaccepted = status.equalsIgnoreCase("accepted");
            if (status.equalsIgnoreCase("accepted")) {
                isaccepted=1;
                System.out.println("You are accepted");
            } else if (status.equalsIgnoreCase("present"))
            {
                isaccepted=0;
                System.out.println("You are already logged in");
            }
            else {
                isaccepted=2;
                System.out.println("You are not accepted");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public SocketWrapper getServersocket() {
        return serversocket;
    }

    public void terminateconnection() throws Exception {
        serversocket.write("terminate");
        serversocket.closeConnection();
    }
}

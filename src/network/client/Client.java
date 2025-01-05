package network.client;

import network.SocketWrapper;

import java.io.IOException;

public class Client {
    private SocketWrapper socketWrapper;
    private String clubName;

    public Client(SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
    }

    public Client(String serverAddress, int serverPort) throws IOException {
        socketWrapper = new SocketWrapper(serverAddress, serverPort);
    }

    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }
    
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
    public String getClubName() {
        return clubName;
    }
}

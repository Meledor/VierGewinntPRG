package Model.Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkPlayerSearcher {

    private final int timeoutInMilliseconds = 200;
    private final int port;
    private final String baseIpAddress;

    private boolean continueSearching;
    private NewPlayersFoundListener newPlayersFoundListener;

    public NetworkPlayerSearcher() {
        this.port = Settings.getPort();
        this.baseIpAddress = Settings.getBaseIpAddress();
    }

    public void startSearching() {
        this.continueSearching = true;

        new Thread(() -> {
            this.continueslySearchPlayers();
        }).start();
    }

    private void continueslySearchPlayers() {
        while (this.continueSearching) {
            try {
                SearchPlayersAndThrowEvent();
                Thread.sleep(10000);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(NetworkPlayerSearcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void SearchPlayersAndThrowEvent() throws IOException {
        final List<String> availableHosts = this.GetAvailableHostsInNetwork();
        final List<String> answeringHosts = this.GetAnsweringHosts(availableHosts);
        if (this.newPlayersFoundListener != null && answeringHosts.isEmpty() == false) {
            newPlayersFoundListener.NewPlayersFound(answeringHosts);
        }
    }

    private List<String> GetAvailableHostsInNetwork() throws UnknownHostException, IOException {
        List<String> availableHosts = new ArrayList<>();

        InetAddress localHostLANAddress = LocalIpProvider.getLocalHostLANAddress();
        String localHostAddress = localHostLANAddress.getHostAddress();
        
        for (int i = 0; i < 255; i++) {
            String host = baseIpAddress + i;
            if (!host.equals(localHostAddress)) {
                boolean isReachable = InetAddress.getByName(host).isReachable(this.timeoutInMilliseconds);
                System.out.println(host + " -> " + isReachable);
                if (isReachable) {
                    availableHosts.add(host);
                }
            }
        }

        return availableHosts;
    }

    private List<String> GetAnsweringHosts(final List<String> availableHosts) {
        List<String> answeringHosts = new ArrayList<>();

        for (String host : availableHosts) {
            try {
                try (Socket hostSocket = new Socket(host, this.port)) {
                    DataOutputStream streamToHost = new DataOutputStream(hostSocket.getOutputStream());
                    BufferedReader streamFromHost = new BufferedReader(new InputStreamReader(hostSocket.getInputStream()));
                    streamToHost.writeBytes(ProtocolKeywords.AvailableNetworkPlayerListingRequest + "\n");
                    streamToHost.flush();
                    String response = streamFromHost.readLine();
                    if (ProtocolKeywords.AvailableNetworkPlayerListingAnswer.equals(response)) {
                        answeringHosts.add(host);
                    }
                }
            } catch (IOException ex) {
            }
        }

        return answeringHosts;
    }

    public void setListener(NewPlayersFoundListener newPlayersFoundListener) {
        this.newPlayersFoundListener = newPlayersFoundListener;
    }

    public void stopSearching() {
        this.continueSearching = false;
    }
}

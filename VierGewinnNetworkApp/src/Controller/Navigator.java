package Controller;

import Model.Network.NetworkPlayerSearcher;
import Model.Network.RequestHandling.NetworkRequestManager;
import Views.Interfaces.GameView;
import Views.Interfaces.HelpView;
import Views.Interfaces.LocalGameCreationView;
import Views.Interfaces.NetworkView;
import Views.Interfaces.StartView;
import Views.Interfaces.ViewHandler;

public class Navigator {

    private final ViewHandler viewHandler;
    private final NetworkPlayerSearcher networkPlayerSearcher;
    private final NetworkRequestManager networkRequestManager;

    public Navigator(ViewHandler viewHandler, NetworkPlayerSearcher networkPlayerSearcher, NetworkRequestManager networkRequestManager) {
        this.viewHandler = viewHandler;
        this.networkPlayerSearcher = networkPlayerSearcher;
        this.networkRequestManager = networkRequestManager;
    }

    public void navigateToStartView() {
        StartView view = this.viewHandler.switchToStartView();
        StartViewController controller = new StartViewController(this);
        view.setListener(controller);
        controller.init();
    }

    public void navigateToLocalGameCreationView() {
        LocalGameCreationView view = this.viewHandler.switchToLocalGameCreationView();
        LocalGameCreationViewController controller = new LocalGameCreationViewController(this);
        view.setListener(controller);
        controller.init();
    }

    public void navigateToNetworkView() {
        NetworkView view = this.viewHandler.switchToNetworkView();
        NetworkViewController controller = new NetworkViewController(view, this);
        view.setListener(controller);
        controller.init();
    }

    public void navigateToGameViewForLocalPlay() {
        GameView view = this.viewHandler.switchToGameView();
        GameViewController controller = new LocalGameViewController(view, this);
        view.setListener(controller);
        controller.init();
    }
    
    public void navigateToGameViewForNetworkPlay(String ipAddress) {
        GameView view = this.viewHandler.switchToGameView();
        GameViewController controller = new NetworkGameViewController(view, this, this.networkRequestManager, ipAddress);
        view.setListener(controller);
        controller.init();
    }

    public void navigateToHelpView() {
        HelpView view = this.viewHandler.switchToHelpView();
        HelpViewController controller = new HelpViewController(this);
        view.setListener(controller);
        controller.init();
    }
}

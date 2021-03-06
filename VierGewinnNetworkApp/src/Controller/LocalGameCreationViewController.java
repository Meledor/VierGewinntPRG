package Controller;

import Model.Network.Settings;
import Views.Interfaces.LocalGameCreationViewListener;

public class LocalGameCreationViewController implements LocalGameCreationViewListener {

    private final Navigator navigator;

    public LocalGameCreationViewController(Navigator navigator) {
        this.navigator = navigator;
    }

    void init() {
    }
    
    @Override
    public void ResumeGamePressed() {
        this.navigator.navigateToGameViewForResumingLocalGame();
    }

    @Override
    public void NewGamePressed(int width, int height) {
        Settings.setGameFieldWidth(width);
        Settings.setGameFieldHeight(height);
        this.navigator.navigateToGameViewForLocalPlay();
    }

    @Override
    public void BackPressed() {
        this.navigator.navigateToStartView();
    }
}

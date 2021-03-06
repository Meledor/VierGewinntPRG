/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.subPanel;

import Views.Interfaces.StartView;
import Views.Interfaces.StartViewListener;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartViewPanel extends JPanel implements StartView{
    
    private StartViewListener startViewListener;
    
    public StartViewPanel(){
        initComponent();
    }
    
    protected final void initComponent(){
        GridLayout layout = new GridLayout(3, 1);        
        startAI = new JButton("Play against AI");
        startNetGame = new JButton("Play over Network");
        openHelp = new JButton("Help");
        
        this.setBorder(BorderFactory.createEmptyBorder(80, 20, 80, 20));
        layout.setVgap(5);
        
        startAI.addActionListener(x -> { 
            if (this.startViewListener != null) {                
                this.startViewListener.PlayerAgainstComputerPressed();
            }
        });
        
        startNetGame.addActionListener(x -> {
            if (this.startViewListener != null) {
                this.startViewListener.PlayOverNetworkPressed();
            }
        });
        
        openHelp.addActionListener(x -> {
            if (this.startViewListener != null) {
                this.startViewListener.OpenHelpPressed();
            }
        });
                
        startAI.setPreferredSize(startNetGame.getPreferredSize());
        openHelp.setPreferredSize(startNetGame.getPreferredSize());
        
        this.setLayout(layout);
        this.add(startAI);
        this.add(startNetGame);
        this.add(openHelp);
    }
    
    JButton startAI;
    JButton startNetGame;
    JButton openHelp;

    @Override
    public void setListener(StartViewListener listener) {
        this.startViewListener = listener;
    }
}

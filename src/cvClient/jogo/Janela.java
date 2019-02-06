package cvClient.jogo;

import generics.*;
import javax.swing.*;

public class Janela extends JFrame
{
    private GameEngine ge;
    private AreaJogo areaJogo;
    
    public Janela() {}

    public Janela(GameEngine ge)
    {
        setTitle("Janela de Jogo");
        this.ge = ge;
        this.ge.setPosIniciais();    
        
        // desenha os quadriculados e o vetor
        areaJogo = new AreaJogo(this.ge);
        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(240, 100);
        setSize(800, 600);
        setResizable(false);

        setVisible(true);
        getContentPane().add(areaJogo);
        areaJogo.desenharFundo(areaJogo.getGraphics());
        
        StatsPanel sp = new StatsPanel(this.ge.getPlayers(),areaJogo,this);
        getContentPane().add(sp);
        repaint();
    }
}
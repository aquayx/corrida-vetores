package cvClient.jogo;

import generics.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StatsPanel extends JPanel implements ActionListener, KeyListener
{
    private JFrame janela;
    private JLabel x1 = new JLabel("X");
    private JLabel y1 = new JLabel("Y");
    private JLabel v = new JLabel("Velocidade Instantânea:");
    private JLabel a = new JLabel("Aceleração:");
    private JLabel vmLabel = new JLabel("Jogador: ");
    private JLabel x2 = new JLabel("X");
    private JLabel y2 = new JLabel("Y");
    private JLabel vm = new JLabel();
    private JLabel vx = new JLabel("0");
    private JLabel vy = new JLabel("0");
    private JLabel ax = new JLabel("0");
    private JLabel ay = new JLabel("0");
    private JButton xmais = new JButton("+");
    private JButton ymais = new JButton("+");
    private JButton xmenos = new JButton("-");
    private JButton ymenos = new JButton("-");
    private JButton ready = new JButton("JOGAR");
    private ArrayList<Jogador> players;
    private AreaJogo areaJogo; //para receber o painel onde são printados os vetores
    private Jogador jogadorDaVez;
    private int quemJoga;    
    private boolean[] permitirMudanca = {true, true, true, true}; // x- x+ y- y+

    public StatsPanel(ArrayList<Jogador> players, AreaJogo areaJogo, JFrame janela)
    {
        this.janela = janela;
        this.areaJogo = areaJogo;
        this.players = players;
        quemJoga = 0;
        jogadorDaVez = players.get(quemJoga);

        setLayout(null);
        setSize(180, 400);
        setLocation(620, 15);
        setFocusable(true);
        addKeyListener(this);
        xmenos.addActionListener(this);
        xmais.addActionListener(this);
        ymenos.addActionListener(this);
        ymais.addActionListener(this);
        ready.addActionListener(this);

        a.setBounds(45, 0, 80, 20);
        x1.setBounds(20, 40, 20, 20);

        xmenos.setBorder(null);
        xmenos.setFocusable(false);
        xmenos.setBounds(45, 40, 17, 17);

        ax.setBounds(70, 40, 50, 20);
        ax.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        xmais.setBorder(null);
        xmais.setFocusable(false);
        xmais.setBounds(130, 40, 17, 17);

        y1.setBounds(20, 100, 20, 20);

        ymenos.setBorder(null);
        ymenos.setFocusable(false);
        ymenos.setBounds(45, 100, 17, 17);

        ay.setBounds(70, 100, 50, 20);
        ay.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        ymais.setBorder(null);
        ymais.setFocusable(false);
        ymais.setBounds(130, 100, 17, 17);

        v.setBounds(10, 150, 140, 20);

        vx.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        vy.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        vx.setBounds(60, 185, 50, 20);
        vy.setBounds(60, 215, 50, 20);

        x2.setBounds(20, 185, 20, 20);
        y2.setBounds(20, 215, 20, 20);

        vmLabel.setBounds(25, 260, 110, 20);
        vm.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        vm.setBounds(65, 290, 80, 20);
        vm.setText(jogadorDaVez.getNickname());

        ready.setFocusable(false);
        ready.setBounds(35, 340, 80, 30);

        add(a);
        add(v);
        add(x1);
        add(y1);
        add(xmenos);
        add(xmais);
        add(ax);
        add(ay);
        add(ymais);
        add(ymenos);
        add(vx);
        add(vy);
        add(x2);
        add(y2);
        add(vmLabel);
        add(vm);
        add(ready);

    }

    public void refreshPanel()
    {
        vm.setText(jogadorDaVez.getNickname());
        vx.setText(jogadorDaVez.getVetor().getSpd()[0] + "");
        vy.setText(jogadorDaVez.getVetor().getSpd()[1] + "");
        ax.setText(jogadorDaVez.getVetor().getAccel(0) + "");
        ay.setText(jogadorDaVez.getVetor().getAccel(1) + "");
        repaint();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(xmais))
        {
            aumentaX();
        }
        else if (e.getSource().equals(xmenos))
        {
            diminuiX();
        }
        else if (e.getSource().equals(ymais))
        {
            aumentaY();
        }
        else if (e.getSource().equals(ymenos))
        {
            diminuiY();
        }
        else if (e.getSource().equals(ready))
        {
            jogar();
        }
        checkButtons();
    }

    public void aumentaX()
    {
        jogadorDaVez.getVetor().updateAccel(1, 0);
        xmenos.setEnabled(true);
        xmais.setEnabled(true);
        ax.setText(jogadorDaVez.getVetor().getAccel(0) + "");
        repaint();
    }

    public void aumentaY()
    {
        jogadorDaVez.getVetor().updateAccel(0, 1);
        ymenos.setEnabled(true);
        ymais.setEnabled(true);
        ay.setText(jogadorDaVez.getVetor().getAccel(1) + "");
        repaint();
    }

    public void diminuiX()
    {
        jogadorDaVez.getVetor().updateAccel(-1, 0);
        xmenos.setEnabled(true);
        xmais.setEnabled(true);
        ax.setText(jogadorDaVez.getVetor().getAccel(0) + "");
        repaint();
    }

    public void diminuiY()
    {
        jogadorDaVez.getVetor().updateAccel(0, -1);
        ymenos.setEnabled(true);
        ymais.setEnabled(true);
        ay.setText(jogadorDaVez.getVetor().getAccel(1) + "");
        repaint();
    }

    public void jogar()
    {
        //atualiza os dados do vetor
        jogadorDaVez.getVetor().updateSpd();
        refreshPanel();
        //desenha o vetor
        areaJogo.desenhaVetor(quemJoga);
        refreshPanel();
               
        quemJoga++;
        if(quemJoga < players.size())
        {            
            jogadorDaVez = players.get(quemJoga);         
        }
        else{//volta ao primeiro jogador
            quemJoga = 0;
            jogadorDaVez = players.get(quemJoga);            
        }
        refreshPanel();
    }

    public void checkButtons()
    {
        permitirMudanca[0] = true;
        permitirMudanca[1] = true;
        permitirMudanca[2] = true;
        permitirMudanca[3] = true;
        xmenos.setEnabled(true);
        xmais.setEnabled(true);
        ymenos.setEnabled(true);
        ymais.setEnabled(true);

        if (jogadorDaVez.getVetor().getAccel(0) >= 1)
        {
            permitirMudanca[1] = false;
            xmais.setEnabled(false);
        }
        if (jogadorDaVez.getVetor().getAccel(0) <= -1)
        {
            permitirMudanca[0] = false;
            xmenos.setEnabled(false);
        }
        if (jogadorDaVez.getVetor().getAccel(1) <= -1)
        {
            permitirMudanca[2] = false;
            ymenos.setEnabled(false);
        }
        if (jogadorDaVez.getVetor().getAccel(1) >= 1)
        {
            permitirMudanca[3] = false;
            ymais.setEnabled(false);
        }
    }

    public void keyTyped(KeyEvent e)
    {
        if (e.getKeyChar() == KeyEvent.VK_ENTER)
        {
            jogar();
        }
        checkButtons();
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
           if (permitirMudanca[0]) { diminuiX(); }             
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if (permitirMudanca[1]) { aumentaX(); }             
        }
        else if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            if (permitirMudanca[3]) { aumentaY(); }
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if (permitirMudanca[2]) { diminuiY(); }
        }
        checkButtons();
    }

    public void keyReleased(KeyEvent e) {}
}
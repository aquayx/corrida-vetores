package cvClient.rede;

import cvClient.jogo.*;
import cvServer.*;
import generics.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class ClientLobby extends JFrame
{
    private Jogador j;
    private boolean host = false;

    private ServerMainWindow win;
    private ClientServerBridge csb;
    
    private LobbyPanel lp;

    // lista de jogadores conectados
    private ArrayList<Jogador> players;

    // construtor chamado a partir de um client
    public ClientLobby(Jogador j, ClientServerBridge csb, boolean host)
    {
        this.j = j;
        csb.setLobby(this);
        this.csb = csb;
        this.host = host;
        
        setSize(150, 300);
        setLayout(null);
        lp = new LobbyPanel(this);
        getContentPane().add(lp);

        setVisible(true);
    }

    public void setWin(ServerMainWindow win) { this.win = win; }
    public ArrayList<Jogador> getPlayerList() { return players; }
    public void setPlayerList(ArrayList<Jogador> players) { this.players = players; }
    public boolean getHost() { return host; }
    public ClientServerBridge getBridge() { return csb; }
    LobbyPanel getPanel() { return lp; }
}

class LobbyPanel extends JPanel implements ActionListener
{
    private ArrayList<JLabel> nickLabels = new ArrayList<JLabel>();
    private JButton iniciar;

    private Pista pis;

    private ClientLobby cl;

    public LobbyPanel(ClientLobby cl)
    {
        this.cl = cl;

        setSize(150, 300);
        setLayout(null);

        setVisible(true);
    }

    public void updateList()
    {
        removeAll();
        int ypos = 15;
        if (cl.getHost())
        {
            iniciar = new JButton("Iniciar!");
            iniciar.setBounds(10, ypos, 100, 20);
            ypos += 30; add(iniciar);
            iniciar.addActionListener(this);
        }
        JLabel info;
        if (cl.getPlayerList().size() == 1) { info = new JLabel("1 jogador conectado."); }
        else { info = new JLabel(cl.getPlayerList().size() + " jogadores conectados."); }
        
        info.setBounds(10, ypos, 200, 15); add(info); ypos += 25;
        for (int pos = 0; pos < cl.getPlayerList().size(); pos++)
        {
            JLabel label = new JLabel(cl.getPlayerList().get(pos).getNickname());
            label.setForeground(cl.getPlayerList().get(pos).getVetor().getColor());
            label.setBounds(10, ypos, 100, 15);
            ypos += 15;
            nickLabels.add(label);
            add(label);
        }
        repaint();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(iniciar))
        {
            setVisible(false);
            if (pedirPista())
            {
                sendPista();
            }
        }
    }
    
    public boolean pedirPista()
    {
        // o boolean é usado para ver se não deu nenhuma exception ou whatever no carregamento
        boolean success = false;
        JFileChooser chooser = new JFileChooser();
        PistaFileFilter ff = new PistaFileFilter();
        chooser.setFileFilter(ff);
        chooser.setDialogTitle("Abrir pista...");
        chooser.setApproveButtonText("Abrir");
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            pis = new Pista(file);
            success = true;
        }
        else
        {
            setVisible(true);
        }
        return success;
    }
    
    public boolean sendPista()
    {
        boolean success = false;
        try
        {
            // código para retransmitir pista
            cl.getBridge().getOutputStream().writeInt(1);
            for (int y = 0; y < pis.getYSize(); y++)
            {
                String line = "";
                for (int x = 0; x < pis.getXSize(); x++)
                {
                    line += String.valueOf(pis.getTile(x,y));
                }
                cl.getBridge().getOutputStream().writeUTF(line);
            }
        }
        catch (Exception fail)
        {
            Fail.swingFail("sendPista", "ClientLobby", fail);
        }
        return success;
    }
}
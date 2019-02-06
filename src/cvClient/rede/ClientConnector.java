package cvClient.rede;

import cvClient.menus.*;
import cvServer.ServerMainWindow;
import generics.*;
import java.net.*;
import javax.swing.*;

public class ClientConnector
{
    GameChoice gc;
    Jogador j;
    Socket sock;

    public ClientConnector(Jogador j) { this.j = j; }
    public ClientConnector(GameChoice gc, Jogador j)
    {
        this.gc = gc;
        this.j = j;
    }

    public boolean connect()
    {
        boolean success = false;
        String serverIP = JOptionPane.showInputDialog(gc, "Digite o IP do servidor:", "127.0.0.1");
        while (serverIP != (null) && !serverIP.equals("") && !success) // quitar quando ip for vazio ou conseguir o sock
        {
            try
            {
                sock = new Socket(serverIP, 6969);
                ClientServerBridge csb = new ClientServerBridge(sock, j);
                csb.start();
                ClientLobby cl = new ClientLobby(j, csb, false);
                success = true;
            }
            catch (Exception e)
            {
                serverIP = JOptionPane.showInputDialog(gc, "Exception - " + e + "\nNão foi possível conectar-se ao servidor. Digite um novo endereço:");
            }
        }
        return success;
    }
    
    // connect a si mesmo
    public void connect(String serverIP, ServerMainWindow smw)
    {
        try
        {
            sock = new Socket(serverIP, 6969);
            ClientServerBridge csb = new ClientServerBridge(sock, j);
            csb.start();
            // aguardar no lobby como host
            ClientLobby cl = new ClientLobby(j, csb, true);
            cl.setWin(smw); // para poder dispose a janela quando o jogo iniciar
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Exception - " + e + "\nNão foi possível conectar-se ao próprio servidor.", "Fail", 0);
        }
    }
}
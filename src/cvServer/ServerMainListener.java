package cvServer;

import generics.*;
import java.io.*; // streams
import java.net.*; // sockets
import java.util.*;

public class ServerMainListener extends Thread
{
    boolean stayAlive = true;
    ArrayList<Socket> clients = new ArrayList<Socket>();
    ArrayList<Jogador> players = new ArrayList<Jogador>();
    Socket sock;

    DataInputStream dis;
    DataOutputStream dos;
    
    ServerMainWindow win; // para associação
    
    public ServerMainListener(ServerMainWindow win)
    {
        this.win = win;
    }
    
    @Override public void run()
    {
        try
        {
            ServerSocket serverSock = new ServerSocket(6969);
            while (stayAlive)
            {
                sock = serverSock.accept();
                dis = new DataInputStream(sock.getInputStream());
                dos = new DataOutputStream(sock.getOutputStream());

                // adiciona ao vetor de clients conectados
                clients.add(sock);

                // envia uma resposta ao cliente solicitando um nick no construtor da ServerClientBridge.
                // (comunica-se com a parte bridge do client)
                Thread thisClientBridge = new ServerClientBridge(sock, win, this);
                thisClientBridge.start();
            }
        }
        catch (Exception fail)
        {
            Fail.swingFail("run", "ServerMainListener", fail);
        }
    }
    
    public ArrayList<Socket> getSocketList() { return clients; }
    public ArrayList<Jogador> getPlayerList() { return players; }
    public void addToPlayers(Jogador j) { players.add(j); }
}
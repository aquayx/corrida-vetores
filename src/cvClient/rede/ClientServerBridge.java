package cvClient.rede;

import generics.*;
import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientServerBridge extends Thread
{
    Jogador j;
    Pista p;

    DataInputStream dis;
    DataOutputStream dos;
    int actionCode;
    boolean stayAlive = true;
    
    ClientLobby cl;
    
    public ClientServerBridge(Socket sock, Jogador j)
    {
        this.j = j;

        try
        {
            dis = new DataInputStream(sock.getInputStream());
            dos = new DataOutputStream(sock.getOutputStream());
        }
        catch (Exception fail)
        {
            Fail.swingFail("construtor", "ClientServerBridge", fail);
        }
    }

    @Override public void run()
    {
        try
        {
            while (stayAlive)
            {
                actionCode = dis.readInt();

                // over 9000 cases abaixo
                switch(actionCode)
                {
                    // usuário acabou de conectar-se ao servidor. enviar seu nick e cor do vetor
                    case 0:
                    {
                        dos.writeInt(0);
                        dos.writeUTF(j.getNickname());
                        // enviar cor pelos três componentes
                        dos.writeUTF("" + j.getVetor().getColor().getRed());
                        dos.writeUTF("" + j.getVetor().getColor().getGreen());
                        dos.writeUTF("" + j.getVetor().getColor().getBlue());
                        break;
                    }
                    // recebendo nicknames para a espera no lobby
                    case 1:
                    {
                        // recebe "dis.readInt()" jogadores e adiciona num arraylist
                        ArrayList<Jogador> players = new ArrayList<Jogador>();
                        int numPlayers = dis.readInt();
                        for (int pos = 0; pos < numPlayers; pos++)
                        {
                            // receber nicks
                            String nickname = dis.readUTF();
                            // receber cores
                            int red = Integer.parseInt(dis.readUTF().trim());
                            int green = Integer.parseInt(dis.readUTF());
                            int blue = Integer.parseInt(dis.readUTF());
                            Color vetColor = new Color(red, green, blue);
                            players.add(new Jogador(nickname, vetColor));
                        }
                        // atualiza o lobby
                        while (cl == null) { Thread.sleep(100); }
                        cl.setPlayerList(players);
                        cl.getPanel().updateList();
                        break;
                    }
                    
                    // recebe pista do server e cria um jogo propriamente dito
                    case 2:
                    {
                        p = new Pista();
                        for (int y = 0; y < p.getYSize(); y++)
                        {
                            String line = dis.readUTF();
                            char[] linha = line.toCharArray();
                            for (int xPos = 0; xPos < linha.length; xPos++)
                            {
                                p.setTile(xPos, y, linha[xPos]);
                            }
                        }
                        GameEngine ge = new GameEngine(p, cl.getPlayerList());
                        p.mostraPistaConsole();
                        // inicializar janela com a ge... etc >.>
                        break;
                    }
                }
            }
        }
        catch (Exception fail)
        {
            Fail.swingFail("run", "ClientServerBridge", fail);
            fail.printStackTrace();
        }
    }
    
    public DataOutputStream getOutputStream() { return dos; }
    public void setLobby(ClientLobby cl) { this.cl = cl; }
}
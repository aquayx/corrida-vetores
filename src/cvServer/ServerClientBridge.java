package cvServer;

import generics.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class ServerClientBridge extends Thread
{
    Socket sock;
    DataInputStream dis;
    DataOutputStream dos;
    int actionCode;
    boolean stayAlive = true;
    
    Pista p;

    // associações
    ServerMainWindow win;
    ServerMainListener sml;
    
    public ServerClientBridge(Socket sock, ServerMainWindow win, ServerMainListener sml)
    {
        try
        {
            this.win = win;
            this.sock = sock;
            this.sml = sml;

            dis = new DataInputStream(sock.getInputStream());
            dos = new DataOutputStream(sock.getOutputStream());
            dos.writeInt(0);
        }
        catch (Exception fail)
        {
            Fail.swingFail("construtor", "ServerClientBridge", fail);
        }
    }
    
    // função que envia um codigo para todos os usuários conectados
    public void enviaCodigo(String send, boolean isInt)
    {
        // um outro outputstream
        DataOutputStream allOut;
        for (int pos = 0; pos < sml.getSocketList().size(); pos++)
        {
            try
            {
                allOut = new DataOutputStream(sml.getSocketList().get(pos).getOutputStream());
                if (isInt) { allOut.writeInt(Integer.parseInt(send)); }
                else { allOut.writeUTF(send); }
            }
            catch (Exception fail)
            {
                Fail.swingFail("enviaCodigo", "ServerClientBridge", fail);
            }
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
                    // ações básicas do momento de connect
                    case 0:
                    {
                        String userNick = dis.readUTF();
                        win.appendToLog(userNick + " conectou-se ao servidor!");

                        // lendo cor do vetor do usuário
                        int red = Integer.parseInt(dis.readUTF());
                        int green = Integer.parseInt(dis.readUTF());
                        int blue = Integer.parseInt(dis.readUTF());
                        Color vetColor = new Color(red, green, blue);

                        Jogador j = new Jogador(userNick, vetColor);
                        sml.addToPlayers(j);

                        refreshAllLobbies();
                        break;
                    }
                    // recebe e envia a pista para todos os clients
                    case 1:
                    {
                        // recebendo
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

                        // enviando
                        enviaCodigo("2", true);
                        for (int y = 0; y < p.getYSize(); y++)
                        {
                            String line = "";
                            for (int x = 0; x < p.getXSize(); x++)
                            {
                                line += String.valueOf(p.getTile(x,y));
                            }
                            enviaCodigo(line, false);
                        }
                        break;
                    }
                }
            }
        }
        catch (Exception fail)
        {
            Fail.swingFail("run", "ServerClientBridge", fail);
            fail.printStackTrace();
        }
    }

    public void refreshAllLobbies()
    {
        enviaCodigo("1", true);

        // preparar o client para receber "size" nicks
        enviaCodigo("" + sml.getPlayerList().size(), true);

        for (int pos = 0; pos < sml.getPlayerList().size(); pos++) {
        // enviar nicks
        enviaCodigo(sml.getPlayerList().get(pos).getNickname(), false);
        // enviar cores
        enviaCodigo("" + sml.getPlayerList().get(pos).getVetor().getColor().getRed(), false); 
        enviaCodigo("" + sml.getPlayerList().get(pos).getVetor().getColor().getGreen(), false); 
        enviaCodigo("" + sml.getPlayerList().get(pos).getVetor().getColor().getBlue(), false); }
    }
}
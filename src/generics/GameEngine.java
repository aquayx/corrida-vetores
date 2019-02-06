package generics;

import java.util.*;

public class GameEngine
{
    private Pista p;
    private ArrayList<Jogador> players;    

    public GameEngine(Pista p, ArrayList<Jogador> players)
    {
        this.p = p;
        this.players = players;

        // cada jogador tem seu próprio array de checkpoints
        for(int cont = 0; cont < players.size(); cont ++)
        {
            players.get(cont).setCheck(p.getChecks());
        }
    }

    public void setPosIniciais() // define todas as posições iniciais
    {
        int pos = 0;
        for(int cont = 0; cont < players.size(); cont ++)
        {
            players.get(cont).getVetor().setPos(p.getPosInicial().get(pos),-p.getPosInicial().get(pos+1));
            pos += 2;
        }
    }
    public ArrayList<Jogador> getPlayers()
    {
        return players;
    }
    
    public Pista getPista()
    {
        return p;
    }
}

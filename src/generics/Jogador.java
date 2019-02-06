package generics;

import java.awt.*;
import java.util.*;

public class Jogador
{
    private String nickname;
    private Vetor meuVetor;
    private ArrayList<Integer>[] checkList;
    private int turno = 0;

    public Jogador()
    {
        nickname = "Jogador " + (int) (Math.random() * 69 + 1);
        meuVetor = new Vetor(this);
    }
    
    public Jogador(String nickname, Color vetColor)
    {
        this.nickname = nickname;
        meuVetor = new Vetor(this, vetColor);
    }
    
    public void turnoMais() { turno++; }
    
    // gets e sets
    public int getTurno() { return turno; }
    public void setCheck(ArrayList<Integer>[] checkList) { this.checkList = checkList; }
    public ArrayList<Integer>[] getCheck() { return checkList; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public Vetor getVetor() { return meuVetor; }
}
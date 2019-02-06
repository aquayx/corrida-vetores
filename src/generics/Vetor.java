package generics;

import java.awt.*;

public class Vetor
{
    // acelerações e velocidades como vetores bidimensionais,
    // representando posição x e posição y
    // (0=x, 1=y)
    private int[] a = {0,0};
    private int[] v = {0,0};
    
    private int[] pos = new int[2]; // posições x, y do vetor
    
    Color cor;
    Jogador meuDono;

    public Vetor(Jogador jogador)
    {
        // sortear uma cor
        int randomColor = (int)(Math.random()*7);
        switch (randomColor)
        {
            case 0: cor = Color.RED; break;
            case 1: cor = Color.BLUE; break;
            case 2: cor = Color.GREEN; break;
            case 3: cor = Color.MAGENTA; break;
            case 4: cor = Color.ORANGE; break;
            case 5: cor = Color.CYAN; break;
            case 6: cor = Color.DARK_GRAY; break;
        }
        meuDono = jogador;        
    }
    
    public Vetor (Jogador jogador, Color cor)
    {
        meuDono = jogador;
        this.cor = cor;
    }
    
    // métodos de alterar velocidade e aceleração
    public void updateSpd() // aumenta as velocidades do vetor de acordo com suas acelerações
    {
        v[0] = v[0]+a[0];
        v[1] = v[1]+a[1];
    }

    public void updateAccel(int x, int y) // aumenta a aceleração do vetor nos eixos. os valores recebidos podem ser -1, 0, 1
    {
        a[0] = a[0]+x; // eixo x
        a[1] = a[1]+y; // eixo y
    }

    public void zeraSpd() // zera as acelerações e as velocidades
    {
        a[0] = 0; a[1] = 0; // acelerações x, y
        v[0] = 0; v[1] = 0; // velocidades x, y
    }

    public void move() // move o vetor
    {
        pos[0] = pos[0]+v[0]; // posição final x = posição atual x + velocidade x
        pos[1] = pos[1]+v[1]; // posição final y = posição atual y + velocidade y
    }
    
    public double moduloV()
    {
        double m = 0;
        m += Math.pow(v[0], 2); // elevando termo x ao quadrado
        m += Math.pow(v[1], 2); // elevando termo y ao quadrado
        m = Math.sqrt(m);
        return m;
    }
    
    // alguns gets e sets
    public void setPos(int xPos, int yPos) // setX e setY (usar quando colocar o vetor na pista)
    { pos[0] = xPos; pos[1] = yPos; }

    public void setColor(Color cor)
    { this.cor = cor; }

    public Color getColor()
    { return cor; }
    
    public int[] getPos()
    { return pos; }
    
    public int[] getSpd()
    { return v; }
    
    public int getAccel(int eixo)
    { return a[eixo]; }
}
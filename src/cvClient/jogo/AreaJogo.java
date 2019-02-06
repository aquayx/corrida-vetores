package cvClient.jogo;

import generics.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class AreaJogo extends JPanel
{
    private static final int ALTURA = 420;
    private static final int LARGURA = 600;
    private ArrayList<Integer>[] posVetX;
    private ArrayList<Integer>[] posVetY;
    private Jogador jogadorDaVez;
    private Pista p;
    private boolean bateu = false;
    private GameEngine ge;
    private double m; // coeficiente angular
    private int xi, xf, yi, yf, xlinha; // pontos finais e iniciais de um movimento
    private double ylinha;
    private boolean goOn; // continuar conferindo colisões - "break" alternativo
    private int quemJoga;
    private String result = "";

    private ImageIcon fundoPistaIcon, fundoLargadaIcon, fundoNadaIcon, check1Icon, check2Icon, check3Icon, check4Icon, check5Icon, check6Icon, check7Icon;
    private Image fundoPista, fundoLargada, fundoNada, check1, check2, check3, check4, check5, check6, check7;

    public AreaJogo(GameEngine ge)
    {
        this.p = ge.getPista();
        this.ge = ge;
        quemJoga = 0;
        jogadorDaVez = ge.getPlayers().get(quemJoga);
        setBounds(15, 15, 405, 575);
        setSize(LARGURA + 1, ALTURA + 1);
        setVisible(true);
        posVetX = new ArrayList[ge.getPlayers().size()];
        posVetY = new ArrayList[ge.getPlayers().size()];  

        //adiciona as primeiras posicoes
        for(int cont = 0; cont < ge.getPlayers().size(); cont ++)
        {
            posVetX[cont] = new ArrayList();
            posVetY[cont] = new ArrayList();
            posVetX[cont].add(ge.getPlayers().get(cont).getVetor().getPos()[0] * 15);
            posVetY[cont].add(ge.getPlayers().get(cont).getVetor().getPos()[1] * 15);
            posVetX[cont].add(ge.getPlayers().get(cont).getVetor().getPos()[0] * 15);
            posVetY[cont].add(ge.getPlayers().get(cont).getVetor().getPos()[1] * 15);
        }
        loadImages();
    }

    @Override public void paint(Graphics g)
    {
        desenharFundo(g);
    }

    public void desenharFundo(Graphics g)
    {
        super.paint(g);
        drawEverything(g);
        if (bateu)
        {            
            ge.getPlayers().get(quemJoga).getVetor().setPos(((Integer) posVetX[quemJoga].get(posVetX[quemJoga].size() - 2)) / 15, ((Integer) posVetY[quemJoga].get(posVetY[quemJoga].size() - 2)) / 15);
        }
        repaint();
    }

    public void desenhaVetor(int quemJoga)
    {
        this.quemJoga = quemJoga;
        ge.getPlayers().get(quemJoga).turnoMais();//aumenta o turno
        bateu = false;
        xi = ge.getPlayers().get(quemJoga).getVetor().getPos()[0] * 15;
        yi = -ge.getPlayers().get(quemJoga).getVetor().getPos()[1] * 15;

        posVetX[quemJoga].add(xi);
        posVetY[quemJoga].add(-yi);

        ge.getPlayers().get(quemJoga).getVetor().move();

        xf = ge.getPlayers().get(quemJoga).getVetor().getPos()[0] * 15;
        yf = -ge.getPlayers().get(quemJoga).getVetor().getPos()[1] * 15;
        posVetX[quemJoga].add(xf);
        posVetY[quemJoga].add(-yf);
        
        // centralização na tile
        xi = xi + 7;
        xf = xf + 7;
        yi = yi + 7;
        yf = yf + 7;
       
        goOn = true;
        
        if ((int) (xf/15) < 40 && (int) (yf/15) < 28 && (int) (xf/15) >= 0 && (int) (yf/15) >= 0) // in bounds
        {
            if ((xi - xf) != 0) // já que é impossivel dividir por zero
            {              
                m = (double)(yi-yf)/(xi-xf);
                if (xi < xf)
                {
                    for (xlinha = xi; xlinha <= xf & goOn; xlinha++)
                    {                        
                       ylinha = ((m * xlinha) - (m * xi) + yi); collisionCheck(xlinha,ylinha); }
                }
                else
                {
                    for (xlinha = xf; xlinha <= xi && goOn; xlinha++)
                    {
                        ylinha = ((m * xlinha) - (m * xi) + yi);collisionCheck(xlinha,ylinha); }
                }
            }
            else
            {
                // verifica colisões verticais
                if (yi < yf)
                {
                    for (ylinha = yi; ylinha <= yf && goOn; ylinha++)
                    { collisionCheck(xi,ylinha); }
                }
                else
                {
                    for (ylinha = yf; ylinha <= yi && goOn; ylinha++)
                    { collisionCheck(xi,ylinha); }
                }
            }
            repaint();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Você bateu fora das bordas da pista!", "Hardcore", 0);
            ge.getPlayers().get(quemJoga).getVetor().zeraSpd();
            ge.getPlayers().get(quemJoga).getVetor().setPos(((Integer) posVetX[quemJoga].get(posVetX[quemJoga].size() - 2)) / 15, ((Integer) posVetY[quemJoga].get(posVetY[quemJoga].size() - 2)) / 15);
        }
    }

    public void collisionCheck(double x, double y)
    {
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '0')
        {
            ge.getPlayers().get(quemJoga).getVetor().zeraSpd();
            JOptionPane.showMessageDialog(null, "Você bateu...", "Miss", 0);
            bateu = true;
            goOn = false;
            return;
        }
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '3') { ge.getPlayers().get(quemJoga).getCheck()[0].clear(); }
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '4' && ge.getPlayers().get(quemJoga).getCheck()[0].isEmpty()) { ge.getPlayers().get(quemJoga).getCheck()[1].clear(); }
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '5' && ge.getPlayers().get(quemJoga).getCheck()[1].isEmpty()) { ge.getPlayers().get(quemJoga).getCheck()[2].clear(); }
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '6' && ge.getPlayers().get(quemJoga).getCheck()[2].isEmpty()) { ge.getPlayers().get(quemJoga).getCheck()[3].clear(); }
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '7' && ge.getPlayers().get(quemJoga).getCheck()[3].isEmpty()) { ge.getPlayers().get(quemJoga).getCheck()[4].clear(); }
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '8' && ge.getPlayers().get(quemJoga).getCheck()[4].isEmpty()) { ge.getPlayers().get(quemJoga).getCheck()[5].clear(); }
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '9' && ge.getPlayers().get(quemJoga).getCheck()[5].isEmpty()) { ge.getPlayers().get(quemJoga).getCheck()[6].clear(); }
        if (p.getTile((int)((x / 15)), (int)(y / 15)) == '2')
        {
            boolean win = true;
            for (int pos = 0; pos < 7; pos++)
            { if (!ge.getPlayers().get(quemJoga).getCheck()[pos].isEmpty()) { win = false; } }
            
            if (win && ge.getPlayers().size() == 1)
            {
                 result = result + "O jogador " + ge.getPlayers().get(quemJoga).getNickname() + " completou a corrida em " + ge.getPlayers().get(quemJoga).getTurno() + " turnos.\n";
                 JOptionPane.showMessageDialog(null, result, "Win", 1, null);
                 goOn = false;
                 return;
                 //acabar a corrida
            }
            else if (win)
            {
                result = result + "O jogador "+ ge.getPlayers().get(quemJoga).getNickname() + " completou a corrida em " + ge.getPlayers().get(quemJoga).getTurno()+ " turnos.\n";
                ge.getPlayers().remove(quemJoga);
                posVetX[quemJoga].clear();
                goOn = false;
                return;
            }
        }
    }
    
    public void loadImages() // carrega as imagens usando ImageIcons
    {
        fundoPistaIcon = new ImageIcon("imagens\\pista.png");
        fundoLargadaIcon = new ImageIcon("imagens\\largada.png");
        fundoNadaIcon = new ImageIcon("imagens\\nada.png");
        check1Icon = new ImageIcon("imagens\\check1.png");
        check2Icon = new ImageIcon("imagens\\check2.png");
        check3Icon = new ImageIcon("imagens\\check3.png");
        check4Icon = new ImageIcon("imagens\\check4.png");
        check5Icon = new ImageIcon("imagens\\check5.png");
        check6Icon = new ImageIcon("imagens\\check6.png");
        check7Icon = new ImageIcon("imagens\\check7.png");

        fundoPista = fundoPistaIcon.getImage();
        fundoLargada = fundoLargadaIcon.getImage();
        fundoNada = fundoNadaIcon.getImage();
        check1 = check1Icon.getImage();
        check2 = check2Icon.getImage();
        check3 = check3Icon.getImage();
        check4 = check4Icon.getImage();
        check5 = check5Icon.getImage();
        check6 = check6Icon.getImage();
        check7 = check7Icon.getImage();
    }
    
    public void drawEverything(Graphics g)
    {
        int posX1 = 0;
        int posY1 = 0;

        for (int x = 0; x < 40; x++)
        {
            for (int y = 0; y < 28; y++)
            {
                if (p.getTile(x, y) == '0') { g.drawImage(fundoNada, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '1') { g.drawImage(fundoPista, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '2') { g.drawImage(fundoLargada, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '3') { g.drawImage(check1, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '4') { g.drawImage(check2, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '5') { g.drawImage(check3, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '6') { g.drawImage(check4, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '7') { g.drawImage(check5, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '8') { g.drawImage(check6, x * 15, y * 15, null); }
                if (p.getTile(x, y) == '9') { g.drawImage(check7, x * 15, y * 15, null); }
            }
        }

        //desenha as linhas.
        //linhas verticais
        for (int posXi = posX1; posXi <= LARGURA; posXi = posXi + 15)
        {
            g.drawLine(posXi, posY1, posXi, ALTURA);
        }

        //linhas horizontais
        for (int posYi = posY1; posYi <= ALTURA; posYi = posYi + 15)
        {
            g.drawLine(posX1, posYi, LARGURA, posYi);
        }

        //desenha os vetores
        for(int cont1 = 0; cont1 < posVetX.length; cont1++)
        {
            for (int cont = 0; cont < posVetX[cont1].size() - 1; cont++)
            {
                try
                {
                    g.setColor(ge.getPlayers().get(cont1).getVetor().getColor());
                    g.drawLine(((Integer) (posVetX[cont1].get(cont))) + 7, -((Integer) posVetY[cont1].get(cont)) + 7, ((Integer) posVetX[cont1].get(cont + 1)) + 7, -((Integer) posVetY[cont1].get(cont + 1)) + 7);
                    g.fillOval((Integer) (posVetX[cont1].get(cont + 1) - 3) + 7, -((Integer) posVetY[cont1].get(cont + 1) - 3), 7, 7);
                }
                catch (Exception e) {}
            }
        }
    }
}
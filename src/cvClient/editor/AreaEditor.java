package cvClient.editor;

import generics.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class AreaEditor extends JPanel implements MouseListener, MouseMotionListener
{
    private Pista p;
    private static final int ALTURA = 420;
    private static final int LARGURA = 600;
    private char acao = '1'; // 0 = não pista, 1 = pista, 2 = linha de partida, 3-9 = checkpoints

    // objetos image e imageicon
    private ImageIcon fundoPistaIcon, fundoLargadaIcon, fundoNadaIcon, check1Icon, check2Icon, check3Icon, check4Icon, check5Icon, check6Icon, check7Icon;
    private Image fundoPista, fundoLargada, fundoNada, check1, check2, check3, check4, check5, check6, check7;

    public AreaEditor(Pista p)
    {
        this.p = p;
        setBounds(15, 15, 405, 575);
        setSize(LARGURA + 1, ALTURA + 1);
        addMouseListener(this);
        addMouseMotionListener(this);
        setVisible(true);
        
        loadImages();
    }

    @Override public void paint(Graphics g)
    {
        desenharFundo(g);
    }

    public void desenharFundo(Graphics g)
    {
        super.paint(g);
        int posX1 = 0;
        int posY1 = 0;

        for (int x = 0; x < 40; x++)
        {
            for (int y = 0; y < 28; y++)
            {
                if (p.getTile(x, y) == '0') { g.drawImage(fundoNada, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '1') { g.drawImage(fundoPista, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '2') { g.drawImage(fundoLargada, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '3') { g.drawImage(check1, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '4') { g.drawImage(check2, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '5') { g.drawImage(check3, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '6') { g.drawImage(check4, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '7') { g.drawImage(check5, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '8') { g.drawImage(check6, x * 15, y * 15, null); }
                else if (p.getTile(x, y) == '9') { g.drawImage(check7, x * 15, y * 15, null); }
            }
        }
        
        // desenha as linhas.
        // linhas verticais
        for (int posXi = posX1; posXi <= LARGURA; posXi = posXi + 15)
        {
            g.drawLine(posXi, posY1, posXi, ALTURA);
        }
        // linhas horizontais
        for (int posYi = posY1; posYi <= ALTURA; posYi = posYi + 15)
        {
            g.drawLine(posX1, posYi, LARGURA, posYi);
        }
    }

    public void setAcao(char acao)
    {
        this.acao = acao;
    }
    
    public char getAcao()
    {
        return acao;
    }

    public Pista getPista()
    {
        return p;
    }

    public void setPista(Pista p)
    {
        this.p = p;        
        repaint();
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

    public void mousePressed(MouseEvent e) { drawTile(e); }
    public void mouseDragged(MouseEvent e) { drawTile(e); }
    
    public void drawTile(MouseEvent e)
    {
        int posX = (int)(e.getX() / 15);
        int posY = (int)(e.getY() / 15);
        if (posX < 40 && posY < 28 && posX >= 0 && posY >= 0) // in bounds
        { p.setTile(posX, posY, acao); }
        repaint();
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
}

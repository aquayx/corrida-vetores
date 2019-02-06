package cvClient.menus;

import cvClient.jogo.*;
import cvClient.rede.*;
import cvServer.*;
import generics.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.*;

// contém uma classe ChoicePanel que contém o conteúdo da janela

public class GameChoice extends JFrame implements MouseListener
{
    private Jogador j;
    private ArrayList<Jogador> jogadores;
    private int nJogadores;
    private Pista pis;

    private static final int[] LOCAL_POS = { 19, 15, 206, 56 };
    private static final int[] MULTI_POS = { 19, 64, 206, 105 };
    private static final int[] VOLTAR_POS = { 143, 113, 200, 134 };

    public GameChoice(Jogador j)
    {
        setTitle("Escolher Modo");
        this.j = j;

        setSize(225, 150);
        setUndecorated(true);
        setLocation(450, 300);
        ChoicePanel cp = new ChoicePanel(this);
        getContentPane().add(cp);
    }

    public void mouseClicked(MouseEvent e) { checkClick(e); }
    public void mouseReleased(MouseEvent e) { checkClick(e); }
    
    public void checkClick(MouseEvent e)
    {
        int xMouse = e.getX();
        int yMouse = e.getY();

        // local button
        if (xMouse >= LOCAL_POS[0] && xMouse <= LOCAL_POS[2] && yMouse >= LOCAL_POS[1] && yMouse <= LOCAL_POS[3])
        { localActions(); }

        // multi button
        else if (xMouse >= MULTI_POS[0] && xMouse <= MULTI_POS[2] && yMouse >= MULTI_POS[1] && yMouse <= MULTI_POS[3])
        { multiplayerActions(); }

        // voltar button
        if (xMouse >= VOLTAR_POS[0] && xMouse <= VOLTAR_POS[2] && yMouse >= VOLTAR_POS[1] && yMouse <= VOLTAR_POS[3])
        { voltar(); }
    }
    
    public void localActions()
    {
        setVisible(false);
        if (pedirPista())
        {
            try { nJogadores = Integer.parseInt(JOptionPane.showInputDialog("Número de players:")); }
            catch (Exception fail) { Fail.input(fail); }
            jogadores = new ArrayList<Jogador>();
            jogadores.add(j); // add o player um
            for (int cont = 0; cont < nJogadores-1; cont++) { jogadores.add(new Jogador()); } // add os outros
            GameEngine ge = new GameEngine(pis, jogadores);            
            Janela jogo = new Janela(ge);
        }
    }
    
    public boolean pedirPista()
    {
        // o boolean é usado para ver se não deu nenhuma exception ou whatever no carregamento
        boolean success = false;
        JFileChooser chooser = new JFileChooser();
        FileFilter ff = new PistaFileFilter();
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

    public void multiplayerActions()
    {
        boolean validInput = false;
        setVisible(false);
        String multiChoice = JOptionPane.showInputDialog(this, "Deseja ser host? (S/N)\nDeixar em branco para voltar ao menu.", "S");
        while (multiChoice != (null) && !multiChoice.equals("") && !validInput)
        {
            if (multiChoice.equalsIgnoreCase("s"))
            {
                validInput = true;
                ServerMainWindow win = new ServerMainWindow(j);
                win.setVisible(true);
            }
            else if (multiChoice.equalsIgnoreCase("n"))
            {
                validInput = true;
                ClientConnector con = new ClientConnector(this, j);
                if (!con.connect()) { setVisible(true); }
            }
            else
            {
                multiChoice = JOptionPane.showInputDialog(this, "Entrada inválida.\nDigite \"S\" para ser host ou \"N\" em caso contrário.\nDeixar em branco para voltar ao menu.");
            }
        }
        if (!validInput) // null, mostrar de novo o menu
        {
            setVisible(true);
        }
    }
    
    public void voltar()
    {
        dispose();
        Menu m = new Menu(j);
    }

    // lol interface
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

class ChoicePanel extends JPanel
{
    public ChoicePanel(GameChoice gc)
    {
        setSize(225, 150);
        addMouseListener(gc);
    }
    
    @Override public void paint(Graphics g)
    {
        setChoices(g);
    }
    
    public void setChoices(Graphics g)
    {
        // carrega a imagem de escolhas com um ImageIcon
        ImageIcon choicesIcon = new ImageIcon("imagens\\escolha.png");
        Image choices = choicesIcon.getImage();
        
        g.drawImage(choices, 0, 0, 225, 150, null, null);
    }
}
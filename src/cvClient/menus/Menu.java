package cvClient.menus;

import cvClient.editor.*;
import generics.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Menu extends JFrame implements MouseListener
{
    private Jogador j;
    private MainPanel mp;
    
    // posições dos botões
    private static final int[] START_POS = { 19, 168, 228, 226 };
    private static final int[] INSTRUCOES_POS = { 18, 230, 227, 289 };
    private static final int[] CONFIG_POS = { 17, 226, 292, 350 };
    private static final int[] EXIT_POS = { 160, 361, 239, 386 };

    public Menu(Jogador j)
    {
        setTitle("Corrida de Vetores");
        this.j = j;

        setSize(250, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(470, 150);
        setUndecorated(true);

        mp = new MainPanel(this);
        getContentPane().add(mp);

        setVisible(true);
    }

    public void mouseClicked(MouseEvent e) { checkClick(e); }
    public void mouseReleased(MouseEvent e) { checkClick(e); }

    public void checkClick(MouseEvent e)
    {
        int xMouse = e.getX();
        int yMouse = e.getY();

        // start
        if (xMouse >= START_POS[0] && xMouse <= START_POS[2] && yMouse >= START_POS[1] && yMouse <= START_POS[3])
        {
            dispose();
            GameChoice escolha = new GameChoice(j);
            escolha.setVisible(true);
        }

        // instruções
        else if (xMouse >= INSTRUCOES_POS[0] && xMouse <= INSTRUCOES_POS[2] && yMouse >= INSTRUCOES_POS[1] && yMouse <= INSTRUCOES_POS[3])
        {
            InstrucoesPanel ip = new InstrucoesPanel(this, mp);
            getContentPane().remove(mp);
            getContentPane().add(ip);
            repaint();
        }
        
        // configurações
        else if (xMouse >= CONFIG_POS[0] && xMouse <= CONFIG_POS[2] && yMouse >= CONFIG_POS[1] && yMouse <= CONFIG_POS[3])
        {
            OptionsPanel op = new OptionsPanel(this, mp, j);
            getContentPane().remove(mp);
            getContentPane().add(op);
            repaint();
        }

        else if (xMouse >= EXIT_POS[0] && xMouse <= EXIT_POS[2] && yMouse >= EXIT_POS[1] && yMouse <= EXIT_POS[3])
        {
            int sair = JOptionPane.showConfirmDialog(this, "Deseja mesmo sair?", "Sair", 1, 3, null);
            if (sair == 0)
            {
                System.exit(0);
            }
        }
    }
    
    // lol interface
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

// painel principal (start/instruções/opções/quit)
class MainPanel extends JPanel
{
    public MainPanel(Menu m)
    {
        setSize(250, 400);
        addMouseListener(m);
    }

    @Override public void paint(Graphics g)
    {
        setLogo(g);
    }

    public void setLogo(Graphics g)
    {
        // carrega a imagem de m inicial com um ImageIcon
        ImageIcon opIcon = new ImageIcon("imagens\\op.png");
        Image op = opIcon.getImage();
        
        g.drawImage(op, 0, 0, 250, 400, null, null);
    }
}

class InstrucoesPanel extends JPanel implements ActionListener
{
    private Menu m;
    private MainPanel mp;
    
    private JButton voltar;
    
    public InstrucoesPanel(Menu m, MainPanel mp)
    {
        this.m = m;
        this.mp = mp;
        
        setSize(250, 400);
        setLayout(null);
        
        JTextArea instrucoes = new JTextArea();

        instrucoes.setEditable(false);
        instrucoes.setLineWrap(true);
        instrucoes.setWrapStyleWord(true);
        instrucoes.setText("Regras básicas da Corrida de Vetores:\n" +
                    "1 - A cada turno o jogador poderá aumentar ou diminuir sua velocidade em 1 em cada um dos eixos, independentemente.\n" +
                    "2 - Se o vetor sair da pista, ou colidir com outro vetor, ele terá suas velocidades zeradas.\n" +
                    "3 - Ganha aquele que der uma volta na pista primeiro.\n" +
                    "4 - Em caso de empate, ganha aquele que \"chegar mais longe\".\n"
                    );
        instrucoes.setBounds(25, 70, 190, 230);
        instrucoes.setAutoscrolls(true);

        ScrollPane sp = new ScrollPane();
        sp.add(instrucoes);
        sp.setBounds(10, 70, 220, 260);

        voltar = new JButton("Voltar");
        voltar.setBounds(165, 330, 70, 35);
        voltar.addActionListener(this);

        add(sp);
        add(voltar);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(voltar))
        {
            m.getContentPane().remove(this);
            m.getContentPane().add(mp);
            m.repaint();
        }
    }
    
}

class OptionsPanel extends JPanel implements ActionListener
{
    private Menu m;
    private MainPanel mp; // para poder adicioná-lo novamente depois
    private Jogador j;

    private JLabel nickLabel, corLabel;
    private JTextField nickF;
    private JButton changeColor, voltar, editor;
    private Color vetColor;

    private JLabel title;
    
    public OptionsPanel(Menu m, MainPanel mp, Jogador j)
    {
        this.m = m;
        this.mp = mp;
        this.j = j;

        setSize(250, 400);
        setLayout(null);

        nickLabel = new JLabel("Nick:");
        nickLabel.setBounds(30, 160, 70, 30);
        nickF = new JTextField(j.getNickname());
        nickF.setEditable(true);
        nickF.setBounds(80, 160, 140, 30);

        corLabel = new JLabel("Cor:");
        corLabel.setBounds(30, 210, 70, 30);

        changeColor = new JButton();
        changeColor.setBounds(80, 210, 140, 30);
        changeColor.setBackground(j.getVetor().getColor());
        changeColor.addActionListener(this);

        Font fonte = new Font("Tahoma", 0, 30);
        title = new JLabel("Opções");
        title.setFont(fonte);
        title.setBounds(25, 25, 100, 50);

        editor = new JButton("Editor de Pistas");
        editor.setBounds(80, 250, 140, 30);
        editor.addActionListener(this);

        voltar = new JButton("Voltar");
        voltar.setBounds(165, 330, 70, 35);
        voltar.addActionListener(this);
        
        add(editor);
        add(changeColor);
        add(nickF);
        add(nickLabel);
        add(corLabel);
        add(voltar);
        add(title);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(changeColor))
        {
            vetColor = j.getVetor().getColor();
            j.getVetor().setColor(JColorChooser.showDialog(this, "Escolha uma cor...", j.getVetor().getColor()));
            if (j.getVetor().getColor() == null)
            {
                j.getVetor().setColor(vetColor);
            }
            changeColor.setBackground(j.getVetor().getColor());
            repaint();
        }
        
        if (e.getSource().equals(editor))
        {
            Editor edit = new Editor(m);
            m.setVisible(false);
        }
        
        if (e.getSource().equals(voltar))
        {
            j.setNickname(nickF.getText());
            m.getContentPane().remove(this);
            m.getContentPane().add(mp);
            m.repaint();
        }
    }
}
package cvServer;

// inclui uma classe ServerPanel que contém o conteúdo da janela

import cvClient.rede.*;
import generics.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerMainWindow extends JFrame implements MouseListener
{
    private ServerPanel panel;
    private ServerMainListener conListener;

    // host
    private Jogador j;
    
    // permitir ou não clicar em "Hostear"
    private boolean hostButtonEnabled;
    
    // posições dos botões
    private static final int[] HOST_POS = { 27, 14, 163, 53 };
    private static final int[] RANDOM_POS = { 27, 64, 163, 103 };
    private static final int[] EXIT_POS = { 27, 114, 163, 153 };

    public ServerMainWindow(Jogador j)
    {
        this.j = j;

        setTitle("Server Corrida de Vetores");
        hostButtonEnabled = true;
        setSize(420, 170);
        setResizable(false);
        setUndecorated(true);
        panel = new ServerPanel(this); // criando uma associação
        getContentPane().add(panel);
    }
    
    public void appendToLog(String s)
    {
        panel.appendToLog(s);
    }

    public void mouseClicked(MouseEvent e) { checkClick(e); }
    public void mouseReleased(MouseEvent e) { checkClick(e); }
    
    public void checkClick(MouseEvent e)
    {
        int xMouse = e.getX();
        int yMouse = e.getY();

        // host button
        if (hostButtonEnabled && xMouse >= HOST_POS[0] && xMouse <= HOST_POS[2] && yMouse >= HOST_POS[1] && yMouse <= HOST_POS[3])
        {
            hostButtonEnabled = false;
            appendToLog("Aguardando conexões...");
            conListener = new ServerMainListener(this);
            conListener.start();
            
            // connect a si mesmo
            ClientConnector con = new ClientConnector(j);
            con.connect("localhost", this);
        }

        // random button
        else if (xMouse >= RANDOM_POS[0] && xMouse <= RANDOM_POS[2] && yMouse >= RANDOM_POS[1] && yMouse <= RANDOM_POS[3])
        { panel.lolCode(); }

        // exit button
        else if (xMouse >= EXIT_POS[0] && xMouse <= EXIT_POS[2] && yMouse >= EXIT_POS[1] && yMouse <= EXIT_POS[3])
        {
            if (conListener != null && conListener.getSocketList().size() > 1)
            {
                int sair = -2;
                
                // if-else para fazer a concordância verbal correta
                if (conListener.getSocketList().size() == 2) { sair = JOptionPane.showConfirmDialog(this, "Atualmente há 1 client conectado.\nDeseja sair assim mesmo?", "Sair", 1, 3, null); }
                else { sair = JOptionPane.showConfirmDialog(this, "Atualmente há " + (conListener.getSocketList().size()-1) + " clients conectados.\nDeseja sair assim mesmo?", "Sair", 1, 3, null); }
                
                if (sair == 0)
                {
                    // inserir aqui algum código rox para desconectar os clientes
                    dispose();
                }
            }
            else
            {
                dispose();
            }
        }
    }
    
    public Jogador getJogador() { return j; }

    // lol interface
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

class ServerPanel extends JPanel
{
    private JTextArea logArea = new JTextArea();
    private JScrollPane areaSP;
    
    // associação com o frame criador
    private ServerMainWindow win;
    
    public ServerPanel(ServerMainWindow win)
    {
        this.win = win;
        setLayout(null); // senão não é possível posicionar a logarea com setbounds

        configureScrollPane();
        configureLogArea();

        addMouseListener(win);
    }

    @Override protected void paintComponent(Graphics g)
    {
        setMenu(g);
    }
    
    public void setMenu(Graphics g)
    {
        // carrega a imagem de menu inicial com um ImageIcon
        ImageIcon opIcon = new ImageIcon("imagens\\serv.png");
        Image op = opIcon.getImage();
        
        g.drawImage(op, 0, 0, 420, 170, null, null);
    }
    
    public void appendToLog(String s)
    {
        logArea.append("\n" + s);
        logArea.setCaretPosition(logArea.getDocument().getLength()); // "auto-scroll"
    }
    
    public void configureScrollPane()
    {
        // declara o scrollpane como contendo a logarea
        areaSP = new JScrollPane(logArea);

        // adiciona ao painel e define a área onde o scrollpane contendo a logarea irá ficar
        add(areaSP);
        areaSP.setBounds(198, 45, 200, 100);
    }

    public void configureLogArea()
    {
        // definindo as margens
        Insets i = new Insets(5, 5, 5, 5);
        logArea.setMargin(i);

        // definindo linewrapping correto
        logArea.setLineWrap(true);  
        logArea.setWrapStyleWord(true); 
        
        // paradas visuais
        logArea.setBackground(new java.awt.Color(248, 248, 255));
        logArea.setFont(new java.awt.Font("Tahoma", 0, 9));

        // coisas finais
        logArea.setEditable(false);
        logArea.append("Welcome to the server interface! :D");
        appendToLog(win.getJogador().getNickname() + " é o host...");
    }

    public void lolCode()
    {
        // toda criança brinca e se diverte
        int lolRandom = (int)(1+Math.random()*10);
        String lolMessage = "", lolTitle = "";

        switch (lolRandom)
        {
            case 1: lolMessage = "Eu não venho, amigos, para roubar seus corações...\n- Júlio César, ato III, cena II"; lolTitle = "Citação para o Jogo de copas da Microsoft"; break;
            case 2: lolMessage = "A violência não é natural, mas o amor sim que é natural.\n- Tony da Gatorra, cantor, inventor e compositor"; lolTitle = "Assassino"; break;
            case 3: lolMessage = "E acaba por aqui\nA vida de um perdedor\nQue morreu num canto escuro\nVítima do próprio amor\n\n- Lucas Silveira, Fresno"; lolTitle = "A faca - Fresno"; break;
            case 4: lolMessage = "Por favor, atenda minha ligação\nDoce menina\nMeu pedaço de paixão\nNa calada da noite da solidão\nImagino você no meu colchão\nMas é sonho e acordo sem querer\nVenha logo me dar o seu prazer\n\n- William & Renan"; lolTitle = "William & Renan - Apartamento e Solidão"; break;
            case 5: lolMessage = "Em um ninho de mafagafos haviam sete mafagafinhos; quem amafagafar mais mafagafinhos, bom amagafanhador será.\n- Sabedoria Popular"; lolTitle = "Trava-Língua"; break;
            case 6: lolMessage = "\"Em um ninho de mafagafos, quantos magafafinhos (sic) há?\"\n\n\nMelhor resposta - Escolhida por votação:\n\n\"7 mafagafinhos!! Lindos!!!!!!!!!! tão pequeninhos...\nQuem desmafagafizar??? Aaaaahhhhhhhh, ai de quem desmafagafizar!!!!!!\nSerá com certeza um ótimo desmagafigador, mas será um desmagafigador morto!!!!!!!!!!\nPra quê desmafagafizar os mafagafinhos??? Têm uma vida mafagafa todinha brilhante pela frente!!\""; lolTitle = "Yahoo! Brasil Respostas"; break;
            case 7: lolMessage = "Roubar idéias de uma pessoa é plágio... Roubar de várias, é Monografia.\n- Sabedoria Popular"; lolTitle = "Frase de Efeito"; break;
            case 8: lolMessage = "Se você é capaz de sorrir quando tudo deu errado, é porque já descobriu em quem colocar a culpa.\n- Sabedoria Popular"; lolTitle = "Frase de Efeito"; break;
            case 9: lolMessage = "Se caminhar fosse bom para a saúde, o carteiro seria imortal.\n- Sabedoria Popular"; lolTitle = "Frase de Efeito"; break;
            case 10: lolMessage = "Um cigarro encurta a vida em 2 minutos...\nUma garrafa de álcool encurta a vida em 4 minutos...\nUm dia de trabalho encurta a vida em 8 horas.\n\n- Sabedoria Popular"; lolTitle = "Frase de Efeito"; break;
        }
        JOptionPane.showMessageDialog(this, lolMessage, lolTitle, 1);
    }
}

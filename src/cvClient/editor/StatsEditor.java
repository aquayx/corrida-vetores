package cvClient.editor;

import generics.PistaFileFilter;
import generics.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.filechooser.*;
import javax.swing.*;

public class StatsEditor extends JPanel implements ActionListener
{
    private AreaEditor areaEditor;
    private JButton printPista;
    private JButton printNada;
    private JButton printLargada;
    private JButton load;
    private JButton save;
    
    private JButton checkpoint;
    private JLabel numCheckLabel;
    private JButton checkMais = new JButton("+");
    private JButton checkMenos = new JButton("-");
    private JLabel actualAction = new JLabel("Ação atual: Pista");
    
    private int numCheck = 1;

    public StatsEditor(AreaEditor areaEditor)
    {
        //falta add o nova pista, salvar pista e abrir pista
        //dois cliques para printar alguma coisa
        this.areaEditor = areaEditor;
        setLayout(null);
        setSize(180, 400);
        setLocation(620, 15);

        printPista = new JButton("Pista");
        printNada = new JButton("Apaga");
        printLargada = new JButton("Largada");
        load = new JButton("Load");
        save = new JButton("Save");
        printPista.setBounds(10, 10, 102, 20);
        printNada.setBounds(10, 50, 102, 20);
        printLargada.setBounds(10, 90, 102, 20);
        load.setBounds(10, 190, 102, 20);
        save.setBounds(10, 230, 102, 20);

        checkpoint = new JButton("Checkpoint:");
        checkpoint.setBounds(10, 130, 102, 20);
        checkMenos.setBorder(null);
        checkMenos.setFocusable(false);
        checkMenos.setBounds(10, 150, 17, 17);
        checkMenos.setEnabled(false);
        numCheckLabel = new JLabel(numCheck + "");
        numCheckLabel.setBounds(35, 150, 50, 20);
        numCheckLabel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        checkMais.setBorder(null);
        checkMais.setFocusable(false);
        checkMais.setBounds(95, 150, 17, 17);
        actualAction.setBounds(10, 280, 160, 20);

        printNada.addActionListener(this);
        printPista.addActionListener(this);
        printLargada.addActionListener(this);
        load.addActionListener(this);
        save.addActionListener(this);
        checkpoint.addActionListener(this);
        checkMenos.addActionListener(this);
        checkMais.addActionListener(this);

        add(printPista);
        add(printNada);
        add(printLargada);
        add(load);
        add(save);
        add(checkpoint);
        add(checkMenos);
        add(checkMais);
        add(numCheckLabel);
        add(actualAction);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(printNada))
        { areaEditor.setAcao('0'); actualAction.setText("Ação atual: Borracha"); }
        else if (e.getSource().equals(printPista))
        { areaEditor.setAcao('1'); actualAction.setText("Ação atual: Pista"); }
        else if (e.getSource().equals(printLargada))
        { areaEditor.setAcao('2'); actualAction.setText("Ação atual: Largada"); }
        else if (e.getSource().equals(checkpoint))
        { areaEditor.setAcao((char)('2' + numCheck)); actualAction.setText("Ação atual: Checkpoint " + numCheck); }
        else if(e.getSource().equals(checkMais))
        { numCheck++; if (areaEditor.getAcao() > '2') { areaEditor.setAcao((char)('2' + numCheck)); actualAction.setText("Ação atual: Checkpoint " + numCheck); } }
        else if(e.getSource().equals(checkMenos))
        { numCheck--; if (areaEditor.getAcao() > '2') { areaEditor.setAcao((char)('2' + numCheck)); actualAction.setText("Ação atual: Checkpoint " + numCheck); } }
        else if (e.getSource().equals(load))
        { pedirPath(0); }
        else if (e.getSource().equals(save))
        { pedirPath(1); }
        managePanel();
    }

    public boolean pedirPath(int a) // 0 para load, 1 para save
    {
        // o boolean pode ser usado para talvez ver se não deu nenhuma exception ou whatever no carregamento
        boolean success = false;
        JFileChooser chooser = new JFileChooser();
        FileFilter ff = new PistaFileFilter();
        chooser.setFileFilter(ff);
        if (a == 0) { chooser.setDialogTitle("Abrir pista..."); chooser.setApproveButtonText("Abrir"); }
        if (a == 1) { chooser.setDialogTitle("Salvar pista..."); chooser.setApproveButtonText("Salvar");}
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            switch (a)
            {
                case 0:
                {
                    Pista p = new Pista(file);                    
                    areaEditor.setPista(p);
                    areaEditor.desenharFundo(areaEditor.getGraphics());//repaint pra mostrar a pista na tela
                    break;
                }
                case 1:
                {
                    Pista p = areaEditor.getPista();                    
                    p.savePista(file);
                    break;
                }
            }
        }
        return success;
    }
    
    private void managePanel()
    {
        if (numCheck <= 1) { checkMenos.setEnabled(false); }
        else { checkMenos.setEnabled(true); }
        if (numCheck >= 7) { checkMais.setEnabled(false); }
        else { checkMais.setEnabled(true); }
        numCheckLabel.setText(numCheck + "");
    }
}
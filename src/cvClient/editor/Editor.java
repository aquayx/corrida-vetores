package cvClient.editor;

import cvClient.menus.*;
import generics.*;
import java.awt.event.*;
import javax.swing.*;

public class Editor extends JFrame implements WindowListener
{
    private Menu m;
    private AreaEditor areaEditor;
    private Pista p;

    public Editor(Menu m)
    {
        this.m = m;

        p = new Pista();

        setLayout(null);
        setLocation(240, 100);
        setSize(800, 600);
        setResizable(false);
        setVisible(true);

        areaEditor = new AreaEditor(p);
        getContentPane().add(areaEditor);
        areaEditor.desenharFundo(areaEditor.getGraphics());

        StatsEditor se = new StatsEditor(areaEditor);
        getContentPane().add(se);

        addWindowListener(this);
        repaint();
    }

    public void windowClosing(WindowEvent e)
    {
        m.setVisible(true);
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
}
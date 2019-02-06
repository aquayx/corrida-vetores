package cvClient;

import cvClient.menus.*;
import generics.*;
import javax.swing.*;

public class Main
{
    public static void main(String[] lulz)
    {
        aplicarLaF();
        Jogador j = new Jogador();
        Menu menu = new Menu(j);
    }
    
    public static void aplicarLaF() // LaF = LookAndFeel
    {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception fail) { Fail.swingFail("AplicarLaF", "Main", fail);}
    }
}

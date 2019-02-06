package generics;

import javax.swing.*;

// @author Lulz
// classe simples contendo alguns tipos de tratamentos de Exceptions...

public class Fail
{
    public static void show(Exception noLulz)
    {
        System.err.println("Erro: " + noLulz);
    }
    
    public static void stack(Exception noLulz)
    {
        System.err.println("Erro causado: " + noLulz);
        System.err.println("Stack Trace:"); noLulz.printStackTrace();
    }
    
    public static void input(Exception noLulz)
    {
        System.err.println("Entrada inválida, por favor digite novamente.");
        System.err.println("Erro causado: " + noLulz);
    }
    
    public static void swingFail(String method, String className, Exception noLulz)
    {
        JOptionPane.showMessageDialog(null, "Erro no método " + method + " da classe " + className + ".\n\n" + noLulz, "Fatal Error", 0);
    }
}
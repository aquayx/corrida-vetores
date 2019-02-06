package generics;

import java.io.*;
import java.util.ArrayList;

public class Pista
{
    private int xSize = 40; int ySize = 28; // tamanho de uma pista
    private char[][] pista = new char[xSize][ySize];
    private int[] start = new int[2];//para saber onde começa a ser traçados os vetores
    private int[] end = new int[2];

    // objetos de read/write
    private LineNumberReader lnr;
    private BufferedWriter bw;

    // construtor sem parâmetros - cria uma pista vazia
    public Pista()
    {
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                pista[x][y] = '0';
            }
        }
    }
    
    // construtor com parâmetro array de chars - passa uma pista diretamente
    public Pista(char[][] pista)
    {
        this.pista = pista;
    }

    // construtor com parâmetro arquivo - carrega pista de um arquivo
    public Pista(File arquivo)
    {
        loadPista(arquivo);
    }

    public char[][] getPista()
    {
        return pista;
    }

    public char getTile(int x, int y)
    {
        return pista[x][y];
    }

    public void setTile(int x, int y, char v)
    {
        pista[x][y] = v;
    }

    public int[] getStart()
    {
        return start;
    }

    public int[] getEnd()
    {
        return end;
    }
    
    public int getXSize() { return xSize; }
    public int getYSize() { return ySize; }

    public boolean loadPista(File arquivo)
    {
        boolean success = false;
        try // carregar linha por linha
        {
            lnr = new LineNumberReader(new FileReader(arquivo));
            String linhaArquivo = null;
            {
                while ((linhaArquivo = lnr.readLine()) != null)
                {
                    char[] linha = linhaArquivo.toCharArray();
                    for (int xPos = 0; xPos < linha.length; xPos++)
                    {
                        pista[xPos][lnr.getLineNumber()-1] = linha[xPos]; // getLineNumber()-1 porque já é pulada uma linha com lnr.readLine no while
                    }
                }
            }
        }
        catch(Exception fail)
        {
            Fail.swingFail("loadPista", "Pista", fail);
        }
        finally
        {
            try
            {
                lnr.close();
            }
            catch(Exception fail)
            {
                Fail.swingFail("loadPista", "Pista", fail);
            }
        }
        return success;
    }

    public boolean savePista(File arquivo)
    {
        boolean success = false;
        try
        {
            bw = new BufferedWriter(new FileWriter(arquivo));
            for (int y = 0; y < ySize; y++)
            {
                String line = "";
                for (int x = 0; x < xSize; x++)
                {
                    line += String.valueOf(pista[x][y]);
                }
                bw.write(line);
                if (y != ySize - 1) { bw.newLine(); }
            }
        }
        catch (Exception fail)
        {
            Fail.swingFail("savePista", "Pista", fail);
        }
        finally
        {
            try { bw.close(); }
            catch (Exception fail) { Fail.swingFail("savePista", "Pista", fail); }
        }
        return success;
    }
    
    public ArrayList<Integer> getPosInicial()
    {
        ArrayList<Integer> posInicial = new ArrayList();
        for (int x = 0; x < 40; x++)
        {
            for (int y = 0; y < 28; y++)
            {
                if (getTile(x, y) == '2')
                {
                    posInicial.add(x);
                    posInicial.add(y);
                }
            }
        }
        return posInicial;
    }
    
    public ArrayList<Integer>[] getChecks()
    {
        ArrayList<Integer>[] checkList = new ArrayList[7];
        for (int pos = 0; pos < 7; pos++) { checkList[pos] = new ArrayList<Integer>(); }
        for (int numCheck = 0; numCheck < 7; numCheck++)
        {
            for (int x = 0; x < 40; x++)
            {
                for (int y = 0; y < 28; y++)
                {
                    if (getTile(x, y) == ('3' + numCheck))
                    {
                        checkList[numCheck].add(x);
                        checkList[numCheck].add(y);
                    }
                }
            }
        }
        return checkList;
    }

    public void mostraPistaConsole()
    {
        for(int y = 0; y < ySize; y++)
        {
            for (int x = 0; x < xSize; x++)
            {
                System.out.print(pista[x][y]);
            }
            System.out.println();
        }
    }
}
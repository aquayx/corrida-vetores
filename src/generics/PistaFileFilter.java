package generics;

import java.io.File;
import javax.swing.filechooser.*;

public class PistaFileFilter extends FileFilter
{
    public static final String pis = "pis";
    
    // aceitar somente .pis
    public boolean accept(File f)
    {
        if (f.isDirectory())
        {
            return true;
        }
        
        String extension = getExtension(f);
        
        if (extension != null)
        {
            if (extension.equals(pis))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    public static String getExtension(File f)
    {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1)
        {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    public String getDescription()
    {
        return "Arquivos .pis";
    }
}
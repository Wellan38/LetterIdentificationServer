/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexandre.letteridentification;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author alexa
 */
public class TrainingAction extends Action
{

    @Override
    public void execute(HttpServletRequest request, PrintWriter out)
    {
        try
        {
            String image = request.getParameter("image");
            
            image = image.split(",")[1];
            
            Decoder decoder = Base64.getDecoder();
            
            byte[] src = decoder.decode(image);
            
            InputStream in = new ByteArrayInputStream(src);
            BufferedImage bImageFromConvert = ImageIO.read(in);

            ImageIO.write(bImageFromConvert, "PNG", new File("C:\\Users\\alexa\\Desktop\\index.png"));
            
            BufferedImage im = ImageIO.read(new File("C:\\Users\\alexa\\Desktop\\index.png"));
            
            BufferedImage im2 = serv.rescaleImage(im);
            
            Double[] tab = serv.createInputFromImage(im2);
            
            for (int i = 0; i < Math.sqrt(tab.length); i++)
            {
                for (int j = 0; j < Math.sqrt(tab.length); j++)
                {                
                    if (tab[(int)(i * Math.sqrt(tab.length) + j)].equals(1.))
                    {
                        System.out.print("*");
                    }
                    else
                    {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(TrainingAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

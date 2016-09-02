/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexandre.letteridentification;

import alexandre.letteridentification.util.NeuralNetwork;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author alexa
 */
public class TestAction extends Action
{

    @Override
    public void execute(HttpServletRequest request, PrintWriter out)
    {
        
        try
        {
            String src_image = request.getParameter("image");

            Base64.Decoder decoder = Base64.getDecoder();

            byte[] src = decoder.decode(src_image);

            InputStream in = new ByteArrayInputStream(src);
            
            BufferedImage image = ImageIO.read(in);
            
            Double[] input = serv.getCenteredImage(image);
            
            HashMap<Character, Double> res = new LinkedHashMap();
            
            for (int i = 65; i <= 90; i++)
            {
                Character letter = (char)i;
                res.put(letter, Double.valueOf((int)((serv.testNetwork(letter, input)) * 10000)) / 100);
                System.out.println(letter + " : " + res.get(letter) + "%");
            }
            
            for (int i = 0; i < Math.sqrt(NeuralNetwork.NB_INPUT); i++)
            {
                for (int j = 0; j < Math.sqrt(NeuralNetwork.NB_INPUT); j++)
                {
                    if (input[(int)(i * Math.sqrt(NeuralNetwork.NB_INPUT) + j)].equals(1.))
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
        catch (IOException ex)
        {
            Logger.getLogger(TestAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex)
        {
            Logger.getLogger(TestAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

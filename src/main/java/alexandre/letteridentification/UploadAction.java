/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexandre.letteridentification;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author alexa
 */
public class UploadAction extends Action
{

    @Override
    public void execute(HttpServletRequest request, PrintWriter out)
    {
        try
        {
            Character letter = request.getParameter("letter").charAt(0);
            String image_src = request.getParameter("image");

            String path = ActionServlet.path + "\\Training\\" + letter;
            
            if (!new File(path).exists())
            {
                new File(path).mkdirs();
            }
            int index = new File(path).list().length + 1;

            Base64.Decoder decoder = Base64.getDecoder();

            byte[] src = decoder.decode(image_src);

            InputStream in = new ByteArrayInputStream(src);
            BufferedImage bImageFromConvert;

            bImageFromConvert = ImageIO.read(in);
            
            ImageIO.write(bImageFromConvert, "PNG", new File(path + "\\" + index + ".png"));
        }
        catch (IOException ex)
        {
            Logger.getLogger(UploadAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

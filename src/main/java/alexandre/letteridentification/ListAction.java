/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alexandre.letteridentification;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
public class ListAction extends Action
{

    @Override
    public void execute(HttpServletRequest request, PrintWriter out)
    {
        try
        {
            String type = request.getParameter("type");

            JsonArray list = new JsonArray();

            switch(type)
            {
                case "images_to_be_analyzed":
                    
                    String path = ActionServlet.path + "\\Images to be analyzed";
                    
                    File[] letters = new File(path).listFiles();
                    
                    for (int i = 0; i < letters.length; i++)
                    {
                        JsonObject letter_obj = new JsonObject();
                        letter_obj.addProperty("letter", letters[i].getName());
                        
                        File[] drawings = new File(letters[i].getAbsolutePath() + "\\Drawings").listFiles();
                        
                        JsonArray list_drawings = new JsonArray();

                        for (int j = 0; j < drawings.length; j++)
                        {
                            BufferedImage im = ImageIO.read(drawings[j]);
                            
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            
                            ImageIO.write(im, "png", baos);
                            baos.flush();
                            byte[] data = baos.toByteArray();
                            
                            baos.close();

                            String encodedImage = "data:image/png;base64," + Base64.getEncoder().encodeToString(data);

                            list_drawings.add(encodedImage);
                        }

                        letter_obj.add("drawings", list_drawings);
                        
                        File[] computer_vision = new File(letters[i].getAbsolutePath() + "\\Computer Vision").listFiles();
                        
                        JsonArray list_computer = new JsonArray();

                        for (int j = 0; j < computer_vision.length; j++)
                        {
                            BufferedImage im = ImageIO.read(computer_vision[j]);
                            
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            
                            ImageIO.write(im, "png", baos);
                            baos.flush();
                            byte[] data = baos.toByteArray();
                            
                            baos.close();

                            String encodedImage = "data:image/png;base64," + Base64.getEncoder().encodeToString(data);

                            list_computer.add(encodedImage);
                        }

                        letter_obj.add("computer_vision", list_computer);
                        
                        list.add(letter_obj);
                    }
                    
                    break;
            }
            
            JsonObject container = new JsonObject();
            container.add("list", list);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(container);
            out.println(json);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ListAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

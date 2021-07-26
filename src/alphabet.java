import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class alphabet extends JLabel{
    private Character IMAGE_LETTER;
    private String IMAGE_DIRECTORY;
    private String IMAGE_TYPE;
    private int PREFERRED_WIDTH;
    private int PREFERRED_HEIGHT;
    private String path;
    private BufferedImage image;
    private MouseListener ClickListener;

    public alphabet() {
        this('a', "images/",".png");
    }


    public alphabet(char imageLetter, String imageDirectory, String imageType) {
        IMAGE_LETTER = imageLetter;
        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;

        PREFERRED_WIDTH = PREFERRED_HEIGHT = 50;

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        path = IMAGE_DIRECTORY + IMAGE_LETTER + IMAGE_TYPE;
        image = loadImage(path);
    }



    private BufferedImage loadImage(String imagePath){
        BufferedImage img = null;

        try{
            img = ImageIO.read(new File(imagePath));
        }
        catch (IOException ex){
            System.err.println("loadImage() : Error: Image at " + imagePath + " could not be found");
            System.exit(1);
        }
        return img;
    }


    public char guess(){
        loadNewImage("guessed");
        removeClickListener();
        return IMAGE_LETTER;
    }

    private void loadNewImage(String valueOf){
        path = IMAGE_DIRECTORY + IMAGE_LETTER + "_" + valueOf + IMAGE_TYPE;
        image = loadImage(path);
        repaint();

    }

    public void addClickListener(MouseListener c){
        ClickListener = c;
        addMouseListener(ClickListener);
    }

    public void removeClickListener() {removeMouseListener(ClickListener);}

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, PREFERRED_WIDTH, PREFERRED_HEIGHT, null);
    }



}

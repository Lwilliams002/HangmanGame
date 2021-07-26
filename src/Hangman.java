import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;



public class Hangman extends JLabel {

    private int PREFERRED_WIDTH;


    private int PREFERRED_HEIGHT;


    private String IMAGE_BASE_NAME = null;


    private String IMAGE_DIRECTORY = null;


    private String IMAGE_TYPE = null;


    private String path;


    private BufferedImage image;

    public Hangman(){
        this("hangman", "images/", ".png");
    }

//images of Hangman based off the images' base name, the directory of the hangman images , and the type of image

    public Hangman(String imageBaseName, String imageDirectory, String imageType) {
        PREFERRED_WIDTH = 440;
        PREFERRED_HEIGHT = 255;

        IMAGE_BASE_NAME = imageBaseName;
        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;

        // value of : _0 to go through the images of hangman
        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        path = IMAGE_DIRECTORY + IMAGE_BASE_NAME + "_0" + IMAGE_TYPE;
        image = loadImage(path);
    }

    // loads the image
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

    public void nextImage(int imageNumber){
        loadNewImage(String.valueOf(imageNumber));
    }

    public void loseImage() { loadNewImage("lose");}

    public void winImage() {loadNewImage("win");}

    private void loadNewImage(String suffix) {
    path = IMAGE_DIRECTORY + IMAGE_BASE_NAME + "_" + suffix + IMAGE_TYPE;
    image = loadImage(path);
    repaint();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, PREFERRED_WIDTH, PREFERRED_HEIGHT, null);
    }
}


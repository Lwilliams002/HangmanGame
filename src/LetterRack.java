import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import javax.swing.*;

public class LetterRack extends JPanel{
    private int COLUMN;
    private int ROWS;
    private GridLayout LETTER_LAYOUT;
    private int CAPACITY;
    private String IMAGE_DIRECTORY;
    private String IMAGE_TYPE;
    private String password;

    private ArrayList<alphabet> rack;

    public LetterRack(){
        this("password", "images/", ".png");
    }

    public LetterRack(String inPassword, String imageDirectory , String imageType) {
        COLUMN = 8;
        ROWS = 3;
        LETTER_LAYOUT = new GridLayout(ROWS , COLUMN);
        LETTER_LAYOUT.setVgap(10);
        CAPACITY = ROWS * COLUMN;

        IMAGE_DIRECTORY = imageDirectory;
        IMAGE_TYPE = imageType;

        rack = new ArrayList<>();
        password = inPassword;

        setBorder(BorderFactory.createEmptyBorder(10,17,10,10));
        setLayout(LETTER_LAYOUT);
        loadRack();
    }

    private void loadRack() {
        buildRack();
        for(alphabet tile : rack)
            add(tile);
    }

    private void buildRack(){
        StringBuilder passwordBuilder = new StringBuilder(password.toLowerCase());
        ArrayList<Character> tiles = new ArrayList<>();
        Random rand = new Random();
        int i = 0, j = 0;

        while (passwordBuilder.length() > 0 ){
            if (!tiles.contains(passwordBuilder.charAt(0))){
                tiles.add(passwordBuilder.charAt(0));
                i++;
            }
            passwordBuilder.deleteCharAt(0);

        }
        for(; i < CAPACITY; i++){
            Character c = 'a';
            do{
                c = (char) (rand.nextInt(26) + 'a');
            }
            while(tiles.contains(c));
            tiles.add(c);
            }

        for(i = 0; i < CAPACITY; i++){
            j = rand.nextInt(tiles.size());
            rack.add(new alphabet(tiles.get(j), IMAGE_DIRECTORY, IMAGE_TYPE));
            tiles.remove(j);

        }
    }

    public void attachListener(MouseListener c){
        for(alphabet tile : rack){
            tile.addClickListener(c);
        }

    }
    public void removeListener(){
        for(alphabet tile : rack){
            tile.removeClickListener();
        }
    }
}
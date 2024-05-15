
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class BrickGenerator {
    public int bricks[][];
    public int brickWidth;
    public int brickHeight;
    public Color[][] brickColors;
    Random rand = new Random();

    public BrickGenerator(int row, int col) {
        bricks = new int[row][col]; 
        brickColors = new Color[row][col];
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                bricks[i][j] = 1;
                //generate random color of bricks
                int red = rand.nextInt(256); 
                int green = rand.nextInt(256); 
                int blue = rand.nextInt(256); 
                brickColors[i][j] = new Color(red, green, blue);
            }
        }
        brickWidth = 750/col;
        brickHeight = 100/row;
    }
    //draw bricks
    public void draw(Graphics2D g) {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[0].length; j++) {
                if (bricks[i][j] > 0) {
                    //brick color
                    g.setColor(brickColors[i][j]);
                    g.fillRect(j*brickWidth+120, i*brickHeight+80, brickWidth, brickHeight);
                    //brick border
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j*brickWidth+120, i*brickHeight+80, brickWidth, brickHeight);
                }
            }
        }
    }
    public void setBrickValue(int value, int row, int col) {
        bricks[row][col] = value;
    }
}

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 50;
    Random rand = new Random();
    private BrickGenerator bricks;

    //Timer
    private final Timer timer;
    private int timeDelay = 5;

    //Position
    private int playerPosition = 300;
    private int ballPositionX = rand(0, 500);
    private int ballPositionY = rand(350, 450);
    private int ballDirX = -1;
    private int ballDirY = -2;
    
    public Gameplay() {
        bricks = new BrickGenerator(5,10);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        timer = new Timer(timeDelay, this);
        timer.start();
    }

    //random starting position for the ball
    private int rand(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 980, 890);
        //border
        g.setColor(Color.white);
        g.fillRect(0, 0, 5, 990);
        g.fillRect(0, 0, 990, 5);
        //bricks
        bricks.draw((Graphics2D)g);
        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 40));
        g.drawString(""+score, 850, 600);
        //player
        g.setColor(Color.white);
        g.fillRect(playerPosition, 620, 100, 8);
        g.setColor(Color.red);
        g.fillRect(playerPosition, 618, 100, 2);
        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPositionX, ballPositionY, 20, 20);
        //gameover screen
        if (ballPositionY > 670) {
            play = false;
            ballDirX = 0;
            ballDirY = 0;
            g.setColor(Color.pink);
            g.setFont(new Font("serif", Font.BOLD, 50));
            g.drawString("Game Over, Score: "+score, 400, 350);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Press Enter to Restart", 500, 400);
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            //ball detects player
            if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerPosition, 620, 120, 8))) {
                ballDirY = - ballDirY;
            }
            //ball detects bricks
            for (int i = 0; i < bricks.bricks.length; i++) {
                for (int j = 0; j < bricks.bricks[0].length; j++) {
                    if (bricks.bricks[i][j] > 0) {
                        int brickX = j*bricks.brickWidth + 120;
                        int brickY = i*bricks.brickHeight + 80;
                        int brickWidth = bricks.brickWidth;
                        int brickHeight = bricks.brickHeight;

                        Rectangle ball = new Rectangle(ballPositionX, ballPositionY, 20, 20);
                        Rectangle brick = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        
                        if (ball.intersects(brick)) {
                            bricks.setBrickValue(0, i, j); //set the value of the brick to 0 (destroyed)
                            totalBricks--;
                            score+=1;

                            if(ballPositionX + 19 <= brick.x || ballPositionX + 1 >= brick.x + brick.width) {
                                ballDirX = -ballDirX;
                            }
                            else {
                                ballDirY = - ballDirY;
                            }
                            //break loop after first collision is detected preventing multiple collisions with the same ball
                            break;
                        }
                    }
                }
            }
            //ball detects walls
            ballPositionX += ballDirX;
            ballPositionY += ballDirY;
            if (ballPositionX <= 0 || ballPositionX >= 960) {
                ballDirX = -ballDirX;
            }
            if (ballPositionY <= 0) {
                ballDirY = -ballDirY;
            }
        }
        //refresh the screen
        repaint();
    } 

    //Player buttons
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerPosition >= 880) {
                playerPosition = 880;
            }
            else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerPosition <= 10) {
                playerPosition = 10;
            }
            else {
                moveLeft();
            }
        }
        if (e.getKeyCode()== KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                playerPosition = 300;
                ballPositionX = rand(0, 500);
                ballPositionY = rand(350, 450);
                ballDirX = -1;
                ballDirY = -2;
                score = 0;
                totalBricks = 50;
                bricks = new BrickGenerator(5,10);

                repaint();
            }
        }
    }
    
    public void moveRight() {
        play = true;
        playerPosition +=18;
    }

    public void moveLeft() {
        play = true;
        playerPosition -=18;
    }

//---------------//
    @Override
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

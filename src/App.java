import javax.swing.JFrame;

public class App {
    public static void main(String[] args) {
        JFrame screen = new JFrame();
        Gameplay gamePlay = new Gameplay();
        screen.setBounds(10, 10, 1000, 700);
        screen.setTitle("BrickBreaker");
        screen.setResizable(false);
        screen.setVisible(true);
        screen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen.add(gamePlay);
        }
    }

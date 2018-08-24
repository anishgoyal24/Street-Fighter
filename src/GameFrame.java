import javax.swing.*;

public class GameFrame extends JFrame implements IGameConstants {
    public GameFrame(){
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(GWIDTH, GHEIGHT);
        setLocationRelativeTo(null);
        Board board = new Board();
        add(board);
        setVisible(true);
    }

    public static void main(String args[]){
        GameFrame g = new GameFrame();
    }
}

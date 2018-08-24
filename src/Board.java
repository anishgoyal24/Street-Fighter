import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Board extends JPanel implements IGameConstants, IPlayerStates {
    private Image image;
    private Timer timer;
    private Player player;
    private Enemy enemy;
    private boolean reactFlag;
    private int runBackDelay;

    public Board(){
        reactFlag = false;
        setFocusable(true);
        image = new ImageIcon(Board.class.getResource(BGIMAGE)).getImage();
        player = new Player();
        enemy = new Enemy();
        bindEvents();
        gameLoop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        gameEngine();
        player.drawPlayer(g);
        enemy.drawEnemy(g);
        updateHealthBar(g);
        gameOver(g);
    }

    private void drawBackground(Graphics graphics){
        graphics.drawImage(image, 0, 0, GWIDTH, GHEIGHT, null);
    }

    private void gameLoop(){
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                repaint();
                player.move();
                enemy.move();
                collisionActions();
            }
        };
        timer = new Timer(DELAY, actionListener);
        timer.start();
    }

    private void bindEvents(){
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {

                if (keyEvent.getKeyCode() == KeyEvent.VK_P){
                    player.state = LOW_PUNCH;
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_O){
                    player.state = HIGH_PUNCH;
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){
                    player.setDirection(1);
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT){
                    player.setDirection(-1);
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_I){
                    player.state = UPPER_PUNCH;
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_SHIFT){
                    player.state = JUMP_FORWARD;
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_CONTROL){
                    player.state = JUMP_BACKWARD;
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_Z){
                    player.state = LOW_KICK;
                }
                else if (keyEvent.getKeyCode() == KeyEvent.VK_X){
                    player.state = HIGH_KICK;
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if ((keyEvent.getKeyCode() == KeyEvent.VK_LEFT) || (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)){
                    player.setDirection(0);
                }
            }
        };
        this.addKeyListener(keyListener);
    }

    private boolean collision(){
        if ((player.state !=0) || (enemy.state != 0)){
            if ((player.getX() + player.getW() > enemy.getX()) && (player.getX() + player.getW() < enemy.getX() + enemy.getW())){
                return true;
            }
        }
        return false;
    }

    private void collisionActions() {
        if (collision()) {
            if (player.counter == 0) {
                if ((player.state == IPlayerStates.LOW_PUNCH) || (player.state == IPlayerStates.LOW_KICK)) {
                    enemy.state = IEnemyStates.LOW_POWER_REACT;
                    player.counter++;
                    enemy.health -= 5;
                    reactFlag = true;
                }
                if ((player.state == IPlayerStates.HIGH_PUNCH) || (player.state == IPlayerStates.HIGH_KICK) || (player.state == IPlayerStates.UPPER_PUNCH)){
                    enemy.state = IEnemyStates.HIGH_POWER_REACT;
                    player.counter++;
                    enemy.health -= 10;
                    reactFlag = true;
                }
            }
            if (enemy.counter == 0) {
                if ((enemy.state == IEnemyStates.LOW_PUNCH) || (enemy.state == IEnemyStates.LOW_KICK)){
                    player.state = IPlayerStates.LOW_POWER_REACT;
                    enemy.counter++;
                    player.health -= 10;
                }
                if ((enemy.state == IEnemyStates.HIGH_PUNCH) || (enemy.state == IEnemyStates.HIGH_KICK)){
                    player.state = IPlayerStates.HIGH_POWER_REACT;
                    enemy.counter++;
                    player.health -= 10;
                }
            }
        }
    }

    private void updateHealthBar(Graphics graphics) {
        if ((enemy.health > 0) && (player.health > 0)) {
            graphics.setFont(new Font("Arial", Font.BOLD, 15));
            graphics.setColor(Color.WHITE);
            graphics.drawString("Player", 50, 45);
            graphics.drawString("Enemy", 795, 45);
            graphics.drawString(String.valueOf(player.health), 120, 90);
            graphics.drawString(String.valueOf(enemy.health), 750, 90);
            graphics.drawRoundRect(50, 50, 100, 20, 5, 5);
            graphics.fillRoundRect(50, 50, 100, 20, 5, 5);
            graphics.setColor(Color.RED);
            graphics.drawRoundRect(50, 50, player.health, 20, 5, 5);
            graphics.fillRoundRect(50, 50, player.health, 20, 5, 5);
            graphics.setColor(Color.WHITE);
            graphics.drawRoundRect(750, 50, 100, 20, 5, 5);
            graphics.fillRoundRect(750, 50, 100, 20, 5, 5);
            graphics.setColor(Color.RED);
            graphics.drawRoundRect(750, 50, enemy.health, 20, 5, 5);
            graphics.fillRoundRect(750, 50, enemy.health, 20, 5, 5);
        }
    }

    private void gameEngine(){
        if (reactFlag){
            enemy.direction = 1;
            enemy.setDirection(1);
            runBackDelay++;
            if (runBackDelay == 25){
                enemy.direction = 0;
                enemy.setDirection(0);
                runBackDelay = 0;
                reactFlag = false;
            }
        }
        if (player.direction == 1){
             if (player.getX() + player.getW() - 18 < enemy.getX()){

                enemy.direction = -1;
                enemy.setDirection(enemy.direction);
            }
        }
        else if ((player.direction == 0) && (!reactFlag)){
            enemy.direction = 0;
            enemy.setDirection(enemy.direction);
        }
        if ((player.getX() + player.getW() - 18 == enemy.getX()) && (!reactFlag)) {
            enemy.state = new Random().nextInt(4) + 1;
        }
    }

    private void gameOver(Graphics graphics){
        if (enemy.health <= 0){
            timer.stop();
            graphics.setFont(new Font("Arial", Font.BOLD, 32));
            graphics.setColor(Color.RED);
            graphics.drawString("YOU WIN", 350, 300);
        }
        else if (player.health <= 0){
            graphics.setFont(new Font("Arial", Font.BOLD, 32));
            graphics.setColor(Color.RED);
            graphics.drawString("GAMEOVER", 350, 300);
            timer.stop();
        }
    }
}

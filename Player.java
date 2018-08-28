import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player implements IGameConstants, IPlayerStates {
    private int x;
    private int y;
    private int w;
    private int h;
    public int state;
    private int speed;
    public int health;
    public int counter;
    public int direction;
    private BufferedImage image;
    private BufferedImage walk[] = new BufferedImage[4];
    private BufferedImage lowPunch[] = new BufferedImage[6];
    private BufferedImage highPunch[] = new BufferedImage[6];
    private BufferedImage upperPunch[] = new BufferedImage[4];
    private BufferedImage jumpForward[] = new BufferedImage[7];
    private BufferedImage jumpBackward[] = new BufferedImage[5];
    private BufferedImage lowKick[] = new BufferedImage[5];
    private BufferedImage highKick[] = new BufferedImage[5];
    private BufferedImage lowPowerReact[] = new BufferedImage[3];
    private BufferedImage highPowerReact[] = new BufferedImage[5];


    public Player() {
        try {
            image = ImageIO.read(Player.class.getResource(PLAYER_IMAGE));
        } catch (Exception e) {
            System.out.println(e);
        }
        state = 0;
        health = 100;
        counter = 0;
        loadWalk();
        loadLowPunch();
        loadHighPunch();
        loadUpperPunch();
        loadJumpForward();
        loadJumpBackward();
        loadLowKick();
        loadHighKick();
        loadLowPowerReact();
        loadHighPowerReact();
        x = 100;
        w = h = 125;
        y = FLOOR - h;
    }

    private void loadWalk() {
        walk[0] = image.getSubimage(0, 12, 43, 84);
        walk[1] = image.getSubimage(75, 16, 42, 79);
        walk[2] = image.getSubimage(140, 11, 40, 81);
        walk[3] = image.getSubimage(209, 13, 44, 81);
    }

    private void loadLowPunch() {
        lowPunch[0] = image.getSubimage(342, 38, 41, 55);
        lowPunch[1] = image.getSubimage(401, 37, 43, 55);
        lowPunch[2] = image.getSubimage(457, 37, 60, 54);
        lowPunch[3] = image.getSubimage(534, 40, 45, 54);
        lowPunch[4] = image.getSubimage(458, 116, 60, 54);
        lowPunch[5] = image.getSubimage(528, 117, 45, 54);
    }

    private void loadHighPunch() {
        highPunch[0] = image.getSubimage(0, 207, 43, 82);
        highPunch[1] = image.getSubimage(59, 206, 59, 82);
        highPunch[2] = image.getSubimage(137, 206, 45, 82);
        highPunch[3] = image.getSubimage(136, 424, 68, 85);
        highPunch[4] = image.getSubimage(220, 423, 45, 87);
        highPunch[5] = image.getSubimage(291, 424, 45, 87);
    }

    private void loadUpperPunch() {
        upperPunch[0] = image.getSubimage(390, 385, 45, 67);
        upperPunch[1] = image.getSubimage(451, 369, 52, 82);
        upperPunch[2] = image.getSubimage(515, 339, 44, 115);
        upperPunch[3] = image.getSubimage(570, 369, 49, 84);
    }

    private void loadJumpForward(){
        jumpForward[0] = image.getSubimage(620, 105, 36, 77);
        jumpForward[1] = image.getSubimage(678, 105, 36, 90);
        jumpForward[2] = image.getSubimage(724, 126, 69, 45);
        jumpForward[3] = image.getSubimage(810, 100, 42, 71);
        jumpForward[4] = image.getSubimage(857, 114, 83, 44);
        jumpForward[5] = image.getSubimage(947, 89, 48, 81);
        jumpForward[6] = image.getSubimage(1016, 75, 39, 97);
    }

    private void loadJumpBackward(){
        jumpBackward[0] = image.getSubimage(671, 215, 45, 79);
        jumpBackward[1] = image.getSubimage(727, 232, 77, 43);
        jumpBackward[2] = image.getSubimage(812, 208, 38, 73);
        jumpBackward[3] = image.getSubimage(859, 226, 67, 39);
        jumpBackward[4] = image.getSubimage(959, 194, 39, 93);
    }

    private void loadLowKick(){
        lowKick[0] = image.getSubimage(394, 274, 48, 59);
        lowKick[1] = image.getSubimage(450, 281, 89, 54);
        lowKick[2] = image.getSubimage(549, 275, 50, 57);
        lowKick[3] = image.getSubimage(445, 477, 73, 54);
        lowKick[4] = image.getSubimage(549, 275, 50, 57);
    }

    private void loadHighKick(){
        highKick[0] = image.getSubimage(3, 311, 41, 83);
        highKick[1] = image.getSubimage(67, 311, 41, 83);
        highKick[2] = image.getSubimage(130, 308, 72, 83);
        highKick[3] = image.getSubimage(223, 310, 44, 83);
        highKick[4] = image.getSubimage(3, 311, 41, 83);
    }

    private void loadLowPowerReact(){
        lowPowerReact[0] = image.getSubimage(546, 554, 47, 78);
        lowPowerReact[1] = image.getSubimage(611, 549, 56, 84);
        lowPowerReact[2] = image.getSubimage(681, 549, 45, 84);
    }

    private void loadHighPowerReact(){
        highPowerReact[0] = image.getSubimage(370, 790, 53, 62);
        highPowerReact[1] = image.getSubimage(437, 792, 76, 48);
        highPowerReact[2] = image.getSubimage(537, 811, 76, 34);
        highPowerReact[3] = image.getSubimage(6, 929, 37, 67);
        highPowerReact[4] = image.getSubimage(68, 891, 37, 104);
    }

    int walkIndex = 0;
    int walkDelay = 0;

    public void defaultWalk(Graphics graphics){
        if (walkIndex >= 4){
            walkIndex = 0;
        }
        graphics.drawImage(walk[walkIndex], x, y, w, h, null);
        if (walkDelay >= 3){
            walkDelay = 0;
            walkIndex++;
        }
        walkDelay++;
    }

    int lowPunchIndex = 0;
    int lowPunchDelay = 0;

    public void lowPunch(Graphics graphics){
        if (lowPunchIndex >= 6){
            lowPunchIndex = 0;
            state = IDLE;
            counter = 0;
        }
        graphics.drawImage(lowPunch[lowPunchIndex], x, y, w, h, null);
        if (lowPunchDelay >= 3){
            lowPunchDelay = 0;
            lowPunchIndex++;
        }
        lowPunchDelay++;
    }

    int highPunchIndex = 0;
    int highPunchDelay = 0;

    public void highPunch(Graphics graphics){
        if (highPunchIndex >= 6){
            highPunchIndex = 0;
            state = IDLE;
            counter = 0;
        }
        graphics.drawImage(highPunch[highPunchIndex], x, y, w, h, null);
        if (highPunchDelay >= 3){
            highPunchDelay = 0;
            highPunchIndex++;
        }
        highPunchDelay++;
    }

    int upperPunchIndex = 0;
    int upperPunchDelay = 0;

    public void upperPunch(Graphics graphics){
        if (upperPunchIndex >= 4){
            upperPunchIndex = 0;
            state = IDLE;
            counter = 0;
        }
        graphics.drawImage(upperPunch[upperPunchIndex], x, y, w, h, null);
        if (upperPunchDelay >= 3){
            upperPunchDelay = 0;
            upperPunchIndex++;
        }
        upperPunchDelay++;
    }

    int jumpForwardIndex = 0;
    int jumpForwardDelay = 0;

    public void jumpForward(Graphics graphics){
        if (jumpForwardIndex >= 7){
            jumpForwardIndex = 0;
            state = IDLE;
            counter = 0;
        }
        graphics.drawImage(jumpForward[jumpForwardIndex], x, y, w, h, null);
        if (jumpForwardDelay >= 3){
            jumpForwardDelay = 0;
            jumpForwardIndex++;
        }
        jumpForwardDelay++;
    }

    int jumpBackwardIndex = 0;
    int jumpBackwardDelay = 0;

    public void jumpBackward(Graphics graphics){
        if (jumpBackwardIndex >= 5){
            jumpBackwardIndex = 0;
            state = IDLE;
            counter = 0;
        }
        graphics.drawImage(jumpBackward[jumpBackwardIndex], x, y, w, h, null);
        if (jumpBackwardDelay >= 3){
            jumpBackwardDelay = 0;
            jumpBackwardIndex++;
        }
        jumpBackwardDelay++;
    }

    int lowKickIndex = 0;
    int lowKickDelay = 0;

    public void lowKick(Graphics graphics){
        if (lowKickIndex >= 5){
            lowKickIndex = 0;
            state = IDLE;
            counter = 0;
        }
        graphics.drawImage(lowKick[lowKickIndex], x, y, w, h, null);
        if (lowKickDelay >= 3){
            lowKickDelay = 0;
            lowKickIndex++;
        }
        lowKickDelay++;
    }

    int highKickIndex = 0;
    int highKickDelay = 0;

    public void highKick(Graphics graphics){
        if (highKickIndex >= 5){
            highKickIndex = 0;
            state = IDLE;
            counter = 0;
        }
        graphics.drawImage(highKick[highKickIndex], x, y, w, h, null);
        if (highKickDelay >= 3){
            highKickDelay = 0;
            highKickIndex++;
        }
        highKickDelay++;
    }

    int lowPowerReactIndex = 0;
    int lowPowerReactDelay = 0;

    public void lowPowerReact(Graphics graphics){
        if (lowPowerReactIndex >= 3){
            lowPowerReactIndex = 0;
            state = IDLE;
        }
        graphics.drawImage(lowPowerReact[lowPowerReactIndex], x, y, w, h, null);
        if (lowPowerReactDelay >= 3){
            lowPowerReactDelay = 0;
            lowPowerReactIndex++;
        }
        lowPowerReactDelay++;
    }

    int highPowerReactIndex = 0;
    int highPowerReactDelay = 0;

    public void highPowerReact(Graphics graphics){
        if (highPowerReactIndex >= 5){
            highPowerReactIndex = 0;
            state = IDLE;
        }
        graphics.drawImage(highPowerReact[highPowerReactIndex], x, y, w, h, null);
        if (highPowerReactDelay >= 3){
            highPowerReactDelay = 0;
            highPowerReactIndex++;
        }
        highPowerReactDelay++;
    }

    public void setDirection(int dir){
        speed = 3;
        speed = speed * dir;
        direction = dir;
    }

    public void move(){
        if (x < 5){
            x = 6;
            speed = 0;
        }
        else if (x > GWIDTH - w - 10){
            x = GWIDTH - w -11;
            speed = 0;
        }
        x += speed;
    }

    public void drawPlayer(Graphics graphics) {
            if (state == IDLE) {
                defaultWalk(graphics);
            } else if (state == LOW_PUNCH) {
                lowPunch(graphics);
            } else if (state == HIGH_PUNCH) {
                highPunch(graphics);
            } else if (state == UPPER_PUNCH) {
                upperPunch(graphics);
            } else if (state == JUMP_FORWARD) {
                jumpForward(graphics);
            } else if (state == JUMP_BACKWARD) {
                jumpBackward(graphics);
            } else if (state == LOW_KICK) {
                lowKick(graphics);
            } else if (state == HIGH_KICK) {
                highKick(graphics);
            } else if (state == LOW_POWER_REACT) {
                lowPowerReact(graphics);
            } else if (state == HIGH_POWER_REACT) {
                highPowerReact(graphics);
            }
        }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

}

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy implements IGameConstants, IEnemyStates{
    private int x;
    private int y;
    private int w;
    private int h;
    public int state;
    private int speed;
    public int health;
    public int counter;
    public int direction;
    public boolean attackState;
    private BufferedImage image;
    private BufferedImage walk[] = new BufferedImage[4];
    private BufferedImage lowPunch[] = new BufferedImage[3];
    private BufferedImage highPunch[] = new BufferedImage[8];
    private BufferedImage lowKick[] = new BufferedImage[3];
    private BufferedImage highKick[] = new BufferedImage[7];
    private BufferedImage lowPowerReact[] = new BufferedImage[4];
    private BufferedImage highPowerReact[] = new BufferedImage[10];

    public Enemy(){
        try{
            image = ImageIO.read(Enemy.class.getResource(ENEMY_IMAGE));
        }
        catch (Exception e){
            System.out.println(e);
        }
        state = 0;
        x = 600;
        w = h = 125;
        y = FLOOR - h;
        health = 100;
        counter = 0;
        loadWalk();
        loadLowPunch();
        loadHighPunch();
        loadLowKick();
        loadHighKick();
        loadLowPowerReact();
        loadHighPowerReact();
        attackState = false;
    }

    private void loadWalk(){
        walk[0] = image.getSubimage(2781, 11, 80, 103);
        walk[1] = image.getSubimage(2705, 10, 75, 99);
        walk[2] = image.getSubimage(2629, 10, 75, 99);
        walk[3] = image.getSubimage(2554, 10, 74, 99);
    }

    private void loadLowPunch(){
        lowPunch[0] = image.getSubimage(2772, 1046, 89, 109);
        lowPunch[1] = image.getSubimage(2661, 1046, 111, 109);
        lowPunch[2] = image.getSubimage(2558, 1046, 103, 109);
    }

    private void loadHighPunch(){
        highPunch[0] = image.getSubimage(2777, 1242, 92, 106);
        highPunch[1] = image.getSubimage(2676, 1242, 92, 106);
        highPunch[2] = image.getSubimage(2572, 1242, 96, 106);
        highPunch[3] = image.getSubimage(2437, 1242, 134, 106);
        highPunch[4] = image.getSubimage(2326, 1228, 110, 120);
        highPunch[5] = image.getSubimage(2231, 1236, 94, 112);
        highPunch[6] = image.getSubimage(2140, 1242, 90, 105);
        highPunch[7] = image.getSubimage(2047, 1243, 97, 105);
    }

    private void loadLowKick(){
        lowKick[0] = image.getSubimage(2123, 1050, 70, 105);
        lowKick[1] = image.getSubimage(2015, 1050, 103, 105);
        lowKick[2] = image.getSubimage(1946, 1048, 70, 105);
    }

    private void loadHighKick(){
        highKick[0] = image.getSubimage(1448, 1242, 85, 110);
        highKick[1] = image.getSubimage(1326, 1232, 94, 120);
        highKick[2] = image.getSubimage(1235, 1242, 86, 110);
        highKick[3] = image.getSubimage(1139, 1242, 78, 110);
        highKick[4] = image.getSubimage(1000, 1236, 135, 116);
        highKick[5] = image.getSubimage(892, 1239, 94, 113);
        highKick[6] = image.getSubimage(1448, 1242, 85, 110);
    }

    private void loadLowPowerReact(){
        lowPowerReact[0] = image.getSubimage(1789, 850, 76, 101);
        lowPowerReact[1] = image.getSubimage(1701, 844, 76, 107);
        lowPowerReact[2] = image.getSubimage(1614, 844, 76, 107);
        lowPowerReact[3] = image.getSubimage(1531, 844, 76, 107);
    }

    private void loadHighPowerReact(){
        highPowerReact[0] = image.getSubimage(1567, 651, 66, 125);
        highPowerReact[1] = image.getSubimage(1479, 662, 84, 112);
        highPowerReact[2] = image.getSubimage(1364, 662, 110, 109);
        highPowerReact[3] = image.getSubimage(1225, 662, 135, 99);
        highPowerReact[4] = image.getSubimage(1078, 668, 147, 78);
        highPowerReact[5] = image.getSubimage(2741, 907, 120, 47);
        highPowerReact[6] = image.getSubimage(2621, 903, 120, 51);
        highPowerReact[7] = image.getSubimage(2508, 898, 97, 56);
        highPowerReact[8] = image.getSubimage(2409, 882, 75, 72);
        highPowerReact[9] = image.getSubimage(2318, 862, 79, 92);
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
        if (lowPunchIndex >= 3){
            lowPunchIndex = 0;
            state = IDLE;
            counter = 0;
            attackState = false;

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
        if (highPunchIndex >= 8){
            highPunchIndex = 0;
            state = IDLE;
            counter = 0;
            attackState = false;
        }
        graphics.drawImage(highPunch[highPunchIndex], x, y, w, h, null);
        if (highPunchDelay >= 3){
            highPunchDelay = 0;
            highPunchIndex++;
        }
        highPunchDelay++;
    }

    int lowKickIndex = 0;
    int lowKickDelay = 0;

    public void lowKick(Graphics graphics){
        if (lowKickIndex >= 3){
            lowKickIndex = 0;
            state = IDLE;
            counter = 0;
            attackState = false;
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
        if (highKickIndex >= 7){
            highKickIndex = 0;
            state = IDLE;
            counter = 0;
            attackState = false;
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
        if (lowPowerReactIndex >= 4){
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
        if (highPowerReactIndex >= 10){
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

    public void drawEnemy(Graphics graphics) {
            if (state == IDLE) {
                defaultWalk(graphics);
            } else if (state == LOW_PUNCH) {
                lowPunch(graphics);
            } else if (state == HIGH_PUNCH) {
                highPunch(graphics);
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


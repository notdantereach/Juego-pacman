package lospedros.edu.pacman.process;

import lospedros.edu.pacman.data.Entity;
import lospedros.edu.pacman.ui.GamePanel;
import lospedros.edu.pacman.ui.KeyHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Pacman extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public String nextDirection;

    public Pacman(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        // Hitbox setup (20x20 centered in 32x32 tile)
        setSolidArea(new Rectangle(6, 6, 20, 20));

        setDefaultValues();
    }

    public void setDefaultValues() {
        setX(gp.tileSize * 1);
        setY(gp.tileSize * 1);
        setSpeed(2);
        setDirection("right");
        nextDirection = "right";
    }

    public void update() {
        // Read input
        if (keyH.upPressed) nextDirection = "up";
        if (keyH.downPressed) nextDirection = "down";
        if (keyH.leftPressed) nextDirection = "left";
        if (keyH.rightPressed) nextDirection = "right";

        // Check if we can turn
        boolean canTurn = false;

        // Allow turning if we are close to the center of the tile
        int xRem = getX() % gp.tileSize;
        int yRem = getY() % gp.tileSize;
        int speed = getSpeed();

        // Tolerance logic: if remainder is small (less than speed), consider it aligned
        if (Math.abs(xRem) < speed && Math.abs(yRem) < speed) {
            canTurn = true;
        }

        if (canTurn) {
            String prevDir = getDirection();
            setDirection(nextDirection);

            // Check collision in the NEW direction
            setCollisionOn(false);
            gp.cChecker.checkTile(this);

            if (isCollisionOn()) {
                // If we can't move in nextDirection, revert to previous
                setDirection(prevDir);
            } else {
                // If turn is successful, snap to exact grid to prevent drift
                // This ensures we are perfectly aligned for the new axis
                if (getDirection().equals("up") || getDirection().equals("down")) {
                    setX((getX() + gp.tileSize / 2) / gp.tileSize * gp.tileSize);
                } else {
                    setY((getY() + gp.tileSize / 2) / gp.tileSize * gp.tileSize);
                }
            }
        } else if (isOpposite(getDirection(), nextDirection)) {
            // Allow immediate U-turn anywhere
            setDirection(nextDirection);
        }

        // Move in current direction
        setCollisionOn(false);
        gp.cChecker.checkTile(this);

        if (!isCollisionOn()) {
            switch (getDirection()) {
                case "up":
                    setY(getY() - speed);
                    break;
                case "down":
                    setY(getY() + speed);
                    break;
                case "left":
                    setX(getX() - speed);
                    break;
                case "right":
                    setX(getX() + speed);
                    break;
            }
        } else {
            // COLLISION DETECTED
            // Snap to the nearest grid position to ensure we can turn next time
            int snapX = (getX() + gp.tileSize / 2) / gp.tileSize * gp.tileSize;
            int snapY = (getY() + gp.tileSize / 2) / gp.tileSize * gp.tileSize;
            setX(snapX);
            setY(snapY);
        }

        // Handle tunnel
        if (getX() < -gp.tileSize) setX(gp.screenWidth);
        if (getX() > gp.screenWidth) setX(-gp.tileSize);
    }

    private boolean isOpposite(String dir1, String dir2) {
        if (dir1.equals("up") && dir2.equals("down")) return true;
        if (dir1.equals("down") && dir2.equals("up")) return true;
        if (dir1.equals("left") && dir2.equals("right")) return true;
        if (dir1.equals("right") && dir2.equals("left")) return true;
        return false;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.yellow);
        g2.fillOval(getX(), getY(), gp.tileSize, gp.tileSize);
    }
}
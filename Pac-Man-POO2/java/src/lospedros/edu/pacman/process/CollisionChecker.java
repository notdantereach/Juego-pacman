package lospedros.edu.pacman.process;

import lospedros.edu.pacman.data.Entity;
import lospedros.edu.pacman.tile.Tile;
import lospedros.edu.pacman.ui.GamePanel;

import java.awt.Rectangle;

public class CollisionChecker {

    private final GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        Rectangle solidArea = entity.getSolidArea();
        int entityLeftWorldX = entity.getX() + solidArea.x;
        int entityRightWorldX = entity.getX() + solidArea.x + solidArea.width;
        int entityTopWorldY = entity.getY() + solidArea.y;
        int entityBottomWorldY = entity.getY() + solidArea.y + solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1;
        int tileNum2;

        int[][] mapTileNum = gp.tileM.getMapTileNum();
        int speed = entity.getSpeed();

        switch (entity.getDirection()) {
            case "up":
                entityTopRow = (entityTopWorldY - speed) / gp.tileSize;
                tileNum1 = mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = mapTileNum[entityRightCol][entityTopRow];
                if (isTileCollision(tileNum1) || isTileCollision(tileNum2)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + speed) / gp.tileSize;
                tileNum1 = mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = mapTileNum[entityRightCol][entityBottomRow];
                if (isTileCollision(tileNum1) || isTileCollision(tileNum2)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - speed) / gp.tileSize;
                tileNum1 = mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = mapTileNum[entityLeftCol][entityBottomRow];
                if (isTileCollision(tileNum1) || isTileCollision(tileNum2)) {
                    entity.setCollisionOn(true);
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + speed) / gp.tileSize;
                tileNum1 = mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = mapTileNum[entityRightCol][entityBottomRow];
                if (isTileCollision(tileNum1) || isTileCollision(tileNum2)) {
                    entity.setCollisionOn(true);
                }
                break;
        }
    }

    private boolean isTileCollision(int tileNum) {
        Tile tile = gp.tileM.getTile(tileNum);
        return tile != null && tile.isCollision();
    }
}
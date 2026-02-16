// Existing content of GamePanel.java file

public class GamePanel {
    // Other existing methods and attributes...

    // Store random targets for each enemy
    private List<Point> enemyTargets;

    // Initialize targets for each enemy
    private void initializeEnemyTargets() {
        enemyTargets = new ArrayList<>();
        for (Enemy enemy : enemies) {
            enemyTargets.add(pickRandomWalkableTarget());
        }
    }

    // Picks a random walkable target
    private Point pickRandomWalkableTarget() {
        // Logic to find a random walkable tile on the map
    }

    // Update method to move enemies before the game starts
    public void update() {
        if (enInicio) {
            moveEnemies(); // Call to move enemies in start-screen
        }
        // Other existing update logic...
    }

    // Move each enemy toward its target
    private void moveEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            Point target = enemyTargets.get(i);
            // Logic to move enemy toward target
            // Update enemy position
        }
    }

    // Other existing methods and attributes...
}
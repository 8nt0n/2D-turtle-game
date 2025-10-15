import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {

    // coordinates be like that frfr
    public static int playerRow = 0;
    public static int playerCol = 0;

    static int goalXPos = 0;
    static int goalYPos = 0;

    static String player = "\uD83D\uDC22";
    static String field = "⬜\uFE0F";
    static String enemy = "\uD83E\uDDC3"; // plastic straws grrr!!!!
    static int enemySpawnrate = 100; // the lower, the more often Enemies spawn, 1 == every tile is an enemy
    static final int maxEnemys = 10; // no way were gonna ever have more than 10 enemys
    static int[][] enemysPos = new int[maxEnemys][2]; //2 vals for x,y
    static boolean dead = false;
    static String goal = "⏹\uFE0F";
    static Scanner input = new Scanner(System.in);

    static int level = 1;
    static boolean won = false;

    public static String map[][] = {{player, field, field, field}, {field, field, field, field}, {field, field, field, field}, {field, field, field, field}};

    // --- how many rows / columns the map has --- //
    public static int rows = map.length;
    public static int columns = map[0].length;


    public static void main(String[] args) {
            spawnGoal();

            // gameloop if you can call it that
            while (!Main.dead) {
                while (!won && !dead) {
                    printMap();
                    getInput(); // this looks really confusing but basically get Input just returns the updated map
                    if (level != 1) {
                        updateEnemys();
                    }
                    checkDeath();
                    won = checkWin();
                    clearScreen();
                }

                resetGameState(); // set the player back to 0,0 and reset the map
            }
            gameOver();
    }

    private static void updateEnemys() {
        Random random = new Random();

        System.out.println("Updating Enemys");
        for (int i = 0; i < maxEnemys; i++) {
            if (enemysPos[i][0] != -1) { // if the enemy exists
                // remove the Enemy from the map
                Main.map[enemysPos[i][0]][enemysPos[i][1]] = Main.field;
                // update the Enemys position in enemyPos (random for now)
                enemysPos[i][0] += random.nextInt(3) -1; // val between -1 and 1
                enemysPos[i][1] += random.nextInt(3) -1; // same -- || --

                // clip the position to the box
                if (enemysPos[i][0] > rows-1) {
                    enemysPos[i][0] = rows-1;
                }
                if (enemysPos[i][1] > columns-1) {
                    enemysPos[i][1] = columns-1;
                }
                if (enemysPos[i][0] < 0) {
                    enemysPos[i][0] = 0;
                }
                if (enemysPos[i][1] < 0) {
                    enemysPos[i][1] = 0;
                }
                // re-draw the Enemy
                Main.map[enemysPos[i][0]][enemysPos[i][1]] = Main.enemy;
            }
        }
    }

    private static void gameOver() {
        System.out.println("#---------------------------------#");
        System.out.println("|                                 |");
        System.out.println("|         ☠\uFE0F Game Over ☠\uFE0F         |");
        System.out.println("|                                 |");
        System.out.println("#---------------------------------#");
    }

    private static void checkDeath() {
        System.out.println("Checking for Player death");
        for (int i = 0; i < maxEnemys; i++) {
            if (enemysPos[i][0] == playerRow && enemysPos[i][1] == playerCol) {
                Main.dead = true;
                System.out.println("Player is dead");
            }
        }
        // im so stupid i had this implemented as a return and here was a return false and when i switched to a boolean var
        // i left that in and changed the boolean to false everytime no matter the check i fucking wasted 3 hours of my life on TS
    }

    private static void buildMap(int r, int c) {
        Main.map = new String[r][c];
        // System.out.println("making a " + r + "x" + c + " map");
        Random random = new Random();
        int enemyCounter = 0;


        for (int i = 0; i < r-1; ++i) {
            for (int j = 0; j < c-1; ++j) {
                Main.map[i][j] = field;
                if (random.nextInt(enemySpawnrate) == 1 && enemyCounter < maxEnemys) {
                    Main.map[i][j] = enemy;
                    enemysPos[enemyCounter][0] = i;
                    enemysPos[enemyCounter][1] = j;
                    enemyCounter ++;
                }
            }
        }

        Main.rows = r-1;
        Main.columns = c-1;
        Main.map[0][0] = player;
    }


    private static boolean checkWin() {
        if(goalYPos == playerCol && goalXPos == playerRow && !Main.dead) {
            return true;
        }
        else {
            return false;
        }
    }

    private static void resetEnemyPosition() {
        enemysPos = new int[maxEnemys][2];

        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < maxEnemys; k++) {
                enemysPos[k][i] = -1;
            }
        }
    }

    private static void resetGameState() {
        Random random = new Random();
        enemySpawnrate --;
        resetEnemyPosition();
        buildMap((level + 3) + random.nextInt(3), (level + 3) + random.nextInt(3));

        playerRow = 0;
        playerCol = 0;
        spawnGoal();
        won = false;
        level ++;
        if (!Main.dead) {
            System.out.println("You Won \uD83E\uDD47");
        }

    }

    private static void spawnGoal() {

        // generate random x and y position
        Random random = new Random();

        // i dont know if this works but i ran it a few times and it seems to
        do {
            goalXPos = random.nextInt(rows);
            goalYPos = random.nextInt(columns);
        } while(goalXPos == playerRow && goalYPos == playerCol);


        Main.map[goalXPos][goalYPos] = goal;

    }

    private static void printMap() {
        // System.out.println("printing a " + rows + "x" + columns + " map");
        // print da map
        System.out.println("[  Level " + level + "  ]");
        for (int i = 0; i < Main.columns; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < Main.rows; j++) {
                row.append(Main.map[j][i]);
                row.append(" "); // for visuals since line spacing is really high
            }
            System.out.println(row);
        }

        System.out.println("");
        System.out.println("[    Player pos   ]");
        System.out.println("X: " + playerRow + "   |   Y: " + playerCol);

        System.out.println("");
        System.out.println("[    Enemys pos   ]");
        for (int i = 0; i < 2; i++) {
            StringBuilder tmp = new StringBuilder();
            if (i == 0) {
                tmp.append("X: ");
            }
            else {
                tmp.append("Y: ");
            }

            for (int j = 0; j < maxEnemys; j++) {
                tmp.append(Main.enemysPos[j][i]);
                tmp.append(" "); // for visuals since line spacing is really high
            }
            System.out.println(tmp);
        }
    }

    private static void getInput() {
        char move = input.next().charAt(0); // stack overflow says this is the equivalent of nextchar()
        System.out.println(move);

        if (move == 'w') {
            // move player up
            moveY(-1);
        }
        else if (move == 's') {
            // move player down
            moveY(1);
        }
        else if (move == 'a') {
            // move player up
            moveX(-1);
        }
        else if (move == 'd') {
            // move player down
            moveX(1);
        }

    }

    private static void moveY(int amount) {
        //clear current player position
        Main.map[playerRow][playerCol] = field;

        // update it by Y value
        Main.playerCol += amount;

        // ensure we cant go out of bound
        if (Main.playerCol < 0) {Main.playerCol = 0;}
        if (Main.playerCol > columns-1) {Main.playerCol = columns-1;}

        // draw new player position
        Main.map[playerRow][playerCol] = player;
    }

    private static void moveX(int amount) {
        //clear current player position
        Main.map[playerRow][playerCol] = field;

        // update it by Y value
        Main.playerRow += amount;

        // ensure we cant go out of bound
        if (Main.playerRow < 0) {Main.playerRow = 0;}
        if (Main.playerRow > rows-1) {Main.playerRow = rows-1;}

        // draw new player position
        Main.map[playerRow][playerCol] = player;
    }


    // maaan intellij really doesnt wanna hear that "\033[H\033[2J" stuff
    private static void clearScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }
}
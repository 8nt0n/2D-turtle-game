import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    // straight chat gpt ai wisdom
    public static int playerRow = 0;
    public static int playerCol = 0;

    static int goalXPos = 0;
    static int goalYPos = 0;

    static String player = "\uD83D\uDC22";
    static String field = "\uD83D\uDD32";
    static String goal = "\uD83E\uDD47";
    static Scanner input = new Scanner(System.in);

    public static String map[][] = {{player, field, field, field}, {field, field, field, field}, {field, field, field, field}, {field, field, field, field}};

    public static void main(String[] args) {
            spawnGoal();
            boolean won = false;
            // gameloop if you can call it that
            while (!won) {
                printMap();
                getInput(); // this looks really confusing but basically get Input just returns the updated map
                won = checkWin();
                clearScreen();
            }
            System.out.println("You Won \uD83E\uDD47");

    }
    private static boolean checkWin() {
        if(goalYPos == playerCol && goalXPos == playerRow) {
            return true;
        }
        else {
            return false;
        }
    }


    private static void spawnGoal() {

        // generate random x and y position
        Random random = new Random();

        // i dont know if this works but i ran it a few times and it seems to
        do {
            goalXPos = random.nextInt(4);
            goalYPos = random.nextInt(4);
        } while(goalXPos == playerRow && goalYPos == playerCol);


        System.out.println("xPos: " + goalXPos);
        System.out.println("yPos: " + goalYPos);

        Main.map[goalXPos][goalYPos] = goal;

    }

    private static void printMap() {
        // print da map
        for (int i = 0; i < 4; ++i) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < 4; ++j) {
                row.append(Main.map[j][i]);
                row.append(" "); // for visuals since line spacing is really high
            }
            System.out.println(row);
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
        if (Main.playerCol > 3) {Main.playerCol = 3;}

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
        if (Main.playerRow > 3) {Main.playerRow = 3;}

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
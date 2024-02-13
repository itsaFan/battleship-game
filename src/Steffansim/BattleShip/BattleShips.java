package Steffansim.BattleShip;

import java.util.Scanner;

public class BattleShips {
    public static int oceanRow = 10;
    public static int oceanCol = 10;
    public static String[][] oceanGrid = new String[oceanRow][oceanCol];
    public static boolean displayingOceanLayout;
    public static int playerShips;
    public static int computerShips;
    public static int[][] storeMissedGuesses = new int[oceanRow][oceanCol];
    public static int[][] hideComputerShip = new int[oceanRow][oceanCol];

    public static void main(String[] args) {
        System.out.println("\n**** Welcome to Battle Ships Game ****");
        System.out.println("Right now, the sea is empty.");

        // Ocean Map
        oceanLayout();
        // Player ships
        deployPlayerShips();
        // Computer ships
        deployComputerShips();
        // Gameplay
        do {
            gameplayProgressing();
        }while (playerShips > 0 && computerShips > 0);
        // Game over
        gameOver();
    }


    // Step 1 - Ocean Map Layout
    public static void oceanLayout() {

        // Upper Part - Border Ocean
        System.out.print("\n  ");
        for (int mapBorder = 0; mapBorder < oceanCol; mapBorder++ ) {
            System.out.print(mapBorder);
        } System.out.println();

        // Displaying the Ocean Map if the Layout exist.
        if(displayingOceanLayout) {
            for (int x = 0; x < oceanGrid.length; x++) {
                System.out.print(x + "|");
                for (int y = 0; y < oceanGrid[x].length; y++) {
                    System.out.print(oceanGrid[x][y]);
                }
                System.out.println("|" + x);
            }
        } else {
            //Side Border - Covering the side area of the ocean
            for (int mapBorder = 0; mapBorder < oceanGrid.length; mapBorder++) {
                for (int mapArea = 0; mapArea < oceanGrid[mapBorder].length; mapArea++) {
                    oceanGrid[mapBorder][mapArea] = " ";
                    if (mapArea == 0)
                        System.out.print(mapBorder + "|" + oceanGrid[mapBorder][mapArea]);
                    else if (mapArea == oceanGrid[mapBorder].length - 1)
                        System.out.print(oceanGrid[mapBorder][mapArea] + "|" + mapBorder);
                    else
                        System.out.print(oceanGrid[mapBorder][mapArea]);
                }
                System.out.println();
            }
            displayingOceanLayout = true;
        }

        // Bottom Part - Border Ocean
        System.out.print("  ");
        for(int mapBorder = 0; mapBorder < oceanCol; mapBorder++) {
            System.out.print(mapBorder);
        }   System.out.println();
    }


    //Step 2 - Constructing Player Ships
    public static void deployPlayerShips() {
        Scanner playerInputCoordinate = new Scanner(System.in);

        System.out.println("\nYou Have 5 BattleShips, Deploy All Your Ships in X & Y Coordinate Below:");
        //Player -> must deploy 5 battleships.
        playerShips = 5;
        for (int ship = 1; ship <= playerShips; ) {
            System.out.print("Enter X Coordinate for Your " + ship + ". Ship: ");
            int yCoordinate = playerInputCoordinate.nextInt();
            System.out.print("Enter Y Coordinate for Your " + ship + ". Ship: ");
            int xCoordinate = playerInputCoordinate.nextInt();

            if((xCoordinate >= 0 && xCoordinate < oceanRow) &&
                    (yCoordinate >= 0 && yCoordinate < oceanCol) &&
                    (oceanGrid[xCoordinate][yCoordinate].equals(" ")))
            {
                oceanGrid[xCoordinate][yCoordinate] = "@";
                ship++;
            }
            else if ((xCoordinate >= 0 && xCoordinate < oceanRow) &&
                    (yCoordinate >= 0 && yCoordinate < oceanCol) &&
                    (oceanGrid[xCoordinate][yCoordinate].equals("@"))) {
                System.out.println
                        ("You Can't Place Two or More Ships on the Same Location! It Will Collide with Each Other.");
            }
            else if ((xCoordinate < 0 || xCoordinate >= oceanRow) ||
                    (yCoordinate < 0 || yCoordinate >= oceanCol)) {
                System.out.println
                        ("You Can't Place Your Ship Outside the Grid. Your Ship Will Crash!");
            }
        }
        oceanLayout();
    }


    //Step 3 - Constructing Computer Ships
    public static void deployComputerShips() {

        System.out.println("\nComputer is deploying ships");
        //The computer deploying total of 5 ships.
        computerShips = 5;
        for (int ship = 1; ship <= computerShips; ) {
            int xCoordinate = (int) (Math.random() * 10);
            int yCoordinate = (int) (Math.random() * 10);

            if ((xCoordinate >= 0 && xCoordinate < oceanRow) &&
                    (yCoordinate >= 0 && yCoordinate < oceanCol) &&
                    (oceanGrid[xCoordinate][yCoordinate].equals(" ")))
            {
                hideComputerShip[xCoordinate][yCoordinate] = 1;
                System.out.println(ship + ". Ship Deployed.");
                ship++;
            }
        }
        oceanLayout();
    }

    //Step 4 - Gameplay - Battle

    public static void gameplayProgressing() {
        playerTurn();
        computerTurn();
        oceanLayout();

        System.out.println();
        System.out.println("Your Ships: " + playerShips + " | Computer Ships: " + computerShips);
        System.out.println();
    }

    public static void playerTurn() {
        System.out.println("\nYour Turn:");
        int xCoordinate,
                yCoordinate;

        do {
            Scanner playerTurnInput = new Scanner(System.in);
            System.out.print("Enter Your X Coordinate: ");
            yCoordinate = playerTurnInput.nextInt();
            System.out.print("Enter Your y Coordinate: ");
            xCoordinate = playerTurnInput.nextInt();

            //Player Input Validation
            if ((xCoordinate < 0 || xCoordinate >= oceanRow) || (yCoordinate < 0 || yCoordinate >= oceanCol)) {
                System.out.println("\n---You Can't Place the Ship Outside the " + oceanRow + " by " + oceanCol + " Ocean Grid---\n");
            }
            else if (oceanGrid[xCoordinate][yCoordinate].equals("-")) {
                System.out.println("\n---You Already Guessed There---\n");
            }
        }while ((xCoordinate < 0 || xCoordinate >= oceanRow) || (yCoordinate < 0 || yCoordinate >= oceanCol) || oceanGrid[xCoordinate][yCoordinate].equals("-"));

        //Event
        if (hideComputerShip[xCoordinate][yCoordinate] == 1){
            System.out.println("\n----------------------------------------------------");
            System.out.println("Boom, You Hit the Mark! You Sunk the Computer Ship.");
            System.out.println("____________________________________________________");
            oceanGrid[xCoordinate][yCoordinate] = "!";
            --computerShips;
        }
        switch (oceanGrid[xCoordinate][yCoordinate]) {
            case "@" -> {
                System.out.println("\n----------------------------------------------------");
                System.out.println("Oh No, You Sunk Your Own Ship :(");
                System.out.println("____________________________________________________");
                oceanGrid[xCoordinate][yCoordinate] = "x";
                --playerShips;
            }
            case " " -> {
                System.out.println("\n----------------------------------------------------");
                System.out.println("Sorry, You Missed");
                System.out.println("____________________________________________________");
                oceanGrid[xCoordinate][yCoordinate] = "-";
            }
        }
    }

    public static void computerTurn() {
        System.out.println("\n----------------------------------------------------");
        System.out.println("Computer's Turn");
        int x,
                y;

        do {
            x = (int)(Math.random() * 10);
            y = (int)(Math.random() * 10);

            if ((x >= 0 && x < oceanRow) && (y >= 0 && y < oceanCol)) {
                if (oceanGrid[x][y].equals("@")) {
                    System.out.println("The Computer sunk one of your ships!");
                    System.out.println("____________________________________________________");
                    oceanGrid[x][y] = "x";
                    --playerShips;
                } else if (hideComputerShip[x][y] == 1) {
                    System.out.println("The Computer sunk one of its own ships");
                    System.out.println("____________________________________________________");
                    oceanGrid[x][y] = "!";
                    --computerShips;
                } else if (oceanGrid[x][y].equals(" ")) {
                    System.out.println("Computer missed");
                    System.out.println("____________________________________________________");
                    if(storeMissedGuesses[x][y] != 1)
                        storeMissedGuesses[x][y] = 1;
                }
            }
        }while((x < 0 || x >= oceanRow) || (y < 0 || y >= oceanCol));
    }

    //Step 5 - Game Over
    public static void gameOver() {
        oceanLayout();
        System.out.println("\n----------------------------------------------------");
        System.out.println("Your Ships: " + playerShips + " | Computer Ships: " + computerShips);
        if (playerShips > 0 && computerShips <= 0) {
            System.out.println("Hooray! You Win the Battle :)");
            System.out.println("____________________________________________________\n");
        } else {
            System.out.println("Sorry, you have no ships left, you lost the battle :( ");
            System.out.println("____________________________________________________\n");
        }
    }
}


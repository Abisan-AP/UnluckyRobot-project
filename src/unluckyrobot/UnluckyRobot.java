/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

/**
 *
 * @author Abisan Poothapillai
 */
import java.util.Scanner;
import java.util.Random;
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int x = 0;
        int y = 0;
        int reward;
        int itrCount = 0;
        int totalScore = 300;
        int penaltyDmg = -2000; //cost for exceeding the boundary
        int cost1 = -10;        //cost to move up
        int cost2 = -50;        //cost to move left, right, down
        do{
            displayInfo(x, y, itrCount, totalScore);
            char direction = inputDirection();
            
            if (doesExceed(x, y, direction)){
                System.out.println("Exceed boundry, -2000 damage applied");
                totalScore += penaltyDmg;   
            }
            else {
                switch (direction) {
                    case 'u':
                        y++;
                        break;
                    case 'd':
                        y--;
                        break;
                    case 'l':
                        x--;
                        break;
                    case 'r':
                        x++;
                        break;
                }   
            }
            if (direction == 'u')
                totalScore += cost1;
            else 
                totalScore += cost2;
            
            reward = punishOrMercy(direction, reward());
            totalScore += reward;
            System.out.println();
            itrCount++;
            
        } while (!isGameOver(x, y, totalScore, itrCount));
        System.out.print("Please enter your name (only two words): ");
        String name = toTitleCase(console.nextLine());
        evaluation(totalScore, name);        
    }
    /**
     * It will display all of my info so my position, my iterations, and my score
     * @param x position of my robot on the horizontal line (x axis)
     * @param y position of my robot on the vertical line (y axis)
     * @param itrCount the number of movements made by the player
     * @param totalScore amount of points gathered
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore){
        System.out.printf("%s (%c=%d, %c=%d) %s: %d %s: %d%n", "For point", 'X', x ,'Y', y,
                "at iterations", itrCount, "the total score is", totalScore);
    }
    /**
     * To check if the robot went out of the map (the board)
     * @param x position of my robot on the horizontal line (x axis)
     * @param y position of my robot on the vertical line (y axis)
     * @param direction one of four directions the player could move
     * @return returns true if one of four conditions is met, false otherwise
     */
    public static boolean doesExceed(int x, int y, char direction){
        return (y == 4 && direction == 'u' || x == 0 && direction == 'l' ||
                y == 0 && direction == 'd' || x == 4 && direction == 'r');
    }
    /**
     * This will give us the rewards depending on what the dice rolls 
     * @return It will return either a positive reward or a negative one
     */
    public static int reward(){
        Random rand = new Random();
        int rolls = 6;
        int dice = rand.nextInt(rolls) + 1;
        int reward;
        
        switch (dice) {
            case (1):
                reward = -100;
                break;
            case (2):
                reward = -200;
                break;
            case (3):
                reward = -300;
                break;
            case (4):
                reward = 300;
                break;
            case (5):
                reward = 400;
                break;
            default:
                reward = 600;
        }
        System.out.printf("%s: %d, %s: %d\n", "Dice", dice, "reward", reward);
        return reward;
    }
    /**
     * This will determine if the negative reward will be applied or not 
     * @param direction the direction the player moved (could be u,d,l,r)
     * @param reward the amount of points gained or lost
     * @return it will either return the original reward or the reward with the penalty
     */
    public static int punishOrMercy(char direction, int reward){
        if (reward > 0 || direction != 'u')
            return reward;
        
        Random flip = new Random();
        int headOrTail = 2;     //a coin has 2 sides
        int coin = flip.nextInt(headOrTail);
            
        if (coin == 0) {
            System.out.printf("%s: %s | %s\n", "Coin", "tail",
                    "Mercy, the negative reward is removed.");
            return 0;
        } 
        else {
            System.out.printf("%s: %s | %s\n", "Coin", "head",
                    "No mercy, the negative reward is applied.");
            return reward;
            }
    }
    /**
     * It will change the user's name into title case
     * @param str the user name
     * @return it will return the user's name with both words starting with a capital
     */
    public static String toTitleCase(String str) {
        str = str.toLowerCase();
        int spc = str.indexOf(" ");
        String fN = Character.toTitleCase(str.charAt(0)) + str.substring(1, spc);
        String lN = Character.toTitleCase(str.charAt(spc + 1)) + str.substring(spc + 2);
        return fN + " " + lN;       //fN = firstName, lN = lastName
    }
    /**
     * Says if I won or if I lost the game while showing my name and points
     * @param totalScore the total amount of points 
     * @param name the user name 
     */
    public static void evaluation(int totalScore, String name){
        name = toTitleCase(name);
        if (totalScore >= 2000)
            System.out.printf("%s, %s, %s %d", "Victory", name, "your score is", totalScore);
        else
            System.out.printf("%s, %s, %s %d", "Mission failed", name, "your score is",
                    totalScore);
    }
    /**
     * Makes the user input a valid direction 
     * @return either u,d,l,r and nothing else
     */
    public static char inputDirection(){
        Scanner console = new Scanner(System.in);
        char direction;
        do {
            System.out.print("Please input a valid direction: ");
            direction = console.next().charAt(0);
        } while ((direction != 'u' && direction != 'd' && direction != 'l' 
                && direction != 'r'));
        return direction;
    }
    /**
     * The conditions to end the game (4 conditions)
     * @param x position of our robot on the x axis 
     * @param y position of our robot on the y axis 
     * @param totalScore amount of points I will have 
     * @param itrCount amount of movements
     * @return true if one of the conditions is applied, false otherwise
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount){
        return (totalScore <= -1000 || itrCount > 20 || totalScore >= 2000 || 
                x == 4 && y == 0 || x == 4 && y == 4);  //(4,0) or (4,4) my end states
    }
}

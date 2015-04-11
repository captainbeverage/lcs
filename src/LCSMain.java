import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Stack;
import java.io.*;

/**
 * @author Jordan Harris
 * @since 4/6/2015
 *
 * This program calculates the Normalized Edit Distance and the Longest Common Subsequence (LCS) of two strings.
 * Each string should be the first line in a text file. The program asks the user to enter the names of the
 * two files, then the program reads the strings. The program calculates the Normalized Edit Distance using
 * linear memory by using a table with only 2 rows. It then calculates the LCS using a table which is the size
 * of one string by the size of the other string (n x m). These results are output to a text file entered by the
 * user.
 *
 * Methods: main, getString, getFilename, readFile, getEditDistance, getLCS, outputToFile
 */

public class LCSMain {
    public static void main(String[] args) throws IOException {
        String string1;         // Holds the first string
        String string2;         // Holds the second string
        double editDistance;    // Holds the edit distance
        String lcs;             // Holds the longest common string
        String outputFile;      // Holds the string of the user's desire to output to file

        // Standard input
        Scanner input = new Scanner(System.in);

        // Gets the strings from files entered by the user
        string1 = getString(input);
        string2 = getString(input);

        // Gets the edit distance using linear memory
        editDistance = getEditDistance(string1, string2);

        // Gets the longest common subsequence using mxn memory
        lcs = getLCS(string1, string2);

        // Standard output
        System.out.println("\nOutput:");
        System.out.println(new DecimalFormat("0.000000").format(editDistance));
        System.out.println(lcs + "\n");

        // Asks the user if they'd like to output to file and outputs to file if they enter y or Y
        System.out.print("Would you like to print the output to a file? (enter y or n): ");
        outputFile = input.nextLine();
        if (outputFile.toLowerCase().charAt(0) == 'y')
            outputToFile(editDistance, lcs, input);

        System.out.println("Program complete");
    }

    // Makes a call to get the filename from the user, read the file, and return the string
    public static String getString(Scanner input) throws IOException {
        String inputFilename;   // Holds the name of the first file to open
        String stringFromFile;  // Holds the string read from file

        // Gets the name of the input file
        inputFilename = getFilename(input);
        System.out.println("Reading file...");

        // Reads the file
        stringFromFile = readFile(inputFilename);

        // Returns the string read from the file
        return stringFromFile;
    }

    // Gets the filename to read from the user
    public static String getFilename(Scanner input) throws IOException {
        // Gets the name of the input file
        System.out.print("Enter the name of the file to read: ");
        String inputFilename = input.nextLine();
        File file = new File(inputFilename);

        // User validation for file existence
        while(!file.exists()) {
            System.out.println("Error... File not found.");
            System.out.print("Enter the name of the file to read: ");
            inputFilename = input.nextLine();
            file = new File(inputFilename);
        }

        // Returns the name of the file
        return inputFilename;
    }

    // Opens the file and reads the first string
    public static String readFile(String fileName) throws IOException {
        // Creates a buffered reader
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        // Attempt to read the file
        try {
            // Uses StringBuilder to construct a string from characters read in the file
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            // Appends each character to the string so long as there is a character to add
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }

            return sb.toString();
        } finally {
            br.close();  // Close the file
        }
    }

    // Computes the edit distance between the two words
    public static double getEditDistance(String sideString, String topString) {
        // 2-row Array which holds the edit distance for the characters of the two words
        int[][] editDistanceArray = new int[2][topString.length() + 1];

        // Initialize first row of editDistanceArray
        for (int i = 0; i <= topString.length(); i++) {
            editDistanceArray[0][i] = i;
        }

        // Loops through the side string
        for (int i = 1; i <= sideString.length(); i++) {
            // Initialize second row first cell of editDistanceArray
            editDistanceArray[1][0] = i;

            // Loops through the top string
            for (int j = 1; j <= topString.length(); j++) {

                // If the two characters are the same, it puts the value from the upper-left index at that index.
                // Otherwise, it sets the index to the minimum of the index to the top or left of that index
                if (topString.charAt(j-1) == sideString.charAt(i-1))
                    editDistanceArray[1][j] = editDistanceArray[0][j-1];
                else
                    editDistanceArray[1][j] = Math.min(editDistanceArray[0][j], editDistanceArray[1][j-1]) + 1;
            }

            // Copies the bottom row to the top row in order to use linear memory
            for (int j = 0; j <= topString.length(); j++) {
                editDistanceArray[0][j] = editDistanceArray[1][j];
            }
        }

        int totalLength = sideString.length() + topString.length();          // Holds the combined length of the two strings
        double distance = (double)editDistanceArray[1][topString.length()];  // Holds the edit distance
        return (totalLength - distance) / totalLength;                       // Returns the normalized edit distance
    }

    // Calculates the Longest Common Subsequence
    public static String getLCS(String leftString, String topString) {
        // 2-row Array which holds the edit distance for the characters of the two words
        int[][] editDistanceArray = new int[leftString.length() + 1][topString.length() + 1];

        // Creates a stack for inputting the lcs characters
        Stack<Character> lcsStack = new Stack<Character>();

        // Holds the lcs string
        String lcs = "";

        // Initialize first row of editDistanceArray
        for (int i = 0; i <= topString.length(); i++) {
            editDistanceArray[0][i] = i;
        }

        // Loops through the side string
        for (int i = 1; i <= leftString.length(); i++) {
            // Initialize second row first cell of editDistanceArray
            editDistanceArray[i][0] = i;

            // Loops through the top string
            for (int j = 1; j <= topString.length(); j++) {

                // If the two characters are the same, it puts the value from the upper-left index at that index.
                // Otherwise, it sets the index to the minimum of the index to the top or left of that index
                if (topString.charAt(j-1) == leftString.charAt(i-1))
                    editDistanceArray[i][j] = editDistanceArray[i-1][j-1];
                else
                    editDistanceArray[i][j] = Math.min(editDistanceArray[i-1][j], editDistanceArray[i][j-1]) + 1;
            }
        }

        int i = leftString.length();  // Row index initialized to the value of the left string's length
        int j = topString.length();   // Column index initialized to the value of the top string's length

        // While the row or column index hasn't run off the table
        while (i > 0 && j > 0) {

            // If the two characters match, push the character onto the stack and decrement the row and column indices.
            // Otherwise, move left if the left index is less than the top, or move up if the top index is left than the left
            if (leftString.charAt(i-1) == topString.charAt(j-1)) {
                lcsStack.push(leftString.charAt(i-1));
                i--;
                j--;
            } else if (editDistanceArray[i][j-1] <= editDistanceArray[i-1][j]) {
                j--;
            } else {
                i--;
            }
        }

        // Until the stack is empty, pop each character and add it to the string
        while (!lcsStack.empty()) {
            lcs += lcsStack.pop();
        }

        // Return the lcs
        return lcs;
    }

    // Outputs the Normalized Edit Distance and Longest Common Subsequence to file
    public static void outputToFile(double editDistance, String lcs, Scanner input) throws IOException {
        // Gets the output filename from the user
        System.out.print("Enter the output filename to write to: ");
        String outputFilename = input.nextLine();

        System.out.println("Outputting to file...");

        PrintWriter outputFile = new PrintWriter(outputFilename);

        // Writes the normalized edit distance (formatted) and LCS to the file and closes the file
        outputFile.println(new DecimalFormat("0.000000").format(editDistance));
        outputFile.println(lcs);
        outputFile.close();

        System.out.println("Please see " + outputFilename + " for output.");
    }
}

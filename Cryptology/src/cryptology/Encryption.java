package cryptology;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class encrypts using the cipher provided by the main class
 *
 * @author Breanna Burd
 */
public class Encryption {
    
    /**
    Returns the encrypted string after using the given cipher
    */
    public static String encrypt(String code, String cipher) {
        if(code == null || cipher == null) return code;
        String encryptedString = null;
        switch(cipher){
            case "ATBASH":
                encryptedString = encryptAtbash(code);
                break;
            case "SHIFT":
                encryptedString = encryptShift(code);
                break;
            case "REPEATING KEYWORD":
                encryptedString = encryptRepeatingKeyword(code);
                break;
            case "PLAYFAIR":
                encryptedString = encryptPlayfair(code);
                break;
            case "AFFINE":
                encryptedString = encryptAffine(code);
                break;
                /*
            case "RAIL-FENCE" :
                encryptedString = encryptRailFence(code);
                break;
            case "POLYBIUS SQUARE" :
                encryptedString = encryptPolybiusSquare(code);
                break;
                */
        }
        
        return encryptedString;
    }
    
    /**
    Returns the encrypted string using Atbash. 
    Ex: a->z, b->y, c->x,...,z->a
    */
    public static String encryptAtbash(String code) {
        String encoded = "";
        for(int i = 0; i < code.length(); i++){
            char letter = code.charAt(i);
            //If it's a letter, swap using placements so a-z, b-y, ...
            if(Character.isLetter(letter)){
                encoded += (char) ('A' + ('Z' - letter));
            }
            else
                encoded += letter;
        }
        System.out.println("The encoded string using Atbash is: " + encoded);
        return encoded;
        
    }

    /**
    Returns the encrypted string using Caesar ciper. This is a shift cipher
    using the given character key.
    */
    public static String encryptShift(String code) {
        System.out.println("Enter the shift character key");
        Scanner in = new Scanner(System.in);
        char key = (in.next().toUpperCase()).charAt(0);
        while(key < 'A' || key > 'Z'){
            System.out.println("Invalid key for Shift Cipher");
            key = (in.next()).charAt(0);
        }
        String encoded = "";
        for(int i = 0; i < code.length(); i++) {
            char letter = code.charAt(i);
            if(Character.isLetter(letter)){
                //Using the first letter of the key, use that as the shift
                char encodedLetter = (char) (letter + (key - 'A'));
                if(encodedLetter > 'Z'){
                    encodedLetter -= 26;
                }
                encoded += encodedLetter;
                
            }
            else
                encoded += letter;
        }
        System.out.println("The encoded string using Shift Cipher is: " + encoded);
        return encoded;
    }
    
    /**
     Returns the encrypted string using Repeating keyword. This is a shift 
    cipher that uses the shift for each letter of the given word key.
    Ex: Using "WORD" as the key would mean that for every 4th letter starting 
    at the beginning, the shift would be for the letter W; every 4th leter 
    starting at index 1 would shift for the letter O; every 4th letter 
    starting at index 2 would shift for the letter R; every 4th letter
    starting at index 3 would shift for the letter D.
    */
    public static String encryptRepeatingKeyword(String code) {
        System.out.println("Enter the keyword");
        Scanner in = new Scanner(System.in);
        String key = "";
        
        boolean goodKey = false;
        while(!goodKey){
            key = in.next().toUpperCase();
            for(int i = 0; i < key.length(); i++) {
                if(key.charAt(i) < 'A' || key.charAt(i) > 'Z'){
                    System.out.println("Invalid key for Repeating Keyword Cipher");
                }
                else
                    goodKey = true;
            }
        }
        
        //Remove any  non-letter characters in the code 
        String newCode = "";
        for(int i = 0; i < code.length(); i++){
            if(Character.isLetter(code.charAt(i)))
                newCode += code.charAt(i);
        }
            
        String encoded = "";
        for(int i = 0; i < newCode.length(); i++) {
            char letter = newCode.charAt(i);
            char encodedLetter = (char) (letter + (key.charAt(i % key.length()) - 'A'));
            if(encodedLetter > 'Z'){
                encodedLetter -= 26;
            }
            encoded += encodedLetter;
        }
        System.out.println("The encoded string using Repeating Keyword Cipher (Vigenère Cipher) is: " + encoded);
        return encoded;
    }
    
    /**
    Returns the encrypted string using Wheatstone-Playfair Cipher. This
    creates a 5X5 array of the alphabet (I and J will both be encrypted
    as the letter I) that is used. Letters are grouped into pairs. If they are 
    in the same column, they will move down the column. If they are in the 
    same row, they will move right in the row. If they form a rectangle,
    the rows will stay the same, but they will take the letter in the other's 
    column.
    */
    public static String encryptPlayfair(String code) {
        System.out.println("Enter the keyword");
        Scanner in = new Scanner(System.in);
        String key = "";
        boolean goodKey = false;
        while(!goodKey){
            key = in.next().toUpperCase();
            for(int i = 0; i < key.length(); i++) {
                if(key.charAt(i) < 'A' || key.charAt(i) > 'Z' || key.charAt(i) == 'J'){
                    System.out.println("Invalid key for Playfair Cipher");
                    break;
                }
                else
                    goodKey = true;
            }
        }
        
        for(int i = 0; i < code.length(); i++) {
            if(code.charAt(i) == 'J')
                    code = code.substring(0, i) + "I" + code.substring(i+1);
        }
        
        //Leave out J because I/J is used for our code
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        
        String cipherOrder = "";
        for(int i = 0; i < key.length(); i++){
            if(!cipherOrder.contains(key.substring(i, i+1))) {
                cipherOrder += key.charAt(i);
            }
        }
        for(int i = 0; i < alphabet.length(); i++){
            if(!cipherOrder.contains(alphabet.substring(i, i+1))) {
                cipherOrder += alphabet.charAt(i);
            }
        }
        
        int gridSize = 5;
        String cipherCode[][] = new String[gridSize][gridSize];
        int counter = 0;
        while(counter < cipherOrder.length()-1){
            for(int i = 0; i < gridSize; i++) {
                for(int j = 0; j < gridSize; j++) {
                    cipherCode[i][j] = cipherOrder.substring(counter, counter+1);
                    counter++;
                }
            }
        }
        //Print the grid for a Playfair cipher
        System.out.println("Playfair cipher: ");
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++) {
                System.out.print(cipherCode[i][j] + "\t");
            }
            System.out.println();
        }
        
        //Remove any  non-letter characters in the code 
        String newCode = "";
        for(int i = 0; i < code.length(); i++){
            if(Character.isLetter(code.charAt(i)))
                newCode += code.charAt(i);
        }
        
        //Split the code into 2-letter segments
        //If there is a double letter, place an X in between them
        //If the last group only has 1 letter, add an X to the end
        String splitCode = "";
        for(int i = 1; i < newCode.length(); i++){
            if(newCode.charAt(i) == newCode.charAt(i-1)){
                splitCode += (newCode.charAt(i-1) + "X ");
            } else {
                splitCode += (newCode.substring(i-1, i+1) + " ");
                i++;
            }
            if(i == newCode.length()-1 && splitCode.charAt(i) == ' ')
                splitCode += (newCode.substring(i) + "X");
        }
        
        
        String encoded = "";
        //Check the row and column of both letters in each group
        //We add 3 because each has 2 letters and a space
        int row1 = 0;
        int row2 = 0;
        int col1 = 0;
        int col2 = 0;
        for(int i = 0; i < splitCode.length(); i =i+3){
            for(int j = 0; j < gridSize; j++){
                for(int k = 0; k < gridSize; k++) {
                    if(cipherCode[j][k].equals(splitCode.substring(i, i+1))){
                        row1 = j;
                        col1 = k;
                    }
                    if(cipherCode[j][k].equals(splitCode.substring(i+1, i+2))){
                        row2 = j;
                        col2 = k;
                    }
                }
            }
            //Same row, shift right 1 or back to the beginning of the
            //same row
            if(row1 == row2){
                encoded += (cipherCode[row1][(col1 + 1) % gridSize] +
                            cipherCode[row2][(col2 + 1) % gridSize]);
            }
            //Same col, shift down 1 of back to the top of the same col
            if(col1 == col2) {
                encoded += (cipherCode[(row1 + 1) % gridSize][col1] +
                            cipherCode[(row2 + 1) % gridSize][col2]);
            }
            //Different Row different col, each letter keeps its row, 
            //but the column matches the alternate letter's col
            if(row1 != row2 && col1 != col2){
                encoded += (cipherCode[row1][col2] + cipherCode[row2][col1]);
            }
        }
        System.out.println("The encoded string usingPlayfair Cipher is: " +encoded);
        
        return encoded;
    }
   
    /**
    Returns the encrypted string using Affine. This uses two numbers, a and b,
    in order to encrypt each letter. It uses a mathematical function:
    encoded = (a*letter + b) % 26, where a is relatively prime to 26. 
    */
    public static String encryptAffine(String code) {
        System.out.println("Enter an integer relatively prime to 26");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        while(gcd(a, 26) > 1){
            System.out.println("Please pick an integer relatively prime to 26.");
            a = in.nextInt();
        } 
        System.out.println("Enter an integer b");
        int b = in.nextInt();
        String encoded = "";       
        
        for(int i = 0; i < code.length(); i++) {
            char letter = code.charAt(i);
            if(Character.isLetter(letter)){
                //Move the characters to the first 26 ASCII values
                //A is ASCII value 65
                letter -= 65;
                char encodedLetter = (char) ((a*letter + b) % 26);
                encodedLetter += 65;
                encoded += encodedLetter;
                
            }
            else
                encoded += letter;
        }
        System.out.println("The encoded string using Affine is: " + encoded);
        return encoded; 
    }
    
    /**
     * Returns the gcd between two given integers
     */
    private static int gcd(int a, int b){
        if(b == 0)
            return a;
        return gcd(b, a % b);
    }

    /**
     * 
     */
    private static String encryptRailFence(String code) {
        System.out.println("Enter an integer key");
        Scanner in = new Scanner(System.in);
        int key = in.nextInt();
        int len = code.length();
        char array[][] = new char[key][len];
        
        //Fill array with tabs
        for(int i = 0; i< key; i++){
            for(int j = 0; j < len; j++){
                array[i][j] = '*';
            }
        }
        int change = 1;
        int index = 0;
        int k = 0;
        for(int i = 0; i < len; i++) {
            if(index < len){
                array[k][i] = code.charAt(index);
                k = k + change;
                if(k == key - 1 || k == 0)
                    change *= -1;
                index++;
            }   
        }
        
        String encoded = "";
        //Print array
        for(int i = 0; i < key; i++) {
            for(int j = 0; j < len; j++){
                System.out.print(array[i][j]);
                if(array[i][j] != '*')
                    encoded += array[i][j];
            } 
            System.out.println();
        }
        
        System.out.println("The encoded string using Rail-Fence is: " + encoded);        
        return encoded;        
    }
    
    
    /**
     * Similar to playfair cipher, but matches each individual letter to 
     * the row and column match. So each letter would get matched to a pair 
     * of letters. Ex: if it was in row 3 and column 0, it would get matched to
     * DA. They rows and columns are labeled with A, B, C, D, E in order.
     * I/J are combined again.
     * 
     * @return the encoded string
     */
    /*
    public static String encryptPolybiusSquare(String code) {
        System.out.println("Enter a keyword");
        Scanner in = new Scanner(System.in);
        String key = in.next().toUpperCase();
        
        //Leave out J because I/J is used for our code
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        
        String cipherOrder = "";
        for(int i = 0; i < key.length(); i++){
            if(!cipherOrder.contains(key.substring(i, i+1))) {
                cipherOrder += key.charAt(i);
            }
        }
        for(int i = 0; i < alphabet.length(); i++){
            if(!cipherOrder.contains(alphabet.substring(i, i+1))) {
                cipherOrder += alphabet.charAt(i);
            }
        }
        
        int gridSize = 5;
        String cipherCode[][] = new String[gridSize][gridSize];
        int counter = 0;
        while(counter < cipherOrder.length()){
            for(int i = 0; i < gridSize; i++) {
                for(int j = 0; j < gridSize; j++) {
                    cipherCode[i][j] = cipherOrder.substring(counter, counter+1);
                    counter++;
                }
            }
        }
        //Print the grid for a Playfair cipher
        System.out.println("Polybius Cipher: ");
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j < gridSize; j++) {
                System.out.print(cipherCode[i][j] + "\t");
            }
            System.out.println();
        }
        
        String encoded = "";
        
        for(int i = 0; i < code.length(); i++) {
            char letter = code.charAt(i);
            if(Character.isLetter(letter)){
                for(int k = 0; k < gridSize; k++){
                    for(int j = 0; j < gridSize; j++) {
                        if(cipherCode[k][j].equals(letter))
                            encoded = encoded + (char)(k + 65) + (char)(j + 65);
                    }
                }
            }
        }
        
        return encoded;
    }
*/
}

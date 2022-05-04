package cryptology;

import java.util.Scanner;

/**
 * Prints the decoded string to the standard output given a cipher and
 * encrypted string.
 * 
 * @author Bre Burd
 */
public class Decryption {
    
    public static String decrypt(String code, String cipher) {
        if(code == null || cipher == null) return code;
        String decryptedString = null;
        switch(cipher){
            case "ATBASH":
                decryptedString = decryptAtbash(code);
                break;
            case "SHIFT":
                decryptedString = decryptShift(code);
                break;
            case "REPEATING KEYWORD":
                decryptedString = decryptRepeatingKeyword(code);
                break;
            case "PLAYFAIR":
                decryptedString = decryptPlayfair(code);
                break;
            case "AFFINE":
                decryptedString = decryptAffine(code);
                break;
        }

        return decryptedString; 
    }
    
    /**
     * Returns the decoded string after using Atbash which maps 
     * a->z, b->y, c->x, ..., z->a
     * @param code
     * @return 
     */
    public static String decryptAtbash(String code) {
        String decoded = "";
        for(int i = 0; i < code.length(); i++){
            char letter = code.charAt(i);
            //If it's a letter, swap using placements so a-z, b-y, ...
            if(Character.isLetter(letter)){
                decoded += (char) ('A' + ('Z' - letter));
            }
            else
                decoded += letter;
        }
        System.out.println("The decoded string using Atbash is: " + decoded);
        return decoded;
    }
    
    /**
     * Returns the decoded string after using Caesar's cipher, which is 
     * a shift cipher using a 1-character shift.
     */
    public static String decryptShift(String code) {
        System.out.println("Enter the shift character key");
        Scanner in = new Scanner(System.in);
        char key = (in.next().toUpperCase()).charAt(0);
        while(key < 'A' || key > 'Z'){
            System.out.println("Invalid key for Shift Cipher");
            key = (in.next()).charAt(0);
        }
        String decoded = "";
        for(int i = 0; i < code.length(); i++) {
            char letter = code.charAt(i);
            if(Character.isLetter(letter)){
                //Using the first letter of the key, use that as the shift
                char decodedLetter = (char) (letter - (key - 'A'));
                
                if(decodedLetter < 'A'){
                    decodedLetter += 26;
                }
                
                decoded += decodedLetter;
                
            }
            else
                decoded += letter;
        }
        System.out.println("The decoded string using Shift Cipher is: " + decoded);
        return decoded;
    }
    
    /**
     Returns the decrypted string using Repeating keyword
    */
    public static String decryptRepeatingKeyword(String code) {
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
            
        String decoded = "";
        for(int i = 0; i < newCode.length(); i++) {
            char letter = newCode.charAt(i);
            char decodedLetter = (char) (letter - (key.charAt(i % key.length()) - 'A'));
            if(decodedLetter < 'A'){
                decodedLetter += 26;
            }
            decoded += decodedLetter;
        }
        System.out.println("The decoded string using Repeating Keyword Cipher (Vigenère Cipher) is: " + decoded);
        return decoded;
    }
    
    /**
    Returns the encrypted string using Wheatstone-Playfair Cipher
    */
    public static String decryptPlayfair(String code) {
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
        for(int i = 1; i < newCode.length(); i = i + 2){
                splitCode += (newCode.substring(i-1, i+1) + " ");
        }
        
        System.out.println(splitCode);
        
        String decoded = "";
        //Check the row and column of both letters in each group
        //We add 3 because each has 2 letters and a space
        int row1 = 0;
        int row2 = 0;
        int col1 = 0;
        int col2 = 0;
        for(int i = 0; i < splitCode.length(); i = i+3){
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
                decoded += (cipherCode[row1][(col1 + 4) % gridSize] +
                            cipherCode[row2][(col2 + 4) % gridSize]);
            }
            //Same col, shift down 1 of back to the top of the same col
            //I added 4 because -1 % 5 would not give us 5
            if(col1 == col2) {
                decoded += (cipherCode[(row1 + 4) % gridSize][col1] +
                            cipherCode[(row2 + 4) % gridSize][col2]);
            }
            //Different Row different col, each letter keeps its row, 
            //but the column matches the alternate letter's col
            if(row1 != row2 && col1 != col2){
                decoded += (cipherCode[row1][col2] + cipherCode[row2][col1]);
            }
            decoded += " ";
        }
        System.out.println(decoded);
        
        //Remove the X's that shouldn't be there
        String decodedNoX = "";
        for(int i = 0; i < decoded.length()-1; i++) {
            if(decoded.charAt(i) == 'X' && decoded.charAt(i+1) == ' '){
                if(i < decoded.length() - 2 && decoded.charAt(i+2) == decoded.charAt(i-1))
                    i++;
            }
            else
                decodedNoX += decoded.charAt(i);
            //System.out.println(decodedNoX);
        }
        
        //Remove any  non-letter characters in the code 
        String decodedNoSpace = "";
        for(int i = 0; i < decodedNoX.length(); i++){
            if(Character.isLetter(decodedNoX.charAt(i)))
                decodedNoSpace += decodedNoX.charAt(i);
        }
        System.out.println("The decoded string using Playfair Cipher is: " + decodedNoSpace);
        
        return decodedNoSpace;
    }
    
    /**
    Returns the encrypted string using Affine. This uses two numbers, a and b,
    in order to encrypt each letter. It uses a mathematical function:
    encoded = (a*letter + b) % 26, where a is relatively prime to 26. 
    */
    public static String decryptAffine(String code) {
        System.out.println("Enter an integer relatively prime to 26");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        while(gcd(a, 26) > 1){
            System.out.println("Please pick an integer relatively prime to 26.");
            a = in.nextInt();
        } 
        System.out.println("Enter an integer b");
        int b = in.nextInt();
        String decoded = "";       
        
        for(int i = 0; i < code.length(); i++) {
            char letter = code.charAt(i);
            if(Character.isLetter(letter)){
                //Move the characters to the first 26 ASCII values
                //A is ASCII value 65
                letter -= 65;
                int num = (inverse(a))*(letter - b);
                int mod = num%26;
                if(mod < 0)
                    mod = mod + 26;
                char decodedLetter = (char) (mod);
                decodedLetter += 65;
                decoded += decodedLetter;  
            }
            else
                decoded += letter;
        }
        System.out.println("The decoded string using Affine is: " + decoded);
        return decoded; 
    }
    
    /**
     * Returns the gcd of two given integers 
     */
    private static int gcd(int a, int b){
        if(b == 0)
            return a;
        return gcd(b, a % b);
    }

    /**
        Returns the inverse of a where x*a = 1 Mod 26
    */
    private static int inverse(int a) {
        for(int i = 0; i < 26; i++)
            if((a * i)% 26 == 1)
                return i;
        return -1;
    }
}

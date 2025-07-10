package cryptology;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class breaks codes given the type of cipher used.
 *
 * @author Bre Burd
 */
public class Breaking {
    
    public static void breakCode(String code, String cipher) {
        if(code == null || cipher == null) return;
        switch(cipher){
            case "ATBASH":
                breakAtbash(code);
                break;
            case "SHIFT":
                breakShift(code);
                break;
            case "REPEATING KEYWORD":
                breakRepeatingKeyword(code);
                break;
        }
    }

    /**
     * Returns the decrypted string after breaking Atbash.
     * Note: Breaking Atbash is the same as decrypting Atbash
     */
    private static void breakAtbash(String code) {
        Decryption.decryptAtbash(code);
    }

    /**
     * Displays all of the possible letter shifts so the breaker can see what 
     * shift was used to encrypt. 
     */
    private static void breakShift(String code) {
        String output;
        Scanner in = new Scanner(System.in);
        for(char key = 'A'; key < 'Z'; key++){
            output = "";
            for(int j = 0; j < code.length(); j++){
                char letter = code.charAt(j);
                if(Character.isLetter(letter)){
                    //Using the first letter of the key, use that as the shift
                    char decodedLetter = (char) (letter - (key - 'A'));

                    if(decodedLetter < 'A'){
                        decodedLetter += 26;
                    }

                    output += decodedLetter;
                }
                else
                    output += letter;
            }
            System.out.println("This is using a shift of " + (char) key + 
                    " to break the message: " + output);
            System.out.println();
        }
    }

    private static void breakRepeatingKeyword(String code) {
        
        //Remove any  non-letter characters in the code 
        String newCode = "";
        for(int i = 0; i < code.length(); i++){
            if(Character.isLetter(code.charAt(i)))
                newCode += code.charAt(i);
        }
        System.out.println(newCode);
        
        //Check for any repeated pairs of letters of length 3
        ArrayList<Integer> listOfIndeces = new ArrayList<>();
        for(int i = 0; i < newCode.length() - 5; i++){
            String tryingWord = newCode.substring(i,i+3);
            for(int j = i+3; j < newCode.length()-2; j++){
                if(tryingWord.equals(newCode.substring(j,j+3)))
                    listOfIndeces.add(j-i);
            }
        }
        for(int x : listOfIndeces)
            System.out.print(x + "\t ");
        System.out.println();
        
        System.out.println("Do any of the lengths look wrong? Enter 0 if there are none");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        while(num != 0){
            listOfIndeces.remove((Integer) num);
            System.out.println("New Lengths between repeating sequences: ");
            for(int x : listOfIndeces)
                System.out.print(x + "\t ");
            System.out.println();
            num = in.nextInt();
        }
        
        //Finds the gcd of teh items in the list
        int gcd = gcd(listOfIndeces.get(0), listOfIndeces.get(1));
        for(int i = 2; i < listOfIndeces.size(); i++){
            if(gcd(listOfIndeces.get(i-1), listOfIndeces.get(i)) < gcd)
                gcd = gcd(listOfIndeces.get(i-1), listOfIndeces.get(i));
        }
        
        System.out.println("The gcd of the keywords is: " + gcd);
                
        String[] splitCode = new String[gcd];
        for(int i = 0; i < gcd; i++)
            splitCode[i] = "";
        
        //Now split the code into groups of the gcd length
        for(int i = 0; i < gcd; i++){
            for(int j = 0; j < newCode.length(); j++){
                if((j-i) % gcd == 0)
                    splitCode[i] += newCode.charAt(j);
            }
        }
        
        int numLetters = 26;
        int[][] letterCount = new int[gcd][numLetters];
        for(int i = 0; i < gcd; i++){
            System.out.println(splitCode[i]);
            for(int j = 0; j < numLetters; j++)
                letterCount[i][j] = 0;
        }
        
        //Count the number of each chars in each split word to 
        //use frequency analysis
        for(int i = 0; i < gcd; i++){
            for(int j = 0; j < splitCode[i].length(); j++){
                int charPosition = (int) (splitCode[i].charAt(j)- 'A');
                letterCount[i][charPosition]++;
            }
        }
        
        for(int i = 0; i < gcd; i++){
            for(int j = 0; j < numLetters; j++){
                System.out.print((char) ('A' + j)  + " : " + letterCount[i][j] + ", ");
            }
            System.out.println();
            System.out.println();
        }
        
        //Pick a shift guess for each split word
        char[] shift = new char[gcd];
        
        do{
            
            for(int i = 0; i < gcd; i++)
                shift[i] = 0;
            System.out.println("Enter a shift for each of the groups of letters.");

            for(int i = 0; i < gcd; i++)
                shift[i] = in.next().toUpperCase().charAt(0);
            
            String[] newSplitCode = new String[gcd];
            for(int i = 0; i < gcd; i++) {
                newSplitCode[i] = "";
            }
            
            for(int i = 0; i < gcd; i++){
                newSplitCode[i] = shiftDecode(splitCode[i], shift[i]);
            }

            String decoded = "";
            for(int i = 0; i < newSplitCode[0].length(); i++) {
                for(int j = 0; j < gcd; j++) {
                    if(i < newSplitCode[j].length()){
                        decoded += newSplitCode[j].charAt(i);
                    }
                }
            }
            System.out.println(decoded);
            System.out.println("Above is your guess. Is this correct? (y / n)");
        }while(in.next().equals("n"));
       
    }
        
    private static String shiftDecode(String code, char key){   
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
    
}

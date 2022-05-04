
package cryptology;

import java.util.Scanner;

/**
 * This is the main program that is run to encipher, decipher, and break
 * codes.
 * 
 * http://practicalcryptography.com/ was used to learn about ciphers
 *
 * @author Breanna Burd
 */
public class Cryptology {

    public static void main(String[] args) {
        //Endless loop so the user can do multiple tasks
        while(true){
            //Asks the user if they want to encode, decode, or break a code.
            System.out.println("Do you want to encode (e), decode (d), or break (b) a code?\n"
                    + "Please press (q) to quit");
            Scanner input = new Scanner(System.in);
            String choice = input.next().toLowerCase();
            if(choice.equals("q")){
                System.out.println("Thank you :)");
                return;
            }
            if(!choice.equals("e") && !choice.equals("d") && !choice.equals("b")){
                System.out.println("Invalid Response");
            }
            else {
                String task ="";
                if(choice.equals("e"))
                    task = "encipher";
                if(choice.equals("d"))
                    task = "decipher";
                if(choice.equals("b"))
                    task = "break";

                //Asks the user what cipher is used
                System.out.println("What cipher would you like to use?");
                input = new Scanner(System.in);
                String cipher = input.nextLine().toUpperCase();
                if(checkCipher(cipher)){
                    System.out.println("What is the code you wish to " + task + "?");
                    input = new Scanner(System.in);
                    String code = input.nextLine().toUpperCase();
                    switch(choice){
                        case "e": 
                            Encryption.encrypt(code, cipher);
                            break;
                        case "d":
                            Decryption.decrypt(code, cipher);
                            break;
                        case "b":
                            Breaking.breakCode(code, cipher);
                            break;
                    }
                }
                else
                    System.out.println("Invalid cipher");
            }
        }
    } 

    /**
     * Checks to see if the cipher is a valid entry
     */
    private static boolean checkCipher(String cipher) {
        switch(cipher){
            case "ATBASH" :
            case "SHIFT" :
            case "REPEATING KEYWORD" :
            case "PLAYFAIR" :
            case "AFFINE" :
            //case "RAIL-FENCE" :
            //case "POLYBIUS SQUARE" :
                return true;
            default :
                return false;
        }
    }
}

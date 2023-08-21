//Qinzhi Peng ;qinzhip
package exam2;

import java.util.Scanner;

public class Messenger {

	Message[] messages = {new Caesar(), new Keyword()}; //these objects must be used for encryption / decryption. No new instances of Caesar or Keywold should be created.
	Scanner input = new Scanner(System.in); //to take user inputs

	public static void main(String[] args) {
		Messenger messenger = new Messenger();
		messenger.runMessenger();
	}

	/** runMessenger() prints the menu options, takes user input,
	 * invokes methods from appropriate classes to encrypt or decrypt
	 * messages, and then prints the output
	 */
	void runMessenger() {
		System.out.println("Choose ...");
		System.out.println("1. Caesar encryption");
		System.out.println("2. Keyword encryption");
		System.out.println("3. Caesar decryption");
		System.out.println("4. Keyword decryption");
		int choice = input.nextInt();
		input.nextLine(); //to clear buffer3
		
		//write rest of your code here
		switch (choice) {
		case 1 :
			System.out.println("Enter message to encrypt");
			messages[0].message = input.nextLine();			
			System.out.println("Enter key");
			messages[0].key = input.nextLine();
			messages[0].setMessage(messages[0].message, messages[0].key);		
			String encrypted = messages[0].encrypt();	
			System.out.println("Encrypted message: " + encrypted);
			
			break;
			
		case 2 :
			System.out.println("Enter message to encrypt");
			messages[1].message = input.nextLine();			
			System.out.println("Enter key");
			messages[1].key = input.nextLine();
			messages[1].setMessage(messages[1].message, messages[1].key);		
			encrypted = messages[1].encrypt();	
			System.out.println("Encrypted message: " + encrypted);
			
			break;
			
		case 3 :
			System.out.println("Enter message to encrypt");
			messages[0].message = input.nextLine();			
			System.out.println("Enter key");
			messages[0].key = input.nextLine();
			messages[0].setMessage(messages[0].message, messages[0].key);		
			String decrypted = messages[0].decrypt();	
			System.out.println("Encrypted message: " + decrypted);
			
			break;
			

			
		case 4 :
			System.out.println("Enter message to encrypt");
			messages[1].message = input.nextLine();			
			System.out.println("Enter key");
			messages[1].key = input.nextLine();
			messages[1].setMessage(messages[1].message, messages[1].key);		
			decrypted = messages[1].decrypt();	
			System.out.println("Encrypted message: " + decrypted);
			
			break;
		
			
		}
	
		
	}
}
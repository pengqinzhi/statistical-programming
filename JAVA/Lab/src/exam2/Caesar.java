//Qinzhi Peng ;qinzhip
package exam2;

public class Caesar extends Message{
	@Override
	public String encrypt() {
		StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
		    if (character != ' ') {
		        int oriIndex = character - 'a';
		        int newIndex = (oriIndex + Integer.parseInt(key)) % 26;
		        char newCharacter = (char) ('a' + newIndex);
		        result.append(newCharacter);
		    } else {
		        result.append(character);
		    }
		}
		String encrypted = result.toString();
		
		return encrypted;
	
	}
	@Override
	public String decrypt() {
		StringBuilder result = new StringBuilder();
		for (char character : message.toCharArray()) {
		    if (character != ' ') {
		        int oriIndex = character - 'a';
		        int newIndex = (oriIndex + (26 - (Integer.parseInt(key) % 26))) % 26;
		        char newCharacter = (char) ('a' + newIndex);
		        result.append(newCharacter);
		    } else {
		        result.append(character);
		    }
		}
		String decrypted = result.toString();
		return decrypted;

	}
	


}

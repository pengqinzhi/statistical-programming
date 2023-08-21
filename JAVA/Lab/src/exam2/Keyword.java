//Qinzhi Peng ;qinzhip
package exam2;

public class Keyword extends Message{

	@Override
	public String encrypt() {
		StringBuilder result = new StringBuilder();
		char[] messageArr = message.toCharArray();
		char[] keyArr = key.toCharArray();

		
		for (int i=0;i < message.length(); i++) {
			char character = messageArr[i];
			int j = i % key.length();
			char keyCharacter = keyArr[j];
			
		    if (character != ' ') {
		        int oriIndex = character - 'a';
		        int oriIndex2 = keyCharacter - 'a' + 1;
		        int newIndex = (oriIndex + oriIndex2) % 26;
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
		char[] messageArr = message.toCharArray();
		char[] keyArr = key.toCharArray();

		
		for (int i=0;i < message.length(); i++) {
			char character = messageArr[i];
			int j = i % key.length();
			char keyCharacter = keyArr[j];
			
		    if (character != ' ') {
		        int oriIndex = character - 'a';
		        int oriIndex2 = keyCharacter - 'a' + 1;
		        int newIndex = (oriIndex + (26 - oriIndex2 % 26 )) % 26;
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

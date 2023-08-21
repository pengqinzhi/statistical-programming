//Qinzhi Peng ;qinzhip
package exam2;

public abstract class Message implements Encryptable{
	String message;
	String key;
	

	public void setMessage(String message, String key) {
		this.message = message;
		this.key = key;
		
	}

}

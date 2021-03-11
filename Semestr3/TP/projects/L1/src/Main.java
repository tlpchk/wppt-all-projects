import java.util.ArrayList;

public class Main {
	
	public static void deliverMessage(String message) {
		String word = "Microsoft";
		StringBuilder builder = new StringBuilder();
		int word_length = word.length();
		int message_length = message.length();
	
		for(int i = 0 ; i<=(message_length-word_length);i++){
			String pattern = message.substring(i,word_length+i);
			if (pattern.equals(word)) {
				builder.append(message.substring(0, i));
				builder.append("###");
				builder.append(message.substring(word_length+i));
				message = builder.toString();
				break;
			}
			
		}
		System.out.println(builder.toString());
	}

	public static void main(String[] args) {
		Main.deliverMessage("Microsoft is amazing!");
		System.out.println(1);
	}
}

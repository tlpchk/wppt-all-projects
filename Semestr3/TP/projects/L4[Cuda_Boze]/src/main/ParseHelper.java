import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ParseHelper {

	
	public static String ToJSON(HashMap<String, Integer> hmap) {
		
		 ObjectWriter ow = new ObjectMapper().writer();
		 String json = "";
		 try {
			json = ow.writeValueAsString(hmap);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return json;
	}
	
	public static HashMap<String, Integer> ToMap(String json) {
		
		ObjectMapper mapper = new ObjectMapper();
        
        HashMap<String, Integer> message = null;
		try {
			message = mapper.readValue(json, new TypeReference<HashMap<String, Integer>>() {});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message;
	}
}

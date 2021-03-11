import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

public class ParseHelperTest {
	ParseHelper parseHelper;
	
	public ParseHelperTest() {
		parseHelper = new ParseHelper();
	}
	@Test
	public void ToJSONTest() {
		HashMap<String, Integer> hMap = new HashMap<>();
		hMap.put("word1", 1);
		hMap.put("word2", 2);
		hMap.put("word3", 3);
		
		String str1 = "{\"word1\":1,\"word3\":3,\"word2\":2}";
		String str2 = parseHelper.ToJSON(hMap);
		assertEquals(str1, str2);
	}
	
	@Test
	public void ToMapTest() {
		String str = "{\"word1\":1,\"word3\":3,\"word2\":2}";
		HashMap<String, Integer> hMap1 = parseHelper.ToMap(str);
		HashMap<String, Integer> hMap2 = new HashMap<>();
		hMap2.put("word1", 1);
		hMap2.put("word2", 2);
		hMap2.put("word3", 3);
		
		assertEquals(hMap1, hMap2);
	}
	

}


import java.util.*;

import com.bazaarvoice.jolt.JsonUtils;

import jersey.repackaged.com.google.common.collect.MapDifference;
import jersey.repackaged.com.google.common.collect.Maps;

public class Test {
	
	public static void main(String[] args) throws Exception {
		
		Map<String,Object> inputMap = JsonUtils.jsonToMap("{\n" + 
				"   \"restaurantId\":\"ZZ4ORJDY3E\",\n" + 
				"   \"chainId\":\"RLR932KI\",\n" + 
				"   \"orderItems\":[\n" + 
				"      {\n" + 
				"         \"itemName\":\"Small Barqs\",\n" + 
				"         \"quantity\":2,\n" + 
				"         \"nested\":{\n" + 
				"            \"rating\":{\n" + 
				"               \"primary\":{\n" + 
				"                  \"value\":3\n" + 
				"               },\n" + 
				"               \"quality\":{\n" + 
				"                  \"value\":3\n" + 
				"               }\n" + 
				"            }\n" + 
				"         }\n" + 
				"      },\n" + 
				"      {\n" + 
				"         \"itemName\":\"Mozzz\",\n" + 
				"         \"quantity\":1,\n" + 
				"         \"nested\":{\n" + 
				"            \"rating\":{\n" + 
				"               \"primary\":{\n" + 
				"                  \"value\":3\n" + 
				"               },\n" + 
				"               \"quality\":{\n" + 
				"                  \"value\":3\n" + 
				"               }\n" + 
				"            }\n" + 
				"         }\n" + 
				"      }\n" + 
				"   ]\n" + 
				"}");
		
		Map<String,Object> retainMap = JsonUtils.jsonToMap("{\n" + 
				"   \"restaurantId\":\"\",\n" + 
				"   \"chainId\":\"\",\n" + 
				"   \"orderItems\":[\n" + 
				"      {\n" + 
				"         \"itemName\":\"\",\n" + 
				"         \"nested\":{\n" + 
				"            \"rating\":{\n" + 
				"               \"primary\":{\n" + 
				"                  \"value\": \"\"\n" + 
				"               }\n" + 
				"            }\n" + 
				"         }\n" + 
				"      }\n" + 
				"   ]\n" + 
				"}");

		retainMaps(inputMap, retainMap);
		
		System.out.println( JsonUtils.toPrettyJsonString(inputMap) );
	}
	
	private static void retainMaps( Map<String,Object> inputMap, Map<String,Object> retainMapKeys) {
		inputMap.keySet().retainAll(retainMapKeys.keySet());
		
		for ( String key : retainMapKeys.keySet() ) {
			Object inputObj = inputMap.get(key);
			Object outputObj = retainMapKeys.get(key);
			if ( inputObj instanceof Map && outputObj instanceof Map ) {
				Map<String,Object> keyVal = (Map<String, Object>) inputObj;
				Map<String,Object> outVal = (Map<String, Object>) outputObj;
			
				retainMaps( keyVal, outVal);
			} else if ( inputObj instanceof List && outputObj instanceof List ) {
				for ( Object obj : ( List) inputObj) {
					Object objRetain = (( List ) outputObj ).get(0);
					if ( obj instanceof Map && objRetain instanceof Map ) {
						retainMaps( ( Map<String,Object> ) obj, ( Map<String,Object> ) objRetain);
					}
				}
			}
		}
	}
}

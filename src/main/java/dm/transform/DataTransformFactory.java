package dm.transform;

import java.util.HashMap;
import java.util.Map;

import dm.transform.constants.TransformType;
import dm.transform.json.JsonDataTransform;

/**
 * 
 * @author mannam
 *
 */
public class DataTransformFactory {
	private volatile static Map<String,DataTransform> jsonTransformMap = new HashMap<String,DataTransform>();
	
	public static DataTransform getDataTransform( TransformType transformType, String specPath ) {
		if ( transformType == TransformType.JSON ) { 
			if( !jsonTransformMap.containsKey(specPath) ){
				synchronized (DataTransformFactory.class) {
					if(!jsonTransformMap.containsKey(specPath) ){
						JsonDataTransform jsonTransform = new JsonDataTransform( specPath );
						jsonTransformMap.put(specPath, jsonTransform);
					}
				}
			}
			return jsonTransformMap.get(specPath);
		}
		return null;
	}
}

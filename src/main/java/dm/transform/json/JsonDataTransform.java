package dm.transform.json;

import java.util.List;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

import dm.transform.DataTransform;

/**
 * 
 * @author mannam
 *
 */
public class JsonDataTransform implements DataTransform { 
	private Chainr chainr;
	
	public JsonDataTransform(String specFilePath) {
		List chainrSpecJSON = JsonUtils.filepathToList( specFilePath );
        this.chainr = Chainr.fromSpec( chainrSpecJSON );
	}

	@Override
	public Object transform(Object input) {
		return chainr.transform(input);
	}
}

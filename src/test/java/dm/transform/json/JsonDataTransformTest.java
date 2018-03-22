package dm.transform.json;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.bazaarvoice.jolt.JsonUtils;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import dm.transform.DataTransform;
import dm.transform.DataTransformFactory;
import dm.transform.constants.TransformType;
import dm.transform.util.JsonDataUtils;

/**
 * 
 * @author mannam
 *
 */
public class JsonDataTransformTest {

	@Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
	
	private File specFile;
	
	@Before
	public void setUp() throws IOException {
		// Create a temporary file.
		specFile = testFolder.newFile("spec.json");

	     FileUtils.writeStringToFile(specFile, "[\n" + 
	     		"    {\n" + 
	     		"        \"operation\": \"shift\",\n" + 
	     		"        \"spec\": {\n" + 
	     		"            \"rating\": {\n" + 
	     		"                \"primary\": {\n" + 
	     		"                    \"value\": \"Rating\"\n" + 
	     		"                },\n" + 
	     		"                \"*\": {\n" + 
	     		"                    \"value\": \"SecondaryRatings.&1.Value\",\n" + 
	     		"                    \"$\": \"SecondaryRatings.&.Id\"\n" + 
	     		"                }\n" + 
	     		"            }\n" + 
	     		"        }\n" + 
	     		"    }]");
	}
	
	@Test
	public void testTransform() {
		DataTransform transform = DataTransformFactory.getDataTransform(TransformType.JSON, specFile.getAbsolutePath());
		
		Object input = JsonDataUtils.jsonToMap( "{\n" + 
        		"    \"rating\": {\n" + 
        		"        \"primary\": {\n" + 
        		"            \"value\": 3\n" + 
        		"        },\n" + 
        		"        \"quality\": {\n" + 
        		"            \"value\": 3\n" + 
        		"        }\n" + 
        		"    }\n" + 
        		"}" );

		Object output = transform.transform(input);
		assertThat(output, instanceOf(Map.class));
		
		Map<String,Object> outputMap = (Map<String,Object>) output;
		assertEquals(3,outputMap.get("Rating"));
		
		assertThat(outputMap, Matchers.hasKey("SecondaryRatings"));
		assertThat(outputMap, Matchers.hasKey("Rating"));
	}
	
	@After
	public void tearDown() {
		testFolder.delete();
	}
}

package dm.transform;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;


import static org.hamcrest.CoreMatchers.instanceOf;

import dm.transform.constants.TransformType;
import dm.transform.json.JsonDataTransform;
import dm.transform.DataTransform;

/**
 * 
 * @author mannam
 *
 */
public class DataTransformFactoryTest {
	
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
	
	@Test(expected=RuntimeException.class)
	public void testJsonTransformInstanceFailure() {
		//throws RuntimeException because "TEST" passed in as spec file path
		DataTransformFactory.getDataTransform(TransformType.JSON, "TEST");
	}
	
	@Test
	public void testJsonTransformInstanceSuccess() throws IOException {
		DataTransform transform = DataTransformFactory.getDataTransform(TransformType.JSON, specFile.getAbsolutePath());
		assertThat(transform, instanceOf(JsonDataTransform.class));
	}
	
	@Test
	public void testJsonTransformSameInstance() {
		DataTransform transformOne = DataTransformFactory.getDataTransform(TransformType.JSON, specFile.getAbsolutePath());
		DataTransform transformSecond = DataTransformFactory.getDataTransform(TransformType.JSON, specFile.getAbsolutePath());

		assertSame(transformOne,transformSecond);
	}

	@After
	public void tearDown() {
		testFolder.delete();
	}
}

package dm.transform.hbase;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

/**
 *
 * @author aervits
 */
public class HBaseTestObj {
    private static final Logger LOG = Logger.getLogger(HBaseTestObj.class.getName());
    
    private String rowKey;
    private String data1;
    private String data2;


    public HBaseTestObj() {

    }
    
    public String getRowKey() {
		return rowKey;
	}


	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}


	public String getData1() {
		return data1;
	}


	public void setData1(String data1) {
		this.data1 = data1;
	}


	public String getData2() {
		return data2;
	}


	public void setData2(String data2) {
		this.data2 = data2;
	}
}

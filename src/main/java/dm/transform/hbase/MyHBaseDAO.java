package dm.transform.hbase;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 *
 * @author aervits
 */
public class MyHBaseDAO {
    private static final Logger LOG = Logger.getLogger(MyHBaseDAO.class.getName());
    
    
    public static void main (String [] args) {
        try {
            Configuration conf = HBaseConfiguration.create();
            conf.set("hbase.zookeeper.quorum", "sandbox.hortonworks.com");
            conf.set("hbase.zookeeper.property.clientPort", "2181");
            conf.set("zookeeper.znode.parent", "/hbase-unsecure");
            
            Connection connection = ConnectionFactory.createConnection(conf);
            
            Table table = connection.getTable(TableName.valueOf("tableName"));
            HBaseTestObj obj = new HBaseTestObj();
            obj.setRowKey("row-1");
            obj.setData1("data-1");
            obj.setData2("data-2");
            
            insertRecord(table, obj);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    
    public static void insertRecord(Table table, HBaseTestObj obj) throws Exception {
        Put put = createPut(obj);
        table.put(put);
    }
    
    public static Put createPut(HBaseTestObj obj) {
        Put put = new Put(Bytes.toBytes(obj.getRowKey()));
        put.addColumn(Bytes.toBytes("CF"), Bytes.toBytes("CQ-1"),
                    Bytes.toBytes(obj.getData1()));
        put.addColumn(Bytes.toBytes("CF"), Bytes.toBytes("CQ-2"),
                Bytes.toBytes(obj.getData2()));
        return put;
    }
}


package sandbox;

import java.util.List;

import org.jivesoftware.smackx.disco.packet.DiscoverItems;

import jp.ac.keio.sfc.ht.sox.protocol.Data;
import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;

public class IsNodeExistTest {
	public static void main(String[] args) throws Exception{
		new IsNodeExistTest();
	}
	
	public IsNodeExistTest() throws Exception{
		
		SoxConnection con = new SoxConnection("172.16.212.133","ubuntu",true); //anonymous login

		if(con.isNodeExist("todaiTest")){
			System.out.println("exist");
		}else{
			System.out.println("none");
		}
	
	}

}

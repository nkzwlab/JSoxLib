package sandbox;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.xdata.packet.DataForm;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.DeviceType;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxDevice;


public class Create {

	public static void main(String[] args){
		try {
			new Create();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public Create() throws Exception{
		

		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp","sensorizer","miromiro", true);
		SoxDevice dev  = new SoxDevice(con, "tenki熊本");

		ConfigureForm form = new ConfigureForm(DataForm.Type.submit);
		form.setAccessModel(AccessModel.open);
		form.setPersistentItems(false);
		form.setMaxItems(1);
		form.setPublishModel(PublishModel.open);
	
		dev.getDataNode().sendConfigurationForm(form);

		
		/*
		 * If you want to delete sensor node, please use following code
		 */
		
		//con.deleteNode(nodeName);
		
	}
	
}

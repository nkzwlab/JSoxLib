package example;

import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;
import jp.ac.keio.sfc.ht.sox.soxlib.Utility;
import jp.ac.keio.sfc.ht.sox.soxlib.event.AllSoxEventListener;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxEvent;

public class ShowPublishedDataFromAlreadySubscribedNodes implements AllSoxEventListener{

	public static void main(String args[]){
		new ShowPublishedDataFromAlreadySubscribedNodes();
	}
	

	//be sure that you have to already subscribe node(s) with specific JID to get data
	public ShowPublishedDataFromAlreadySubscribedNodes(){
		try{
 
			SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp","guest","miroguest",true);
			con.addAllSoxEventListener(this);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//this is for not finishing the process
		while(Utility.simpleSleepMs(100000)){
			
		}
	}
	
	@Override
	public void handleAllPublishedSoxEvent(SoxEvent e) {
		// TODO Auto-generated method stub
		System.out.println(":::::Received Data:::::");

		List<TransducerValue> values = e.getTransducerValues();
		System.out.println("Message from:"+e.getOriginServer());
		System.out.println("Node Name: "+e.getNodeID());
		for (TransducerValue value : values) {
			System.out.println("TransducerValue[id:" + value.getId()
					+ ", rawValue:" + value.getRawValue() + ", typedValue:"
					+ value.getTypedValue() + ", timestamp:"
					+ value.getTimestamp() + "]");
		}
	}

}

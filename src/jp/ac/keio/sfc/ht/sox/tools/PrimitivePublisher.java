package jp.ac.keio.sfc.ht.sox.tools;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

import jp.ac.keio.sfc.ht.sox.protocol.Data;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.SoxConnection;

public class PrimitivePublisher {

	private SoxConnection con;
	private String pubSubNodeId;
	private LeafNode eventNode_data;

	public static void main(String[] args) throws Exception{
		SoxConnection con = new SoxConnection("sox.ht.sfc.keio.ac.jp",false);
		PrimitivePublisher publisher = new PrimitivePublisher(con,"hogehoge");
		
		List<TransducerValue> valueList = new ArrayList<TransducerValue>();

		TransducerValue value2 = new TransducerValue();
		value2.setId("temperature");
		value2.setRawValue("0"); //celcius
		value2.setTypedValue("273"); //kelvin
		value2.setCurrentTimestamp();
		
		valueList.add(value2);

		TransducerValue value3 = new TransducerValue();
		value3.setId("humidity");
		value3.setRawValue("82"); //percent
		value3.setTypedValue("82"); //percent
		value3.setCurrentTimestamp();
		
		valueList.add(value3);

		
		publisher.publishValues(valueList);
			
		
		
	}
	
	
	public PrimitivePublisher(SoxConnection _con, String _pubSubNodeId)
			throws Exception {
		con = _con;
		pubSubNodeId = _pubSubNodeId;

		eventNode_data = con.getPubSubManager().getNode(pubSubNodeId + "_data");

	}

	public void publishValue(TransducerValue value) {

		List<TransducerValue> valueList = new ArrayList<TransducerValue>();
		valueList.add(value);
		this.publishValues(valueList);

	}

	public void publishValues(List<TransducerValue> values) {
		Data data = new Data();
		data.setTransducerValue(values);

		// transform data object into XML string
		StringWriter writer = new StringWriter();
		Persister serializer = new Persister(new Matcher() {
			public Transform match(Class type) throws Exception {
				if (type.isEnum()) {
					return new SoxEnumTransform(type);
				}
				return null;
			}
		});

		try {
			serializer.write(data, writer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Creating publish item
		SimplePayload payload = new SimplePayload(pubSubNodeId,
				"http://jabber.org/protocol/sox", writer.toString());

		PayloadItem<SimplePayload> pi = new PayloadItem<SimplePayload>(null,
				payload);

		// Publish
		try {
			eventNode_data.publish(pi);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

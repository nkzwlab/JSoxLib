/*
 * Copyright (C) 2014 Takuro Yonezawa
 * Keio University, Japan
 */

package jp.ac.keio.sfc.ht.sox.soxlib;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.Subscription;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

import jp.ac.keio.sfc.ht.sox.protocol.Data;
import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.protocol.DeviceType;
import jp.ac.keio.sfc.ht.sox.protocol.Transducer;
import jp.ac.keio.sfc.ht.sox.protocol.TransducerValue;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxEvent;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxEventListener;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxTupleEvent;
import jp.ac.keio.sfc.ht.sox.soxlib.event.SoxTupleEventListener;
import jp.ac.keio.sfc.ht.sox.tools.SoxEnumTransform;

public class SoxDevice implements ItemEventListener {

	private SoxConnection con;
	private String pubSubNodeId;
	private Device device;
	private SoxEventListener soxEventListener;
	private SoxTupleEventListener soxTupleEventListener;
	String dataString;
	private LeafNode eventNode_data;

	public SoxDevice(SoxConnection _con, String _pubSubNodeId) throws Exception {

		con = _con;
		pubSubNodeId = _pubSubNodeId;

		this.init();
	}

	private void init() throws Exception {

		// Getting meta information

		LeafNode eventNode_meta = con.getPubSubManager().getNode(
				pubSubNodeId + "_meta");

		List<? extends PayloadItem> items = eventNode_meta.getItems(1);
		Serializer serializer = new Persister(new Matcher() {
			public Transform match(Class type) throws Exception {
				if (type.isEnum()) {
					return new SoxEnumTransform(type);
				}
				return null;
			}
		});

		List<Subscription> subscriptions = eventNode_meta.getSubscriptions();

		for (Subscription s : subscriptions) {

			eventNode_meta.unsubscribe(s.getJid(), s.getId());

		}

		for (PayloadItem item : items) {
			String metaString = item.getPayload().toXML().toString();
			metaString = metaString.replaceAll("&lt;", "<");
			metaString = metaString.replaceAll("/&gt;", ">");
			metaString = metaString.replaceAll("&apos;", "'");
			device = serializer.read(Device.class, metaString);
			System.out.println("[info]device created from meta data:"
					+ pubSubNodeId + "_meta");
		}

		eventNode_data = con.getPubSubManager().getNode(pubSubNodeId + "_data");

	}

	
	
	public Data getLastPublishData() throws Exception {

		List<? extends PayloadItem> items = eventNode_data.getItems(1);
		Serializer serializer = new Persister(new Matcher() {
			public Transform match(Class type) throws Exception {
				if (type.isEnum()) {
					return new SoxEnumTransform(type);
				}
				return null;
			}
		});

		Data data=null;
		for (PayloadItem item : items) {
			dataString = item.getPayload().toXML().toString();
			dataString = dataString.replaceAll("&lt;", "<");
			dataString = dataString.replaceAll("/&gt;", ">");
			dataString = dataString.replaceAll("&apos;", "'");
			data = serializer.read(Data.class, dataString);
		}

		return data;
	}

	// Subscribe data event node
	public void subscribe() {

		try {

			// if the node is already subscribed by the user, unsubscribe
			List<Subscription> subscriptions = eventNode_data
					.getSubscriptions();

			for (Subscription s : subscriptions) {
				eventNode_data.unsubscribe(s.getJid(), s.getId());
			}
			eventNode_data.subscribe(con.getXMPPConnection().getUser());
			eventNode_data.addItemEventListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// unscribe data event node
	public void unsubscribe() {
		try {
			List<Subscription> subscriptions = eventNode_data
					.getSubscriptions();
			for (Subscription s : subscriptions) {
				eventNode_data.unsubscribe(s.getJid(), s.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// publich message
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

	@Override
	public void handlePublishedItems(ItemPublishEvent event) {
		// TODO Auto-generated method stub
		List<PayloadItem> items = event.getItems();

		Serializer serializer = new Persister();
		for (PayloadItem item : items) {
			// System.out.println("item:"+item.getPayload().toXML());
			try {

				System.out.println("[info]data received from data node:"
						+ pubSubNodeId + "_data");

				dataString = item.getPayload().toXML().toString();
				dataString = dataString.replaceAll("&lt;", "<");
				dataString = dataString.replaceAll("/&gt;", ">");
				dataString = dataString.replaceAll("&apos;", "'");

				Data data = serializer.read(Data.class, dataString);
				List<TransducerValue> list = data.getTransducerValue();

				// for soxTupleEventListener
				if (soxTupleEventListener != null) {
					soxTupleEventListener
							.handlePublishedSoxTupleEvent(new SoxTupleEvent(
									this, device, list));
				}

				// for soxEventLisneter;
				if (soxEventListener != null) {
					for (TransducerValue tValue : list) {

						Transducer transducer = null;
						for (Transducer t : device.getTransducers()) {
							if (t.getId().equals(tValue.getId())) {
								transducer = t;
							}
						}

						soxEventListener.handlePublishedSoxEvent(new SoxEvent(
								this, device, transducer, tValue));

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Getter
	public String getNodeId() {
		return pubSubNodeId;
	}

	public Device getDevice() {
		return device;
	}

	// For Event Listener
	public void addSoxEventListener(SoxEventListener _listener) {
		soxEventListener = _listener;
	}

	public void removeSoxEventListener() {
		soxEventListener = null;
	}

	public void addSoxTupleEventListener(SoxTupleEventListener _tupleListener) {
		soxTupleEventListener = _tupleListener;
	}

	public void removeSoxTupleEventListener() {
		soxTupleEventListener = null;
	}

}

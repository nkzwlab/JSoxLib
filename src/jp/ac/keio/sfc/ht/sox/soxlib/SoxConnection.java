/*
 * Copyright (C) 2014 Takuro Yonezawa
 * Keio University, Japan
 */


package jp.ac.keio.sfc.ht.sox.soxlib;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import jp.ac.keio.sfc.ht.sox.protocol.Device;
import jp.ac.keio.sfc.ht.sox.tools.SoxEnumTransform;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.disco.packet.DiscoverItems;
import org.jivesoftware.smackx.disco.packet.DiscoverItems.Item;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.pubsub.FormType;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.packet.DataForm;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

public class SoxConnection implements PacketListener{

	private XMPPConnection con;
	private PubSubManager manager;
	private String jid;
	private String pass;
	private String server;
	private boolean isDebugEnable;
	
	public SoxConnection(String _server, String _jid, String _pass, boolean _isDebugEnable) throws SmackException, IOException, XMPPException{
	
		
			
		this.server = _server;
		this.jid = _jid;
		this.pass = _pass;
		this.isDebugEnable = _isDebugEnable;


		this.connect();
	}
	
	public SoxConnection(String _server, String _jid, String _pass) throws SmackException, IOException, XMPPException{
		this(_server,_jid,_pass,false);
	}
	
	//anonymous login
	//but not implemented
	public SoxConnection(String _server, boolean _isDebugEnable) throws SmackException, IOException, XMPPException{
		this(_server,null,null,_isDebugEnable);
	}
	
	
	
	public void connect() throws SmackException, IOException, XMPPException{


		ConnectionConfiguration config = new ConnectionConfiguration(
				server, 5222);
		config.setSecurityMode(SecurityMode.disabled);
		config.setDebuggerEnabled(isDebugEnable);
		config.setReconnectionAllowed(true);
		con = new XMPPTCPConnection(config);
		
		con.connect();

		if(jid!=null&&pass!=null){
			con.login(jid, pass);
		}else{
			con.loginAnonymously();			
		}
		con.addPacketListener(this,null);
		manager = new PubSubManager(con,"pubsub."+server);
		
		
	}
	
	public void deleteNode(String nodeName) throws NoResponseException, XMPPErrorException, NotConnectedException{
		manager.deleteNode(nodeName+"_meta");
		manager.deleteNode(nodeName+"_data");
	}
	
	public void createNode(String nodeName,Device device, AccessModel aModel, PublishModel pModel) throws NoResponseException, XMPPErrorException, NotConnectedException{

		//create _meta node and _data node

		LeafNode eventNode_meta = manager.createNode(nodeName+"_meta");
		LeafNode eventNode_data = manager.createNode(nodeName+"_data");
		
		ConfigureForm form = new ConfigureForm(FormType.submit);
		form.setAccessModel(aModel);
		form.setMaxItems(1);
		form.setPublishModel(pModel);
	
		eventNode_meta.sendConfigurationForm(form);
		eventNode_data.sendConfigurationForm(form);

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
			serializer.write(device, writer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Creating publish item
		SimplePayload payload = new SimplePayload(nodeName,
				"http://jabber.org/protocol/sox", writer.toString());

		PayloadItem<SimplePayload> pi = new PayloadItem<SimplePayload>(null,
				payload);

		// Publish
		try {
			eventNode_meta.publish(pi);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	  public List<String> getAllSensorList() throws NoResponseException, XMPPErrorException, NotConnectedException{
          DiscoverItems items = manager.discoverNodes(null);
          List<Item> nodeList = items.getItems();
          
          List<String> sensorList = new ArrayList<String>();

          for(Item node:nodeList){
        	  
        	  if(node.getNode().endsWith("_meta")){
        		  String name = node.getNode().substring(0,(node.getNode()).length()-5);
        		  sensorList.add(name);
        	  }
          }
          return sensorList;    	
    }
	
	public XMPPConnection getXMPPConnection(){
		return con;
	}
	
	public PubSubManager getPubSubManager(){
		return manager;
	}
	
	public String getJID(){
		return jid;
	}
	
	public String getServerName(){
		return server;
	}

	@Override
	public void processPacket(Packet arg0) throws NotConnectedException {
		// TODO Auto-generated method stub
		
	}
}

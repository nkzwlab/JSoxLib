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

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.disco.packet.DiscoverItems;
import org.jivesoftware.smackx.disco.packet.DiscoverItems.Item;
import org.jivesoftware.smackx.pubsub.AccessModel;
import org.jivesoftware.smackx.pubsub.ConfigureForm;
import org.jivesoftware.smackx.xdata.packet.DataForm;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.PublishModel;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.Transform;

public class SoxConnection {

	private XMPPTCPConnection con;
	private PubSubManager manager;
	private String jid;
	private String pass;
	private String server;
	private String service;
	private boolean isDebugEnable;
	
	public SoxConnection(String _server, String _service,String _jid, String _pass, boolean _isDebugEnable) throws SmackException, IOException, XMPPException{
			
		this.server = _server;
		this.service = _service;
		this.jid = _jid;
		this.pass = _pass;
		this.isDebugEnable = _isDebugEnable;


		this.connect();
	}
	
	public SoxConnection(String _server,String _jid, String _pass, boolean _isDebugEnable) throws SmackException, IOException, XMPPException{
		
		this(_server,_server,_jid,_pass,_isDebugEnable);
	}
	
	public SoxConnection(String _server, String _service, boolean _isDebugEnable) throws SmackException, IOException, XMPPException{
		this(_server,_service,null,null,_isDebugEnable);
	}
	
	
	public SoxConnection(String _server, String _jid, String _pass) throws SmackException, IOException, XMPPException{
		this(_server,_server,_jid,_pass,false);
	}
	
	
	
	//anonymous login
	public SoxConnection(String _server, boolean _isDebugEnable) throws SmackException, IOException, XMPPException{
		this(_server,_server,null,null,_isDebugEnable);
	}
	
	
	public void disconnect(){
		if(con!=null && con.isConnected()){
			con.disconnect();
		}
	}
	
	
	public void connect() throws SmackException, IOException, XMPPException{

		SmackConfiguration.setDefaultPacketReplyTimeout(300*1000);
		  // You have to put this code before you login

		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				  .setHost(server)
				  .setPort(5222)
				  .setServiceName(service)
				  .setSecurityMode(SecurityMode.disabled)
				  .setDebuggerEnabled(isDebugEnable)
				  .setConnectTimeout(30*1000)
				  .build();

		
		con = new XMPPTCPConnection(config);
		
		con.connect();

		if(jid!=null&&pass!=null){
			con.login(jid, pass);
		}else{
			con.loginAnonymously();
		}
	
		manager = new PubSubManager(con,"pubsub."+service);
		
		
	}
	
	
	public void deleteNode(String nodeName) throws NoResponseException, XMPPErrorException, NotConnectedException{
		manager.deleteNode(nodeName+"_data");
		manager.deleteNode(nodeName+"_meta");
	}
	
	public void createNode(String nodeName,AccessModel aModel,PublishModel pModel) throws NoResponseException, XMPPErrorException, NotConnectedException{
		
		//create _meta node and _data node

		LeafNode eventNode_meta = manager.createNode(nodeName+"_meta");
		LeafNode eventNode_data = manager.createNode(nodeName+"_data");
		
		ConfigureForm form = new ConfigureForm(DataForm.Type.submit);
		form.setAccessModel(aModel);
		form.setPersistentItems(false);
		form.setMaxItems(0);
		form.setPublishModel(pModel);
	
		eventNode_data.sendConfigurationForm(form);

		
		ConfigureForm form2 = new ConfigureForm(DataForm.Type.submit);
		form2.setAccessModel(aModel);
		form2.setPersistentItems(true);
		form2.setMaxItems(1);
		form2.setPublishModel(pModel);
		
		eventNode_meta.sendConfigurationForm(form2);
		
	}
	
	
	
	public void createDataNode(String nodeName, AccessModel aModel, PublishModel pModel) throws NoResponseException, XMPPErrorException, NotConnectedException{
		LeafNode eventNode_data = manager.createNode(nodeName+"_data");
		ConfigureForm form = new ConfigureForm(DataForm.Type.submit);
		form.setAccessModel(aModel);
		form.setPersistentItems(false);
		form.setMaxItems(1);
		form.setPublishModel(pModel);
	
		eventNode_data.sendConfigurationForm(form);
	}
	
	public void createNode(String nodeName,Device device, AccessModel aModel, PublishModel pModel) throws NoResponseException, XMPPErrorException, NotConnectedException{

		//create _meta node and _data node

		LeafNode eventNode_meta = manager.createNode(nodeName+"_meta");
		LeafNode eventNode_data = manager.createNode(nodeName+"_data");
		
		ConfigureForm form = new ConfigureForm(DataForm.Type.submit);
		form.setAccessModel(aModel);
		//form.setMaxItems(1);
		form.setPersistentItems(false);
		form.setPublishModel(pModel);
	
		eventNode_data.sendConfigurationForm(form);

		
		ConfigureForm form2 = new ConfigureForm(DataForm.Type.submit);
		form2.setAccessModel(aModel);
		form2.setPersistentItems(true);
		form2.setMaxItems(1);
		form2.setPublishModel(pModel);
		
		eventNode_meta.sendConfigurationForm(form2);
		
		
		
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
	  
	  public boolean isNodeExist(String nodeId){
		  try {
			List<String> lists = getAllSensorList();
			if(lists.contains(nodeId)){
				return true;
			}
			return false;
			
		} catch (NoResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return false;
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

}

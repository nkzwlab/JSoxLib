package sandbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

public class FirstSmackSample {
    private AbstractXMPPConnection connection = null;
    private Chat chat = null;
    private boolean isRunning = true;

    /* サーバへの接続とログイン */
    public void connect(String server, String username, String password) {
    // 前述の通り
    	SmackConfiguration.setDefaultPacketReplyTimeout(300*1000);
		  // You have to put this code before you login

		XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
				  .setHost(server)
				  .setPort(5222)
				  .setServiceName(server)
				  .setSecurityMode(SecurityMode.disabled)
				  .setDebuggerEnabled(true)
				  .setConnectTimeout(30*1000)
				  .build();

		
		connection = new XMPPTCPConnection(config);
		
		try {
			connection.connect();
			connection.login(username,password);
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    }

    /* チャットの開始 */
    public void chatOpen(String buddyId) {
        // 前述の通り
        // ChatManagerを取得し、チャットを開始する
        ChatManager chatManager = ChatManager.getInstanceFor(connection);

        this.chat = chatManager.createChat(buddyId, new ChatMessageListener() {
          


				@Override
				public void processMessage(Chat arg0, Message arg1) {
					// TODO Auto-generated method stub
					System.out.println(arg0.getParticipant() + ": " + arg1.getBody());
					
				}
            });
    }

    /* メッセージの送信 */
    public void sendMessage(String message) throws NotConnectedException {
        // 前述の通り

    		    this.chat.sendMessage(message);
    		   
    }

    /* チャットが継続中か否か */
    public boolean isRunning() {
        return this.isRunning;
    }

    /* チャットの終了 */
    public void chatClose() {
        this.isRunning = false;
    }

    /* 接続の終了 */
    public void destroy() {
       connection.disconnect();
    }

    public static void main(String[] args) {
        FirstSmackSample xmpp = new FirstSmackSample();
        // 接続してチャットを開始
        xmpp.connect("sox.ht.sfc.keio.ac.jp", "takuro", "minatakuro");
        xmpp.chatOpen("guest@takusox.ht.sfc.keio.ac.jp");

        // メッセージの送信処理
        while(xmpp.isRunning()) {
            try {
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(System.in));
                String message = reader.readLine();
                if ("@close".equals(message.trim())) {
                    xmpp.chatClose();
                } else {
                    try {
						xmpp.sendMessage(message);
					} catch (NotConnectedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // 終了
        xmpp.destroy();
    }
}
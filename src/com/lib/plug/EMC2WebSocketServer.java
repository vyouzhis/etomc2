package com.lib.plug;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


import org.ppl.core.PObject;
import org.ppl.etc.globale_config;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

@ServerEndpoint("/etomc2")
public class EMC2WebSocketServer extends PObject {
	private String mutex = "";

	/**
	 * @OnOpen allows us to intercept the creation of a new session. The session
	 *         class allows us to send data to the user. In the method onOpen,
	 *         we'll let the user know that the handshake was successful.
	 */
	@OnOpen
	public void onOpen(Session session) {
		echo(session.getId() + " has opened a connection");
		synchronized (mutex) {
			globale_config.allSession.put(session.getId(), session);
		}
		try {
			String init = getStockDB();
			session.getBasicRemote().sendText(init);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * When a user sends a message to the server, this method will intercept the
	 * message and allow us to react to it. For now the message is read as a
	 * String.
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		echo("Message from " + session.getId() + ": " + message);
		try {
			for (String id : globale_config.allSession.keySet()) {
				echo(id);
				globale_config.allSession.get(id).getBasicRemote()
						.sendText(message);
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * The user closes the connection.
	 * 
	 * Note: you can't send messages to the client from this method
	 */
	@OnClose
	public void onClose(Session session) {
		synchronized (mutex) {
			globale_config.allSession.remove(session.getId());
		}
		echo("Session " + session.getId() + " has ended");
	}

	private String getStockDB() {
		Shell shell = null;
		String out = "";
		try {
			shell = new SSHByPassword(mConfig.GetValue("pythonIp"), 22,
					mConfig.GetValue("pythonUser"), "!@#qazwsx");

			out = new Shell.Plain(shell)
					.exec("python /root/tushare_pro/tushare_mongo_realtime.py");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out;
	}
}

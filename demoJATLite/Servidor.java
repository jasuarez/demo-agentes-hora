/*
 * $Id$
 */

import RouterLayer.AgentClient.*;
import Abstract.*;
import KQMLLayer.*;
import java.util.*;
import java.io.*;


/**
 * Class Servidor
 *
 * Muestra la hora actual al agente que se lo pida
 *
 * @author  $Author$
 * @version $Revision$
 */

public class Servidor extends RouterClientAction {
    public Servidor (Address myAddress, Address routerAddress, Address registerAddress,
		     int durationTime, boolean registerRequest) {
	super (myAddress, routerAddress, registerAddress, durationTime);
	try {
	    createServerThread (myAddress.getID (), Thread.NORM_PRIORITY);
	    if (registerRequest)
		register (registerAddress, myAddress);
	    connect (myAddress);
	} catch (Exception ex) {
	    System.out.println ("Clase Servidor (primera excepcion)");
	    System.out.println ("==================================");
	    ex.printStackTrace ();
	    System.exit (1);
	}
    }

    public boolean Act (Object obj) {
	String stampMsg = (String) obj;
	try {
	    KQMLmail mail = new KQMLmail (stampMsg, 0);
	    _mailQueue.addElement (mail);
	    KQMLmessage kqml = mail.getKQMLmessage ();
	    String perf = kqml.getValue ("performative");
	    String content = kqml.getValue ("content");
	    String receiver = kqml.getValue ("sender");
	    if (content == null) {
		sendErrorMessage (kqml);
		return false;
	    }
	    if (perf.equals ("achieve") && content.equals ("TIME?")) {
		String sendMsg = "(tell :sender ";
		sendMsg = sendMsg + getName () + " :receiver " + receiver;
		sendMsg = sendMsg + " :language Java :content " + new Date ().toString () + ")";
		sendMessage (sendMsg);
	    } else {
		sendErrorMessage (kqml);
		return false;
	    }
	} catch (Exception ex) {
	    System.out.println ("Clase Servidor (segunda excepcion)");
	    System.out.println ("==================================");
	    ex.printStackTrace ();
	    System.exit (1);
	}
	return true;
    }

    protected void sendErrorMessage (KQMLmessage kqml) {
	String receiver = kqml.getValue ("sender");
	String sendMsg = "(error :sender ";
	sendMsg = sendMsg + getName () + " :receiver " + receiver;
	sendMsg = sendMsg + " :language Java :content (" + kqml.getSendString () + "))";
	try {
	    sendMessage (sendMsg);
	    addToDeleteBuffer (0);
	} catch (Exception ex) {
	    System.out.println ("Clase Servidor (tercera excepcion)");
	    System.out.println ("==================================");
	    ex.printStackTrace ();
	    System.exit (1);
	}
    }

    public void processMessage (String s, Object obj) {}
    
    public static void main(String args[]) {
	if (args.length != 1) {
	    System.out.println ("Uso: java Servidor <direccion>");
	} else {
	    Address myAddress = null;
	    Address routerAddress = null;
	    Address registerAddress = null;
	    boolean registerRequest = false;
	    int idleTime = 1000;
	    try {
		DataInputStream in = new DataInputStream (new FileInputStream (new File (args[0])));
		while (true) {
		    String line = in.readLine ();
		    if (line == null)
			break;
		    String next;
		    if (line.startsWith ("MyAddress")) {
			next = in.readLine ();
			myAddress = new Address (next);
		    } else if (line.startsWith ("RouterAddress")) {
			next = in.readLine ();
			routerAddress = new Address (next);
		    } else if (line.startsWith ("RegisterRequest")) {
			next = in.readLine ();
			if (next.startsWith ("y")) {
			    registerRequest = true;
			} else {
			    registerRequest = false;
			}
		    } else if (line.startsWith ("MaxIdleTime")) {
			next = in.readLine ();
			idleTime = (Integer.valueOf (next)).intValue ();
		    }
		}
		in.close ();
		Servidor serv = new Servidor (myAddress, routerAddress, registerAddress, idleTime, registerRequest);
		serv.start ();
	    } catch (Exception ex) {
		System.out.println ("Clase Servidor (cuarta excepcion)");
		System.out.println ("=================================");
		ex.printStackTrace ();
		System.exit (1);
	    }
	}
    }
}

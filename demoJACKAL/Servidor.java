/*$Id$*/

import J3.*;
import java.util.*;

/**
 * Class Servidor
 *
 * Muestra la hora actual al agente que se lo pida
 *
 * @author  $Author$
 * @version $Revision$
 */

class Servidor {
    public Servidor () {
	try {
	    Message reply;
	    Message response;
	    Intercom intercom = new Intercom ("server", "file:///home/obelix/development/demoJACKAL/server.kqmlrc");
	    intercom.stderr ("El servidor esta esperando");
	    FIFO queue = intercom.attend (null, null, null, 8, false, true, 0, true, false);
	    while ((reply = (Message) queue.dequeue ()) != null) {
		response = new Message ("(tell)");
		response.put ("in-reply-to", reply.get ("reply-with"));
		response.put ("language", "Java");
		response.put ("ontology", "time");
		response.put ("content", "\"" + new Date ().toString () + "\"");
		intercom.send_message (response);
	    }
	} catch (Exception ex) {
	    System.err.println ("Clase Servidor");
            System.err.println ("Primera excepción");
            System.err.println ("=================");
            ex.printStackTrace ();
	}
    }
    
    public static void main (String[] argv) {
	Servidor serv = new Servidor ();
    }
}

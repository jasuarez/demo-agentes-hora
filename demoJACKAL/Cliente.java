/*$Id$*/

import J3.*;

/**
 * Class Cliente
 *
 * Pide la hora a un agente servidor de tiempo y la muestra en la
 * consola.
 *
 * @author  $Author$
 * @version $Revision$
 */


class Cliente {
    public Cliente () {
	try {
	    Intercom intercom = new Intercom ("cliente", "file:///home/jota/eclipse/demoJACKAL/common.kqmlrc");
	    System.out.println ("CLI>> Envio el mensaje");
	    Message ad = new Message ("(achieve :sender.ans cliente :receiver servidor.ans :language Java :ontology time :content TIME?)");
	    Message reply = (Message) intercom.attend (ad, null, null, 5, false, true, 1, true, false).dequeue ();
	    System.out.println ("La hora en el servidor es: " + reply.get("content"));
	} catch (Exception ex) {
	    System.err.println ("Clase Cliente");
            System.err.println ("Primera excepci�n");
            System.err.println ("=================");
            ex.printStackTrace ();
	}
    }
    
    public static void main (String[] argv) {
	Cliente cliente = new Cliente ();
    }
}

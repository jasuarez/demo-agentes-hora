/*$Id$*/

import J3.*;
import java.util.*;

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
	    System.out.println ("CLI>> Antes");
	    Intercom intercom = new Intercom ("cliente", "file:///home/obelix/development/demoJACKAL/common.kqmlrc");
	    System.out.println ("CLI>> Envio el mensaje");
	    Message ad = new Message ("(achieve :sender cliente.ans :receiver servidor.ans :language Java :ontology time :content TIME?)");
	    Message reply = (Message) intercom.attend (ad, null, null, 5, false, true, 1, true, false).dequeue ();
	    System.out.println ("La hora en el servidor es: " + reply.get("content"));
	} catch (Exception ex) {
	    System.err.println ("Clase Cliente");
            System.err.println ("Primera excepción");
            System.err.println ("=================");
            ex.printStackTrace ();
	}
    }
    
    public static void main (String[] argv) {
	Cliente cliente = new Cliente ();
    }
}

/*
 * $Id$
 */

import com.ibm.acl.kqml.*;
import java.net.*;


/**
 * Class Cliente
 *
 * Pide la hora a un agente servidor de tiempo y la muestra en la
 * consola.
 *
 * @author  $Author$
 * @version $Revision$
 */

public class Cliente extends SkeletonAgent {
    int puertoEscucha = 22201;
    String agente = "clienteHora";
    String servidor = "servidorHora";
    KQMLManager km;

    public Cliente () {
	try {
	    InetAddress local = InetAddress.getLocalHost ();
	    StringBuffer buf = new StringBuffer("ktp://");
	    buf.append (local.getHostName () + ":");
	    buf.append (Integer.toString (puertoEscucha));
	    String localhost = new String(buf.toString ());
	    km = new KQMLManager (agente, "ktp", puertoEscucha);
	    TimeInterpreter time = new TimeInterpreter ();
	    km.addContentInterpreter ("Java", "time", time);
	    String registro = register (km, agente, localhost);
	    obtenerHora ();
	    unregister (km, registro);
	    km.stop (60000);
	} catch (Exception ex) {
	    System.err.println ("Clase Cliente");
	    System.err.println ("Primera excepción");
	    System.err.println ("=================");
	    ex.printStackTrace ();
	}
    }

    void obtenerHora () {
	try {
	    KQML peticionHora = new KQML ();
	    peticionHora.setPerformative (KQML.ACHIEVE);
	    peticionHora.setSender (agente);
	    peticionHora.setReceiver (servidor);
	    peticionHora.setRW (km.getInitialID ());
	    peticionHora.setLanguage ("Java");
	    peticionHora.setOntology ("time");
	    peticionHora.setContent ("TIME?");
	    Conversation conv;
	    conv = km.sendMessage (peticionHora);
	    String hora = (String) conv.waitAndGetResponse (60000);
	    System.out.println ("La hora que me ha dado el servidor es: " + hora);
	} catch (Exception ex) {
	    System.err.println ("Clase Cliente");
	    System.err.println ("Segunda excepción");
	    System.err.println ("=================");
	    ex.printStackTrace ();
	}
    }

    public static void main (String[] args) {
	new Cliente ();
    }
}

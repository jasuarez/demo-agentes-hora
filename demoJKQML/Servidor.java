/*
 * $Id$
 */

import com.ibm.acl.kqml.*;
import java.net.*;


/**
 * Class Servidor
 *
 * Muestra la hora actual al agente que se lo pida
 *
 * @author  $Author$
 * @version $Revision$
 */

public class Servidor extends SkeletonAgent {
    int puertoEscucha = 22202;
    String agente = "servidorHora";
    KQMLManager km;

    public Servidor () {
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
	    System.out.println ("Servidor registrado!");
	} catch (Exception ex) {
	    System.err.println ("Clase Cliente");
	    System.err.println ("Primera excepción");
	    System.err.println ("=================");
	    ex.printStackTrace ();
	}
    }

    public static void main (String[] args) {
	new Servidor ();
    }
}

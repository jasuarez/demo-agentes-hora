/*
 * $Id$
 */

import com.ibm.acl.kqml.*;


/**
 * Class SkeletonAgent
 *
 * Contiene los metodos para registrarse y desregistrarse.
 *
 * @author  $Author$
 * @version $Revision$
 */

public class SkeletonAgent {
    private static final String facilitator = "facilitator";
    private String nombreAgente;
    private String direccion;

    public String register (KQMLManager km, String nombreAgente, String direccion) {
	try {
	    KQML registro = new KQML ();
	    registro.setPerformative (KQML.REGISTER);
	    registro.setSender (direccion);
	    registro.setReceiver (facilitator);
	    registro.setRW(km.getInitialID ());
	    registro.setLanguage ("anACL"); // Aqui el lenguaje es indiferente
	    registro.setOntology ("whitepages");
	    registro.setContent ("(:name " + nombreAgente + " :url " + direccion + ")");
	    Conversation conv;
	    conv = km.sendMessage (registro);
	    String numReg = (String) conv.waitAndGetResponse (60000);
	    this.nombreAgente = nombreAgente;
	    this.direccion = direccion;
	    return numReg;
	} catch (Exception ex) {
	    System.err.println ("Clase SkeletonAgent");
	    System.err.println ("Primera excepción");
	    System.err.println ("===================");
	    ex.printStackTrace ();
	    System.exit (1);
	    return null;
	}
    }

    void unregister (KQMLManager km, String numReg) {
	try {
	    KQML desregistro = new KQML ();
	    desregistro.setPerformative (KQML.UNREGISTER);
	    desregistro.setSender (this.direccion);
	    desregistro.setReceiver (facilitator);
	    desregistro.setRW (km.getInitialID ());
	    desregistro.setLanguage ("anACL"); // Aqui no importa el ACL
	    desregistro.setOntology ("whitepages");
	    desregistro.setContent ("(:name " + this.nombreAgente +
				    "\n :registration_number " + numReg + ")");
	    Conversation conv;
	    conv = km.sendMessage (desregistro);
	    String result = (String) conv.waitAndGetResponse (60000);
	} catch (Exception ex) {
	    System.err.println ("Clase SkeletonAgent");
	    System.err.println ("Segunda excepción");
	    System.err.println ("===================");
	    ex.printStackTrace ();
	}
    }
}

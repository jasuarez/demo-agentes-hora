/*
 * $Id$
 */

import com.ibm.acl.kqml.*;
import com.ibm.acl.kqml.contentinterpreter.*;
import java.util.*;


/**
 * Class TimeInterpreter
 *
 * Interpreta los mensajes KQML.
 *
 * @author  $Author$
 * @version $Revision$
 */

public class TimeInterpreter implements ContentInterpreter {
    public ResponseSet doInterpret (Conversation conv, KQML msg)
	throws ContentException, PerformativeHandlerNotFoundException {
	System.out.println ("Entro en Timeinterpreter");
	KQMLManager km = conv.getKQMLManager ();
	String performative = msg.getPerformative ();
	if (performative.equalsIgnoreCase (KQML.ACHIEVE)) {
	    System.out.println ("Se ha recibido un 'achieve'");
	    return doAchieve (conv, msg);
	} else if (performative.equalsIgnoreCase (KQML.TELL)) {
	    System.out.println ("Se ha recibido un 'tell'");
	    return doTell (conv, msg);
	} else if (performative.equalsIgnoreCase (KQML.ERROR)) {
	    System.out.println ("Se ha recibido un 'error'");
	    return doError (conv, msg);
	} else {
	    throw new PerformativeHandlerNotFoundException ("No se puede tratar el mensaje");
	}
    }
    
    private ResponseSet doAchieve (Conversation conv, KQML msg) throws ContentException {
	try {
	    String contenido = (String) msg.getContent ();
	    ResponseSet respuestas = new ResponseSet ();
	    KQML respuesta = new KQML ();
	    if (contenido.equals ("TIME?")) {
		respuesta.setPerformative (KQML.TELL);
		respuesta.setSender (msg.getReceiver ());
		respuesta.setReceiver (msg.getSender ());
		respuesta.setIRT (msg.getRW ());
		respuesta.setLanguage (msg.getLanguage ());
		respuesta.setOntology (msg.getOntology ());
		respuesta.setContent (new Date().toString ());
	    } else {
		respuesta.setPerformative (KQML.ERROR);
		respuesta.setSender (msg.getReceiver ());
		respuesta.setReceiver (msg.getSender ());
		respuesta.setIRT (msg.getRW ());
		respuesta.setLanguage (msg.getLanguage ());
		respuesta.setOntology (msg.getOntology ());
		respuesta.setContent ("No entiendo tu pregunta");
	    }
	    respuestas.addElement (respuesta);
	    return respuestas;
	} catch (Exception ex) {
	    System.err.println ("Clase TimeInterpreter");
	    System.err.println ("Primera excepcion");
	    System.err.println ("=====================");
	    ex.printStackTrace ();
	    return null;
	}
    }

    private ResponseSet doTell (Conversation conv, KQML msg) throws ContentException {
	String contenido = (String)  msg.getContent ();
	conv.setResponse (contenido);
	return null;
    }

    private ResponseSet doError (Conversation conv, KQML msg) throws ContentException {
	String contenido = (String) msg.getContent ();
	conv.setResponse (contenido); //En principio devolvemos el error como una respuesta
	return null;
    }
}

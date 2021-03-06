/**
 *Interface class to fix the method that is 
 *involved to handle the SIB event messages. 
 * <p>
 * @author      Daniele Manzaroli - VTT-Oulu, Technical Research Centre of Finland. ARCES, University of Bologna, Italy
 * @version     %I%, %G%
 */

package sofia_kp;

import java.util.ArrayList;

public interface iKPIC_subscribeHandler2 {
	
	String subID = "";

	public void kpic_RDFEventHandler(ArrayList<ArrayList<String>> newTriples, ArrayList<ArrayList<String>> oldTriples, String indSequence, String subID );
	
	public void kpic_SPARQLEventHandler(SSAP_sparql_response newResults, SSAP_sparql_response oldResults, String indSequence, String subID );
	
	public void kpic_UnsubscribeEventHandler(String sub_ID );
	
	public void kpic_ExceptionEventHandler(Throwable SocketException );

	
}

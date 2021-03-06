/**
 * Example of subscribe handler implementing the interface iKPIC_subscribeHandler2
 * 
 * @author Alfredo D'Elia
 */

package resources;

import java.util.ArrayList;

import sofia_kp.SSAP_sparql_response;
import sofia_kp.iKPIC_subscribeHandler2;


public class Handler2 implements iKPIC_subscribeHandler2 {

	public Handler2() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void kpic_RDFEventHandler(ArrayList<ArrayList<String>> newTriples,
			ArrayList<ArrayList<String>> oldTriples, String indSequence, String subID) {

		String temp = "\nNotification " + indSequence + " id = " + subID +"\n";
		for(int i = 0; i < newTriples.size(); i++ )
		{
			temp+="New triple s =" + newTriples.get(i).get(0) + "  + predicate" + newTriples.get(i).get(1) + "object =" + newTriples.get(i).get(2) +"\n";
		}
		for(int i = 0; i < oldTriples.size(); i++ )
		{
			temp+="Obsolete triple s =" + oldTriples.get(i).get(0) + "  + predicate" + oldTriples.get(i).get(1) + "object =" + oldTriples.get(i).get(2) + "\n";
		}
		System.out.println(temp);
	



		// TODO Auto-generated method stub

	}

	@Override
	public void kpic_SPARQLEventHandler(SSAP_sparql_response newResults,
			SSAP_sparql_response oldResults, String indSequence, String subID) {
		// TODO Auto-generated method stub
		System.out.println("\nNotification " + indSequence  +" id = " + subID + "\n");
		
		if (newResults != null)
		{
			System.out.println("new: \n " + newResults.print_as_string());
			
		}
		if (oldResults != null)
		{
			System.out.println("obsolete: \n " + oldResults.print_as_string());
			
		}
	}

	@Override
	public void kpic_UnsubscribeEventHandler(String sub_ID) {
		// TODO Auto-generated method stub
		System.out.println("Unsubscribed " + sub_ID);
	

	}

	@Override
	public void kpic_ExceptionEventHandler(Throwable SocketException) {
		// TODO Auto-generated method stub

		System.out.println("Exception in subscription handler " + SocketException.toString());
		

	}

}

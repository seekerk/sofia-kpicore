/**
 * Example of subscribe handler under the old handler philosophy, the use of this kind of handlers is deprecated since 2014
 * @author Alfredo D'Elia
 */
package resources;

import java.util.ArrayList;

import sofia_kp.SSAP_XMLTools;
import sofia_kp.SSAP_sparql_response;


public class Handler  implements sofia_kp.iKPIC_subscribeHandler{
	


	//	ArrayList<ArrayList<String>> triples_n ;
	//	ArrayList<ArrayList<String>> triples_o;
	//String xml_i ;
	//k++;



	@Override
	public void kpic_SIBEventHandler(final String xml)
	{
		//System.out.println("##########"+ xml);
		new Thread(
				new Runnable() {
                                        @Override
					public void run() {
						String id;
						
						SSAP_XMLTools xmlTools = new SSAP_XMLTools();
						String k = xmlTools.getSSAPmsgIndicationSequence(xml);
						id = xmlTools.getSubscriptionID(xml);
						System.out.println (xml);
						if(xmlTools.isRDFNotification(xml))
						{
							ArrayList<ArrayList<String>> triples_n;
									                	triples_n = xmlTools.getNewResultEventTriple(xml);
									                	ArrayList<ArrayList<String>> triples_o;
									                	triples_o = xmlTools.getObsoleteResultEventTriple(xml);
									                	String temp = "\n Notif. " + k + " id = " + id +"\n";
									                	for(int i = 0; i < triples_n.size(); i++ )
									                	{
									                		temp+="New triple s =" + triples_n.get(i).get(0) + "  + predicate" + triples_n.get(i).get(1) + "object =" + triples_n.get(i).get(2) +"\n";
									                	}
									                	for(int i = 0; i < triples_o.size(); i++ )
									                	{
									                		temp+="Obsolete triple s =" + triples_o.get(i).get(0) + "  + predicate" + triples_o.get(i).get(1) + "object =" + triples_o.get(i).get(2) + "\n";
									                	}
									                	System.out.println(temp);
						}
						else
						{
							System.out.println("Notif. " + k + " id = " + id +"\n");
							SSAP_sparql_response resp_new = xmlTools.get_SPARQL_indication_new_results(xml);
							SSAP_sparql_response resp_old = xmlTools.get_SPARQL_indication_obsolete_results(xml);
							if (resp_new != null)
							{
								System.out.println("new: \n " + resp_new.print_as_string());
							}
							if (resp_old != null)
							{
								System.out.println("obsolete: \n " + resp_old.print_as_string());
							}
						}
						
					}
				}).start();
	}



}

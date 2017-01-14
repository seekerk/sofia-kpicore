/**
 * This class works with jena libraries correctly imported into namespace. Libraries are not directly included
 * for reasosns of dimension and because of restrictions on directly including Jena liraries into external projects.
 * Jena libraries can be downloaded from the official Jena libraries website
 * @author Alfredo D'Elia
 *
 */

package resources;

import java.util.ArrayList;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import sofia_kp.SSAP_XMLTools;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;



public class JenaBasedOntologyLoader {
	
	String SIB_host = "mml.arces.unibo.it";
	int SIBPort = 10010;
//	String SIB_host = "127.0.0.1";
//    int SIBPort = 10111;
	String SIBName = "X";
	KPICore kp ;
	SIBResponse resp = new SIBResponse();
	private String ontology_path = "";//path to the ontology 
	ArrayList<ArrayList<String>> literalTriples = new ArrayList<>();
	ArrayList<ArrayList<String>> ObjectTriples = new ArrayList<>();


	public static void main(String[] argv)
	{
		JenaBasedOntologyLoader loader = new JenaBasedOntologyLoader();
		loader.LoadOntologyIntoSIB( );
	}
	
	
	public void LoadOntologyIntoSIB (String OntologyPath) 
	{
		
		OntModel model = ModelFactory.createOntologyModel();
		model.read(OntologyPath);
		StmtIterator it = model.listStatements();
		//System.out.println("***" + it.toList().size());
		Statement st;
		while (it.hasNext())
		{
			st = it.next();
			if(st.getObject().isLiteral())
			{
				addLiteralTriple(st);
			}
			else
			{
				addObjectTriple(st);
			}
		}

		boolean ok = bufferedInsert();
//		String ontString = "";
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		model.write(baos);
//		
//		kp = new KPICore(SIB_host, SIBPort, SIBName);
//		kp.join();
//		ontString = baos.toString();
//		System.out.println("*****************" + ontString);
//		resp = kp.insert_rdf_xml(ontString);//.replace("\n", "").replace("\r", ""));
		if(ok)
		{
			System.out.println("Ontology correctly inserted");
		}
		else
		{
			System.out.println("Ontology not inserted");

		}
		
		// TODO Auto-generated constructor stub
	}
	
	public void LoadOntologyIntoSIB ()
	{
		this.LoadOntologyIntoSIB(ontology_path);
	}
	
	public void addLiteralTriple(Statement st)
	{
		ArrayList<String> temp  =new ArrayList<>();
		SSAP_XMLTools ssap = new SSAP_XMLTools();
		temp = ssap.newTriple(st.getSubject().getURI(), st.getPredicate().getURI(), st.getObject().asLiteral().getString(), "uri", "literal");
	literalTriples.add(temp);
	}
	public void addObjectTriple(Statement st)
	{
		ArrayList<String> temp  =new ArrayList<>();
		SSAP_XMLTools ssap = new SSAP_XMLTools();
		temp = ssap.newTriple(st.getSubject().getURI(), st.getPredicate().getURI(), st.getObject().asResource().getURI(), "uri", "uri");
	    ObjectTriples.add(temp);
	}
	
	public boolean bufferedInsert()
	{
		ArrayList<ArrayList<String>> triples = new ArrayList<>();
		kp = new KPICore(SIB_host, SIBPort, SIBName);
		kp.join();
		while((ObjectTriples.size()>0) || (literalTriples.size()>0)   )
		{
			triples =  new ArrayList<>();
			while(triples.size()<100 && ( (ObjectTriples.size()>0) || (literalTriples.size()>0)  ) )
			{
				if(ObjectTriples.size()>0)
				{
					triples.add(ObjectTriples.get(0));
					ObjectTriples.remove(0);
				}
				if(literalTriples.size()>0)
				{
					triples.add(literalTriples.get(0));
					literalTriples.remove(0);
				}
			}
			resp = kp.insert(triples);
			if(!resp.isConfirmed())
			{
				return false;
			}
			
		}
		return true;
			
	}

}

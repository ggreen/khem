package khem.solutions.cheminformatics.dao.gemfire.server;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import khem.solutions.cheminformatics.data.MaterialCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.joelib.JOELibSearch;
import nyla.solutions.core.patterns.iteration.PageCriteria;
import nyla.solutions.core.patterns.iteration.PagingCollection;
import nyla.solutions.core.util.Debugger;

public class FindByMolWithAnnotedFunctionImpl
{

	public Collection<Molecule> findByMol(MaterialCriteria structureCriteria,  Map<String, Molecule> moleculeMap)
	{
		
		Debugger.printInfo(this, "findByMol("+structureCriteria+")");
		
		if(structureCriteria == null)
			return null;
		
		JOELibSearch joeLibSearch = new JOELibSearch();
		Collection<Molecule> moleculeCollection = new ArrayList<Molecule>();
		
		String queryMol = structureCriteria.getMolString();
		
		PageCriteria pageCriteria = structureCriteria.getPageCriteria();
		
		int pageSize = pageCriteria.getSize();
		
		for (Molecule molecule : moleculeMap.values())
		{
			if(joeLibSearch.isSubSearch(queryMol, molecule.getMolString())){
				moleculeCollection.add(molecule);		
				
				if(pageSize > 0 && moleculeCollection.size() >= pageSize)
				{
					break;
				}
			}
		}
		
		
		PagingCollection<Molecule> pCollect = new PagingCollection<Molecule> 
						(moleculeCollection, pageCriteria);
		
		return pCollect;
		
	}
}

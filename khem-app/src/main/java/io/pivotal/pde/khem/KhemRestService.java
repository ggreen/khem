package io.pivotal.pde.khem;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.pde.khem.data.KHEMCriteria;
import io.pivotal.pde.khem.data.Molecule;

@RestController
public class KhemRestService
{

	@RequestMapping("/ping")
	@ResponseBody
	public String index()
	{
		return "hello";

	}//------------------------------------------------
	@PostMapping(path="/findMolecules",consumes = {"application/json;charset=UTF-8"},
	 produces = "application/json")
	public Collection<Molecule> findMolecules(@Valid @RequestBody  KHEMCriteria criteria)
	{
		try
		{
			Collection<Molecule> results = moleculeService.findMolecules(criteria);
			
			return results;
		}
		catch (RuntimeException e)
		{
			e.printStackTrace();
			throw e;
		}
		
	}//------------------------------------------------
	
	@PostMapping(path="/saveMolecule",consumes = {"application/json;charset=UTF-8"},
	 produces = "application/json")
	public Molecule saveMolecule(@Valid @RequestBody Molecule molecule)
	{
		System.out.println("molecule:"+molecule);
		
		String id = new StringBuilder()
		.append(molecule.getSourceCode())
		.append("-")
		.append(molecule.getName()).toString();
		
		molecule.setId(id);
		
		return moleculeService.saveMolecule(molecule);
		
		
	}//------------------------------------------------
	
	@Autowired MoleculeService moleculeService;

}

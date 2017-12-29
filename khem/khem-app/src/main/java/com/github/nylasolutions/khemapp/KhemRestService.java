package com.github.nylasolutions.khemapp;

import java.util.Collection;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import khem.solutions.cheminformatics.CommasCompoundMgmt;
import khem.solutions.cheminformatics.data.KHEMCriteria;
import khem.solutions.cheminformatics.data.Molecule;
import nyla.solutions.core.patterns.iteration.Paging;

@Controller
@EnableAutoConfiguration
public class KhemRestService
{

	@RequestMapping("/ping")
	@ResponseBody
	public String index()
	{
		return "hello";

	}
	@PostMapping("/findMolecules")
	public Collection<Molecule> findMolecules(KHEMCriteria criteria)
	{
		return mgmt.findMolecules(criteria);	
		
	}
	
	private CommasCompoundMgmt mgmt = new CommasCompoundMgmt();
}

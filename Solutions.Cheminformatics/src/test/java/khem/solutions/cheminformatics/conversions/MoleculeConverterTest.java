package khem.solutions.cheminformatics.conversions;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.RawMoleculeInfo;
import khem.solutions.cheminformatics.io.SDFEntry;
import khem.solutions.cheminformatics.joelib.JOELibRawMoleculeCanonicalSMILESConverter;
import khem.solutions.cheminformatics.joelib.JOELibRawMoleculeToWeightConverter;
import nyla.solutions.global.io.IO;
import nyla.solutions.global.patterns.conversion.FixedNamedConverter;
import nyla.solutions.global.patterns.conversion.NameableConverter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MoleculeConverterTest
{

	public MoleculeConverterTest()
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@Test
	public void testConvert()
	throws Exception
	{
		

		FixedNamedConverter<RawMoleculeInfo, String> sourceCode = new FixedNamedConverter<RawMoleculeInfo, String>();
		sourceCode.setName("sourceCode");
		sourceCode.setTarget("JUNIT");
		
		RetrieveParamConverter retrieveVersionConverter = new RetrieveParamConverter();
		retrieveVersionConverter.setName("structureKey");
		retrieveVersionConverter.setParameterName("EMOL_VERSION_ID");
		
		Collection<NameableConverter<RawMoleculeInfo, ?>> converters = new ArrayList<NameableConverter<RawMoleculeInfo,?>>();
		
		converters.add(new JOELibRawMoleculeCanonicalSMILESConverter());
		
		
		JOELibRawMoleculeCanonicalSMILESConverter molKey = new JOELibRawMoleculeCanonicalSMILESConverter();
		molKey.setName("molKey");
		converters.add(molKey);
		
		converters.add(new JOELibRawMoleculeToWeightConverter());
		converters.add(sourceCode);
		converters.add(retrieveVersionConverter);
		
		
		MoleculeConverter moleculeConverter  = new MoleculeConverter();
		moleculeConverter.setConverters(converters);
		
		SDFEntry rawMoleculeInfo = new SDFEntry();
		
		Map<String, Collection<String>> parameters = new HashMap<String, Collection<String>>();
		
		parameters.put("EMOL_VERSION_ID", Collections.singleton("1234"));
		rawMoleculeInfo.setParameters(parameters);
		
		String molString = IO.readFile("runtime/input/data/cyclopentane.mol");
		rawMoleculeInfo.setMolString(molString);
		
		Molecule molecule = moleculeConverter.convert(rawMoleculeInfo);
		
		Assert.assertNotNull(molecule);
		
		Assert.assertNotNull(molecule.getCanonicalSMILES());
		Assert.assertNotNull(molecule.getWeight());
		Assert.assertNotNull(molecule.getMolKey());
		Assert.assertNotNull(molecule.getSourceCode());
		Assert.assertNotNull(molecule.getStructureKey());
		
	}

}

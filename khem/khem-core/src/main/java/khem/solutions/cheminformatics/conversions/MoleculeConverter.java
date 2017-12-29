package khem.solutions.cheminformatics.conversions;

import java.util.Collection;

import khem.solutions.cheminformatics.data.Molecule;
import khem.solutions.cheminformatics.data.RawMoleculeInfo;
import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.conversion.NameableConverter;
import nyla.solutions.core.util.JavaBean;

/**
 * @author Gregory Green
 *
 */
public class MoleculeConverter implements Converter<RawMoleculeInfo, Molecule>
{
	/**
	 * Convert to raw molecule into details 
	 * @see nyla.solutions.global.patterns.conversion.Converter#convert(java.lang.Object)
	 */
	@Override
	public Molecule convert(RawMoleculeInfo rawMoleculeInfo)
	{
		Molecule molecule = new Molecule();
		
		molecule.setMolString(rawMoleculeInfo.getMolString());
		
		for (NameableConverter<RawMoleculeInfo, ?> converter: converters)
		{
			JavaBean.setProperty(molecule, 
					converter.getName(), 
					converter.convert(rawMoleculeInfo));
		}
		
		return molecule;
	}// --------------------------------------------------------
	
	
	/**
	 * @param converters the converters to set
	 */
	public void setConverters(
			Collection<NameableConverter<RawMoleculeInfo, ?>> converters)
	{
		this.converters = converters;
	}


	private Collection<NameableConverter<RawMoleculeInfo, ?>> converters;

}

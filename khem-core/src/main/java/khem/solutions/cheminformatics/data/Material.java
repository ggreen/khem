package khem.solutions.cheminformatics.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

public class Material implements Serializable
{
	
	public Material()
	{}
	// --------------------------------------------------------
	/**
	 * Constructor that set defaults
	 * @param molecule the molecule
	 * @param annotations the annotations properties
	 * @param structureKeys the structureKeys
	 */
	public Material(Molecule molecule, Collection<ChemicalAnnotation>annotations,
			Collection<StructureKey> structureKeys)
	{
		this.molecule = molecule;
		
		if(annotations != null && !annotations.isEmpty())
		{
			this.annotations = new ChemicalAnnotation[annotations.size()];
			annotations.toArray(this.annotations);
		}
			
		if(structureKeys != null && !structureKeys.isEmpty())
		{
			this.structureKeys = new StructureKey[structureKeys.size()];
			structureKeys.toArray(this.structureKeys);
		}
	}// --------------------------------------------------------
	/**
	 * Constructor that set defaults
	 * @param molecule the molecule
	 * @param annotations the annotations properties
	 * @param structureKeys the structureKeys
	 */
	public Material(Molecule molecule, ChemicalAnnotation[] annotations,
			StructureKey[] structureKeys)
	{
		this.molecule = molecule;
		this.annotations = annotations;
		this.structureKeys = structureKeys;
	}
	
	/**
	 * @return the molecule
	 */
	public Molecule getMolecule()
	{
		return molecule;
	}

	/**
	 * @param molecule the molecule to set
	 */
	public void setMolecule(Molecule molecule)
	{
		this.molecule = molecule;
	}

	/**
	 * @return the annotations
	 */
	public ChemicalAnnotation[] getAnnotations()
	{
		return annotations;
	}

	/**
	 * @param annotations the annotations to set
	 */
	public void setAnnotations(ChemicalAnnotation[] annotations)
	{
		this.annotations = annotations;
	}

	/**
	 * @return the structureKeys
	 */
	public StructureKey[] getStructureKeys()
	{
		return structureKeys;
	}

	/**
	 * @param structureKeys the structureKeys to set
	 */
	public void setStructureKeys(StructureKey[] structureKeys)
	{
		this.structureKeys = structureKeys;
	}
	
	
	

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(annotations);
		result = prime * result
				+ ((molecule == null) ? 0 : molecule.hashCode());
		result = prime * result + Arrays.hashCode(structureKeys);
		return result;
	}
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		if (!Arrays.equals(annotations, other.annotations))
			return false;
		if (molecule == null)
		{
			if (other.molecule != null)
				return false;
		}
		else if (!molecule.equals(other.molecule))
			return false;
		if (!Arrays.equals(structureKeys, other.structureKeys))
			return false;
		return true;
	}




	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Material [molecule=" + molecule + ", annotations="
				+ Arrays.toString(annotations) + ", structureKeys="
				+ Arrays.toString(structureKeys) + "]";
	}




	private Molecule molecule;
	private ChemicalAnnotation[] annotations;
	private StructureKey[] structureKeys;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5791503838849751260L;
}

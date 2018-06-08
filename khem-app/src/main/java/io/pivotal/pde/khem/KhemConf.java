package io.pivotal.pde.khem;

import org.apache.geode.cache.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gedi.solutions.geode.client.GeodeClient;
import io.pivotal.pde.khem.data.Molecule;

@Configuration
public class KhemConf
{
	@Bean
	public MoleculeService moleculeService()
	{
		return new MoleculeMgmt();
	}

	@Bean
	public Region<String,Molecule> moleculesRegion()
	{
		Region<String,Molecule> region = GeodeClient.connect().getRegion("molecules");
		return region;
		
	}
}

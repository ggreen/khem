package io.pivotal.pde.khem;

import java.util.Collection;

import org.springframework.stereotype.Component;

import gedi.solutions.geode.io.Querier;

@Component
public class QuerierGeodeService implements QuerierService
{

	@Override
	public <T> Collection<T> query(String oql)
	{
		return Querier.query(oql);
	}//------------------------------------------------

}

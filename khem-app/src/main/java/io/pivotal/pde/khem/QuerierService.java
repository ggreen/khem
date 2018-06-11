package io.pivotal.pde.khem;

import java.util.Collection;


public interface QuerierService
{
	public <T> Collection<T> query(String oql);
}

/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.service;

public interface NttbootcampService
{
	String getHybrisLogoUrl(String logoCode);

	void createLogo(String logoCode);
}

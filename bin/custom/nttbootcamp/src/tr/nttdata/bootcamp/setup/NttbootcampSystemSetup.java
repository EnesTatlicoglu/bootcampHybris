/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.setup;

import static tr.nttdata.bootcamp.constants.NttbootcampConstants.PLATFORM_LOGO_CODE;

import de.hybris.platform.core.initialization.SystemSetup;

import java.io.InputStream;

import tr.nttdata.bootcamp.constants.NttbootcampConstants;
import tr.nttdata.bootcamp.service.NttbootcampService;


@SystemSetup(extension = NttbootcampConstants.EXTENSIONNAME)
public class NttbootcampSystemSetup
{
	private final NttbootcampService nttbootcampService;

	public NttbootcampSystemSetup(final NttbootcampService nttbootcampService)
	{
		this.nttbootcampService = nttbootcampService;
	}

	@SystemSetup(process = SystemSetup.Process.INIT, type = SystemSetup.Type.ESSENTIAL)
	public void createEssentialData()
	{
		nttbootcampService.createLogo(PLATFORM_LOGO_CODE);
	}

	private InputStream getImageStream()
	{
		return NttbootcampSystemSetup.class.getResourceAsStream("/nttbootcamp/sap-hybris-platform.png");
	}
}

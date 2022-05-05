/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import tr.nttdata.bootcamp.core.constants.NttCoreConstants;
import tr.nttdata.bootcamp.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class NttCoreManager extends GeneratedNttCoreManager
{
	public static final NttCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (NttCoreManager) em.getExtension(NttCoreConstants.EXTENSIONNAME);
	}
}

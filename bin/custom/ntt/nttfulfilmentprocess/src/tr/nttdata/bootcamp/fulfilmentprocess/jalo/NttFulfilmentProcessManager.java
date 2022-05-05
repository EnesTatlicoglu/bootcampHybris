/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package tr.nttdata.bootcamp.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import tr.nttdata.bootcamp.fulfilmentprocess.constants.NttFulfilmentProcessConstants;

public class NttFulfilmentProcessManager extends GeneratedNttFulfilmentProcessManager
{
	public static final NttFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (NttFulfilmentProcessManager) em.getExtension(NttFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}

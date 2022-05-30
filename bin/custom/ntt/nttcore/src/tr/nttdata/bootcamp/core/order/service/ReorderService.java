package tr.nttdata.bootcamp.core.order.service;

import de.hybris.platform.commerceservices.order.CommerceCartRestoration;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.service.data.CommerceOrderParameter;

public interface ReorderService {

    CommerceCartRestoration reorder(CommerceOrderParameter parameter) throws CommerceCartRestorationException;
}

package tr.nttdata.bootcamp.core.order.strategy;

import de.hybris.platform.commerceservices.order.CommerceCartRestoration;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.service.data.CommerceOrderParameter;

public interface ReorderStrategy {

    CommerceCartRestoration reorder(CommerceOrderParameter parameter) throws CommerceCartRestorationException;

}

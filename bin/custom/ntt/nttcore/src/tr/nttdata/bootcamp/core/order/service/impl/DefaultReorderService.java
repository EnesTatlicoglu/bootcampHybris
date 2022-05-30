package tr.nttdata.bootcamp.core.order.service.impl;

import de.hybris.platform.commerceservices.order.CommerceCartRestoration;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.service.data.CommerceOrderParameter;
import tr.nttdata.bootcamp.core.order.service.ReorderService;
import tr.nttdata.bootcamp.core.order.strategy.ReorderStrategy;

public class DefaultReorderService implements ReorderService {

    private ReorderStrategy reorderStrategy;


    @Override
    public CommerceCartRestoration reorder(CommerceOrderParameter parameter) throws CommerceCartRestorationException {
        return getReorderStrategy().reorder(parameter);
    }

    public ReorderStrategy getReorderStrategy() {
        return reorderStrategy;
    }

    public void setReorderStrategy(ReorderStrategy reorderStrategy) {
        this.reorderStrategy = reorderStrategy;
    }
}

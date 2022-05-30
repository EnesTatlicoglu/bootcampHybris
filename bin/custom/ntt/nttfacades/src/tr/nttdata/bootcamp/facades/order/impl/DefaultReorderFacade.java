package tr.nttdata.bootcamp.facades.order.impl;

import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.order.CommerceCartRestoration;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.commerceservices.service.data.CommerceOrderParameter;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.core.order.service.ReorderService;
import tr.nttdata.bootcamp.facades.order.ReorderFacade;

public class DefaultReorderFacade implements ReorderFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultReorderFacade.class);
    private UserService userService;
    private CustomerAccountService customerAccountService;
    private BaseStoreService baseStoreService;
    private ReorderService reorderService;

    @Override
    public boolean reorder(String orderCode) {
        final BaseStoreModel baseStoreModel = getBaseStoreService().getCurrentBaseStore();

        try
        {
            OrderModel orderModel = getCustomerAccountService().getOrderForCode((CustomerModel) getUserService().getCurrentUser(),
                    orderCode, baseStoreModel);
            if (orderModel != null){
                CommerceOrderParameter parameter = new CommerceOrderParameter();
                parameter.setOrder(orderModel);
                CommerceCartRestoration restoraiton = reorderService.reorder(parameter);
                return restoraiton != null;
            }
        }
        catch (final ModelNotFoundException e)
        {
            throw new UnknownIdentifierException("Order " + orderCode + " could not be found!");
        }catch (CommerceCartRestorationException e){
            LOG.error("Cart could not be restored for order {}", orderCode, e);
        }

        return false;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public CustomerAccountService getCustomerAccountService() {
        return customerAccountService;
    }

    public void setCustomerAccountService(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    public BaseStoreService getBaseStoreService() {
        return baseStoreService;
    }

    public void setBaseStoreService(BaseStoreService baseStoreService) {
        this.baseStoreService = baseStoreService;
    }

    public ReorderService getReorderService() {
        return reorderService;
    }

    public void setReorderService(ReorderService reorderService) {
        this.reorderService = reorderService;
    }
}

package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class CartCustomerNotePopulator implements Populator<CartModel, CartData> {

    @Override
    public void populate(CartModel source, CartData target) throws ConversionException {
        target.setCustomerNote(source.getCustomerNote());
    }

}
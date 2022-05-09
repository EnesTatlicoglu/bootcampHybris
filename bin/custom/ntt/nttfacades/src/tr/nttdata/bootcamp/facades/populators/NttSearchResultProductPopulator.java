package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class NttSearchResultProductPopulator extends SearchResultProductPopulator {

    @Override
    public void populate(SearchResultValueData source, ProductData target) throws ConversionException {
        target.setNumberOfReviews(this.<Integer>getValue(source, "reviewCount"));
    }

}
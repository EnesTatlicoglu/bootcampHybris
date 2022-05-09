package tr.nttdata.bootcamp.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.CustomerReviewService;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;

public class ProductReviewCountValueResolver extends AbstractValueResolver<ProductModel, Integer, Object> {

    private CustomerReviewService customerReviewService;

    @Override
    protected void addFieldValues(InputDocument document, IndexerBatchContext batchContext,
                                  IndexedProperty indexedProperty, ProductModel product,
                                  ValueResolverContext<Integer, Object> resolverContext) throws FieldValueProviderException {
        if (product == null)
        {
            throw new IllegalArgumentException("No product given");
        }

        final Integer fieldValue = getCustomerReviewService().getNumberOfReviews(product);
        this.addFieldValue(document, batchContext, indexedProperty, fieldValue, resolverContext.getFieldQualifier());
    }

    public CustomerReviewService getCustomerReviewService() {
        return customerReviewService;
    }

    public void setCustomerReviewService(CustomerReviewService customerReviewService) {
        this.customerReviewService = customerReviewService;
    }
}
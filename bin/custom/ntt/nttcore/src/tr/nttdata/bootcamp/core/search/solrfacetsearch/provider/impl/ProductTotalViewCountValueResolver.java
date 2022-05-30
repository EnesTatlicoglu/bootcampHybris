package tr.nttdata.bootcamp.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.CustomerReviewService;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;
import tr.nttdata.bootcamp.core.product.service.ProductViewHistoryService;

public class ProductTotalViewCountValueResolver extends AbstractValueResolver<ProductModel, Integer, Object> {

    private ProductViewHistoryService productViewHistoryService;

    @Override
    protected void addFieldValues(InputDocument document, IndexerBatchContext batchContext,
                                  IndexedProperty indexedProperty, ProductModel product,
                                  ValueResolverContext<Integer, Object> resolverContext) throws FieldValueProviderException {
        if (product == null)
        {
            throw new IllegalArgumentException("No product given");
        }

        final Integer fieldValue = getProductViewHistoryService().getTotalView(product);
        this.addFieldValue(document, batchContext, indexedProperty, fieldValue, resolverContext.getFieldQualifier());
    }

    public ProductViewHistoryService getProductViewHistoryService() {
        return productViewHistoryService;
    }

    public void setProductViewHistoryService(ProductViewHistoryService productViewHistoryService) {
        this.productViewHistoryService = productViewHistoryService;
    }
}
package tr.nttdata.bootcamp.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;
import tr.nttdata.bootcamp.core.model.BestSellerProductModel;
import tr.nttdata.bootcamp.core.product.service.BestSellerProductService;
import tr.nttdata.bootcamp.core.product.service.ProductViewHistoryService;

public class ProductBestSellerCountValueResolver extends AbstractValueResolver<ProductModel, Long, Object> {

    private BestSellerProductService bestSellerProductService;

    @Override
    protected void addFieldValues(InputDocument document, IndexerBatchContext batchContext,
                                  IndexedProperty indexedProperty, ProductModel product,
                                  ValueResolverContext<Long, Object> resolverContext) throws FieldValueProviderException {
        if (product == null)
        {
            throw new IllegalArgumentException("No product given");
        }

        final BestSellerProductModel bestSellerProduct = getBestSellerProductService().getBestSellerProduct(product.getCode());
        if (bestSellerProduct != null) {
            this.addFieldValue(document, batchContext, indexedProperty, bestSellerProduct.getSoldCount(), resolverContext.getFieldQualifier());
        }
    }

    public BestSellerProductService getBestSellerProductService() {
        return bestSellerProductService;
    }

    public void setBestSellerProductService(BestSellerProductService bestSellerProductService) {
        this.bestSellerProductService = bestSellerProductService;
    }
}
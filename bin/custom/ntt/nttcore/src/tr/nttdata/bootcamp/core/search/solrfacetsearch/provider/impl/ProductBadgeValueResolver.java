package tr.nttdata.bootcamp.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.indexer.IndexerBatchContext;
import de.hybris.platform.solrfacetsearch.indexer.spi.InputDocument;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractValueResolver;
import org.apache.commons.collections4.CollectionUtils;
import tr.nttdata.bootcamp.core.model.ProductBadgeModel;

import java.util.Set;

public class ProductBadgeValueResolver extends AbstractValueResolver<ProductModel, String, Object> {

    @Override
    protected void addFieldValues(InputDocument document,
                                  IndexerBatchContext batchContext,
                                  IndexedProperty indexedProperty,
                                  ProductModel product,
                                  ValueResolverContext<String, Object> resolverContext) throws FieldValueProviderException {
        if(product == null){
            throw new IllegalArgumentException("No product given");
        }

        final Set<ProductBadgeModel> badges = product.getBadges();
        if(CollectionUtils.isEmpty(badges)){
            return;
        }

       for (ProductBadgeModel badge : badges){
            this.addFieldValue(document, batchContext, indexedProperty, badge.getCode(), resolverContext.getFieldQualifier());
       }

    }
}

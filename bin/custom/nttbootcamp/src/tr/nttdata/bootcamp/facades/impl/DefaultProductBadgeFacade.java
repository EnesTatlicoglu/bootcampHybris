package tr.nttdata.bootcamp.facades.impl;

import de.hybris.platform.servicelayer.dto.converter.Converter;
import tr.nttdata.bootcamp.data.ProductBadgeData;
import tr.nttdata.bootcamp.facades.ProductBadgeFacade;
import tr.nttdata.bootcamp.model.ProductBadgeModel;
import tr.nttdata.bootcamp.service.ProductBadgeService;

public class DefaultProductBadgeFacade implements ProductBadgeFacade {

    private ProductBadgeService productBadgeService;
    private Converter<ProductBadgeModel, ProductBadgeData> productBadgeConverter;

    @Override
    public ProductBadgeData getProductBadgeForCode(String code) {
        final ProductBadgeModel productBadge = getProductBadgeService().getProductBadge(code);
        return productBadge != null ? getProductBadgeConverter().convert(productBadge) : null;
    }

    @Override
    public ProductBadgeData createProductBadgeForCode(String code) {
        final ProductBadgeModel productBadge = getProductBadgeService().createBadgeForCode(code);
        return getProductBadgeConverter().convert(productBadge);
    }

    @Override
    public void deleteProductBadge(String code) {
        getProductBadgeService().deleteBadgeForCode(code);
    }

    public ProductBadgeService getProductBadgeService() {
        return productBadgeService;
    }

    public void setProductBadgeService(ProductBadgeService productBadgeService) {
        this.productBadgeService = productBadgeService;
    }

    public Converter<ProductBadgeModel, ProductBadgeData> getProductBadgeConverter() {
        return productBadgeConverter;
    }

    public void setProductBadgeConverter(Converter<ProductBadgeModel, ProductBadgeData> productBadgeConverter) {
        this.productBadgeConverter = productBadgeConverter;
    }

}
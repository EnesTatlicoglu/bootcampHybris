package tr.nttdata.bootcamp.facades.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import tr.nttdata.bootcamp.core.model.ProductBadgeModel;
import tr.nttdata.bootcamp.facades.product.data.ProductBadgeData;

public class ProductBadgePopulator implements Populator<ProductBadgeModel, ProductBadgeData> {

    private Converter<MediaModel, ImageData> imageConverter;

    @Override
    public void populate(ProductBadgeModel source, ProductBadgeData target) throws ConversionException {
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        final MediaModel logo = source.getLogo();
        if(logo != null){
            target.setLogo(getImageConverter().convert(logo));
        }else{
            //TODO populate logo as not found image
        }
    }

    public Converter<MediaModel, ImageData> getImageConverter() {
        return imageConverter;
    }

    public void setImageConverter(Converter<MediaModel, ImageData> imageConverter) {
        this.imageConverter = imageConverter;
    }
}

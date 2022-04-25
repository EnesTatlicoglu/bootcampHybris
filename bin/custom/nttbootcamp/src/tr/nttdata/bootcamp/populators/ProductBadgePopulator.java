package tr.nttdata.bootcamp.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import tr.nttdata.bootcamp.data.ProductBadgeData;
import tr.nttdata.bootcamp.model.ProductBadgeModel;

public class ProductBadgePopulator implements Populator<ProductBadgeModel, ProductBadgeData> {

    @Override
    public void populate(ProductBadgeModel source, ProductBadgeData target) throws ConversionException {
        target.setCode(source.getCode());
        target.setTitle(source.getTitle());
        target.setDescription(source.getDescription());
        final MediaModel logoMedia = source.getLogoMedia();
        if(logoMedia != null){
            target.setLogo(logoMedia.getURL());
        } else {
            target.setLogo(source.getLogo());
        }
    }

}
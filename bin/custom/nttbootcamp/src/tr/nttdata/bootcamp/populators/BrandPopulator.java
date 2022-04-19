package tr.nttdata.bootcamp.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import tr.nttdata.bootcamp.data.BrandData;
import tr.nttdata.bootcamp.model.BrandModel;

public class BrandPopulator implements Populator<BrandModel, BrandData> {
    @Override
    public void populate(BrandModel source, BrandData target) throws ConversionException {
        target.setCode(source.getCode());
        target.setName(source.getName());
        final MediaModel logo = source.getLogo();
        if(logo != null){
            target.setLogo(logo.getURL());
        }
    }
}

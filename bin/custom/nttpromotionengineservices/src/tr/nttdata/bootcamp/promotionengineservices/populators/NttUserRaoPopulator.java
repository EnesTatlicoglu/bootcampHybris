package tr.nttdata.bootcamp.promotionengineservices.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.ruleengineservices.rao.UserRAO;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import tr.nttdata.bootcamp.core.user.dao.NttUserDao;

public class NttUserRaoPopulator implements Populator<UserModel, UserRAO> {

    private NttUserDao nttUserDao;

    @Override
    public void populate(UserModel source, UserRAO target) throws ConversionException {
        target.setHasOrder(getNttUserDao().hasOrder(source));
    }

    public NttUserDao getNttUserDao() {
        return nttUserDao;
    }

    public void setNttUserDao(NttUserDao nttUserDao) {
        this.nttUserDao = nttUserDao;
    }
}
package tr.nttdata.bootcamp.service.impl;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.impl.DefaultUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NttUserService extends DefaultUserService {

    private static final Logger LOG = LoggerFactory.getLogger(NttUserService.class);

    @Override
    public UserModel getUserForUID(String userId) {
        LOG.debug("Using NTT Data User Service to search user {}", userId);
        return super.getUserForUID(userId);
    }
}
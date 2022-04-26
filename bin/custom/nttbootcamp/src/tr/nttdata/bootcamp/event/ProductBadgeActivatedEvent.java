package tr.nttdata.bootcamp.event;

import de.hybris.platform.servicelayer.event.events.AbstractEvent;

public class ProductBadgeActivatedEvent extends AbstractEvent {

    private final String code;
    private final Long pk;
    private final String userId;

    public ProductBadgeActivatedEvent(String code, Long pk, String userId) {
        super();
        this.code = code;
        this.pk = pk;
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public Long getPk() {
        return pk;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "ProductBadgeActivatedEvent{" +
                "code='" + code + '\'' +
                ", pk=" + pk +
                ", userId='" + userId + '\'' +
                '}';
    }
}
package tr.nttdata.bootcamp.cache;

import de.hybris.platform.regioncache.key.CacheKey;
import de.hybris.platform.regioncache.key.CacheUnitValueType;
import de.hybris.platform.util.Config;

import java.util.Objects;

public class ProductBadgeCacheKey implements CacheKey {

    private final String tenantId;
    private final String code;

    public ProductBadgeCacheKey(String tenantId, String code) {
        this.tenantId = tenantId;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public CacheUnitValueType getCacheValueType() {
        return null;
    }

    @Override
    public Object getTypeCode() {
        return Config.getBoolean("regioncache.productbadgeregion.usetypecode", true) ? "20500" : "__NOTYPE__";
    }

    @Override
    public String getTenantId() {
        return this.tenantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBadgeCacheKey that = (ProductBadgeCacheKey) o;
        return Objects.equals(tenantId, that.tenantId) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, code);
    }
}
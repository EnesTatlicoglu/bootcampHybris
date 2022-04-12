package tr.nttdata.bootcamp.setup;

import de.hybris.platform.core.initialization.SystemSetup;
import de.hybris.platform.core.initialization.SystemSetupContext;
import de.hybris.platform.core.initialization.SystemSetupParameter;
import de.hybris.platform.core.initialization.SystemSetupParameterMethod;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.nttdata.bootcamp.constants.NttbootcampConstants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SystemSetup(extension = NttbootcampConstants.EXTENSIONNAME)
public class BootcampSystemSetup {

    private static final Logger LOG = LoggerFactory.getLogger(BootcampSystemSetup.class);

    private ImportService importService;

    public BootcampSystemSetup(ImportService importService) {
        this.importService = importService;
    }

    @SystemSetup(type = SystemSetup.Type.ESSENTIAL)
    public boolean putInMyEssentialData()
    {
        LOG.info("Starting custom essential data loading for the {} extension", NttbootcampConstants.EXTENSIONNAME);
        LOG.info("Custom essential data loading for the {} extension completed", NttbootcampConstants.EXTENSIONNAME);
        return true;
    }
    @SystemSetup(type = SystemSetup.Type.ESSENTIAL)
    public boolean putInMyEssentialDataWithContext(SystemSetupContext context)
    {
        LOG.info("Starting custom essential data loading for the {} extension with context", NttbootcampConstants.EXTENSIONNAME);
        final Map<String, String[]> parameterMap = context.getParameterMap();
        if(MapUtils.isNotEmpty(parameterMap)){
            parameterMap.forEach((k, v) -> LOG.info("Parameter {} --> {}", k, v));
        }
        LOG.info("Custom essential data loading for the {} extension completed with context", NttbootcampConstants.EXTENSIONNAME);
        return true;
    }

    @SystemSetup(type = SystemSetup.Type.PROJECT)
    public boolean addMyProjectData()
    {
        LOG.info("Starting custom project data loading for the {} extension", NttbootcampConstants.EXTENSIONNAME);
        impexImport("/impex/testproducts.impex");
        LOG.info("Custom project data loading for the {} extension completed", NttbootcampConstants.EXTENSIONNAME);
        return true;
    }

    @SystemSetup(type = SystemSetup.Type.PROJECT)
    public boolean addMyProjectDataWithContext(SystemSetupContext context)
    {
        LOG.info("Starting custom project data loading for the {} extension with context", NttbootcampConstants.EXTENSIONNAME);
        final Map<String, String[]> parameterMap = context.getParameterMap();
        if(MapUtils.isNotEmpty(parameterMap)){
            parameterMap.forEach((k, v) -> LOG.info("Parameter {} --> {}", k, v));
        }
        LOG.info("Custom project data loading for the {} extension completed with context", NttbootcampConstants.EXTENSIONNAME);
        return true;
    }

    protected void impexImport(final String filename)
    {
        try (final InputStream resourceAsStream = getClass().getResourceAsStream(filename))
        {
            LOG.info("Importing file {}", filename);
            final ImportConfig importConfig = new ImportConfig();
            importConfig.setScript(new StreamBasedImpExResource(resourceAsStream, "UTF-8"));
            importConfig.setLegacyMode(Boolean.FALSE);
            final ImportResult importResult = importService.importData(importConfig);
            if (importResult.isError())
            {
                LOG.error("Import of {} FAILED", filename);
            }
        }
        catch (final Exception e)
        {
            LOG.error("Import of {} FAILED with exception", filename, e);
        }
    }

    @SystemSetupParameterMethod
    public List<SystemSetupParameter> getSystemSetupParameters()
    {
        final List<SystemSetupParameter> params = new ArrayList<>();

        final SystemSetupParameter customDataParameter = new SystemSetupParameter("createCustomData");
        customDataParameter.setLabel("Create custom data?");
        customDataParameter.addValue("true");
        customDataParameter.addValue("false", true);
        params.add(customDataParameter);

        final SystemSetupParameter imports = new SystemSetupParameter("imports");
        imports.setMultiSelect(true);
        imports.setLabel("Data to import : ");
        imports.addValue("users", true);
        imports.addValues(new String[]{ "groups", "tenants", "products", "categories" });
        params.add(imports);

        return params;
    }
}
package tr.nttdata.bootcamp.controller;

import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tr.nttdata.bootcamp.data.BrandData;
import tr.nttdata.bootcamp.facades.BrandFacade;
import tr.nttdata.bootcamp.facades.ProductFacade;

@Controller
public class BrandPageController {

    private static final Logger LOG = LoggerFactory.getLogger(BrandPageController.class);

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private BrandFacade brandFacade;


    @GetMapping(value = "/brands")
    public String getAllProducts(final Model model){
        model.addAttribute("brands", brandFacade.getAllBrands());
        return "BrandsPage";
    }

    @GetMapping(value = "/brands/{brandCode}")
    public String getProduct(final Model model, @PathVariable String brandCode){
        try {
            model.addAttribute("brand", brandFacade.getBrandForCode(brandCode));
            model.addAttribute("products", productFacade.getProductsForBrand(brandCode));
        } catch (UnknownIdentifierException ex){
            LOG.error("Product with code {} could not be found!", brandCode);
            return "redirect:/brands";
        }
        return "BrandDetailsPage";
    }

}
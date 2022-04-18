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
import tr.nttdata.bootcamp.facades.ProductFacade;

@Controller
public class ProductPageController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductPageController.class);

    @Autowired
    private ProductFacade productFacade;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String getAllProducts(final Model model){
        model.addAttribute("products", productFacade.getProducts());
        return "ProductsPage";
    }

    @GetMapping(value = "/products/{productCode}")
    public String getProduct(final Model model, @PathVariable String productCode){
        try {
            model.addAttribute("product", productFacade.getProductForCode(productCode));
        } catch (UnknownIdentifierException ex){
            LOG.error("Product with code {} could not be found!", productCode);
            return "redirect:/products";
        }
        return "ProductDetailsPage";
    }

}
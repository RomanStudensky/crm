package ru.pnzgu.crm.controller.director;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pnzgu.crm.dto.ProductDto;
import ru.pnzgu.crm.service.ProductService;

@Controller
@RequestMapping("/director/product")
@RequiredArgsConstructor
public class ProductController {
    private final String COMMON_VIEW = "/director/product/commonProductView";
    private final String CREATE_VIEW = "/director/product/action/create";
    private final String UPDATE_VIEW = "/director/product/action/update";
    private final String REDIRECT_URL = "redirect:/director/product";

    private final ProductService productService;

    @GetMapping("")
    public String getCommonView(Model model) {
        model.addAttribute("productList", productService.readAll());


        model.addAttribute("productList", productService.readAll());

        return COMMON_VIEW;
    }


    @GetMapping("/create/view")
    public String getProductCreateView(Model model) {
        model.addAttribute("product", new ProductDto());

        return CREATE_VIEW;
    }

    @GetMapping("/update/view/{id}")
    public String getProductUpdateView(@PathVariable Long id, Model model) {
        ProductDto productDto = productService.read(id);
        model.addAttribute("product", productDto);

        return UPDATE_VIEW;
    }

    // ------------- REST ------------

    @PostMapping("/create")
    public String createProduct(@ModelAttribute ProductDto ProductDto) {
        productService.create(ProductDto);

        return REDIRECT_URL;
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute ProductDto ProductDto) {
        productService.update(id, ProductDto);

        return REDIRECT_URL;
    }

}
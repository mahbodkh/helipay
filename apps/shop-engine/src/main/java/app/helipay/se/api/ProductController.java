package app.helipay.se.api;

import app.helipay.se.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/products", produces = "application/json")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    // ==============================================
    //                     CLIENT
    // ==============================================
    @GetMapping("/{id}/")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") Long product) {
        var reply = productService.getProductById(product);
        return ResponseEntity.ok(productMapper.toDto(reply));
    }

    @GetMapping("/all/")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @Valid PaginationParams params
    ) throws BadRequestException {
        var products = productService.getProducts(PageRequest.of(params.getPage(), params.getSize()));
        return ResponseEntity.ok(productMapper.toPage(products));
    }

    // ==============================================
    //                     ADMIN
    // ==============================================
    @PostMapping("/admin/create/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestBody ProductDTO requestDto) throws BadRequestException {
        productService.createProduct(requestDto);
    }

    @PutMapping("/admin/{id}/edit/")
    @ResponseStatus(HttpStatus.OK)
    public void editProduct(@Valid @PathVariable("id") Long productId, @Valid @RequestBody ProductDTO requestDto) {
        productService.updateProduct(productId, requestDto);
    }

    @Operation(summary = "Delete a product by productId.")
    @DeleteMapping("/admin/{id}/delete/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccount(@Valid @PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
    }
}

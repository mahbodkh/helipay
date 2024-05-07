package app.helipay.se.service;

import app.helipay.se.domain.ProductEntity;
import app.helipay.se.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

//    public ProductEntity createProduct(ProductDTO request) {
//        log.info(" ==> Request the productDto prepare: ({})", request);
//        final var product = productMapper.toEntity(request);
//        final var save = productRepository.save(product);
//        log.info(" <== The product has been persisted: ({})", save);
//        return save;
//    }


    /**
     * Get product by product id.
     *
     * @param productId is the id of product and should be Long.
     * @return Product Domain Model.
     */
//    public ProductEntity getProductById(Long productId) {
//        log.info(" ==> Fetching product with ID: {}", productId);
//        return Optional.of(productRepository.findById(productId))
//                .filter(Optional::isPresent)
//                .map(Optional::get)
//                .orElseThrow(() -> {
//                    log.warn("Product with ID {} not found.", productId);
//                    return new ProductNotFoundException("The product (" + productId + ") not found.");
//                });
//    }


    public Page<ProductEntity> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Update a product ( Drink / Topping ).
     *
     * @param productId is a product id and it should be Long.
     * @param request   the product dto comes from the API request.
     * @return an Optional Product.
     */
//    public Optional<ProductEntity> updateProduct(Long productId, ProductDTO request) {
//        return Optional.of(productRepository.findById(productId)).filter(Optional::isPresent).map(Optional::get).map(product -> {
//            var updatedProduct = productMapper.copyProductDtoToEntity(request, product);
//            var savedProduct = productRepository.save(updatedProduct);
//            log.info(" <== The product has been edited: {}", savedProduct);
//            return savedProduct;
//        });
//    }


    /**
     * admin able to delete of the product.
     *
     * @param productId is the id of product.
     */
    public void deleteProduct(Long productId) {
        productRepository.findById(productId).ifPresent(entity -> {
            productRepository.delete(entity);
            log.info(" <== Deleted product: {}", entity);
        });
    }
}

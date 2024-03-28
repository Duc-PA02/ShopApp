package com.example.shopappbackend.services;

import com.example.shopappbackend.dtos.ProductDTO;
import com.example.shopappbackend.dtos.ProductImageDTO;
import com.example.shopappbackend.exceptions.DataNotFoundException;
import com.example.shopappbackend.exceptions.InvalidParamException;
import com.example.shopappbackend.models.Category;
import com.example.shopappbackend.models.Product;
import com.example.shopappbackend.models.ProductImage;
import com.example.shopappbackend.repositories.CategoryRepository;
import com.example.shopappbackend.repositories.ProductImageRepository;
import com.example.shopappbackend.repositories.ProductRepository;
import com.example.shopappbackend.responses.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot found category with id: " + productDTO.getCategoryId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .category(category)
                .description(productDTO.getDescription())
                .build();
        productRepository.save(newProduct);
        return newProduct;
    }

    @Override
    public Product updateProduct(int id, ProductDTO productDTO) throws Exception{
        Product existingProduct = getProductById(id);
        if (existingProduct != null){
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() ->
                            new DataNotFoundException("Cannot found category with id: " + productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(category);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            productRepository.deleteById(id);
        }
    }

    @Override
    public Product getProductById(int id) throws Exception {
        return productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Cannot find product with id = " + id));
    }

    @Override
    public Page<ProductResponse> getAllProduct(PageRequest pageRequest) {
        return productRepository
                .findAll(pageRequest)
                .map(ProductResponse::fromProduct);
    }

    @Override
    public boolean existByName(String name) {
        return productRepository.existsByName(name);
    }
    @Override
    public ProductImage createProductImage(int productId, ProductImageDTO productImageDTO) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new DataNotFoundException("Cannot found category with id: " + productImageDTO.getProductId()));
        ProductImage newProductImage = ProductImage.builder()
                .product(product)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        //Ko cho thêm quá 5 ảnh 1 sp
        int size = productImageRepository.findByProductId(productId).size();
        if (size >= 5){
            throw new InvalidParamException("Number up image must be <= 5");
        }
        return productImageRepository.save(newProductImage);
    }
}

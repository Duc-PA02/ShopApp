package com.example.shopappbackend.controllers;

import com.example.shopappbackend.dtos.ProductDTO;
import com.example.shopappbackend.dtos.ProductImageDTO;
import com.example.shopappbackend.exceptions.DataNotFoundException;
import com.example.shopappbackend.models.Product;
import com.example.shopappbackend.models.ProductImage;
import com.example.shopappbackend.responses.product.ProductListResponse;
import com.example.shopappbackend.responses.product.ProductResponse;
import com.example.shopappbackend.services.IProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    @PostMapping( "")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO,
                                           BindingResult result){
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok().body(newProduct);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable("id") int productId, @ModelAttribute("files") List<MultipartFile> files){
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files){
                if (file.getSize() == 0){
                    continue;
                }
                //Kiểm tra kích thước và định dạng file
                if (file.getSize() > 10*1024*1024){ //kích thước > 10MB
                    throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE,"File is too larget! Maximun size in 10MB");
                }
                String contentFile = file.getContentType();
                if (contentFile == null || !contentFile.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image");
                }
                String fileName = storeFile(file);
                ProductImage productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(fileName)
                                .build());
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        //Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        //Đường dẫn đến thư mục muốn lưu file
        Path uploadDir = Paths.get("uploads");
        //Kiểm tra và tao thư mục nếu nó không tồn tại
        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Đường dẫn đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        //Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(@RequestParam int page, @RequestParam int limit){
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductResponse> productPage = productService.getAllProduct(pageRequest);
        //Lấy tổng số trang
        int totalPage = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok().body(ProductListResponse
                .builder()
                .products(products)
                .totalPages(totalPage)
                .build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") int productId){
        try {
            Product existingProduct = productService.getProductById(productId);
            return ResponseEntity.ok().body(ProductResponse.fromProduct(existingProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") int id, @RequestBody ProductDTO productDTO){
        try {
            Product updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok().body(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //@PostMapping("/generateFakeProducts")
    public ResponseEntity<?> generateFakeProduct(){
        Faker faker = new Faker();
        for (int i = 0; i < 10000; i++){
            String productName = faker.commerce().productName();
            if (productService.existByName(productName)){
                continue;
            }
            ProductDTO productDTO = ProductDTO.builder()
                    .name(productName)
                    .price((float)faker.number().numberBetween(10,10000))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .categoryId(faker.number().numberBetween(2,6))
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake products created successfully");
    }
}

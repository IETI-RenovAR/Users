package org.adaschool.project.controller.user;

import jakarta.annotation.security.RolesAllowed;
import org.adaschool.project.dto.*;
import org.adaschool.project.model.*;
import org.adaschool.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;

import static org.adaschool.project.utils.Constants.ADMIN_ROLE;

@RestController()
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Users

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> userEntities = userService.getAllUsers();
        return ResponseEntity.ok(userEntities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable("id") String id) {
        UserEntity userEntity = userService.getUserById(id);
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserDTO userDTO) {
        UserEntity createdUserEntity = userService.saveUser(userDTO);
        URI createdUserUri = URI.create("/v1/users/" + createdUserEntity.getId());
        return ResponseEntity.created(createdUserUri).body(createdUserEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        UserEntity updatedUserEntity = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUserEntity);
    }

    @RolesAllowed(ADMIN_ROLE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }

    // Products

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        try {
            return ResponseEntity.ok(userService.getAllProducts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") String productId){
        try {
            return ResponseEntity.ok(userService.getProductById(productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/products/{idProduct}/price")
    public ResponseEntity<Double> getProductPrice(@PathVariable("idProduct") String productId){
        try {
            return ResponseEntity.ok(userService.getProductPrice(productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/products/{idProduct}/dimensions")
    public ResponseEntity<String> getProductDimensions(@PathVariable("idProduct") String productId){
        try {
            return ResponseEntity.ok(userService.getProductDimensions(productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/products/{idProduct}/seller")
    public ResponseEntity<String> getProductSeller(@PathVariable("idProduct") String productId){
        try {
            return ResponseEntity.ok(userService.getProductStore (productId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/products/sort/{criteria}/{category}")
    public ResponseEntity<List<Product>> sortProducts(@PathVariable("criteria") String criteria,
                                                      @PathVariable("category") String category){
        try {
            return ResponseEntity.ok(userService.sortProducts(criteria, category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO){
        try {
            return ResponseEntity.ok(userService.createProduct(productDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable("productId") String productId,
                                                 @RequestBody ProductDTO productDTO){
        try {
            return ResponseEntity.ok(userService.updateProduct(productId, productDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") String productId){
        try {
            userService.deleteProduct(productId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    // Designs

    @GetMapping("/designs")  //revisar
    public ResponseEntity<List<Design>> getAllPublicDesigns(){
        try {
            return ResponseEntity.ok(userService.getAllPublicDesigns());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/designs/{idDesign}") //revisar
    public ResponseEntity<Design> getDesignById(@PathVariable("idDesign") String idDesign){
        try {
            return ResponseEntity.ok(userService.getDesignById(idDesign));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/designs/user/{userId}") 
    public ResponseEntity<List<Design>> getDesignsOfUser(@PathVariable("userId") String userId) throws Exception {
        return ResponseEntity.ok(userService.getDesignsOfUser(userId));
    }

    @GetMapping("/designs/{idDesign}/quotations") //por probar
    public ResponseEntity<List<Quotation>> getAllQuotations(String idDesign){
        try {
            return ResponseEntity.ok(userService.getAllQuotations(idDesign));
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/quotations/{idQuotation}")
    public ResponseEntity<Quotation> getQuotationById(String idQuotation) throws Exception {
        return ResponseEntity.ok(userService.getQuotationById(idQuotation));
    }

    @PostMapping("/designs")
    public ResponseEntity<Design> addDesign(@RequestBody DesignDTO design){
        try {
            return ResponseEntity.ok(userService.addDesign(design));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/designs/{idDesign}/quotations")
    public ResponseEntity<Quotation> createQuotation(@PathVariable("idDesign") String idDesign,
                                                     @RequestBody QuotationDTO quotationDTO) throws Exception {
        return ResponseEntity.ok(userService.createQuotation(idDesign, quotationDTO));
    }

    @PutMapping("/designs/{designId}")
    public ResponseEntity<Design> updateDesign(@PathVariable("productId") String designId,
                                                 @RequestBody DesignDTO designDTO){
        try {
            return ResponseEntity.ok(userService.updateDesign(designId, designDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/quotations/{idQuotation}")
    public ResponseEntity<Quotation> updateQuotation(@PathVariable("idQuotation") String idQuotation,
                                                     @RequestBody QuotationDTO quotationDTO){
        try {
            return ResponseEntity.ok(userService.updateQuotation(idQuotation, quotationDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/designs/{designId}")
    public ResponseEntity<Void> deleteDesign(@PathVariable("designId") String designId) throws Exception {
        userService.deleteDesign(designId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/quotations/{idQuotation}")
    public ResponseEntity<Void> deleteQuotation(@PathVariable("idQuotation") String quotationId) throws Exception {
        userService.deleteQuotation(quotationId);
        return ResponseEntity.ok().build();
    }

    // Purchases

    @GetMapping("/purchases")
    public ResponseEntity<List<Purchase>> getAllPurchases() throws Exception {
        return ResponseEntity.ok(userService.getAllPurchases());
    }

    @GetMapping("/purchases/{purchaseId}")
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable("purchaseId") String purchaseId) throws Exception {
        return ResponseEntity.ok(userService.getPurchaseById(purchaseId));
    }

    @PostMapping("/purchases")                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseDTO purchaseDTO) throws Exception {
        return ResponseEntity.ok(userService.createPurchase(purchaseDTO));
    }

    @DeleteMapping("/purchases/{purchaseId}")
    public ResponseEntity<Void> deletePurchase(@PathVariable("purchaseId") String purchaseId) throws Exception {
        userService.deletePurchase(purchaseId);
        return ResponseEntity.ok().build();
    }
}

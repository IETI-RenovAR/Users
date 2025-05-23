package org.adaschool.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adaschool.project.model.*;
import org.adaschool.project.dto.*;
import org.adaschool.project.repository.UserRepository;
import org.adaschool.project.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    // User

    public List<UserEntity> getAllUsers(){
        return new ArrayList<>(userRepository.findAll());
    }

    public UserEntity getUserById(String id){
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) return user.get();
        throw new UserNotFoundException(id);
    }

    public Optional<UserEntity> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public UserEntity saveUser(UserDTO userDTO){
        UserEntity newUserEntity = new UserEntity(userDTO);
        userRepository.save(newUserEntity);
        return newUserEntity;
    }

    public UserEntity updateUser(String id, UserDTO userDTO){
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()) throw new UserNotFoundException(id);
        UserEntity updateUserEntity = user.get();
        updateUserEntity.update(userDTO);
        userRepository.save(updateUserEntity);
        return updateUserEntity;
    }

    public void deleteUser(String id) throws UserNotFoundException{
        if(!userRepository.existsById(id)) throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }

    // Design

    // GET Requests

    public List<Design> getAllPublicDesigns() throws Exception{
        System.out.println(1);
        HttpClient client = HttpClient.newHttpClient();
        System.out.println(2);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs"))
                .GET()
                .build();
        System.out.println(3);
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(4);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(5); 
        System.out.println(response.body().toString());
        return objectMapper.readValue(response.body(), new TypeReference<List<Design>>() {});
    }

    public Design getDesignById(String idDesign) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/" + idDesign))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Design.class);
    }

    public List<Design> getDesignsOfUser(String idUser) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/users/" + idUser))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<List<Design>>() {});
    }

    public List<Quotation> getAllQuotations(String idDesign) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/" + idDesign + "/quotations"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<List<Quotation>>() {});
    }

    public Quotation getQuotationById(String quotationId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/quotations/" + quotationId))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Quotation.class);
    }

    // POST Requests

    public Design addDesign(DesignDTO design) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(design);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        return objectMapper.readValue(response.body(), Design.class);
    }

    public Quotation createQuotation(String idQuotation, QuotationDTO quotationDTO) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(quotationDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/" + idQuotation + "/quotations"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        return objectMapper.readValue(response.body(), Quotation.class);
    }

    // PUT Requests

    public Design updateDesign(String idDesign, DesignDTO designDTO) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(designDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/" + idDesign))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        return objectMapper.readValue(response.body(), Design.class);
    }

    public Quotation updateQuotation(String quotationId, QuotationDTO quotationDTO) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(quotationDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/quotations/" + quotationId))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        return objectMapper.readValue(response.body(), Quotation.class);
    }

    // DELETE Requests

    public void deleteDesign(String idDesign) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/" + idDesign))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() > 300){
            throw new Exception();
        }
    }

    public void deleteQuotation(String idQuotation) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8081/v1/designs/quotations" + idQuotation))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
    }

    // Product

    // GET Requests

    public List<Product> getAllProducts() throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
    }

    public Product getProductById(String id) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products/" + id))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Product.class);
    }

    public Double getProductPrice(String productId) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products/price/" + productId))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<Double>() {});
    }

    public String getProductDimensions(String productId) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products/dimensions/" + productId))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<String>() {});
    }

    public String getProductStore(String productId) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products/seller/" + productId))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<String>() {});
    }

    public List<Product> sortProducts(String criteria, String category) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products/sort/" + criteria + "/" + category))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<List<Product>>() {});
    }

    // POST Requests

    public Product createProduct(ProductDTO productDTO) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(productDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() >= 300){
            throw new Exception();
        }
        return objectMapper.readValue(response.body(), Product.class);
    }

    // PUT Requests

    public Product updateProduct(String idProduct, ProductDTO productDTO) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(productDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products/" + idProduct))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        return objectMapper.readValue(response.body(), Product.class);
    }

    // DELETE Requests

    public void deleteProduct(String idProduct) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8082/v1/products/" + idProduct))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() > 300){
            throw new Exception();
        }
    }

    // Purchase

    // GET Requests

    public List<Purchase> getAllPurchases() throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8083/v1/purchases"))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), new TypeReference<List<Purchase>>() {});
    }

    public Purchase getPurchaseById(String id) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8083/v1/purchases/" + id))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Purchase.class);
    }

    // POST Requests

    public Purchase createPurchase(PurchaseDTO purchaseDTO) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String requestBody = objectMapper.writeValueAsString(purchaseDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8083/v1/purchases"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() >= 300){
            throw new Exception();
        }
        return objectMapper.readValue(response.body(), Purchase.class);
    }

    // DELETE Requests

    public void deletePurchase(String idPurchase) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.83.172.95:8083/v1/purchases/" + idPurchase))
                .DELETE()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() != 200){
            throw new Exception();
        }
    }

}

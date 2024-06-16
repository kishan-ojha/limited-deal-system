package dao;

import models.Deal;
import models.Product;

import java.util.HashMap;

public class ProductRepository {
    private HashMap<Integer, Product> products;

    public ProductRepository(){
        this.products = new HashMap<>();
    }

    public void createProductIfNotExists(Product product){
        if(this.products.containsKey(product)){
            return;
        }

        this.products.put(product.getId(), product);
    }

    public Product getProduct(int productId){
        return this.products.getOrDefault(productId, null);
    }
}

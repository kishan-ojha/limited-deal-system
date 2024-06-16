package models;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
public class Deal implements Cloneable {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isLive;
    private Map<Integer, Integer> products = new HashMap<>();

    public void addProduct(int productId, int numberOfProducts) {
        int num = this.products.getOrDefault(productId, 0);
        this.products.put(productId, num + numberOfProducts);
    }

    public Deal cloneDeal() {
        Deal newDeal = new Deal();
        newDeal.setId(this.id);
        newDeal.setStartTime(this.startTime);
        newDeal.setEndTime(this.endTime);
        newDeal.setLive(this.isLive);
        newDeal.setProducts(copyProducts(this.products));
        return newDeal;
    }

    public Map<Integer, Integer> copyProducts(Map<Integer, Integer> products) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            int productId = entry.getKey();
            int numeriofItems = entry.getValue();
            map.put(productId, numeriofItems);
        }

        return map;
    }

    public int getProductAvailability(int productId) {
        return this.products.getOrDefault(productId, 0);
    }

    public void decrementProductAvailability(int productId) {
        if (this.products.containsKey(productId)) {
            this.products.put(productId, this.products.get(productId) - 1);
        }
    }

    public boolean isOver(){
        LocalDateTime currentTime = LocalDateTime.now();
        return !this.isLive || currentTime.isAfter(this.endTime);
    }
}

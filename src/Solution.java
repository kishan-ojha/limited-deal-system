import Exceptions.DealException;
import dao.DealRepository;
import dao.ProductRepository;
import dao.UserRepository;
import models.Deal;
import models.Product;
import models.User;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Solution {

    private static DealRepository dealRepository;
    private static ProductRepository productRepository;
    private static UserRepository userRepository;
    public static void main(String[] args) throws Exception {
        dealRepository = new DealRepository();
        productRepository = new ProductRepository();
         userRepository =new UserRepository();

         // create user
        User user = new User();
        user.setId(1);
        addUser(user);

        // create a deal
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime endTime = currentTime.plusMinutes(120);
        Map<Integer, Integer> products = new HashMap<>();
        products.put(1, 1);

        createDeal(1, currentTime, endTime, products, 100);

        try{
            boolean success = claimDeal(1, 1, 1);
            System.out.println("Success");
        }catch(Exception ex){
            System.out.println("failure");
            System.out.println(ex);
        }
    }

    private static void createDeal(int id, LocalDateTime startTime, LocalDateTime endTime, Map<Integer, Integer> products,
                                   int price) throws Exception {
        if (endTime.isBefore(startTime) || price <= 0 || products.isEmpty() || id <= 0) {
            //log
            throw new DealException("Invalid deal attributes");
        }

        Deal deal = new Deal();
        deal.setId(id);
        deal.setStartTime(startTime);
        deal.setEndTime(endTime);
        deal.setLive(true);

        for(Map.Entry<Integer, Integer> productEntry: products.entrySet()){
            int productId = productEntry.getKey();
            int numberOfItems = productEntry.getValue();
            deal.addProduct(productId, numberOfItems);
        }

        dealRepository.createDeal(deal);
    }

    private static void endDeal(int dealId) throws Exception {
        dealRepository.endDeal(dealId);
    }

    private static void updateDeal(int dealId, Deal deal) throws Exception{
        dealRepository.updateDeal(dealId, deal);
    }

    private static void createProduct(int productId, int price){
        Product product = new Product();
        product.setId(productId);
        product.setPrice(price);
        productRepository.createProductIfNotExists(product);
    }

    private static boolean claimDeal(int userid, int dealId, int productId) throws Exception {

        Deal deal = dealRepository.getDeal(dealId);
        if(deal == null){
            throw new Exception("Deal not valid for claim");
        }

        if(deal.isOver()){
            throw new Exception("Deal is not live anymore");
        }

        if(dealRepository.checkDealCLaim(productId, dealId, userid)){
            throw new Exception("Deal is already claimed by user");
        }

        //update product in deal
        int currentNum = deal.getProductAvailability(productId);
        if(currentNum <=0){
            throw new Exception("Product not available");
        }

        deal.decrementProductAvailability(productId);
        dealRepository.addDealClaim(productId, dealId, userid);
        return true;
    }

    private static void addUser(User user) throws Exception {
        userRepository.addUser(user);
    }
}
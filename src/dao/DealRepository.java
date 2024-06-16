package dao;

import Exceptions.DealException;
import Exceptions.DealRepositoryException;
import models.Deal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DealRepository {
    private HashMap<Integer, Deal> deals;
    private Set<String> claimedDeals;

    public DealRepository(){
        this.deals = new HashMap<>();
        this.claimedDeals = new HashSet<>();
    }

    public void createDeal(Deal deal) throws DealRepositoryException{
        if(deals.containsKey(deal.getId())){
            throw new DealRepositoryException("Deal already exists");
        }

        deals.put(deal.getId(), deal);
    }

    public void endDeal(int dealId) throws DealRepositoryException{
        if(!deals.containsKey(dealId)){
            throw new DealRepositoryException("Deal not present");
        }

        Deal deal = deals.get(dealId);
        deal.setLive(false);
    }

    public void updateDeal(int dealId, Deal deal) throws DealRepositoryException {
        if (!deals.containsKey(deal.getId())) {
            throw new DealRepositoryException("Deal not present");
        }

        this.deals.put(dealId, deal.cloneDeal());
    }

    public Deal getDeal(int dealId){
        return this.deals.getOrDefault(dealId, null);
    }

    public void addDealClaim(int productId, int dealId, int userId){
        String key = getUserClaimKey(userId, dealId, productId);
        claimedDeals.add(key);
    }

    public boolean checkDealCLaim(int productId, int dealId, int userId){
        String key = getUserClaimKey(userId, dealId, productId);
        return claimedDeals.contains(key);
    }

    private String getUserClaimKey(int userId, int dealId, int productId) {
        return userId + "_" + dealId + "_" + productId;
    }
}

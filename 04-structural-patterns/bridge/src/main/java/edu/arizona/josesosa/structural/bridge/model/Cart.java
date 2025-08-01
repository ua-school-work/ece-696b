package edu.arizona.josesosa.structural.bridge.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {
    private String id;
    private List<CartLineItem> items;
    
    public Cart() {
        this.id = UUID.randomUUID().toString();
        this.items = new ArrayList<>();
    }
    
    public String getId() {
        return id;
    }
    
    public void addItem(Product product, int quantity) {
        items.add(new CartLineItem(product, quantity));
    }
    
    public List<CartLineItem> getItems() {
        return new ArrayList<>(items);
    }
    
    public double getTotal() {
        return items.stream()
                .mapToDouble(CartLineItem::getSubtotal)
                .sum();
    }
    
    public int getTotalItems() {
        return items.stream()
                .mapToInt(CartLineItem::getQuantity)
                .sum();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cart{id='").append(id).append("', items=[\n");
        
        for (CartLineItem item : items) {
            sb.append("  ").append(item).append("\n");
        }
        
        sb.append("], total=").append(getTotal()).append("}");
        return sb.toString();
    }
}
package edu.arizona.josesosa.structural.bridge.model;

public class OrderTracking {
    public enum Status {
        PENDING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }
    
    private String orderId;
    private Status status;
    private String fromAddress;
    private String toAddress;
    private String additionalInfo;
    
    public OrderTracking(String orderId, Status status, String fromAddress, String toAddress) {
        this.orderId = orderId;
        this.status = status;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.additionalInfo = "";
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getFromAddress() {
        return fromAddress;
    }
    
    public String getToAddress() {
        return toAddress;
    }
    
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    
    @Override
    public String toString() {
        return "OrderTracking{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                ", fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                (additionalInfo.isEmpty() ? "" : ", additionalInfo='" + additionalInfo + '\'') +
                '}';
    }
}
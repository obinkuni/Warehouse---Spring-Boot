package com.example.Warehouse.dto;

import jakarta.validation.constraints.Min;

public class StockOperationRequest {

    @Min(1)
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

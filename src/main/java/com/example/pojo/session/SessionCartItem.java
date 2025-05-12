package com.example.pojo.session;

import com.example.pojo.entity.Product;

/**
 * 暫存於 Session 的購物車項目。
 * 不對應資料庫，只是臨時用來累計購買資料。
 */
public class SessionCartItem {

    private Product product; // 商品資訊
    private int quantity;    // 購買數量

    // 👉 計算該項目的小計金額
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    // ===== Getter / Setter =====
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

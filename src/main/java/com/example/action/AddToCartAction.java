package com.example.action;

import com.example.pojo.entity.Product;
import com.example.pojo.session.SessionCartItem;
import com.example.service.ProductService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Action：處理加入購物車的請求。
 * 將商品加入 session 中的購物車（Map<Long, SessionCartItem>）。
 */
public class AddToCartAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 1L;
    
	private Long productId;  // 從前端取得的商品 ID
    private Map<String, Object> session;

    @Autowired
    private ProductService productService;

    @Override
    public String execute() {
        // 1️⃣ 根據 ID 查詢商品
        Product product = productService.getProductById(productId);
        if (product == null) {
            addActionError("查無此商品");
            return ERROR;
        }

        // 2️⃣ 取得 session 中的購物車（Map<ProductId, SessionCartItem>）
        Map<Long, SessionCartItem> cart = (Map<Long, SessionCartItem>) session.get("cart");

        if (cart == null) {
            cart = new HashMap<>();
            session.put("cart", cart);
        }

        // 3️⃣ 檢查商品是否已存在於購物車中
        SessionCartItem item = cart.get(productId);
        if (item == null) {
            item = new SessionCartItem();
            item.setProduct(product);
            item.setQuantity(1);
            cart.put(productId, item);
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
        
        String message = "✅ 已將『" + product.getName() + "』加入購物車！";
        session.put("toastifyMessage", message);

        return SUCCESS;
    }

    // ===== Setter 方法（Struts2 自動注入） =====
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    
}

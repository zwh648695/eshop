package com.example.action;

import com.example.pojo.session.SessionCartItem;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class CartUpdateAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 1L;
    
	private Long productId;
    private String actionType;
    private Map<String, Object> session;

    @Override
    public String execute() {
        Map<Long, SessionCartItem> cart = (Map<Long, SessionCartItem>) session.get("cart");
        if (cart == null || !cart.containsKey(productId)) return SUCCESS;

        SessionCartItem item = cart.get(productId);

        switch (actionType) {
            case "increase":
                item.setQuantity(item.getQuantity() + 1);
                session.put("toastifyMessage", "✅ 數量 +1");
                break;
            case "decrease":
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    session.put("toastifyMessage", "✅ 數量 -1");
                } else {
                    cart.remove(productId);
                    session.put("toastifyMessage", "🗑 已移除商品");
                }
                break;
            case "remove":
                cart.remove(productId);
                session.put("toastifyMessage", "🗑 已移除商品");
                break;
        }

        return SUCCESS;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    
}

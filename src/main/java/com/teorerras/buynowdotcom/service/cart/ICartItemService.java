package com.teorerras.buynowdotcom.service.cart;

import com.teorerras.buynowdotcom.dtos.CartItemDto;
import com.teorerras.buynowdotcom.model.CartItem;

public interface ICartItemService {
    CartItem addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);
    CartItem getCartItem(Long cartId, Long productId);

    CartItemDto convertToDto(CartItem cartItem);
}

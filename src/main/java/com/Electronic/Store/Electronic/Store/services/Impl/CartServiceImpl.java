package com.Electronic.Store.Electronic.Store.services.Impl;

import com.Electronic.Store.Electronic.Store.dtos.AddItemToCartRequest;
import com.Electronic.Store.Electronic.Store.dtos.CartDto;
import com.Electronic.Store.Electronic.Store.entities.Cart;
import com.Electronic.Store.Electronic.Store.entities.CartItem;
import com.Electronic.Store.Electronic.Store.entities.Product;
import com.Electronic.Store.Electronic.Store.entities.User;
import com.Electronic.Store.Electronic.Store.exceptions.BadApiRequest;
import com.Electronic.Store.Electronic.Store.exceptions.ResourceNotFoundException;
import com.Electronic.Store.Electronic.Store.repositories.CartItemRepository;
import com.Electronic.Store.Electronic.Store.repositories.CartRepository;
import com.Electronic.Store.Electronic.Store.repositories.ProductRepository;
import com.Electronic.Store.Electronic.Store.repositories.UserRepository;
import com.Electronic.Store.Electronic.Store.services.CartService;
import com.Electronic.Store.Electronic.Store.services.ProductService;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;
   @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productid = request.getProductId();
        Product product = productRepository.findById(productid).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (quantity <= 0) {
            throw new BadApiRequest("Requested quantity is not valid !!");
        }

        Cart cart = null;
        try{
             cart = cartRepository.findByUser(user).get();
        }
        catch(NoSuchElementException e){
          cart = new Cart();
          cart.setCartId(UUID.randomUUID().toString());
          cart.setCreatedAt(new Date());
        }

        //if cart iTEM ALREADY PRESENT TEHN UPDATE

       //perform cart operation
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedList = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productid)) {
                //item alredy present
                item.setQuantity(quantity);
                item.setTotalprice(quantity * product.getPrice());
                updated.set(true);

            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedList);


        if(!updated.get()){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalprice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }

     cart.setUser(user);
     Cart updatedcart = cartRepository.save(cart);
     return mapper.map(updatedcart,CartDto.class);
    }

    @Override
    public void removeItemfromCart(String userId, int cartItem) {
        CartItem cartitem = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart item not present"));

        cartItemRepository.delete(cartitem);

    }

    @Override
    public void clearcart(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException());
   cart.getItems().clear();
   cartRepository.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException());
   return mapper.map(cart,CartDto.class);
    }
}

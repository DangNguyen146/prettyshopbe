package com.prettyshopbe.prettyshopbe.service;

import com.prettyshopbe.prettyshopbe.dto.cart.CartDto;
import com.prettyshopbe.prettyshopbe.dto.cart.CartItemDto;
import com.prettyshopbe.prettyshopbe.dto.cart.OrderDto;
import com.prettyshopbe.prettyshopbe.dto.checkout.CheckoutItemDto;
import com.prettyshopbe.prettyshopbe.exceptions.OrderNotFoundException;
import com.prettyshopbe.prettyshopbe.model.*;
import com.prettyshopbe.prettyshopbe.respository.OrderItemsRepository;
import com.prettyshopbe.prettyshopbe.respository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Transactional
public class OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${BASE_URL_PC}")
    private String baseURLPC;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    // create total price
    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long)(checkoutItemDto.getPrice()*100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getProductName())
                                .build())
                .build();
    }

    // build each product in the stripe checkout page
    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                // set price for each product
                .setPriceData(createPriceData(checkoutItemDto))
                // set quantity for each product
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    // create session from list of checkout items
    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList, String device) throws StripeException {
        // supply success and failure url for stripe
        String successURL = baseURL + "payment/success";
        String failedURL = baseURL + "payment/failed";
        if(Objects.equals(device, "pc")){
            successURL = baseURLPC + "payment/success";
            failedURL = baseURLPC + "payment/failed";
        }


        // set the private key
        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();

        // for each product compute SessionCreateParams.LineItem
        for (CheckoutItemDto checkoutItemDto : checkoutItemDtoList) {
            sessionItemsList.add(createSessionLineItem(checkoutItemDto));
        }

        // build the session param
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failedURL)
                .addAllLineItem(sessionItemsList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }

    public Order placeOrder(User user, String sessionId, OrderDto orderDto) {
        // first let get cart items for the user
        CartDto cartDto = cartService.listCartItems(user);

        List<CartItemDto> cartItemDtoList = cartDto.getcartItems();

        // create the order and save it
        Order newOrder = new Order();
        newOrder.setCreatedDate(new Date());
        newOrder.setSessionId(sessionId);
        newOrder.setUser(user);
        newOrder.setTotalPrice(cartDto.getTotalCost());
        newOrder.setPhone(orderDto.getPhone()) ;
        newOrder.setAddpress(orderDto.getAddpress());
        newOrder.setFullname(orderDto.getFullname());
        newOrder.setShipcod(orderDto.getShipcod());
        orderRepository.save(newOrder);

        for (CartItemDto cartItemDto : cartItemDtoList) {
            // create orderItem and save each one
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedDate(new Date());
            orderItem.setPrice(cartItemDto.getProduct().getPrice());
            orderItem.setProduct(cartItemDto.getProduct());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);
            // add to order item list
            orderItemsRepository.save(orderItem);
        }
        //
        cartService.deleteUserCartItems(user);
        return newOrder;
    }

    public List<Order> listOrders(User user) {
        return orderRepository.findAllByUserOrderByCreatedDateDesc(user);
    }

    public List<Order> listAllOrders(Pageable pageable) {
        Sort sort = Sort.by("createdDate").descending(); // sắp xếp theo thuộc tính 'createdDate' giảm dần
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return orderRepository.findAll(pageRequest).getContent();
    }





    public Order getOrder(Integer orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();

        }
        throw new OrderNotFoundException("Order not found");
    }

    public Order saveOrder(Order order) {
        orderRepository.save(order);
        return order;
    }
    public Order updateProduct(String status, Order order) {
        order.setStatus(status);
        return orderRepository.save(order);
    }
    public Order updateProductStatus(Boolean status, Order order){
        order.setStatuspayment(status);
        return orderRepository.save(order);
    }

}

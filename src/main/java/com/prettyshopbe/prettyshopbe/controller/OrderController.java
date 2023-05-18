package com.prettyshopbe.prettyshopbe.controller;

import com.prettyshopbe.prettyshopbe.common.OrderApiResponse;
import com.prettyshopbe.prettyshopbe.dto.boolenStatusDto;
import com.prettyshopbe.prettyshopbe.dto.cart.OrderDto;
import com.prettyshopbe.prettyshopbe.dto.checkout.CheckoutItemDto;
import com.prettyshopbe.prettyshopbe.dto.checkout.StripeResponse;
import com.prettyshopbe.prettyshopbe.dto.stringStatusDto;
import com.prettyshopbe.prettyshopbe.exceptions.AuthenticationFailException;
import com.prettyshopbe.prettyshopbe.exceptions.OrderNotFoundException;
import com.prettyshopbe.prettyshopbe.model.Order;
import com.prettyshopbe.prettyshopbe.model.User;
import com.prettyshopbe.prettyshopbe.service.AuthenticationService;
import com.prettyshopbe.prettyshopbe.service.OrderService;
import com.stripe.exception.StripeException;

import com.stripe.model.checkout.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;


    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // get orders
        List<Order> orderDtoList = orderService.listOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    // get all orders
    @GetMapping("/getallorder")
    public ResponseEntity<List<Order>> getAllAdminOrders(
            @RequestParam("token") String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate,desc") String[] sort // đổi giá trị mặc định của tham số sắp xếp
    ) throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // get orders
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort[0]).descending());
        List<Order> orders = orderService.listAllOrders(pageable);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }



    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        // create the stripe session
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        // send the stripe session id in response
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<OrderApiResponse> placeOrder(@RequestBody OrderDto orderDto, @RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // place the order
        Order order = orderService.placeOrder(user, sessionId, orderDto);
        return new ResponseEntity<OrderApiResponse>(new OrderApiResponse(true, "Order has been placed", order.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Integer id,
                                                   @RequestParam("token") String token,
                                                   @RequestBody stringStatusDto statusDto) throws AuthenticationFailException {
        // authenticate and retrieve user
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        // get the order by ID
        Order existingOrder = orderService.getOrder(id);

        // check if user has permission to update the order
        if (user.isAdmin()) {
            // update the order status
            existingOrder.setStatus(statusDto.getStatus());
            orderService.updateProduct(statusDto.getStatus(),existingOrder);
        }
        return new ResponseEntity<>(existingOrder, HttpStatus.OK);
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Order> updateOrderPayment(@PathVariable Integer id,
                                                    @RequestParam("token") String token,
                                                    @RequestBody boolenStatusDto statusDto) throws AuthenticationFailException {
        // authenticate and retrieve user
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);

        // get the order by ID
        Order existingOrder = orderService.getOrder(id);

        // check if user has permission to update the order
        if (user.isAdmin()) {
            // update the order status
            existingOrder.setStatuspayment(Boolean.valueOf(statusDto.getStatus()));
        }
        return new ResponseEntity<>(existingOrder, HttpStatus.OK);
    }

}

package com.rest.services.payroll.assembler;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.rest.services.payroll.controller.OrderController;
import com.rest.services.payroll.model.Order;
import com.rest.services.payroll.model.Status;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderResourceAssembler implements ResourceAssembler<Order, Resource<Order>> {

    @Override
    public Resource<Order> toResource(Order order) {

        // Unconditional links to single-item resource and aggregate root

        Resource<Order> orderResource = new Resource<>(order,
                linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).all()).withRel("orders")
        );

        // Conditional links based on state of the order

        if (order.getStatus() == Status.IN_PROGRESS) {
            orderResource.add(
                    linkTo(methodOn(OrderController.class)
                            .cancel(order.getId())).withRel("cancel"));
            orderResource.add(
                    linkTo(methodOn(OrderController.class)
                            .complete(order.getId())).withRel("complete"));
        }

        return orderResource;
    }
}

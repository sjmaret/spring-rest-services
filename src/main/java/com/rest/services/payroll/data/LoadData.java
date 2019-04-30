package com.rest.services.payroll.data;

import com.rest.services.payroll.model.Employee;
import com.rest.services.payroll.model.Order;
import com.rest.services.payroll.model.Status;
import com.rest.services.payroll.repository.EmployeeRepository;
import com.rest.services.payroll.repository.OrderRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Log log = LogFactory.getLog(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository,
                                   OrderRepository orderRepository) {
        return args -> {
            employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
            employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));

            employeeRepository.findAll().forEach(employee -> {
                log.info("Preloaded " + employee);
            });

            // tag::order[]
            orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

            orderRepository.findAll().forEach(order -> {
                log.info("Preloaded " + order);
            });
            // end::order[]
        };
    }
}
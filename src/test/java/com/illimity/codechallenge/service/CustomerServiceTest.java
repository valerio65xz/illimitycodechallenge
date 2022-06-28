package com.illimity.codechallenge.service;

import com.illimity.codechallenge.BaseUnitTest;
import com.illimity.codechallenge.converter.CustomerConverter;
import com.illimity.codechallenge.exception.ResponseException;
import com.illimity.codechallenge.model.Customer;
import com.illimity.codechallenge.model.CustomerInputModel;
import com.illimity.codechallenge.model.CustomerOutputModel;
import com.illimity.codechallenge.model.Status;
import com.illimity.codechallenge.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import static com.illimity.codechallenge.exception.ResponseErrorEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest extends BaseUnitTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerConverter customerConverter;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer_success(){
        CustomerInputModel customerInputModel = random(CustomerInputModel.class);
        Customer customer = random(Customer.class);

        when(customerConverter.toCustomer(any())).thenReturn(customer);
        when(customerRepository.findByUsername(anyString())).thenReturn(null);
        when(customerRepository.save(any())).thenReturn(customer);

        Customer result = customerService.createCustomer(customerInputModel);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("customerId", "encodedPassword", "createdDate", "lastModifiedDate", "lastLoginDate")
                .isEqualTo(customerInputModel);

        verify(customerConverter).toCustomer(customerInputModel);
        verify(customerRepository).findByUsername(customer.getUsername());
        verify(customerRepository).save(customer);
    }

    @Test
    void createCustomer_failForUserAlreadyExists(){
        CustomerInputModel customerInputModel = random(CustomerInputModel.class);
        Customer customer = random(Customer.class);

        when(customerConverter.toCustomer(any())).thenReturn(customer);
        when(customerRepository.findByUsername(anyString())).thenReturn(customer);

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> customerService.createCustomer(customerInputModel))
                .matches(e -> e.getError().equals(USER_ALREADY_EXISTS));

        verify(customerConverter).toCustomer(customerInputModel);
        verify(customerRepository).findByUsername(customer.getUsername());
    }

    @Test
    void login_success(){
        String username = random(String.class);
        String rawPassword = random(String.class);
        Customer customer = random(Customer.class);
        customer.setStatus(Status.ACTIVE);
        CustomerOutputModel customerOutputModel = random(CustomerOutputModel.class);

        when(customerRepository.findByUsername(anyString())).thenReturn(customer);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(customerRepository.save(any())).thenReturn(customer);
        when(customerConverter.toCustomerOutputModel(any())).thenReturn(customerOutputModel);

        CustomerOutputModel result = customerService.login(username, rawPassword);

        assertThat(result)
                .isNotNull()
                .isEqualTo(customerOutputModel);

        verify(customerRepository).findByUsername(username);
        verify(passwordEncoder).matches(rawPassword, customer.getEncodedPassword());
        verify(customerRepository).save(customer);
        verify(customerConverter).toCustomerOutputModel(customer);
    }

    @Test
    void login_failForMissingCustomer(){
        String username = random(String.class);
        String rawPassword = random(String.class);

        when(customerRepository.findByUsername(anyString())).thenReturn(null);

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> customerService.login(username, rawPassword))
                .matches(e -> e.getError().equals(CUSTOMER_NOT_FOUND));

        verify(customerRepository).findByUsername(username);
    }

    @Test
    void login_failForUnauthorized(){
        String username = random(String.class);
        String rawPassword = random(String.class);
        Customer customer = random(Customer.class);

        when(customerRepository.findByUsername(anyString())).thenReturn(customer);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> customerService.login(username, rawPassword))
                .matches(e -> e.getError().equals(UNAUTHORIZED));

        verify(customerRepository).findByUsername(username);
        verify(passwordEncoder).matches(rawPassword, customer.getEncodedPassword());
    }

    @Test
    void login_failForWrongStatus(){
        String username = random(String.class);
        String rawPassword = random(String.class);
        Customer customer = random(Customer.class);
        customer.setStatus(Status.BLOCKED);

        when(customerRepository.findByUsername(anyString())).thenReturn(customer);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        assertThatExceptionOfType(ResponseException.class)
                .isThrownBy(() -> customerService.login(username, rawPassword))
                .matches(e -> e.getError().equals(STATUS_NOT_VALID));

        verify(customerRepository).findByUsername(username);
        verify(passwordEncoder).matches(rawPassword, customer.getEncodedPassword());
    }

}

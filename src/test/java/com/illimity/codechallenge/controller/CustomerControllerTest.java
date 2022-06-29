package com.illimity.codechallenge.controller;

import com.illimity.codechallenge.ControllerTest;
import com.illimity.codechallenge.exception.ResponseException;
import com.illimity.codechallenge.model.Customer;
import com.illimity.codechallenge.model.CustomerInputModel;
import com.illimity.codechallenge.model.CustomerOutputModel;
import com.illimity.codechallenge.model.LoginInputModel;
import com.illimity.codechallenge.response.ErrorResponse;
import com.illimity.codechallenge.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.illimity.codechallenge.exception.ResponseErrorEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class CustomerControllerTest extends ControllerTest {

    @MockBean
    private CustomerService customerService;

    @Test
    public void createCustomer_success() {
        String url = "/customers";
        CustomerInputModel customerInputModel = random(CustomerInputModel.class);
        Customer customer = random(Customer.class);

        when(customerService.createCustomer(any())).thenReturn(customer);

        Customer result = performAndExpect(post(url), customerInputModel, Customer.class);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(customer);

        verify(customerService).createCustomer(refEq(customerInputModel));
    }

    @Test
    public void createCustomer_failForBeanValidation() {
        String url = "/customers";
        CustomerInputModel customerInputModel = new CustomerInputModel(
                random(String.class),
                random(String.class),
                random(String.class),
                random(String.class),
                random(String.class),
                random(String.class),
                random(String.class),
                null
        );

        ErrorResponse result = performAndExpect(post(url), customerInputModel, ErrorResponse.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getMessage().get(0)).isEqualTo("status must not be null");
    }

    @Test
    public void login_success() {
        String url = "/customers/login";
        LoginInputModel loginInputModel = random(LoginInputModel.class);
        CustomerOutputModel customerOutputModel = random(CustomerOutputModel.class);

        when(customerService.login(anyString(), anyString())).thenReturn(customerOutputModel);

        CustomerOutputModel result = performAndExpect(post(url), loginInputModel, CustomerOutputModel.class);

        assertThat(result)
                .isNotNull()
                .isEqualTo(customerOutputModel);

        verify(customerService).login(loginInputModel.getUsername(), loginInputModel.getPassword());
    }

    @Test
    public void login_failForCustomerNotFound() {
        String url = "/customers/login";
        LoginInputModel loginInputModel = random(LoginInputModel.class);

        when(customerService.login(anyString(), anyString())).thenThrow(new ResponseException(CUSTOMER_NOT_FOUND));

        ErrorResponse result = performAndExpect(post(url), loginInputModel, ErrorResponse.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(CUSTOMER_NOT_FOUND.getHttpStatus());
        assertThat(result.getMessage().get(0)).isEqualTo(CUSTOMER_NOT_FOUND.getMessage());

        verify(customerService).login(loginInputModel.getUsername(), loginInputModel.getPassword());
    }

    @Test
    public void login_failForUnauthorized() {
        String url = "/customers/login";
        LoginInputModel loginInputModel = random(LoginInputModel.class);

        when(customerService.login(anyString(), anyString())).thenThrow(new ResponseException(UNAUTHORIZED));

        ErrorResponse result = performAndExpect(post(url), loginInputModel, ErrorResponse.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UNAUTHORIZED.getHttpStatus());
        assertThat(result.getMessage().get(0)).isEqualTo(UNAUTHORIZED.getMessage());

        verify(customerService).login(loginInputModel.getUsername(), loginInputModel.getPassword());
    }

    @Test
    public void login_failForStatusNotValid() {
        String url = "/customers/login";
        LoginInputModel loginInputModel = random(LoginInputModel.class);

        when(customerService.login(anyString(), anyString())).thenThrow(new ResponseException(STATUS_NOT_VALID));

        ErrorResponse result = performAndExpect(post(url), loginInputModel, ErrorResponse.class);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(STATUS_NOT_VALID.getHttpStatus());
        assertThat(result.getMessage().get(0)).isEqualTo(STATUS_NOT_VALID.getMessage());

        verify(customerService).login(loginInputModel.getUsername(), loginInputModel.getPassword());
    }

}

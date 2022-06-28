package com.illimity.codechallenge.converter;

import com.illimity.codechallenge.BaseUnitTest;
import com.illimity.codechallenge.model.Customer;
import com.illimity.codechallenge.model.CustomerInputModel;
import com.illimity.codechallenge.model.CustomerOutputModel;
import com.illimity.codechallenge.service.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerConverterTest extends BaseUnitTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerConverter customerConverter;

    @Test
    void toCustomer_success(){
        CustomerInputModel customerInputModel = random(CustomerInputModel.class);
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

        Customer result = customerConverter.toCustomer(customerInputModel);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringFields("customerId", "encodedPassword", "createdDate", "lastModifiedDate", "lastLoginDate")
                .isEqualTo(customerInputModel);
        assertThat(encodedPassword).isEqualTo(result.getEncodedPassword());

        verify(passwordEncoder).encode(customerInputModel.getPassword());
    }

    @Test
    void toCustomerOutputModel_success(){
        Customer customer = random(Customer.class);

        CustomerOutputModel result = customerConverter.toCustomerOutputModel(customer);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(customer);
    }

}
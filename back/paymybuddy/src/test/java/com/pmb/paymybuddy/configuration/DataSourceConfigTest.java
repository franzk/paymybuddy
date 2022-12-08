package com.pmb.paymybuddy.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataSourceConfigTest {

	@InjectMocks
	DataSourceConfig classUnderTest;
	
	@Test 
	void getDataSourceTest() {
		
		// Arrange
		
		// Act
		DataSource result = classUnderTest.getDataSource();
		
		// Assert
		assertThat(result).isNotNull();
	}
}

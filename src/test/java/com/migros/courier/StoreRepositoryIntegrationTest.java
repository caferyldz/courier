package com.migros.courier;

import com.migros.courier.model.Store;
import com.migros.courier.repository.StoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class StoreRepositoryIntegrationTest {
	@Autowired
	private StoreRepository storeRepository;

	@Test
	void getStores() {
		List<Store> stores = storeRepository.getStores();
		assertNotNull(stores);
		assertEquals(stores.size(), 5);

		stores.forEach(store -> {
			assertNotNull(store.getName());
			assertNotNull(store.getLat());
			assertNotNull(store.getLng());
		});
	}

}

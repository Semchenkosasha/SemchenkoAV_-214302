package com.ordermanagement.controller;

import com.ordermanagement.model.Products;
import com.ordermanagement.repo.CategoriesRepo;
import com.ordermanagement.repo.ProductsRepo;
import org.junit.Before;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductsContTest {
        @Mock
        private ProductsRepo productsRepo;
    @Mock
    private ContractsRepo contractsRepo;

        @Mock
        private CategoriesRepo categoriesRepo;

        @InjectMocks
        private ProductsContTest productsContTest;

        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void testProductFilter() {
            Model model = mock(Model.class);
            String name = "test";
            long categoryId = 1;
            String filter = "expensive";

            List<Products> mockProducts = new ArrayList<>();
            Products product1 = new Products();
            product1.setId(1);
            product1.setName("Product1");
            product1.setCategory(new Category());
            product1.setPrice(10.0);
            mockProducts.add(product1);

            Products product2 = new Products();
            product2.setId(2);
            product2.setName("Product2");
            product2.setCategory(new Category());
            product2.setPrice(20.0);
            mockProducts.add(product2);

            when(productsRepo.findAllByNameContainingAndCategory_Id(name, categoryId)).thenReturn(mockProducts);
            when(categoriesRepo.findAll()).thenReturn(new ArrayList<>());

            String result = productsContTest.productFilter(model, name, categoryId, filter);

            verify(model, times(1)).addAttribute("products", mockProducts);
            verify(model, times(1)).addAttribute("name", name);
            verify(model, times(1)).addAttribute("categoryId", categoryId);
            verify(categoriesRepo, times(1)).findAll();
            verify(model, times(1)).addAttribute("filter", filter);
            assertEquals("products", result);
        }
    @Test
    public void testProductContract() {

        int quantity = 5;
        String dateEnd = "2024-05-01";
        long id = 1L;

        Products product = new Products();
        product.setId(id);
        product.setSup(new Sup());

        when(productsRepo.getReferenceById(id)).thenReturn(product);

        String result = productsContTest.productContract(quantity, dateEnd, id);

        verify(productsRepo, times(1)).getReferenceById(id);
        verify(contractsRepo, times(1)).save(any(Contracts.class));
        assertEquals("redirect:/products/" + id, result);
    }
}
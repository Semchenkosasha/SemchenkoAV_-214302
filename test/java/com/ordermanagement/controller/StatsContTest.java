package com.ordermanagement.controller;

import com.ordermanagement.model.Products;
import com.ordermanagement.model.Sups;
import com.ordermanagement.model.Users;
import com.ordermanagement.model.enums.Role;
import com.ordermanagement.repo.CategoriesRepo;
import com.ordermanagement.repo.ProductsRepo;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatsContTest {
    @Mock
    private ProductsRepo productsRepo;

    @Mock
    private CategoriesRepo categoriesRepo;

    @InjectMocks
    private StatsCont statsCont;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStats() {
        Model model = mock(Model.class);

        Users user = new Users();
        user.setRole(Role.SUP);
        Sups sup = new Sups();
        sup.setProducts(new ArrayList<>());
        user.setSup(sup);

        List<Products> mockProducts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Products product = new Products();
            mockProducts.add(product);
        }

        when(productsRepo.findAll()).thenReturn(mockProducts);
        when(categoriesRepo.findAll()).thenReturn(new ArrayList<>());

        String result = statsCont.stats(model);

        verify(productsRepo, times(1)).findAll();
        verify(categoriesRepo, never()).findAll();
        verify(model, times(3)).addAttribute(anyString(), any());

        assertEquals("stats", result);
    }
}
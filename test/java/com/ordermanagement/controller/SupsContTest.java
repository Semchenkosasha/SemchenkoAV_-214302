package com.ordermanagement.controller;

import com.ordermanagement.model.Sups;
import com.ordermanagement.repo.SupsRepo;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupsContTest {
    @Mock
    private SupsRepo supsRepo;

    @InjectMocks
    private SupsContTest supsContTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSups() {
        Model model = mock(Model.class);

        List<Sups> mockSups = new ArrayList<>();
        Sups sups1 = new Sups();
        sups1.setId(1);
        sups1.setName("Sups1");
        mockSups.add(sups1);

        Sups sups2 = new Sups();
        sups2.setId(2);
        sups2.setName("Sups2");
        mockSups.add(sups2);

        when(supsRepo.findAll()).thenReturn(mockSups);

        String result = supsContTest.sups(model);

        verify(model, times(1)).addAttribute("sups", mockSups);
        assertEquals("sups", result);
    }
}
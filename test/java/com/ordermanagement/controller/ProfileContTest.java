package com.ordermanagement.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.ordermanagement.model.Users;
import com.ordermanagement.repo.UsersRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
class ProfileContTest {
    @Mock
    private UsersRepo usersRepo;

    @InjectMocks
    private ProfileCont profileCont;

    @Test
    public void testProfileEdit() {

        Model model = mock(Model.class);

        Users user = mock(Users.class);
        when(profileCont.getUser()).thenReturn(user);

        String fio = "Sasha Exs";
        String email = "sasha.exs@example.com";
        String tel = "123456789";
        String address = "123 Os";

        String result = profileCont.profileEdit(fio, email, tel, address);

        verify(user).setFio(fio);
        verify(user).setEmail(email);
        verify(user).setTel(tel);
        verify(user).setAddress(address);

        verify(usersRepo).save(user);

        assertEquals("redirect:/profile", result);
    }
}
package com.ordermanagement.controller;

import com.ordermanagement.model.Contracts;
import com.ordermanagement.model.Users;
import com.ordermanagement.model.enums.ContractStatus;
import com.ordermanagement.model.enums.Role;
import com.ordermanagement.repo.ContractsRepo;
import com.ordermanagement.repo.ProductsRepo;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContractsContTest {
    @Mock
    private ContractsRepo contractsRepo;

    @Mock
    private ProductsRepo productsRepo;

    @InjectMocks
    private ContractsCont contractsCont;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testContracts() {
        Model model = mock(Model.class);
        Users user = new Users();
        user.setId(1L);
        user.setRole(Role.MANAGER);

        List<Contracts> mockContracts = new ArrayList<>();
        Contracts contract1 = new Contracts();
        mockContracts.add(contract1);

        Contracts contract2 = new Contracts();
        mockContracts.add(contract2);

        when(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.WAITING, user.getId())).thenReturn(mockContracts);
        when(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.CONFIRMED, user.getId())).thenReturn(mockContracts);
        when(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.DELIVERING, user.getId())).thenReturn(mockContracts);
        when(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.WAITING, user.getSup().getId())).thenReturn(mockContracts);
        when(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.CONFIRMED, user.getSup().getId())).thenReturn(mockContracts);
        when(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.DELIVERING, user.getSup().getId())).thenReturn(mockContracts);

        String result = contractsCont.contracts(model);

        verify(contractsRepo, times(3)).findAllByStatusAndOwner_Id(any(), anyLong());
        verify(contractsRepo, times(3)).findAllByStatusAndSup_Id(any(), anyLong());
        verify(model, times(1)).addAttribute("contracts", mockContracts);
        assertEquals("contracts", result);
    }

    @Test
    public void testContractsArchive() {
        Model model = mock(Model.class);
        Users user = new Users();
        user.setId(1L);
        user.setRole(Role.SUP);

        List<Contracts> mockContracts = new ArrayList<>();
        Contracts contract1 = new Contracts();
        mockContracts.add(contract1);

        Contracts contract2 = new Contracts();
        mockContracts.add(contract2);

        when(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.DELIVERED, user.getSup().getId())).thenReturn(mockContracts);
        when(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.REJECT, user.getSup().getId())).thenReturn(mockContracts);
        when(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.DELIVERED, user.getId())).thenReturn(mockContracts);
        when(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.REJECT, user.getId())).thenReturn(mockContracts);

        String result = contractsCont.contractsArchive(model);

        verify(contractsRepo, times(2)).findAllByStatusAndSup_Id(any(), anyLong());
        verify(contractsRepo, times(2)).findAllByStatusAndOwner_Id(any(), anyLong());
        verify(model, times(1)).addAttribute("contracts", mockContracts);
        assertEquals("contracts_archive", result);
    }

    @Test
    public void testContractConfirmed() {
        long id = 1L;
        Contracts contract = new Contracts();

        when(contractsRepo.getReferenceById(id)).thenReturn(contract);

        String result = contractsCont.contractConfirmed(id);

        verify(contractsRepo, times(1)).getReferenceById(id);
        verify(contractsRepo, times(1)).save(contract);
        verify(productsRepo, times(1)).save(contract.getProduct());
        assertEquals("redirect:/contracts", result);
    }

    @Test
    public void testContractReject() {
        long id = 1L;
        Contracts contract = new Contracts();

        when(contractsRepo.getReferenceById(id)).thenReturn(contract);

        String result = contractsCont.contractReject(id);

        verify(contractsRepo, times(1)).getReferenceById(id);
        verify(contractsRepo, times(1)).save(contract);
        assertEquals("redirect:/contracts", result);
    }

    @Test
    public void testContractDelivering() {
        long id = 1L;
        Contracts contract = new Contracts();

        when(contractsRepo.getReferenceById(id)).thenReturn(contract);
        String result = contractsCont.contractDelivering(id);
        verify(contractsRepo, times(1)).getReferenceById(id);
        verify(contractsRepo, times(1)).save(contract);
        assertEquals("redirect:/contracts", result);
    }

    @Test
    public void testContractDelivered() {
        long id = 1L;
        Contracts contract = new Contracts();

        when(contractsRepo.getReferenceById(id)).thenReturn(contract);
        String result = contractsCont.contractDelivered(id);
        verify(contractsRepo, times(1)).getReferenceById(id);
        verify(contractsRepo, times(1)).save(contract);
        assertEquals("redirect:/contracts", result);
    }
}
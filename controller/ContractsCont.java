package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Contracts;
import com.ordermanagement.model.Products;
import com.ordermanagement.model.Users;
import com.ordermanagement.model.enums.ContractStatus;
import com.ordermanagement.model.enums.Role;
import com.ordermanagement.repo.ContractsRepo;
import com.ordermanagement.repo.ProductsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractsCont extends Main {
    private final ContractsRepo contractsRepo;
    private final ProductsRepo productsRepo;

    @GetMapping
    public String contracts(Model model) {
        getCurrentUserAndRole(model);
        List<Contracts> contracts = new ArrayList<>();
        Users user = getUser();
        if (user.getRole() == Role.MANAGER) {
            contracts.addAll(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.WAITING, user.getId()));
            contracts.addAll(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.CONFIRMED, user.getId()));
            contracts.addAll(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.DELIVERING, user.getId()));
        } else if (getUser().getRole() == Role.SUP) {
            contracts.addAll(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.WAITING, user.getSup().getId()));
            contracts.addAll(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.CONFIRMED, user.getSup().getId()));
            contracts.addAll(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.DELIVERING, user.getSup().getId()));
        }
        model.addAttribute("contracts", contracts);
        return "contracts";
    }

    @GetMapping("/archive")
    public String contractsArchive(Model model) {
        getCurrentUserAndRole(model);
        List<Contracts> contracts = new ArrayList<>();
        Users user = getUser();
        if (user.getRole() == Role.SUP) {
            contracts.addAll(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.DELIVERED, user.getSup().getId()));
            contracts.addAll(contractsRepo.findAllByStatusAndSup_Id(ContractStatus.REJECT, user.getSup().getId()));
        } else if (user.getRole() == Role.MANAGER) {
            contracts.addAll(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.DELIVERED, user.getId()));
            contracts.addAll(contractsRepo.findAllByStatusAndOwner_Id(ContractStatus.REJECT, user.getId()));
        }
        model.addAttribute("contracts", contracts);
        return "contracts_archive";
    }

    @GetMapping("/confirmed/{id}")
    public String contractConfirmed(@PathVariable Long id) {
        Contracts contract = contractsRepo.getReferenceById(id);
        contract.setStatus(ContractStatus.CONFIRMED);
        contractsRepo.save(contract);
        Products product = contract.getProduct();
        product.setCount(product.getCount() - contract.getQuantity());
        productsRepo.save(product);
        return "redirect:/contracts";
    }

    @GetMapping("/reject/{id}")
    public String contractReject(@PathVariable Long id) {
        Contracts contract = contractsRepo.getReferenceById(id);
        contract.setStatus(ContractStatus.REJECT);
        contractsRepo.save(contract);
        return "redirect:/contracts";
    }

    @GetMapping("/delivering/{id}")
    public String contractDelivering(@PathVariable Long id) {
        Contracts contract = contractsRepo.getReferenceById(id);
        contract.setStatus(ContractStatus.DELIVERING);
        contractsRepo.save(contract);
        return "redirect:/contracts";
    }

    @GetMapping("/delivered/{id}")
    public String contractDelivered(@PathVariable Long id) {
        Contracts contract = contractsRepo.getReferenceById(id);
        contract.setStatus(ContractStatus.DELIVERED);
        contractsRepo.save(contract);
        return "redirect:/contracts";
    }
}

package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private String name;

    private Long id;

    private Double amount;

    private Integer payments;

    private Long LoanId;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.name = clientLoan.getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.LoanId = clientLoan.getLoan().getId();
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public Long getLoanId() {
        return LoanId;
    }

    public String getName() {
        return name;
    }
}

package com.example.springcloudcontract.resource;

public class CreditCheckRequest {

    private int citizenNumber;

    public int getCitizenNumber() {
        return citizenNumber;
    }

    public CreditCheckRequest setCitizenNumber(int citizenNumber) {
        this.citizenNumber = citizenNumber;
        return this;
    }
}

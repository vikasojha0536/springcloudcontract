package com.example.springcloudcontract.resource;

import com.example.springcloudcontract.enums.CardType;

public class ApplyForCreditCardRequest {

    private CardType cardType;

    private int citizenNumber;

    public CardType getCardType() {
        return cardType;
    }

    public ApplyForCreditCardRequest setCardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public int getCitizenNumber() {
        return citizenNumber;
    }

    public ApplyForCreditCardRequest setCitizenNumber(int citizenNumber) {
        this.citizenNumber = citizenNumber;
        return this;
    }
}

package com.example.springcloudcontract.resource;

import com.example.springcloudcontract.enums.Score;

public class CreditCheckResponse {

    private Score score;

    public Score getScore() {
        return score;
    }

    public Score setScore(Score score) {
        this.score = score;
        return score;
    }

}

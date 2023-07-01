package com.example.springcloudcontract.resource;

import com.example.springcloudcontract.client.CreditScoreClient;
import com.example.springcloudcontract.enums.CardType;
import com.example.springcloudcontract.enums.Score;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.example.springcloudcontract.resource.ApplyForCreditCardResponse.Status.GRANTED;

@RestController
public class CrditCardApplicationController {

    private final CreditScoreClient creditScoreClient;

    public CrditCardApplicationController(CreditScoreClient creditScoreClient) {
        this.creditScoreClient = creditScoreClient;
    }

    @PostMapping(value = "/credit-card-applications", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApplyForCreditCardResponse applyForCreditCard(@RequestBody ApplyForCreditCardRequest applyForCreditCardRequest) throws IOException {
        final String uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080")
                                               .path("/credit-scores")
                                               .toUriString();
        final int citizenNumber = applyForCreditCardRequest.getCitizenNumber();
        final CreditCheckResponse creditCheckResponse =
                creditScoreClient.creditScore(new CreditCheckRequest().setCitizenNumber(citizenNumber)).execute().body();
        if (creditCheckResponse.getScore() == Score.HIGH && applyForCreditCardRequest.getCardType() == CardType.GOLD) {
            return new ApplyForCreditCardResponse(GRANTED);
        }
        throw new RuntimeException(" Card and score not yet implemented");
    }
}

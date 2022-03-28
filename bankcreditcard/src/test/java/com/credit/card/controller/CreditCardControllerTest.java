package com.credit.card.controller;

import com.credit.card.bean.CreditCard;
import com.credit.card.dto.CreditCardDto;
import com.credit.card.service.CreditCardService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(CreditCardController.class)
class CreditCardControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CreditCardService cardService;

    @Test
    public void addSaveCreditCardTest(){
        Mono<CreditCard> cardDtoMono=Mono.just(new CreditCard("1", "123456780987", 1000.00, 200.00, "006"));
        when(cardService.saveCreditCard(any())).thenReturn(cardDtoMono);

        webTestClient.post().uri("/api/creditcard/create")
                .body(Mono.just(cardDtoMono),CreditCard.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getAllCreditCardTest(){
        Flux<CreditCard> CCDtoFlux=Flux.just(new CreditCard("1", "123456780987", 1000.00, 200.00, "006"),
                new CreditCard("2", "123456780966", 1200.00, 300.00, "007"));
        when(cardService.findAllCreditCard()).thenReturn(CCDtoFlux);

        Flux<CreditCard> responseBody=webTestClient.get().uri("/api/creditcard")
                .exchange()
                .expectStatus().isOk()
                .returnResult(CreditCard.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new CreditCard("1", "123456780987", 1000.00, 200.00, "006"))
                .expectNext(new CreditCard("2", "123456780966", 1200.00, 300.00, "007"));
    }

    @Test
    public void getObtenerCreditCardIdTest(){
        Mono<CreditCard> CCMono=Mono.just(new CreditCard("1", "123456780987", 1000.00, 200.00, "006"));
        when(cardService.findByIdCreditCard(any())).thenReturn(CCMono);

        Flux<CreditCard> responseBody = webTestClient.get().uri("/api/creditcard/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(CreditCard.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNextMatches(p->p.getId().equals("1"))
                .verifyComplete();
    }

    @Test
    public void updateCreditCardTest(){
        Mono<CreditCardDto> CCDtoMono=Mono.just(new CreditCardDto("1", "123456780987", 1000.00, 200.00, "006"));
        when(cardService.updateCreditCard(CCDtoMono,"1")).thenReturn(CCDtoMono);

        webTestClient.put().uri("/api/creditcard/1")
                .body(Mono.just(CCDtoMono),CreditCardDto.class)
                .exchange()
                .expectStatus().isOk();//200
    }

    @Test
    public void deleteCreditCardIdTest(){
        given(cardService.deleteByIdCreditCard(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/api/creditcard/1")
                .exchange()
                .expectStatus().isOk();//200
    }
}
package com.credit.card.controller;

import com.credit.card.bean.CreditCard;
import com.credit.card.bean.CreditMovement;
import com.credit.card.dto.CreditCardDto;
import com.credit.card.dto.CreditMovementDto;
import com.credit.card.service.CreditCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


@RestController
@RequestMapping(path = "/api/creditcard")
public class CreditCardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class);
    @Autowired(required = false)
    private WebClient.Builder webClient;
    @Resource
    private CreditCardService creditCardService;

    @GetMapping()
    public Flux<CreditCard> findAllCreditCard(){
        LOGGER.debug("Getting Credit Card!");
        return creditCardService.findAllCreditCard();
    }

    @GetMapping("/{id}")
    public Mono<CreditCard> findByIdCreditCard(@PathVariable String id){
        LOGGER.debug("Getting a credit card!");
        return creditCardService.findByIdCreditCard(id);
    }

    @GetMapping("/client/{idClient}")
    public Flux<CreditCard> findByIdClient(@PathVariable String idClient){
        return creditCardService.findByIdClient(idClient);
    }

    @PostMapping("/create")
    public Mono<CreditCard> saveCreditCard(@RequestBody CreditCard creditCard){
        LOGGER.debug("Saving credit card!");
        return creditCardService.saveCreditCard(creditCard);
    }

    @PutMapping("/{id}")
    public Mono<CreditCardDto> updateCreditCard(@RequestBody Mono<CreditCardDto> creditCardMono, @PathVariable String id){
        LOGGER.debug("Updating credit card!");
        return creditCardService.updateCreditCard(creditCardMono,id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteByIdCreditCard(@PathVariable String id){
        LOGGER.debug("Deleting credit card!");
        return creditCardService.deleteByIdCreditCard(id);
    }

    @GetMapping("/balance/{idCard}")
    public String seeBalance(String idCard){
        return creditCardService.seeBalance(idCard);
    }

    @GetMapping("/consume/{idCard}/{consume}")
    public Mono<CreditMovementDto> consumeCard(String idCard, Double consume){
        return creditCardService.consumeCard(idCard, consume);
    }

    @GetMapping("/has-credit-card/{idClient}")
    public CreditCard findCreditCardByIdClient(String idClient){
        return creditCardService.findCreditCardByIdClient(idClient);
    }

    @GetMapping("/pay/{idCard}/{pay}")
    public Mono<CreditMovementDto> payCard(String idCard, Double pay){
        return creditCardService.payCard(idCard, pay);
    }

    @GetMapping("/credit-movement/")
    public Flux<CreditMovement> findAllCreditMovement(){
        return creditCardService.findAllCreditMovement();
    }

    @GetMapping("/credit-movement/card/{idCard}")
    public Flux<CreditMovement> findByIdCreditMovementByCard(String idCard){
        return creditCardService.findByIdCreditMovementByCard(idCard);
    }

    @GetMapping("/credit-movement/movement/{idMovement}")
    public Mono<CreditMovement> findAllCreditMovementById(String idMovement){
        return creditCardService.findAllCreditMovementById(idMovement);
    }
}

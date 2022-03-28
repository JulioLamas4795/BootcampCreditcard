package com.credit.card.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("creditCard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {
    @Id
    private String id;
    private String accountNumber;
    private Double creditLine;
    private Double balance;
    private String idClient;

}

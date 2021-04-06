package ru.netology.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    String id;
    String created; // дата/время создания
    String credit_id;
    String payment_id;
}

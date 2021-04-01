package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Payment {
    private String id;
    private int amount;
    private String created; // datetime
    private String status;
    private String transaction_id;
}

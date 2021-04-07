package com.example.lesson6_homework;

import android.content.Context;

import java.util.concurrent.ExecutionException;

public interface CardsSource {
    CardsSource init(CardsSourceResponse cardsSourceResponse, Context context) throws ExecutionException, InterruptedException;
    CardData getCardData(int position);
    int size();
    void deleteCardData(int position);
    void updateCardData(int position, CardData cardData);
    void addCardData(CardData cardData);
}


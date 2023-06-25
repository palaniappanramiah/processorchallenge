package com.fetch.rewards.receipt.processorchallenge.service;

import com.fetch.rewards.receipt.processorchallenge.model.Receipt;
import com.fetch.rewards.receipt.processorchallenge.model.Item;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Service
public class ReceiptService {
    public long calculateTotalPoints(Receipt receipt) throws ParseException {

        // Variable initialization and assignment
        long totalPoints = 0;
        Double total = Double.parseDouble(receipt.getTotal());

        // Parsing the Purchase Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date purchaseDate = sdf.parse(receipt.getPurchaseDate());

        // Parsing the Purchase Time
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime());

        // One point for every alphanumeric character in the retailer name.
        for(char ch : receipt.getRetailer().toCharArray())
            if(Character.isLetterOrDigit(ch))
                totalPoints++;

        // 50 points if the total is a round dollar amount with no cents.
        if (total > 0 && total % 1 == 0)
            totalPoints += 50;

        // 25 points if the total is a multiple of 0.25.
        if (total > 0 && total % 0.25 == 0)
            totalPoints += 25;

        // 5 points for every two items on the receipt.
        int totalItems = receipt.getItems().size();
        totalPoints += (totalItems / 2) * 5;

        // If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. The result is the number of points earned.
        for (Item item : receipt.getItems())
            if (item.getShortDescription().trim().length() % 3 == 0)
                totalPoints += Math.max((long) Math.ceil((Double.parseDouble(item.getPrice())) * 0.2), 1);


        // 6 points if the day in the purchase date is odd.
        if (purchaseDate.getDate() % 2 == 1)
            totalPoints += 6;

        // 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        if (purchaseTime.getHour() >= 14 && purchaseTime.getHour() < 16)
            totalPoints += 10;

        System.out.println("Total Points: " + totalPoints);
        return totalPoints;
    }
}
package com.fetch.rewards.receipt.processorchallenge.contoller;

import com.fetch.rewards.receipt.processorchallenge.model.Receipt;
import com.fetch.rewards.receipt.processorchallenge.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final Map<String, Long> receiptMap = new HashMap<>();
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {

        Map<String, String> response = new HashMap<>();
        String id = UUID.randomUUID().toString();
        response.put("id", id);

        try {
            receiptMap.put(id, receiptService.calculateTotalPoints(receipt));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @GetMapping("/{id}/points")
    public Map<String, Long> getPoints(@PathVariable String id) {

        Map<String, Long> response = new HashMap<>();

        if(receiptMap.containsKey(id))
            response.put("points", receiptMap.get(id));
        else
            response.put("id doesn't exists", -1L);

        return response;
    }
}
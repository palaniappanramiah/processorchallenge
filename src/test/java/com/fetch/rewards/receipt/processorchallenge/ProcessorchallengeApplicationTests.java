package com.fetch.rewards.receipt.processorchallenge;

import com.fetch.rewards.receipt.processorchallenge.contoller.ReceiptController;
import com.fetch.rewards.receipt.processorchallenge.model.Item;
import com.fetch.rewards.receipt.processorchallenge.model.Receipt;
import com.fetch.rewards.receipt.processorchallenge.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProcessorchallengeApplicationTests {

	@InjectMocks
	ReceiptController receiptController;

	@Mock
	ReceiptService receiptService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		receiptService = new ReceiptService();
		receiptController = new ReceiptController(receiptService);
	}

	@Test
	public void testCalculatePoints() throws ParseException {
		Receipt receipt = new Receipt();
		receipt.setRetailer("M&M Corner Market");
		receipt.setPurchaseDate("2022-03-20");
		receipt.setPurchaseTime("14:33");
		List<Item> items = Arrays.asList(
				new Item("Gatorade", "2.25"),
				new Item("Gatorade", "2.25"),
				new Item("Gatorade", "2.25"),
				new Item("Gatorade", "2.25")
		);
		receipt.setItems(items);
		receipt.setTotal("9.00");

		long points = receiptService.calculateTotalPoints(receipt);

		assertEquals(109L, points);
	}

	@Test
	public void testSimpleProcessReceipt() {
		Receipt receipt = new Receipt();
		receipt.setRetailer("Target");
		receipt.setPurchaseDate("2022-01-02");
		receipt.setPurchaseTime("13:13");
		List<Item> items = Arrays.asList(
				new Item("Pepsi - 12-oz", "1.25")
		);
		receipt.setItems(items);
		receipt.setTotal("1.25");

		Map<String, String> response = receiptController.processReceipt(receipt);
		assertNotNull(UUID.fromString(response.get("id")));
	}

}

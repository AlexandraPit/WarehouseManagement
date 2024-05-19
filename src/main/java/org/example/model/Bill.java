package org.example.model;

import java.util.List;

/**
 * Represents a bill for a client's orders.
 */
public record Bill(Client client, List<String> orderDetails, double totalAmount) {
}

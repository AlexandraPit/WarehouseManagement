package org.example.model;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.businessLayer.ClientBLL;
import org.example.businessLayer.ProductBLL;
import org.example.connection.ConnectionFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a bill for a client's orders.
 */
public record Bill(int clientId, List<Orders> orders) {
    private static final Logger LOGGER = Logger.getLogger(Bill.class.getName());
    private static final String SELECT_ORDERS_BY_CLIENT_ID = "SELECT * FROM orders WHERE client_id = ?";
    private static final ClientBLL clientBLL = new ClientBLL();
    private static final ProductBLL productBLL = new ProductBLL();

    /**
     * Generates bills for a list of clients.
     *
     * @param clients the list of clients for which bills are generated
     */
    public static void generateBillsForClients(List<Client> clients) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("AllBills.pdf"));
            document.open();

            for (Client client : clients) {
                List<String> orderDetails = new ArrayList<>();
                double totalAmount = 0.0;

                try (Connection connection = ConnectionFactory.getConnection()) {
                    PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_BY_CLIENT_ID);
                    statement.setInt(1, client.getId());

                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        String productName = productBLL.findProductById(resultSet.getInt("product_id")).getName();
                        double price = productBLL.findProductById(resultSet.getInt("product_id")).getPrice();
                        int quantity = resultSet.getInt("quantity");

                        orderDetails.add(productName + " (price " + price + ") * " + quantity + " pcs = " + (price * quantity));
                        totalAmount += price * quantity;
                    }
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "Error fetching orders for client " + client.getName() + ": " + e.getMessage());
                }

                // Add bill details to the PDF document
                document.add(new Paragraph("Client Name: " + client.getName()));
                document.add(new Paragraph("Order:"));
                for (String detail : orderDetails) {
                    document.add(new Paragraph(detail));
                }
                document.add(new Paragraph("Total: $" + totalAmount));
                document.add(new Paragraph("\n")); // Add some spacing between bills
            }
        } catch (DocumentException | IOException e) {
            LOGGER.log(Level.WARNING, "Error generating PDF: " + e.getMessage());
        } finally {
            // Close the PDF document
            if (document != null) {
                document.close();
            }
        }
    }
}

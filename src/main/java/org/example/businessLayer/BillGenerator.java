package org.example.businessLayer;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.connection.ConnectionFactory;
import org.example.model.Bill;
import org.example.model.Client;
import org.example.model.Product;

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

public class BillGenerator {
    private static final Logger LOGGER = Logger.getLogger(BillGenerator.class.getName());
    private static final String SELECT_ORDERS_BY_CLIENT_ID = "SELECT * FROM orders WHERE client_id = ?";
    private static final ClientBLL clientBLL = new ClientBLL();
    private static final ProductBLL productBLL = new ProductBLL();

    public static void generateBillsForClients(List<Client> clients) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Bills.pdf"));
            document.open();

            for (Client client : clients) {
                Bill bill = generateBillForClient(client);

                // Add bill details to the PDF document
                document.add(new Paragraph("Client Name: " + bill.client().getName()));
                document.add(new Paragraph("Order:"));
                for (String detail : bill.orderDetails()) {
                    document.add(new Paragraph(detail));
                }
                document.add(new Paragraph("Total: $" + bill.totalAmount()));
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

    private static Bill generateBillForClient(Client client) {
        List<String> orderDetails = new ArrayList<>();
        double totalAmount = 0.0;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_BY_CLIENT_ID);
            statement.setInt(1, client.getId());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = productBLL.findProductById(resultSet.getInt("product_id"));
                String productName = product.getName();
                double price = product.getPrice();
                int quantity = resultSet.getInt("quantity");

                orderDetails.add(productName + " (price $" + price + ") * " + quantity + " pcs = $" + (price * quantity));
                totalAmount += price * quantity;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error fetching orders for client " + client.getName() + ": " + e.getMessage());
        }

        return new Bill(client, orderDetails, totalAmount);
    }
}

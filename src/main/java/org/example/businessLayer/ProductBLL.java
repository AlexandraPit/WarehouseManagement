package org.example.businessLayer;

import org.example.dataAccessLayer.ProductDAO;
import org.example.model.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer (BLL) for managing products.
 */
public class ProductBLL {
    private ProductDAO productDAO;

    /**
     * Constructs a ProductBLL object and initializes the ProductDAO.
     */
    public ProductBLL(){
        productDAO = new ProductDAO();
    }

    /**
     * Finds a product by its ID.
     *
     * @param id the ID of the product to find
     * @return the found product
     * @throws NoSuchElementException if the product with the specified ID doesn't exist
     */
    public Product findProductById(int id){
        Product product = productDAO.getById(id,"p_id");
        if(product == null){
            throw new NoSuchElementException("The product with id = " + id + " doesn't exist!");
        }
        return product;
    }

    /**
     * Inserts a new product into the database.
     *
     * @param product the product to insert
     */
    public void insertProduct(Product product){
        productDAO.insert(product);
    }

    /**
     * Updates an existing product in the database.
     *
     * @param product the updated product object
     * @param id      the ID of the product to update
     */
    public void editProduct(Product product, int id){
        productDAO.update(product,"p_id", id);
    }

    /**
     * Deletes a product from the database by its ID.
     *
     * @param id the ID of the product to delete
     */
    public void deleteProduct(int id){
        productDAO.delete("p_id",id);
    }

    /**
     * Retrieves a list of all products from the database.
     *
     * @return a list of all products
     */
    public List<Product> viewAllProducts(){
        return productDAO.getAll();
    }
}

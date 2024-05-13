package org.example.businessLayer;

import org.example.dataAccessLayer.ProductDAO;
import org.example.model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {
    private ProductDAO productDAO;

    public ProductBLL(){
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id){
        Product product = productDAO.getById(id);
        if(product == null){
            throw new NoSuchElementException("The client with id = " + id + " doesn't exist!");
        }
        return product;
    }

    public void insertProduct(Product product){

        productDAO.insert(product);
    }

    public void editProduct(Product product, int id){
        productDAO.update(product, id);
    }

    public void deleteProduct(int id){
        productDAO.delete(id);
    }

    public List<Product> viewAllProducts(){
        return productDAO.getAllProducts();
    }

}

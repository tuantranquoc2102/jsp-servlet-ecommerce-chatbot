package com.ecommerce.dao;

import com.ecommerce.database.Database;
import com.ecommerce.entity.Account;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ProductDao {
    private static final Logger LOGGER = Logger.getLogger(ProductDao.class.getName());
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    // Call DAO class to access other entities' database.
    private final CategoryDao categoryDao = new CategoryDao();
    private final AccountDao accountDao = new AccountDao();

    public static void main(String[] args) throws ClassNotFoundException {
        ProductDao productDao = new ProductDao();
        List<Product> list = productDao.getSellerProducts(1);
        for (Product product : list) {
            System.out.println(product.toString());
        }
    }

    // Method to get blob image from database.
    private String getBase64Image(Blob blob) throws SQLException, IOException {
        InputStream inputStream = blob.getBinaryStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Method to execute query to get list products.
    private List<Product> getListProductQuery(String query) throws ClassNotFoundException {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {

            LOGGER.info("✅ Executing query: " + query);
            while (resultSet.next()) {
                try {
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString("product_name");
                    double price = resultSet.getDouble("product_price");
                    String description = resultSet.getString("product_description");
                    Category category = categoryDao.getCategory(resultSet.getInt("fk_category_id"));
                    Account account = accountDao.getAccount(resultSet.getInt("fk_account_id"));
                    boolean isDeleted = resultSet.getBoolean("product_is_deleted");
                    int amount = resultSet.getInt("product_amount");

                    // Convert Blob to Base64
                    String base64Image = getBase64Image(resultSet.getBlob("product_image"));

                    productList.add(new Product(id, name, base64Image, price, description, category, account, isDeleted, amount));

                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, "❌ Error processing product row", e);
                }
            }

        } catch (SQLException | IOException e) {
            LOGGER.log(Level.SEVERE, "Error fetching products", e);
        }

        return productList;
    }

    // Method to get all products from database.
    public List<Product> getAllProducts() throws ClassNotFoundException {
        String query = "SELECT * FROM product WHERE product_is_deleted = false";
        return getListProductQuery(query);
    }

    // Method to get a product by its id from database.
    public Product getProduct(int productId) throws SQLException, IOException {
        Product product = new Product();
        String query = "SELECT * FROM product WHERE product_id = " + productId;
        try (Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setBase64Image(getBase64Image(resultSet.getBlob(3)));
                product.setPrice(resultSet.getDouble(4));
                product.setDescription(resultSet.getString(5));
                product.setCategory(categoryDao.getCategory(resultSet.getInt(6)));
                product.setAccount(accountDao.getAccount(resultSet.getInt(7)));
                product.setDeleted(resultSet.getBoolean(8));
                product.setAmount(resultSet.getInt(9));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    // Method to get a categories by its id from database.
    public List<Product> getAllCategoryProducts(int category_id) throws ClassNotFoundException {
        String query = "SELECT * FROM product WHERE fk_category_id = " + category_id + " AND product_is_deleted = false";
        return getListProductQuery(query);
    }

    // Method to search a product by a keyword.
    public List<Product> searchProduct(String keyword) throws ClassNotFoundException {
        String query = "SELECT * FROM product WHERE product_name like '%" + keyword + "%' AND product_is_deleted = false";
        return getListProductQuery(query);
    }

    // Method to get all products of a seller.
    public List<Product> getSellerProducts(int sellerId) throws ClassNotFoundException {
        String query = "SELECT * FROM product WHERE fk_account_id = " + sellerId;
        return getListProductQuery(query);
    }

    // Method to remove a product from database by its id.
    public void removeProduct(Product product) {
        // Get id of the product.
        int productId = product.getId();

        String query = "UPDATE product SET product_is_deleted = true WHERE product_id = " + productId;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to add product to database.
    public void addProduct(String productName, InputStream productImage, double productPrice, String productDescription, int productCategory, int sellerId, int productAmount) {
        String query = "INSERT INTO product (product_name, product_image, product_price, product_description, fk_category_id, fk_account_id, product_is_deleted, product_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setBinaryStream(2, productImage);
            preparedStatement.setDouble(3, productPrice);
            preparedStatement.setString(4, productDescription);
            preparedStatement.setInt(5, productCategory);
            preparedStatement.setInt(6, sellerId);
            preparedStatement.setBoolean(7, false);
            preparedStatement.setInt(8, productAmount);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to edit product in database.
    public void editProduct(int productId, String productName, InputStream productImage, double productPrice, String productDescription, int productCategory, int productAmount) {
        String query = "UPDATE product SET product_name = ?, product_image = ?, product_price = ?, product_description = ?, fk_category_id = ?, product_amount = ? WHERE product_id = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, productName);
            preparedStatement.setBinaryStream(2, productImage);
            preparedStatement.setDouble(3, productPrice);
            preparedStatement.setString(4, productDescription);
            preparedStatement.setInt(5, productCategory);
            preparedStatement.setInt(6, productId);
            preparedStatement.setInt(7, productAmount);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to get 12 products to display on each page.
    // public List<Product> get12ProductsOfPage(int index) throws ClassNotFoundException {
    //     String query = "SELECT * FROM product WHERE product_is_deleted = false LIMIT " + ((index - 1) * 12) + ", 12";
    //     return getListProductQuery(query);
    // }

    // Method to get total products in database.
    public int getTotalNumberOfProducts() {
        int totalProduct = 0;
        String query = "SELECT COUNT(*) FROM product WHERE product_is_deleted = false";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalProduct = resultSet.getInt(1);
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return totalProduct;
    }

    // Method to decrease new amount of products.
    public void decreaseProductAmount(int productId, int productAmount) {
        String query = "UPDATE product SET product_amount = product_amount - ? WHERE product_id = ?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productAmount);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    /*
    * Get total number of products in database.
    */
    public int getTotalNumberProductsList(String categoryId) throws ClassNotFoundException {
        int totalProduct = 0;
        String sql = "SELECT COUNT(*) FROM product WHERE product_is_deleted = false";

        if (categoryId != null && !categoryId.isEmpty()) {
            sql += " AND fk_category_id = ?";
        }

        try (Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            if (categoryId != null && !categoryId.isEmpty()) {
                preparedStatement.setInt(1, Integer.parseInt(categoryId));
            }    

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                totalProduct = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalProduct;
    }

    /*
     * Get list products and paging with 12 items each page
     */
    public List<Product> getProductsList(String categoryId, String sort, int pageNumber) throws ClassNotFoundException, IOException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE product_is_deleted = false";
        
        if (categoryId != null && !categoryId.isEmpty()) {
            sql += " AND fk_category_id = ?";
        }

        if (sort != null) {
            switch (sort) {
                case "name_asc": sql += " ORDER BY product_name ASC"; break;
                case "name_desc": sql += " ORDER BY product_name DESC"; break;
                case "price_asc": sql += " ORDER BY product_price ASC"; break;
                case "price_desc": sql += " ORDER BY product_price DESC"; break;
            }
        }

        sql += " LIMIT ?, 12";

        try (Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            if (categoryId != null && !categoryId.isEmpty()) {
                preparedStatement.setInt(1, Integer.parseInt(categoryId));
                preparedStatement.setInt(2, (pageNumber - 1) * 12);
            } else {
                preparedStatement.setInt(1, (pageNumber - 1) * 12);
            }
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setName(resultSet.getString("product_name"));
                product.setPrice(resultSet.getDouble("product_price"));
                product.setBase64Image(getBase64Image(resultSet.getBlob("product_image")));
                
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

package com.ecommerce.dao;

import com.ecommerce.database.Database;
import com.ecommerce.entity.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    // Method to set amount of products for category.
    private void queryCategoryProductAmount(Category category) throws ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM product WHERE fk_category_id = ? AND product_is_deleted = false";
        
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, category.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    category.setTotalCategoryProduct(resultSet.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching category product count: " + e.getMessage());
        }
    }

    // Method to get category by id.
    public Category getCategory(int categoryId) throws ClassNotFoundException {
        Category category = new Category();
        String query = "SELECT * FROM category WHERE category_id = " + categoryId;
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            connection = new Database().getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

        // Call method to set category amount for category.
        queryCategoryProductAmount(category);

        return category;
    }

    // Method to get all categories from database.
    public List<Category> getAllCategories() throws ClassNotFoundException {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt(1));
                category.setName(resultSet.getString(2));
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Call method to set category amount for category.
        for (Category category : list) {
            queryCategoryProductAmount(category);
        }

        return list;
    }
}

package com.ecommerce.dao;

import com.ecommerce.database.Database;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Slider;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Layer
 * @author TeKu.Tran
 */
public class SliderDao {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public List<Slider> getAllSliders() throws ClassNotFoundException {
        List<Slider> sliders = new ArrayList<>();
        String sql = "SELECT * FROM slider ORDER BY created_at DESC";

        try (Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Slider slider = new Slider();
                slider.setId(resultSet.getInt("id"));
                slider.setSliderImage(resultSet.getString("slider_image"));
                slider.setSliderTitle(resultSet.getString("slider_title"));
                slider.setSliderDescription(resultSet.getString("slider_description"));
                // ...
                sliders.add(slider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sliders;
    }
}
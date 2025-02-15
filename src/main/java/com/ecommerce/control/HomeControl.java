package com.ecommerce.control;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.dao.SliderDao;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.Slider;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "HomeControl", value = "")
public class HomeControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();
    SliderDao sliderDao = new SliderDao();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Get all featured products.
        List<Product> featuredProductList = null;
        try {
            featuredProductList = productDao.getFeaturedProductsList();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Get all categories from database.
        List<Category> categoryList = new ArrayList<>();
        try {
            categoryList = categoryDao.getAllCategories();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Get all sliders
        List<Slider> slidersList = new ArrayList<>();
        try {
            slidersList = sliderDao.getAllSliders();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        request.setAttribute("sliders_list", slidersList);
        request.setAttribute("featured_products_list", featuredProductList);
        request.setAttribute("category_list", categoryList);
        // Set attribute active class for home in header.
        request.setAttribute("home_active", "active");
        // Get request dispatcher and render to index page.
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }
}
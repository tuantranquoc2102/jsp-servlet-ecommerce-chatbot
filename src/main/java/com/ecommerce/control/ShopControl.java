package com.ecommerce.control;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ShopControl", value = "/shop")
public class ShopControl extends HttpServlet {
    // Call DAO class to access with database.
    ProductDao productDao = new ProductDao();
    CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Get parameters from request.
        // Get param page number(index)
        String index = request.getParameter("index");
        if (index == null) {
            index = "1";
        }
        // Get param category_id
        String categoryId = request.getParameter("category_id");
        // Get param sort
        String sort = request.getParameter("sort");

        // Get 12 products from database to display on each page.
        List<Product> productList = new ArrayList<>();
        int totalProduct = 0;
        try {
            int pageIndex = Integer.parseInt(index);
            productList = productDao.getProductsList(categoryId, sort, pageIndex);
        } catch (NumberFormatException | ClassNotFoundException e) {
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

        // Get total products to count pages.
        try {
            totalProduct = productDao.getTotalNumberProductsList(categoryId);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int totalPages = totalProduct / 12;
        if (totalProduct % 12 != 0) {
            totalPages++;
        }

        // Set attribute active class for home in header and page number.
        String active = "active";

        request.setAttribute("product_list", productList);
        request.setAttribute("category_list", categoryList);
        request.setAttribute("total_pages", totalPages);
        request.setAttribute("shop_active", active);
        request.setAttribute("page_active", index);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("shop.jsp");
        requestDispatcher.forward(request, response);
    }
}

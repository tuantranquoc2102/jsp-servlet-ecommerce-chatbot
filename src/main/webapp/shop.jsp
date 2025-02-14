<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.ecommerce.dao.CategoryDao" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="templates/head.jsp"/>

<body>
<div class="site-wrap">
    <jsp:include page="templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0"><a href="/">Home</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">Shop</strong></div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">

            <div class="row mb-5">
                <!-- Right contents -->
                <div class="col-md-9 order-2">

                    <div class="row">
                        <div class="col-md-12 mb-5">
                            <div class="float-md-left mb-4"><h2 class="text-black h5">Shop All</h2></div>
                            <!-- dropdown filter -->
                            <div class="d-flex">
                                <div class="dropdown mr-1 ml-md-auto">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-secondary btn-sm dropdown-toggle"
                                                id="dropdownMenuReference" data-toggle="dropdown">
                                            <c:choose>
                                                <c:when test="${param.sort == 'name_asc'}">Name, A to Z</c:when>
                                                <c:when test="${param.sort == 'name_desc'}">Name, Z to A</c:when>
                                                <c:when test="${param.sort == 'price_asc'}">Price, low to high</c:when>
                                                <c:when test="${param.sort == 'price_desc'}">Price, high to low</c:when>
                                                <c:otherwise>Reference</c:otherwise>
                                            </c:choose>
                                        </button>
                                        <div class="dropdown-menu" aria-labelledby="dropdownMenuReference">
                                            <c:set var="categoryParam" value="" />
                                            <c:if test="${not empty param.category_id}">
                                                <c:set var="categoryParam" value="category_id=${param.category_id}&" />
                                            </c:if>
                                            <a class="dropdown-item" href="shop?${categoryParam}sort=name_asc">Name, A to Z</a>
                                            <a class="dropdown-item" href="shop?${categoryParam}sort=name_desc">Name, Z to A</a>
                                            <div class="dropdown-divider"></div>
                                            <a class="dropdown-item" href="shop?${categoryParam}sort=price_asc">Price, low to high</a>
                                            <a class="dropdown-item" href="shop?${categoryParam}sort=price_desc">Price, high to low</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Product List -->
                    <div class="row mb-5">
                        <c:forEach items="${product_list}" var="product_obj">
                            <div class="col-sm-6 col-lg-4 mb-4" data-aos="fade-up">
                                <div class="block-4 text-center border" style="height: 100%">
                                    <figure class="block-4-image">
                                        <a href="product-detail?id=${product_obj.id}">
                                            <img src="data:image/jpg;base64,${product_obj.base64Image}" alt="Image placeholder"
                                                 class="img-fluid" style="height: 100%">
                                        </a>
                                    </figure>
                                    <div class="block-4-text p-4">
                                        <h3><a href="product-detail?id=${product_obj.id}">${product_obj.name}</a></h3>
                                        <p class="mb-0">$${product_obj.price}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Phân trang -->
                    <div class="row" data-aos="fade-up">
                        <div class="col-md-12 text-center">
                            <div class="site-block-27">
                                <ul>
                                    <c:if test="${page_active > 1}">
                                        <li><a href="shop?index=${page_active - 1}${not empty param.category_id ? '&category_id=' : ''}${param.category_id}${not empty param.sort ? '&sort=' : ''}${param.sort}">&lt;</a></li>
                                    </c:if>

                                    <c:forEach begin="1" end="${total_pages}" var="i">
                                        <li class="${(page_active == i) ? "active" : " "}"><a
                                                href="shop?index=${i}${not empty param.category_id ? '&category_id=' : ''}${param.category_id}${not empty param.sort ? '&sort=' : ''}${param.sort}">${i}</a></li>
                                    </c:forEach>

                                    <c:if test="${page_active < total_pages}">
                                        <li><a href="shop?index=${page_active + 1}${not empty param.category_id ? '&category_id=' : ''}${param.category_id}${not empty param.sort ? '&sort=' : ''}${param.sort}">&gt;</a></li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Left menu -->
                <div class="col-md-3 order-1 mb-5 mb-md-0">
                    <div class="border p-4 rounded mb-4">
                        <h3 class="mb-3 h6 text-uppercase text-black d-block">Categories</h3>
                        <ul class="list-unstyled mb-0">
                            <li class="mb-1">
                                <a href="shop${not empty param.sort ? '?sort=' : ''}${param.sort}" class="d-flex">
                                    <span>All</span>
                                </a>
                            </li>
                            <c:forEach items="${category_list}" var="category_obj">
                                <li class="mb-1">
                                    <a href="shop?category_id=${category_obj.id}${not empty param.sort ? '&sort=' : ''}${param.sort}" class="d-flex">
                                        <span>${category_obj.name}</span>
                                        <span class="text-black ml-auto">(${category_obj.totalCategoryProduct})</span>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="templates/footer.jsp"/>
</div>

<jsp:include page="templates/scripts.jsp"/>
</body>
</html>
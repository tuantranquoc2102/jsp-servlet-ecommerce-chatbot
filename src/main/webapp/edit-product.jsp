<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<jsp:include page="templates/head.jsp"/>

<body>
<div class="site-wrap">
    <jsp:include page="templates/header.jsp"/>

    <div class="bg-light py-3">
        <div class="container">
            <div class="row">
                <div class="col-md-12 mb-0"><a href="${pageContext.request.contextPath}/">Home</a> <span class="mx-2 mb-0">/</span> <strong
                        class="text-black">Edit Product</strong></div>
            </div>
        </div>
    </div>

    <div class="site-section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h2 class="h3 mb-3 text-black">Product Information</h2>
                </div>

                <div class="col-md-7">
                    <form action="edit-product" method="post"
                          enctype="multipart/form-data">
                        <div class="p-3 border">
                            <!-- Product ID -->
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="id" class="text-black">Product ID <span class="text-danger">*</span></label>
                                    <input name="product-id" type="text" class="form-control" id="id" value="${product.id}" readonly>
                                </div>
                            </div>

                            <!-- Product Name -->
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="name" class="text-black"> Product Name <span class="text-danger">*</span></label>
                                    <input name="product-name" type="text" class="form-control" id="name" value="${product.name}">
                                </div>
                            </div>

                            <!-- Product Image -->
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="image" class="text-black">Image <span class="text-danger">*</span></label>
                                    <input name="product-image" type="file" class="form-control" id="image">
                                </div>
                            </div>

                            <!-- Featured Product -->
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="featured" class="text-black">Featured Product</label>
                                    <input type="checkbox" name="product-featured" id="featured" value="true"
                                        <c:if test="${product.productFeature}">checked="checked"</c:if> >
                                </div>
                            </div>

                            <!-- Description -->
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="description" class="text-black">Description <span class="text-danger">*</span></label>
                                    <textarea name="product-description" id="description" cols="30" rows="7" class="form-control">${product.description}</textarea>
                                </div>
                            </div>

                            <!-- Product Price -->
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="price" class="text-black">Price <span class="text-danger">*</span></label>
                                    <input name="product-price" type="number" class="form-control" id="price" value="${product.price}">
                                </div>
                            </div>

                            <!-- Amount -->
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="productAmount" class="text-black">Amount <span class="text-danger">*</span></label>
                                    <input name="product-amount" type="number" class="form-control" id="productAmount" value="${product.amount}">
                                </div>
                            </div>

                            <!-- Category -->
                            <div class="form-group row">
                                <div class="col-md-12">
                                    <label for="category" class="text-black">Category <span class="text-danger">*</span></label>
                                    <select name="product-category" id="category" class="form-control">
                                        <c:forEach items="${category_list}" var="category">
                                            <option value="${category.id}" <c:if test="${category.id eq product.category.id}">selected="selected"</c:if>>${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <!-- Button -->
                            <div class="form-group row">
                                <div class="col-lg-12">
                                    <input type="submit" class="btn btn-primary btn-lg btn-block" value="Edit product">
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

                <!-- Product Image -->
                <div class="col-md-5 ml-auto">
                    <div class="p-3 border">
                        <img src="data:image/jpg;base64,${product.base64Image}" alt="image" width="100%">
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
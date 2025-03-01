<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="site-section block-3 site-blocks-2 bg-light">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-7 site-section-heading text-center pt-4">
                <h2>Featured Products</h2>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="nonloop-block-3 owl-carousel">
                    <c:forEach items="${featured_products_list}" var="featuredProduct" begin="0" end="5">
                        <div class="item">
                            <div class="block-4 text-center">
                                <figure class="block-4-image">
                                    <a href="${pageContext.request.contextPath}/product-detail?id=${featuredProduct.id}">
                                        <img src="data:image/jpg;base64,${featuredProduct.base64Image}" alt="Image placeholder" class="img-fluid">
                                    </a>
                                </figure>
                                <div class="block-4-text p-4">
                                    <h3><a href="${pageContext.request.contextPath}/product-detail?id=${featuredProduct.id}">${featuredProduct.name}</a></h3>
                                    <p class="mb-0">$${featuredProduct.price}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>

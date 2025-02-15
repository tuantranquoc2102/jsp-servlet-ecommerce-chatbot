<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>

<div class="site-section site-blocks-2">
    <div class="container">
        <div class="row">
            <c:forEach items="${category_list}" var="obj_category">
                <div class="col-sm-6 col-md-6 col-lg-4 mb-4 mb-lg-0" data-aos="fade" data-aos-delay="">
                    <a class="block-2-item" href="${pageContext.request.contextPath}/category?category_id=${obj_category.id}">
                        <figure class="image">
                            <img src="static/images/${obj_category.name}.jpg" alt="" class="img-fluid">
                        </figure>

                        <div class="text">
                            <span class="text-uppercase">Collections</span>
                            <h3>${obj_category.name}</h3>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
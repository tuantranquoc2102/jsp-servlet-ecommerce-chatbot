<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="commonSlider" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
        <c:forEach items="${sliders_list}" var="slider" varStatus="status">
            <div class="carousel-item ${status.first ? 'active' : ''}">
                <img src="${slider.sliderImage}" class="d-block w-100" alt="${slider.sliderTitle}">
                <div class="carousel-caption d-none d-md-block" style="top: 50%; transform: translateY(-50%); text-align: center;">
                    <h5 style="font-size: 48px; text-align: left;">${slider.sliderTitle}</h5>
                    <p style="font-size: 24px; text-align: left;">${slider.sliderDescription}</p>
                    <a href="${pageContext.request.contextPath}/shop" class="btn" style="background-color: #ff0000; color: white; border: none; border-radius: 10px; padding: 10px 20px;">
                     Shop now
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
    <a class="carousel-control-prev" href="#commonSlider" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#commonSlider" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
    </a>
</div>
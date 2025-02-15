<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"   prefix="fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="messages" />

<header class="site-navbar" role="banner">
    
    <%@ include file="/templates/i18n.jsp" %>
    <div class="site-navbar-top">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-6 col-md-4 order-2 order-md-1 site-search-icon text-left">
                    <form action="search" method="get" class="site-block-top-search">
                        <span class="icon icon-search2"></span>
                        <input name="keyword" type="text" class="form-control border-0" placeholder="Search">
                    </form>
                </div>

                <div class="col-12 mb-3 mb-md-0 col-md-4 order-1 order-md-2 text-center">
                    <div class="site-logo">
                        <a href="${pageContext.request.contextPath}/" class="js-logo-clone">Shoppers</a>
                    </div>
                </div>

                <div class="col-6 col-md-4 order-3 order-md-3 text-right">
                    <div class="site-top-icons">
                        <ul>
                            <c:if test="${sessionScope.account != null}">
                                <li>
                                    <c:if test="${account.base64Image != null}">
                                        <img class="icon" src="data:image/jpg;base64,${account.base64Image}"
                                             id="dropdownMenuReference"
                                             data-toggle="dropdown" alt="image"
                                             style="width: 1.5em; border-radius: 50%; margin-right: 10px; margin-bottom: 10px">
                                    </c:if>

                                    <c:if test="${account.base64Image == null}">
                                        <img class="icon" src="../static/images/blank_avatar.png"
                                             id="dropdownMenuReference"
                                             data-toggle="dropdown" alt="image"
                                             style="width: 1.5em; border-radius: 50%; margin-right: 10px; margin-bottom: 10px">
                                    </c:if>

                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuReference">
                                        <a class="dropdown-item" href="profile-page">Your profile</a>
                                        <a class="dropdown-item" href="logout">Logout</a>
                                    </div>
                                </li>
                            </c:if>

                            <c:if test="${sessionScope.account == null}">
                                <li><a href="${pageContext.request.contextPath}/login"><span class="icon icon-person"></span></a></li>
                            </c:if>

                            <li>
                                <!-- Biểu tượng giỏ hàng -->
                                <a href="${pageContext.request.contextPath}/cart.jsp" class="site-cart">
                                    <span class="icon icon-shopping_cart"></span>
                                    <c:if test="${order.cartProducts.size() != null}">
                                        <span class="count">${order.cartProducts.size()}</span>
                                    </c:if>
                                </a>
                            </li>

                            <!-- Chọn ngôn ngữ bằng flag -->
                            <li>
                                <div class="dropdown ml-3">
                                    <a href="#" id="languageDropdown" data-toggle="dropdown" class="dropdown-toggle">
                                        <img src="<c:choose>
                                                    <c:when test="${sessionScope.lang == 'vi'}">../static/images/flag_vn.png</c:when>
                                                    <c:otherwise>../static/images/flag_en.png</c:otherwise>
                                                </c:choose>" 
                                            alt="Language" style="width: 16px; height: 16px; margin-left: 10px;">
                                    </a>
                                    <div class="dropdown-menu">
                                        <a class="dropdown-item" href="change-language?lang=en">
                                            <img src="../static/images/flag_en.png" alt="English" style="width: 16px; height: 16px;">${bundle.getString("lbl.lang.en")}
                                        </a>
                                        <a class="dropdown-item" href="change-language?lang=vi">
                                            <img src="../static/images/flag_vn.png" alt="Vietnamese" style="width: 16px; height: 16px;">${bundle.getString("lbl.lang.vi")}
                                        </a>
                                    </div>
                                </div>
                            </li>
                            <li class="d-inline-block d-md-none ml-md-0">
                                <a href="#" class="site-menu-toggle js-menu-toggle">
                                    <span class="icon-menu"></span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <nav class="site-navigation text-right text-md-center" role="navigation">
        <div class="container">
            <ul class="site-menu js-clone-nav d-none d-md-block" style="padding: 0">
                <li class="${home_active}"><a href="${pageContext.request.contextPath}/">${bundle.getString("lbl.home")}</a></li>
                <li class="${about_active}"><a href="${pageContext.request.contextPath}/about.jsp">${bundle.getString("lbl.about")}</a></li>
                <li class="${shop_active}"><a href="${pageContext.request.contextPath}/shop">${bundle.getString("lbl.shop")}</a></li>
                <li class="${contact_active}"><a href="${pageContext.request.contextPath}/contact.jsp">${bundle.getString("lbl.contact")}</a></li>

                <c:if test="${sessionScope.account != null}">
                    <li class="${order_history_active}"><a href="order-history">${bundle.getString("lbl.orders.history")}</a></li>
                </c:if>

                <c:if test="${sessionScope.account.isSeller == 1}">
                    <li class="${product_management_active}"><a href="product-management">${bundle.getString("lbl.products.management")}</a></li>
                    <li class="${order_management_active}"><a href="order-management">${bundle.getString("lbl.orders.management")}</a></li>
                </c:if>

                <c:if test="${sessionScope.account.isAdmin == 1}">
                    <li class="${websitem_active}"><a href="#">${bundle.getString("lbl.website.management")}</a></li>
                </c:if>
            </ul>
        </div>
    </nav>
</header>

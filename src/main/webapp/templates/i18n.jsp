<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>

<%
    String lang = (String) session.getAttribute("lang");
    if (lang == null) {
        lang = "en"; // Mặc định là English
        session.setAttribute("lang", lang);
    }
    Locale locale = new Locale(lang);
    ResourceBundle bundle = ResourceBundle.getBundle("i18n/messages", locale);
    request.setAttribute("bundle", bundle);
%>
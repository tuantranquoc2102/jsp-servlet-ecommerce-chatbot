# Sử dụng Tomcat 11 với JDK 17
FROM tomcat:11-jdk17

# Đặt thư mục làm việc
WORKDIR /usr/local/tomcat

# Xóa thư mục mặc định để mount code từ host
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Sao chép ứng dụng WAR vào thư mục webapps của Tomcat
COPY target/*.war /usr/local/tomcat/webapps/app.war

# Mở cổng 8080
EXPOSE 8080

# Chạy Tomcat khi container khởi động
CMD ["catalina.sh", "run"]
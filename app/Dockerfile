# Use official Python image
FROM python:3.9

# Set working directory
WORKDIR /app

# Copy files to container
COPY . .

# Install dependencies
RUN pip install flask openai==0.28 gunicorn flask-cors python-dotenv

# Expose Flask port
EXPOSE 3000

# Start the API using Gunicorn
CMD ["gunicorn", "-w", "2", "-b", "0.0.0.0:3000", "app:app"]
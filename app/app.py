import os
from flask import Flask, request, jsonify
from dotenv import load_dotenv
from flask_cors import cross_origin
import openai

# Load biến môi trường từ file .env
load_dotenv()

# Lấy API Key từ biến môi trường
openai.api_key = os.getenv("OPENAI_API_KEY")

# Initialize Flask app
app = Flask(__name__)

@app.route("/chatbot", methods=["POST"])
@cross_origin()  # Bật CORS chỉ cho endpoint này
def chatbot():
    try:
        data = request.get_json()
        user_message = data.get("message", "")

        if not user_message:
            return jsonify({"error": "Missing 'message' field"}), 400

        # Call OpenAI API
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=[{"role": "user", "content": user_message}]
        )

        bot_reply = response["choices"][0]["message"]["content"]
        return jsonify({"reply": bot_reply})

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=3000, debug=True)
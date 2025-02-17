<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chatbot Mua Hàng</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        #chat-container {
            width: 400px;
            border: 1px solid #ccc;
            padding: 10px;
            border-radius: 5px;
        }
        #chat-box {
            height: 300px;
            overflow-y: auto;
            border-bottom: 1px solid #ccc;
            margin-bottom: 10px;
            padding: 5px;
        }
        .user-message {
            text-align: right;
            color: blue;
            margin-bottom: 5px;
        }
        .bot-message {
            text-align: left;
            color: green;
            margin-bottom: 5px;
        }
        input, button {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
        }
    </style>
</head>
<body>

<h2>Chatbot AI - OpenAI</h2>

<div id="chat-container">
    <div id="chat-box"></div>
    <input type="text" id="user-input" placeholder="Enter message...">
    <button onclick="sendMessage()">Send message</button>
</div>

<script>
function sendMessage() {
    let userInput = $("#user-input").val().trim();
    if (userInput === "") return;

    $("#chat-box").append('<div class="user-message">You: ' + userInput + '</div>');
    $("#user-input").val(""); 

    $.post("chatbot", { message: userInput }, function(response) {
        if (response.response) {
            $("#chat-box").append('<div class="bot-message">Error: ' + response.error + '</div>');
        } else {
            $("#chat-box").append('<div class="bot-message">Chatbot: ' + response.reply + '</div>');
        }
        $("#chat-box").scrollTop($("#chat-box")[0].scrollHeight);
    }, "json").fail(function() {
        $("#chat-box").append('<div class="bot-message">Chatbot not responding!</div>');
    });
}
</script>

</body>
</html>

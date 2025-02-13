<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="chatbot-icon" onclick="toggleChat()">
    💬
</div>

<div id="chat-container">
    <div id="chat-header">
        <span>Chatbot Hỗ Trợ Mua Hàng</span>
        <button onclick="toggleChat()">❌</button>
    </div>
    <div id="chat-box"></div>
    <input type="text" id="user-input" placeholder="Nhập tin nhắn...">
    <button onclick="sendMessage()">Gửi</button>
</div>

<style>
    #chatbot-icon {
        position: fixed;
        bottom: 20px;
        right: 20px;
        background: #007bff;
        color: white;
        width: 50px;
        height: 50px;
        font-size: 24px;
        text-align: center;
        line-height: 50px;
        border-radius: 50%;
        cursor: pointer;
        box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.3);
    }

    #chat-container {
        display: none;
        position: fixed;
        bottom: 80px;
        right: 20px;
        width: 350px;
        background: white;
        border: 1px solid #ccc;
        border-radius: 10px;
        box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.3);
        padding: 10px;
    }

    #chat-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        background: #007bff;
        color: white;
        padding: 5px;
        border-radius: 10px 10px 0 0;
    }

    #chat-header button {
        width: 10%;
        padding: 0;
        margin: 0;
    }

    #chat-box {
        height: 250px;
        overflow-y: auto;
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

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
function toggleChat() {
    $("#chat-container").toggle();
}

function sendMessage() {
    let userInput = $("#user-input").val().trim();
    if (userInput === "") return;

    $("#chat-box").append('<div class="user-message">Bạn: ' + userInput + '</div>');
    $("#user-input").val(""); 

    $.post("chatbot", { message: userInput }, function(response) {
        if (response.response) {
            $("#chat-box").append('<div class="bot-message">Lỗi: ' + response.error + '</div>');
        } else {
            $("#chat-box").append('<div class="bot-message">Chatbot: ' + response.reply + '</div>');
        }
        $("#chat-box").scrollTop($("#chat-box")[0].scrollHeight);
    }, "json").fail(function() {
        $("#chat-box").append('<div class="bot-message">Chatbot không phản hồi!</div>');
    });
}
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>StorySpeak AI 동화 생성</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap');

        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 2rem;
            background-color: #f0f2f5;
            color: #333;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background: #fff;
            padding: 3rem;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
            text-align: center;
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 2rem;
            font-weight: 700;
        }

        p {
            font-size: 1.1rem;
            color: #555;
            margin-bottom: 1.5rem;
        }

        .topics {
            display: flex;
            justify-content: center;
            gap: 1rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
        }

        .topics button {
            padding: 0.8rem 1.5rem;
            border: 2px solid transparent;
            border-radius: 25px;
            background-color: #3498db;
            color: white;
            cursor: pointer;
            font-size: 1rem;
            font-weight: bold;
            transition: all 0.3s ease;
        }

        .topics button:hover {
            background-color: #2980b9;
            transform: translateY(-3px);
        }

        input[type="text"], select {
            padding: 0.8rem;
            border-radius: 8px;
            border: 1px solid #ccc;
            width: 100%;
            box-sizing: border-box;
            font-size: 1rem;
        }

        button[type="submit"] {
            padding: 1rem 2.5rem;
            background: linear-gradient(45deg, #2ecc71, #28a745);
            color: white;
            border: none;
            border-radius: 25px;
            cursor: pointer;
            font-size: 1.2rem;
            font-weight: bold;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(46, 204, 113, 0.4);
            margin-top: 1rem;
        }

        button[type="submit"]:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(46, 204, 113, 0.5);
        }

        .form-group {
            margin-bottom: 1.5rem;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: bold;
            color: #333;
        }

        .input-with-icon {
            display: flex;
            align-items: center;
        }

        #custom_topic {
            flex-grow: 1;
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 2rem;
            font-weight: 700;
        }

        .vocabulary-button {
            text-decoration: none;
            padding: 0.8rem 1.5rem;
            border-radius: 25px;
            background: linear-gradient(45deg, #6a11cb, #2575fc);
            color: white;
            font-weight: bold;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(106, 17, 203, 0.4);
        }

        .vocabulary-button:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(106, 17, 203, 0.5);
        }

        p {
            font-size: 1.1rem;
            color: #555;
            margin-bottom: 1.5rem;
        }

        .topics {
            display: flex;
            justify-content: center;
            gap: 1rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
        }

        .topics button {
            padding: 0.8rem 1.5rem;
            border: 2px solid transparent;
            border-radius: 25px;
            background-color: #3498db;
            color: white;
            cursor: pointer;
            font-size: 1rem;
            font-weight: bold;
            transition: all 0.3s ease;
        }

        .topics button:hover {
            background-color: #2980b9;
            transform: translateY(-3px);
        }

        input[type="text"], select {
            padding: 0.8rem;
            border-radius: 8px;
            border: 1px solid #ccc;
            width: 100%;
            box-sizing: border-box;
            font-size: 1rem;
        }

        button[type="submit"] {
            padding: 1rem 2.5rem;
            background: linear-gradient(45deg, #2ecc71, #28a745);
            color: white;
            border: none;
            border-radius: 25px;
            cursor: pointer;
            font-size: 1.2rem;
            font-weight: bold;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(46, 204, 113, 0.4);
            margin-top: 1rem;
        }

        button[type="submit"]:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(46, 204, 113, 0.5);
        }

        .form-group {
            margin-bottom: 1.5rem;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: bold;
            color: #333;
        }

        .input-with-icon {
            display: flex;
            align-items: center;
        }

        #custom_topic {
            flex-grow: 1;
        }

        .mic-icon {
            cursor: pointer;
            font-size: 1.5rem;
            margin-left: 10px;
        }
    </style>
</head>

<body>
<div class="container">

    <h1>🌟 AI 영어 동화 만들기</h1>
    <div style="text-align: center; margin-bottom: 20px;">
        <a href="/vocabulary" class="vocabulary-button">
            단어장 보러 가기
        </a>
    </div>

    <form action="/story/create" method="post" accept-charset="UTF-8">        <div class="form-group">
            <label>주제 선택:</label>
            <div class="topics">
                <button type="submit" name="topic" value="용과 마법사">용과 마법사</button>
                <button type="submit" name="topic" value="숲 속 친구들">숲 속 친구들</button>
                <button type="submit" name="topic" value="바다 탐험">바다 탐험</button>
            </div>
        </div>

        <div class="form-group">
            <label for="custom_topic">나만의 주제:</label>
            <div class="input-with-icon">
                <input type="text" id="custom_topic" name="custom_topic" placeholder="직접 입력하세요">
                <span class="mic-icon" onclick="startSpeechRecognition()">🎤</span>
            </div>
        </div>

        <div class="form-group">
            <label for="level">난이도 (CEFR):</label>
            <select id="level" name="level">
                <option value="A1">초급</option>
                <option value="B1">중급</option>
                <option value="C1">상급</option>
            </select>
        </div>

        <button type="submit">동화 생성</button>
    </form>
</div>

<script>
    function startSpeechRecognition() {
        if ('webkitSpeechRecognition' in window) {
            const recognition = new webkitSpeechRecognition();
            recognition.lang = 'ko-KR';
            recognition.onresult = function(event) {
                const transcript = event.results[0][0].transcript;
                document.getElementById('custom_topic').value = transcript;
            }
            recognition.start();
        } else {
            alert('음성 인식을 지원하지 않는 브라우저입니다.');
        }
    }
</script>

</body>
</html>

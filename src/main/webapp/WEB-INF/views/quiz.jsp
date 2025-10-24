<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>단어 퀴즈</title>
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

        h2 {
            color: #2c3e50;
            margin-bottom: 2rem;
            font-weight: 700;
        }

        .feedback {
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 2rem;
            font-size: 1.1rem;
            font-weight: bold;
        }

        .feedback.correct {
            background-color: #e8f5e9;
            color: #2e7d32;
        }

        .feedback.incorrect {
            background-color: #ffebee;
            color: #c62828;
        }

        .question {
            margin-bottom: 2rem;
            text-align: left;
        }

        .question p {
            font-size: 1.2rem;
        }

        .options {
            display: flex;
            flex-direction: column;
            gap: 1rem;
            margin-top: 1.5rem;
        }

        .options label {
            display: block;
            padding: 1rem;
            border: 2px solid #eee;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 1.1rem;
        }

        .options label:hover {
            background-color: #f9f9f9;
            border-color: #3498db;
        }

        .options input[type="radio"] {
            margin-right: 1rem;
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
            margin-top: 2rem;
        }

        button[type="submit"]:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 20px rgba(46, 204, 113, 0.5);
        }

        .actions a {
            padding: 1rem 2.5rem;
            border-radius: 25px;
            color: white;
            text-decoration: none;
            background: linear-gradient(45deg, #95a5a6, #7f8c8d);
            box-shadow: 0 4px 15px rgba(149, 165, 166, 0.4);
            transition: all 0.3s ease;
            display: inline-block;
            margin-top: 1rem;
        }

        .actions a:hover {
            transform: translateY(-3px);
        }

        .speaker-icon {
            cursor: pointer;
            font-size: 1.5rem;
            margin-left: 10px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>단어 퀴즈</h2>

    <c:if test="${not empty previousCorrect}">
        <c:choose>
            <c:when test="${previousCorrect}">
                <p class="feedback correct">정답입니다! 다음 문제로 넘어갑니다. 🎉</p>
            </c:when>
            <c:otherwise>
                <p class="feedback incorrect">오답입니다. 다음 문제로 넘어갑니다. 😢</p>
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${not empty question}">
        <div class="question">
            <p>단어: <strong>${question.word}</strong> <span class="speaker-icon" data-word="${question.word}" onclick="playWord(this)">🔊</span></p>
            <p>뜻을 고르세요:</p>
            <form action="/quiz/submit" method="post">
                <input type="hidden" name="index" value="${index}"/>
                <div class="options">
                    <c:forEach var="option" items="${question.options}">
                        <label><input type="radio" name="answer" value="${option}" required> ${option}</label>
                    </c:forEach>
                </div>
                <button type="submit">제출</button>
            </form>
        </div>
    </c:if>

    <c:if test="${empty question}">
        <div class="actions">
            <p>모든 퀴즈를 완료했습니다! 결과를 확인하려면 <a href="/result">여기</a>를 클릭하세요.</p>
            <a href="/">처음으로 돌아가기</a>
        </div>
    </c:if>
</div>

<script>
    function playWord(element) {
        const word = element.getAttribute('data-word');
        const audio = new Audio(`/tts?text=` + encodeURIComponent(word));
        audio.play();
    }
</script>

</body>
</html>
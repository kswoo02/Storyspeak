<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>퀴즈 결과</title>
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

        p {
            font-size: 1.2rem;
            color: #555;
            margin-bottom: 2rem;
        }

        ul {
            list-style: none;
            padding: 0;
            margin-bottom: 2rem;
        }

        li {
            background: #e8f5e9;
            color: #2e7d32;
            margin-bottom: 1rem;
            padding: 1rem;
            border-radius: 8px;
            font-size: 1.1rem;
            font-weight: bold;
        }

        li.incorrect {
            background: #ffebee;
            color: #c62828;
        }

        a {
            padding: 1rem 2.5rem;
            border-radius: 25px;
            color: white;
            text-decoration: none;
            background: linear-gradient(45deg, #3498db, #2980b9);
            box-shadow: 0 4px 15px rgba(52, 152, 219, 0.4);
            transition: all 0.3s ease;
            display: inline-block;
        }

        a:hover {
            transform: translateY(-3px);
        }
    </style>
</head>
<body>
<div class="container">
    <h2>퀴즈 결과</h2>
    <c:if test="${empty correctWords && empty incorrectWords}">
        <p>퀴즈를 풀지 않았습니다.</p>
    </c:if>

    <c:if test="${not empty correctWords}">
        <p>다음 단어를 맞추셨습니다:</p>
        <ul>
            <c:forEach var="word" items="${correctWords}">
                <li>${word}</li>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${not empty incorrectWords}">
        <p>다음 단어를 틀리셨습니다:</p>
        <ul>
            <c:forEach var="question" items="${incorrectWords}">
                <li class="incorrect"><strong>${question.word}</strong>: ${question.correctMeaning}</li>
            </c:forEach>
        </ul>
    </c:if>

    <a href="/">처음으로 돌아가기</a>
</div>
</body>
</html>
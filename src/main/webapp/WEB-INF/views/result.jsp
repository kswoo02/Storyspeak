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

    <form action="/quiz/addVocabulary" method="post" onsubmit="return validateForm();">
        <c:if test="${not empty correctWords}">
            <p>다음 단어를 맞추셨습니다:</p>
            <ul>
                <c:forEach var="word" items="${correctWords}">
                    <li>
                        <input type="checkbox" name="selectedWords" value="${word}" id="word-${word}">
                        <label for="word-${word}">${word}</label>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${not empty incorrectWords}">
            <p>다음 단어를 틀리셨습니다:</p>
            <ul>
                <c:forEach var="question" items="${incorrectWords}">
                    <li class="incorrect">
                        <input type="checkbox" name="selectedWords" value="${question.word}" id="question-${question.word}">
                        <label for="question-${question.word}"><strong>${question.word}</strong>: ${question.correctMeaning}</label>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${not empty correctWords || not empty incorrectWords}">
            <div style="display: flex; justify-content: center; gap: 1rem; margin-top: 2rem;">
                <a href="/" style="padding: 1rem 2.5rem; border-radius: 25px; color: white; text-decoration: none; background: linear-gradient(45deg, #3498db, #2980b9); box-shadow: 0 4px 15px rgba(52, 152, 219, 0.4); transition: all 0.3s ease; display: inline-block;">처음으로 돌아가기</a>
                <button type="submit" style="padding: 1rem 2.5rem; border-radius: 25px; color: white; border: none; background: linear-gradient(45deg, #2ecc71, #28a745); box-shadow: 0 4px 15px rgba(46, 204, 113, 0.4); transition: all 0.3s ease; cursor: pointer;" onmouseover="this.style.transform='translateY(-3px)'" onmouseout="this.style.transform='translateY(0)'">선택한 단어 단어장에 추가</button>
            </div>
        </c:if>
    </form>
</div>
<script>
    function validateForm() {
        const checkboxes = document.querySelectorAll('input[name="selectedWords"]');
        let isChecked = false;
        for (let i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].checked) {
                isChecked = true;
                break;
            }
        }
        if (!isChecked) {
            alert('단어를 선택해주세요');
            return false;
        }
        return true;
    }
</script>
</body>
</html>
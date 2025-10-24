<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>${story.title}</title>
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
            max-width: 800px;
            text-align: center;
        }

        h2 {
            color: #2c3e50;
            margin-bottom: 2rem;
            font-weight: 700;
        }

        img {
            max-width: 100%;
            height: auto;
            border-radius: 10px;
            margin-bottom: 2rem;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        pre {
            white-space: pre-wrap;
            line-height: 1.8;
            text-align: left;
            background: #fafafa;
            padding: 2rem;
            border-radius: 10px;
            font-size: 1.1rem;
            color: #444;
        }

        .actions {
            margin-top: 2rem;
            display: flex;
            justify-content: center;
            gap: 1rem;
        }

        .actions button, .actions a {
            padding: 1rem 2.5rem;
            border: none;
            border-radius: 25px;
            color: white;
            cursor: pointer;
            font-size: 1.2rem;
            font-weight: bold;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }

        .actions button {
            background: linear-gradient(45deg, #3498db, #2980b9);
            box-shadow: 0 4px 15px rgba(52, 152, 219, 0.4);
        }

        .actions a {
            background: linear-gradient(45deg, #95a5a6, #7f8c8d);
            box-shadow: 0 4px 15px rgba(149, 165, 166, 0.4);
        }

        .actions button:hover, .actions a:hover {
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
    <h2>${story.title}</h2>
    <c:if test="${not empty imageUrl}">
        <img src="${imageUrl}" alt="Story cover">
    </c:if>
    <div class="story-content">
        <c:forEach var="paragraph" items="${fn:split(story.content, '|||')}">
            <p>
                ${paragraph}
                <span class="speaker-icon" data-text="${fn:escapeXml(paragraph)}" onclick="playText(this)">🔊</span>
            </p>
        </c:forEach>
    </div>

    <div class="actions">
        <form action="/quiz" method="get">
            <input type="hidden" name="story" value="${fn:escapeXml(story.content)}"/>
            <button type="submit">단어 퀴즈 풀기</button>
        </form>
        <a href="/">처음으로</a>
    </div>
</div>

<script>
    function playText(element) {
        const text = element.getAttribute('data-text');
        const audio = new Audio('/tts?text=' + encodeURIComponent(text));
        audio.play();
    }
</script>

</body>
</html>
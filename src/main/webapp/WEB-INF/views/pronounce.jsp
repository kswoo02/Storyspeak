<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>발음 피드백</title>
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

        p {
            font-size: 1.2rem;
            color: #555;
            margin-bottom: 1rem;
            text-align: left;
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
            margin-bottom: 2rem;
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
    <h2>발음 피드백</h2>
    <p><strong>연습 문구:</strong> ${target}</p>
    <p><strong>피드백:</strong></p>
    <pre>${feedback}</pre>

    <a href="/">처음으로 돌아가기</a>
</div>
</body>
</html>
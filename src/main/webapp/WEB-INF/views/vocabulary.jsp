<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vocabulary List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            margin: auto;
        }
        h1, h2 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        form {
            margin-top: 20px;
            padding: 15px;
            border: 1px solid #eee;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        form input[type="text"] {
            width: calc(100% - 100px);
            padding: 8px;
            margin-right: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        form input[type="submit"] {
            padding: 8px 15px;
            background-color: #5cb85c;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        form input[type="submit"]:hover {
            background-color: #4cae4c;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Vocabulary List</h1>

        <form action="/vocabulary/add" method="post">
            <label for="word">Add New Word:</label>
            <input type="text" id="word" name="word" placeholder="Enter English word" required>
            <input type="submit" value="Add Word">
        </form>

        <h2>My Words</h2>
        <c:if test="${empty vocabularyList}">
            <p>No words in your vocabulary yet. Add some above!</p>
        </c:if>
        <c:if test="${not empty vocabularyList}">
            <table>
                <thead>
                    <tr>
                        <th>Word</th>
                        <th>Meaning</th>
                        <th>Example</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="vocab" items="${vocabularyList}">
                        <tr>
                            <td>${vocab.word}</td>
                            <td>${vocab.meaning}</td>
                            <td>${vocab.example}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>

<#ftl encoding="UTF-8">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thank you for registration, ${name}</title>
    <style>
        #body {
            font-family: "Segoe UI", serif;

            display: flex;
            flex-direction: column;
            align-items: center;

            background-color: #607FF2;
            font-size: 20px;
            height: 100%;
        }

        #div {
            display: block;
            flex-direction: column;
            margin: 10%;
            font-family: "Segoe UI", serif;
            background-color: white;
            padding: 3%;
            border-radius: 20px;
            color: black;
        }

        #a {
            text-decoration: none;
        }
    </style>
</head>
<body>
<div id="body">
    <div id="div">
        <h1 style="text-align: center">Registration</h1>
        <p>Hello, ${name}!</p>
        <p>This is registration mail. You can log in and use task list app.</p>
        <p>With best regards, <br> Ilya Lisov</p>
        <a id="a" href="https://github.com/Baldezxniy/lists-task">Github</a>
    </div>
</div>
</body>
</head>
</html>
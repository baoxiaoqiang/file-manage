<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
</head>
<body>
文件上传示例
<form action="/file/upload" method="post" enctype="multipart/form-data">
<input name="file" type="file"><p/>
    <input name="file2" type="file">
    <input type="submit" value="upload">

</form>
</body>
</html>
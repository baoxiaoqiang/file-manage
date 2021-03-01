<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文件上传</title>
</head>
<body>
文件上传示例
<form action="/file/import" method="post" enctype="multipart/form-data">
    <input name="file" type="file">
    <select name="importType" >
        <#list importFileTypes as type>
            <option value="${type.group}">${type.description}</option>
        </#list>

    </select>
    <input name = "date" type ="hidden" value = "2019-06-18" >

    <input type="submit" value="upload">

</form>
</body>
</html>
<!DOCTYPE html>

<html lang="en">
<#include "../layout/header.ftl">
<body>
<div>
    <div class="triggers container" style="width:70%;margin-top:5%;">
       <form role="form" method="POST">
         <div class="form-group">
           <label for="username">用户名</label>
           <input type="text" class="form-control" name="username" placeholder="Username">
         </div>
         <div class="form-group">
           <label for="password">Password</label>
           <input type="password" class="form-control" name="password" placeholder="Password">
         </div>
         <button type="submit" class="btn btn-primary">Submit</button>
       </form>
    </div>
</body>
</html>
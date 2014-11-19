<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

	<%
		String contextPath=request.getContextPath();
	%>
	
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type='text/javascript' src='<%=contextPath%>/dwr/engine.js'></script>
        <script type='text/javascript' src='<%=contextPath%>/dwr/interface/UserServiceImpl.js'></script>
        <script type='text/javascript' src='<%=contextPath%>/dwr/util.js'></script>
        <title>Welcome to Spring Web MVC project</title>
        <script type="text/javascript">
            function goGetit(){
                UserServiceImpl.getUser(document.getElementById("userid").value, {
                    callback:function(user) { 
                        document.getElementById("userage").value=user.age;
                        document.getElementById("username").value=user.name;
                    }
                });
            }
            
            function goSaveit(){
                var user= {
                    id: document.getElementById("userid").value,
                    age:document.getElementById("userage").value,
                    name:document.getElementById("username").value
                };
                UserServiceImpl.addUser(user, {
                    callback:function(user) { 
                        var useraction = (document.getElementById("userid").value=="")? "added":"altered";
                        document.getElementById("message").innerHTML = "User "+useraction+" with the ID: "+user.id;
                        getAllUsers();
                    }
                });
            }
            
            function goRemoveit(){
                var user= {
                    id: document.getElementById("userid").value
                };
                UserServiceImpl.removeUser(user, {
                    callback:function(user) { 
                        document.getElementById("userid").value="";
                        document.getElementById("userage").value="";
                        document.getElementById("username").value="";
                        document.getElementById("message").innerHTML = "User removed with the ID: "+user.id;
                        getAllUsers();
                    }
                });
            }
            
            function getAllUsers(){
                
                UserServiceImpl.getAllUsers({
                    callback:function(users) {
                        var cellFunctions = [
                            function(user) { return user.id; },
                            function(user) { return user.age; },
                            function(user) { return user.name; }
                        ];
                        dwr.util.removeAllRows("allusers");
                        dwr.util.addRows( "allusers", users, cellFunctions,{ escapeHtml:false });
                    }
                });
            }
        </script>
    </head>

    <body onload="getAllUsers()">
        <H2>Hello! This is the default welcome page for a Spring Web MVC project.</H2>
        <h3>${message}<h3/>
            <input type="text" id="userid"><input type="button" value="Get User By ID" onclick="goGetit();"/>
            <hr width="70%"/>
            <div id="message"> </div>
            <table>
                <tr>
                    <td>Age:</td>
                    <td><input type="text" id="userage" value="10"></td>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" id="username" value="appdynamics"></td>
                </tr>
                <tr>
                    <td><input type="button" value="Add/Edit User" onclick="goSaveit();"/></td>
                    <td><input type="button" value="Remove User" onclick="goRemoveit();"/></td>
                </tr>
            </table>
            <hr width="70%"/>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Age</th>
                        <th>Name</th>
                    </tr>
                </thead>
                <tbody id="allusers"> </tbody>
            </table>
    </body>
</html>

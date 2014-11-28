<%--
  Created by IntelliJ IDEA.
  User: jayesh
  Date: Feb 18, 2009
  Time: 6:03:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Message Sender Page</title></head>
<body>


<form action="Producer.do">
  Producer Queue Connection Factory: <input type="text"
                                            name="connectionfactory" value="QueueConnectionFactory"/><br/>
  Producer Queue Destination:  <input type="text" name="destination" value="DLQ"/>
    <input type="submit"/>
</form>
<br/><br/>
<form action="queuesendere.queue">
   Queue Connection Factory: <input type="text"
                                    name="connectionfactory" value="QueueConnectionFactory"/><br/>
   Queue Destination:  <input type="text" name="destination" value="DLQ"/>
    <input type="submit"/>
</form>
<br/><br/>
<form action="TopicPublisher.topic">
   Topic Connection Factory: <input type="text"
                                    name="connectionfactory" value="TopicConnectionFactory"/><br/>
   Topic Destination:  <input type="text" name="destination" value="topicTopic"/>
    <input type="submit"/>
</form>
<br/><br/>



</body>
</html>
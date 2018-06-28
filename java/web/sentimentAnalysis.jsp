<%-- 
    Document   : sentimentAnalysis
    Created on : 25 Jun, 2018, 5:04:18 PM
    Author     : SOUMYAGOURAB.S
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.ist.model.SentimentalAnalysis"%>
<%@page import="com.ist.resources.utils.CommonUtils"%>
<%@page import="com.ist.resources.utils.CommonDbVariable"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
  <%
      try{
        SentimentalAnalysis tc = new SentimentalAnalysis();
        tc.populateSentiment();
      }catch (Exception e){
          e.getStackTrace();
          System.out.println("hello");
      }
  %>
 <%= "Congratulations... over!!" %>
</body>
</html>

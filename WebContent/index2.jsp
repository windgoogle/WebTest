<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>index 2 page</title>
</head>
<body>
<ul>
    <li><a href="logtest">测试日志</a>
    <li><a href="logtest2">测试日志2</a>
    <li><a href="lock">测试线程锁LockSupport.park()</a> &nbsp; <a href="lock?unpark=true">解锁</a>
    <li><a href="testsleep">测试线程sleep</a> &nbsp;
    <li><a href="threadDump">线程dump</a>
    <li><a href="threadDeadLock">线程死锁检测</a>
    <li><a href="cluster">集群测试</a>
    <li><a href="login.jsp">web认证测试</a>
    <li><a href="getRes">getResource测试</a>
</ul>
</body>
</html>
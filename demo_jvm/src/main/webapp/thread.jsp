<%@ page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>服务器线程信息</title>
</head>
<body>
<pre>
<% 
for(Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()){
    Thread thread = (Thread)stackTrace.getKey();
    StackTraceElement[] stack = (StackTraceElement[])stackTrace.getValue();
    if(thread.equals(Thread.currentThread())){
        continue;
    }
    out.print("\n线程："+thread.getName()+"\n");
    for(StackTraceElement element : stack){
        out.print("\t"+element+"\n");
    }
}
%>
</pre>
</body>
</html>

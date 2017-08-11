<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="java.lang.management.ManagementFactory"%>
<%@ page language="java" import="java.lang.management.MemoryPoolMXBean"%>
<%@ page language="java" import="java.lang.management.RuntimeMXBean"%>
<%@ page language="java" import="java.lang.management.GarbageCollectorMXBean"%>

<%@ page language="java" import="java.text.NumberFormat"%>
<%@ page language="java" import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.Date"%>
<%@ page language="java" import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%!
public String abb(long a, long b) {
	NumberFormat numberFormat = NumberFormat.getInstance();
	numberFormat.setMaximumFractionDigits(2);
	String result = numberFormat.format((double) a / (double) b * 100);
	// System.out.println("num1和num2的百分比为:" + result + "%");
	return result;
}

public String getString(String[] str){
	String ret="";
	for(String s:str){
		ret+=s+",";
	}
	return ret;
}

%>
<%
RuntimeMXBean rmb = ManagementFactory.getRuntimeMXBean();
Date d = new Date(rmb.getStartTime());
SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String dateString = formatter.format( d);

List<MemoryPoolMXBean> s = ManagementFactory.getMemoryPoolMXBeans();
List<GarbageCollectorMXBean> gcm = ManagementFactory.getGarbageCollectorMXBeans();

%>
<table border="1">
<tr>
<td colspan="4">JVM health check(by sdc):</td>
</tr>
<tr>
<td>ContextClassLoader:</td>
<td colspan="2">:=<%=Thread.currentThread().getContextClassLoader() %></td>
<td>查询当前的ClassLoader，用于判断是否有重启现象</td>
</tr>
<tr>
<td>startTime:</td>
<td colspan="2">:=<%= dateString%></td>
<td>启动时间，用户判断是否自动重启，这个重启是进程级别的。</td>
</tr>
<tr>
<td colspan="4">Memory Check: "PS Old Gen" and "PS Perm Gen" is vary important(PS Old Gen和PS Perm Gen最为重要)</td>
</tr>
<tr >
<td>序号</td>
<td>最大容量</td>
<td>当前使用</td>
<td>最大百分比</td>
</tr>
<%
for (MemoryPoolMXBean ss : s) {
	//ss.getUsage();
	%>
	<tr >
<td><%=ss.getName() %></td>
<td><%=ss.getUsage().getMax() %>：（<%=abb(ss.getUsage().getMax(),1024*1024*100) %>M）</td>
<td><%=ss.getUsage().getUsed() %>：（<%=abb(ss.getUsage().getUsed(),1024*1024*100) %>M）</td>
<td><%=abb(ss.getUsage().getUsed(),ss.getUsage().getMax()) %>%</td>
</tr>
	<%
}
%>
<tr>
<td colspan="4">GC Check: PS MarkSweep is the best zero(PS MarkSweep最好是0)</td>
</tr>
<tr >
<td>名称</td>
<td>GC 内存区域</td>
<td>GC次数</td>
<td>GC时间</td>
</tr>
<%
for (GarbageCollectorMXBean gg : gcm) {
	//ss.getUsage();
	%>
	<tr >
<td><%=gg.getName() %></td>
<td><%=getString(gg.getMemoryPoolNames()) %></td>
<td><%=gg.getCollectionCount() %></td>
<td><%=gg.getCollectionTime() %>ns（<%=abb(gg.getCollectionTime(),1000*100) %>s）</td>
</tr>
	<%
}
%>
</table>
</body>
</html>
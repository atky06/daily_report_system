<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h3>フォロー　一覧</h3>
        <table id="follow_list">
            <tbody>
                <tr>
                    <th class="follow_name">氏名</th>
                    <th class="follow_date">日付</th>
                </tr>
                <c:forEach var="follow" items="${follows}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="follow_name"><c:out value="${follow.follow.name}" /></td>
                        <td class="follow_date"><fmt:formatDate value="${follow.created_at}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                   </tr>
               </c:forEach>
           </tbody>
       </table>

       <div id="pagination">
            （全 ${follows_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((follows_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>
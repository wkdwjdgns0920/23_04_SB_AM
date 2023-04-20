<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE LIST" />
<%@ include file="../common/head.jspf"%>
<hr />

<section class="mt-8 text-xl">
		<div class="container mx-auto px-3">
				<div class="table-box-type-1">
						<table>
								<thead>
										<tr>
												<th>번호</th>
												<th>날짜</th>
												<th>제목</th>
												<th>작성자</th>
										</tr>
								</thead>

								<tbody>
										<c:forEach var="article" items="${articles }">
												<tr>
														<td>${article.id}</td>
														<td>${article.regDate.substring(2,16)}</td>
														<td>
																<a href="../article/detail?id=${article.id}">${article.title}</a>
														</td>
														<td>${article.extra__writer}</td>
														<td>
																<a href="/usr/article/doDelete?id${article.id}">삭제</a>
														</td>
														<td>
																<button type="button" onclick="/usr/article/doModify?id${article.id}">수정</button>
														</td>

												</tr>
										</c:forEach>
								</tbody>

						</table>
				</div>
		</div>
</section>

<%@ include file="../common/foot.jspf"%>
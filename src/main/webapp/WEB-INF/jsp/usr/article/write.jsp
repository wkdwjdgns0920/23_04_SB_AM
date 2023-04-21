<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="ARTICLE WRITE" />
<%@ include file="../common/head.jspf"%>
<hr />

	<section class="mt-8 text-xl">
	<div class="container mx-auto px-3">

		<div class="table-box-type-1">
			<form method="post" action="doWrite">
				<table border="1">
					<colgroup>
						<col width="200" />
					</colgroup>

					<tbody>
						<tr>
							<th>제목 :</th>
							<td><input autocomplete="off" type="text" placeholder="제목을 입력해주세요" name="title" /></td>
						</tr>
						<tr>
							<th>내용 :</th>
							<td><textarea type="text" placeholder="내용을 입력해주세요" name="body"></textarea></td>
						</tr>

					</tbody>

				</table>
				<button type="submit">글쓰기</button>
			</form>
		</div>
	</div>
</section>


<%@ include file="../common/foot.jspf"%>
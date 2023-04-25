package com.KoreaIT.jjh.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.jjh.demo.service.ArticleService;
import com.KoreaIT.jjh.demo.service.BoardService;
import com.KoreaIT.jjh.demo.util.Ut;
import com.KoreaIT.jjh.demo.vo.Article;
import com.KoreaIT.jjh.demo.vo.Board;
import com.KoreaIT.jjh.demo.vo.ResultData;
import com.KoreaIT.jjh.demo.vo.Rq;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private BoardService boardService;

	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.jsHitoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다!", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHitoryBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);

		return "usr/article/modify";
	}

	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHitoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다@", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHitoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}

		articleService.modifyArticle(id, title, body);

		return rq.jsReplace(Ut.f("%d번 글을 수정 했습니다", id), Ut.f("../article/detail?id=%d", id));
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHitoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHitoryBack("F-2", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 글을 삭제 했습니다", id), "../article/list");
	}

	@RequestMapping("/usr/article/write")
	public String showWrite(HttpServletRequest req, String title, String body) {

		return "usr/article/write";
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {
		Rq rq = (Rq) req.getAttribute("rq");

		if (Ut.empty(title)) {
			return rq.jsHitoryBack("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHitoryBack("F-2", "내용을 입력해주세요");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);

		int id = (int) writeArticleRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}

		return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다", id), replaceUri);
	}

	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model, int boardId) {
		Rq rq = (Rq) req.getAttribute("rq");

		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.jsHitoryBackOnView("없는 게시판이야");
		}

		int articlesCount = articleService.getArticlesCount(boardId);
		List<Article> articles = articleService.getForPrintArticles(boardId);

		model.addAttribute("board", board);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq) req.getAttribute("rq");
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);

		return "usr/article/detail";
	}

}
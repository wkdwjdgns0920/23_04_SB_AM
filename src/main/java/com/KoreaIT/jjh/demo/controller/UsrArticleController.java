package com.KoreaIT.jjh.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.jjh.demo.service.ArticleService;
import com.KoreaIT.jjh.demo.util.Ut;
import com.KoreaIT.jjh.demo.vo.Article;
import com.KoreaIT.jjh.demo.vo.ResultData;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;

	// 액션메서드
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Object doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		articleService.modifyArticle(id, title, body);

		return article;
	}

	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);
		if (article == null) {
			return id + "번 글은 존재하지 않습니다";
		}

		articleService.deleteArticle(id);

		return id + "번 글을 삭제했습니다";
	}

	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public Article doWrite(String title, String body) {
		int id = articleService.writeArticle(title, body);

		Article article = articleService.getArticle(id);

		return article;
	}

	@RequestMapping("/usr/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return articleService.articles();
	}

	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물은 존재하지 않습니다", id));
		}

		return ResultData.from("S-1", Ut.f("%d번 게시물입니다", id), article);
	}

}
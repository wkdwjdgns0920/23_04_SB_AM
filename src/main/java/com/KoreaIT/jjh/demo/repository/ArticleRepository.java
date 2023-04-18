package com.KoreaIT.jjh.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.KoreaIT.jjh.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public void writeArticle(int memberId, String title, String body);

	public List<Article> getArticles();

	public Article getArticle(int id);

	public void deleteArticle(int id);

	public void modifyArticle(int id, String title, String body);

	public int getLastInsertId();

}
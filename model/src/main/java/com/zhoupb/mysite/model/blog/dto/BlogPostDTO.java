package com.zhoupb.mysite.model.blog.dto;

public record BlogPostDTO(

		String title,
		
		String rawContent,
		
		String htmlContent,
		
		String summary,
		
		String[] keywords,
		
		Long categoryId) {}

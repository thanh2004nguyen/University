package com.aptech.group3.Controller.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.News;
import com.aptech.group3.service.NewsService;

@RestController
@RequestMapping("/api/public/news")
public class NewsApiController {

	@Autowired private NewsService newService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<News>> getAll(){
		
		List<News> data = newService.getTopNews(6);
		
		
		return ResponseEntity.ok().body(data);
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<News> getDetailNews( @PathVariable("id") Long newsId){
		News data= newService.findById(newsId).orElse(null);
		
		return ResponseEntity.ok().body(data);
	}
}

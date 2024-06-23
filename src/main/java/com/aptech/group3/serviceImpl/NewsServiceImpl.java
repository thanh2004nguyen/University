package com.aptech.group3.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aptech.group3.entity.News;
import com.aptech.group3.Repository.NewsRepository;
import com.aptech.group3.service.NewsService;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public Optional<News> findById(Long id) {
        return newsRepository.findById(id);
    }

    @Override
    public void save(News news) {
        newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }
    
    public List<News> getTopNews(int quantity){
    	return newsRepository.selectTopNew();
    }
    
 
}

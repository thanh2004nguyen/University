package com.aptech.group3.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.aptech.group3.entity.News;
import com.aptech.group3.service.NewsService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller

public class NewsController {
    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/admin/news/index")
    public String getAllNews(Model model) {
        List<News> newsList = newsService.findAll();
        model.addAttribute("newsList", newsList);
        return "news/index";
    }

    @GetMapping("/admin/news/create")
    public String createNewsForm(Model model) {
        model.addAttribute("news", new News());
        System.out.print("aaaaaaaaaaaaa"+model);
        return "news/create";
    }

    @PostMapping("/admin/news/save")
    public String saveNews(@ModelAttribute("news") News news, @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            String imagePath = saveImageFile(imageFile);
            news.setCreateDate(new Date());
            news.setImage(imagePath);
        }
        
        newsService.save(news);
        return "redirect:/admin/news/index";
    }

    @GetMapping("/admin/news/update/{id}")
    public String editNewsForm(@PathVariable("id") Long id, Model model) {
        Optional<News> news = newsService.findById(id);
        if (news.isPresent()) {
            model.addAttribute("news", news.get());
            return "news/update"; // Assuming you have an "update.html" Thymeleaf template
        } else {
            return "redirect:/news/index";
        }
    }

    @PostMapping("/admin/news/update/{id}")
    public String updateNews(@PathVariable("id") Long id,
                             @ModelAttribute("news") News updatedNews,
                             
                             @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            String imagePath = saveImageFile(imageFile);
            updatedNews.setImage(imagePath);
        }
        updatedNews.setId(id);
        
        newsService.save(updatedNews);
        return "redirect:/news/index";
    }

    private String saveImageFile(MultipartFile imageFile) {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = imageFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new IllegalStateException("Uploaded file must have a filename");
        }

        
        Path filePath = Paths.get(UPLOAD_DIR, originalFilename);

        try {
            Files.write(filePath, imageFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save the image file", e);
        }

        return   originalFilename;
    }

    @GetMapping("/admin/news/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
        return "redirect:/news/index";
    }
    @GetMapping("/web/news/details/{id}")
    public String getNewsDetails(@PathVariable("id") Long id, Model model) {
        Optional<News> news = newsService.findById(id);
        if (news.isPresent()) {
            model.addAttribute("news", news.get());
            return "news/details"; // Assuming you have a "details.html" Thymeleaf template
        } else {
            return "redirect:/news/index"; // Redirect to index if news not found
        }
    }
}

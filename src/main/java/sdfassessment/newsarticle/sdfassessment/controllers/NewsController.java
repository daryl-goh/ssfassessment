package sdfassessment.newsarticle.sdfassessment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import sdfassessment.newsarticle.sdfassessment.models.News;
import sdfassessment.newsarticle.sdfassessment.services.NewsService;

@Controller
@RequestMapping(path = {"", "/"})
public class NewsController {

    @Autowired
    private NewsService newsSvc;

    @GetMapping
        private String newsDisplay(Model model
        ) {
            List<News> list = newsSvc.getArticles();
            model.addAttribute("list", list);

            
            return "index";
        }
    
    @PostMapping({"/articles"})
        private String newsData(@RequestBody MultiValueMap<String, String> form, Model model) {
            String name = form.getFirst("id");
            List<News> articles = newsSvc.saveArticles();
            model.addAttribute("articles", articles);
            return "index";
        }
    
    
}

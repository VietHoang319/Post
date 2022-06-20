package com.example.post.controller;

import com.example.post.model.Post;
import com.example.post.service.IPostService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    IPostService IPostService;

    @GetMapping
    public ModelAndView showHome(ModelAndView modelAndView ,@PageableDefault(value = 5) Pageable pageable) {
        Page<Post> posts = IPostService.findAll(pageable);
        modelAndView = new ModelAndView("index");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate(ModelAndView modelAndView) {
        modelAndView = new ModelAndView("/post/create");
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(ModelAndView modelAndView, Post post) {
        post.setCreateAt(LocalDateTime.now());
        post.setLikes(0L);
        IPostService.save(post);
        modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(ModelAndView modelAndView, @PathVariable Long id) {
        modelAndView = new ModelAndView("/post/edit");
        modelAndView.addObject("post", IPostService.findById(id).get());
        return modelAndView;
    }

    @PostMapping("/edit/{id}")
    public ModelAndView edit(ModelAndView modelAndView, Post post, @PathVariable Long id) {
        Post oldPost = IPostService.findById(id).get();
        post.setLikes(oldPost.getLikes());
        post.setCreateAt(oldPost.getCreateAt());
        IPostService.save(post);
        modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(ModelAndView modelAndView, @RequestParam String title, @RequestParam String dateFrom, @RequestParam String dateTo) {
        if ((dateFrom.equals("") && dateTo.equals(""))) {
            dateFrom = "1900-01-01T00:00:00";
            dateTo = String.valueOf(LocalDateTime.now());
        }
        Iterable<Post> posts = IPostService.findByTitle('%' + title + '%', LocalDateTime.parse(dateFrom), LocalDateTime.parse(dateTo));
        modelAndView = new ModelAndView("index");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(ModelAndView modelAndView, @PathVariable Long id) {
        IPostService.remove(id);
        modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    @GetMapping("/like/{id}")
    public ModelAndView like(ModelAndView modelAndView, @PathVariable Long id) {
        Post post = IPostService.findById(id).get();
        post.setLikes(post.getLikes() + 1);
        IPostService.save(post);
        modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    @GetMapping("/orderbylikes")
    public ModelAndView orderByLikes(ModelAndView modelAndView) {
        Iterable<Post> posts = IPostService.findAllByOrOrderByLikes();
        modelAndView = new ModelAndView("index");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }

    @GetMapping("/findpostnewest")
    public ModelAndView findTop4PostNewest(ModelAndView modelAndView) {
        Iterable<Post> posts = IPostService.findTopByCreateAt();
        modelAndView = new ModelAndView("index");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }
}

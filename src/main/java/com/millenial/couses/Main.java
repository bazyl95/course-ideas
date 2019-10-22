package com.millenial.couses;

import com.millenial.couses.model.CourseIdea;
import com.millenial.couses.model.CourseIdeaDAO;
import com.millenial.couses.model.SimpleCourseIdeaDAO;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        CourseIdeaDAO dao = new SimpleCourseIdeaDAO();
        before((req, res) -> {
            if (req.cookie("username") != null) {
                req.attribute("username", req.cookie("username"));
            }
        });
        before("/ideas", (req, res) -> {
            //TODO:csd - Send message about redirect...somehow.
            if (req.attribute("username") == null) {
                res.redirect("/");
                halt();
            }
        });
        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.attribute("username"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sign-in", (req, res) -> {
            res.redirect("/");
            return null;
        });

        post("/sign-in", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            String username = req.queryParams("username");
            if (!username.equals("")) {
                res.cookie("username", username);
                model.put("username", username);
            }
            res.redirect("/");
            return null;
        });

        get("/ideas", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("ideas", dao.findAll());
            return new ModelAndView(model, "ideas.hbs");
        }, new HandlebarsTemplateEngine());

        post("/ideas", (req, res) -> {
            String title = req.queryParams("title");
            CourseIdea courseIdea = new CourseIdea(title, req.attribute("username"));
            dao.add(courseIdea);
            res.redirect("/ideas");
            return null;
        });

        post("/ideas/:slug/vote", (req, res) -> {
            CourseIdea idea = dao.findBySlug(req.params("slug"));
            idea.addVoter(req.attribute("username"));
            res.redirect("/ideas");
            return null;
        });

        /*
        Add a new page that responds to /ideas/:slug/. The controller should get the model by the slug passed in the url and pass it as the model for the template created in step 2.
        Add a new template for the idea detail page. Make it inherit from our base template.
        The content of the new idea detail page should list everyone who voted. You might need a new keyword.
        Add a form that allows voting for this specific idea. Route it to the existing vote route.
        */
        get("/ideas/:slug/", (req, res) -> {
            //
        });
    }
}

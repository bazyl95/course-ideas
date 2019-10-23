package com.millenial.couses.model;

import com.github.slugify.Slugify;

import java.util.*;

public class CourseIdea {
    private String slug;
    private String title;
    private String creator;
    private Set<String> voters;

    public CourseIdea(String title, String creator) {
        voters = new HashSet<>();
        this.title = title;
        this.creator = creator;
        Slugify slugify = new Slugify();
        slug = slugify.slugify(title);
    }

    public String getTitle() {
        return title;
    }

    public String getCreator() {
        return creator;
    }

    public String getSlug() {
        return slug;
    }

    public boolean addVoter(String voterUsername) {
        return voters.add(voterUsername);
    }

    public int getVoteCount() {
        return voters.size();
    }

    public List<Object> getVoters() {
        return new ArrayList<>(voters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseIdea that = (CourseIdea) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(creator, that.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, creator);
    }
}

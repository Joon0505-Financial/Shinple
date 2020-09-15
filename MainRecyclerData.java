package com.example.shinple;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainRecyclerData {

    private String title;
    private String content;
    private String image_thumb;
    private String course_id;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb, ImageView img) {
        this.image_thumb = image_thumb;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

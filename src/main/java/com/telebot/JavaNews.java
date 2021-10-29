package com.telebot;

import lombok.Data;

@Data
class JavaNews {
    private String title;
    private String href;

    public JavaNews(String title, String href) {
        this.title = title;
        this.href = href;
    }

    @Override
    public String toString() {
        return "parsing(" +
                "title='"+title +'\''+
                ",url='"+href+'\''
                +')';
    }
}
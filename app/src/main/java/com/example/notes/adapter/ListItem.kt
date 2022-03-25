package com.example.notes.adapter

class ListItem {
    private var title: String? = null
    private var content: String? = null

    fun getTitle(): String? {
        return title
    }

    fun getContent(): String? {
        return content
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun setContent(content: String?) {
        this.content = content
    }

}


package com.example.fyp_reminder;

public class ReminderEvent
{
    private String title, description, timeline, id;

    public ReminderEvent (){}

    public ReminderEvent(String title, String description, String timeline, String id)
    {
        this.title = title;
        this.description = description;
        this.timeline = timeline;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeline() {
        return timeline;
    }

    public void setTimeline(String timeline) {
        this.timeline = timeline;
    }
}

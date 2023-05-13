package client.Models;


public class Message {
    private String content;
    private String author;
    private boolean alignLeft;

    public Message(String content, boolean alignLeft) {
        this.content = content;
        this.alignLeft = alignLeft;
    }
    public Message(String content, String author, boolean alignLeft) {
        this.content = content;
        this.alignLeft = alignLeft;
        this.setAuthor(author);
    }

    public String getContent() {
        return content;
    }

    public boolean isAlignLeft() {
        return alignLeft;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

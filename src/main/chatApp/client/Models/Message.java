package client.Models;


import config.ServerConfig;

public class Message {
    private String content;
    private String author;
    private boolean alignLeft;

    public Message(String content, boolean alignLeft) {
        this.setContent(content);
        this.setAlignLeft(alignLeft);
    }

    public Message(String content, String author, boolean alignLeft) {
        this.setContent(content);
        this.setAlignLeft(alignLeft);
        this.setAuthor(author);
    }
    public Message() {
        this.setContent("");
        this.setAlignLeft(true);
        this.setAuthor("");
    }

    public void setAlignLeft(boolean b) {
        this.alignLeft = b;
    }

    private void setContent(String s) {
        this.content = s;
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


    public static Message getMessageFromString(String msg){
        String[] arr = msg.split(ServerConfig.Separator, 2);
        Message message = new Message();
        if (arr.length == 2){
            message.setAuthor(arr[0]);
            message.setContent(arr[1]);
        }else if (arr.length > 0){
            message.setContent(arr[0]);
        }
        return message;
    }
}

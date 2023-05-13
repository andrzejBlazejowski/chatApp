package client.Views.Components;

import client.Models.Message;

import javax.swing.*;
import java.awt.*;

public class MessageListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        Message message = (Message) value;

        if (message.isAlignLeft()) {
            setHorizontalAlignment(SwingConstants.LEFT);
        } else {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }

        String text = (!message.getAuthor().isEmpty()? message.getAuthor() + " : " : "") + message.getContent() ;
        setText(text);

        return this;
    }
}
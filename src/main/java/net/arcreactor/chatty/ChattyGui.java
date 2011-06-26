package net.arcreactor.chatty;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 6/18/11
 * Time: 10:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChattyGui extends JFrame{

    public ChattyGui(){
        buildUi();
    }

    public void buildUi() {
        this.add(new JTextArea(10,10));
        this.setSize(250,250);
    }

}

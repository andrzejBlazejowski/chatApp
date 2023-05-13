package client.Views;

import client.Listeners.LoginActionListener;
import client.Listeners.LoginChangeActionListener;
import client.Listeners.RegisterActionListener;
import client.Listeners.SendMessageActionListener;
import client.Models.Message;
import client.Views.Components.MessageListCellRenderer;
import client.Views.UserViews.ChangeLogin;
import client.Views.UserViews.Login;
import client.Views.UserViews.Register;
import server.Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author andrz
 */
public class ChatView extends javax.swing.JFrame {
    private DefaultListModel<Message> messageListModel;
    private User user;
    private String textInput;
    private LoginActionListener loginActionListener;
    private LoginChangeActionListener loginChangeActionListener;
    private RegisterActionListener registerActionListener;
    private SendMessageActionListener sendMessageActionListener;


    /**
     * Creates new form chatView
     */
    public ChatView() {
        initComponents();
        user = new User("", "");
        messageListModel = new DefaultListModel<Message>();
        MessageWrapper.setModel(messageListModel);

        MessageWrapper.setCellRenderer(new MessageListCellRenderer());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        MessageTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MessageWrapper = new javax.swing.JList<>();

        MessageTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = MessageTextField.getText();
                SendMessageActionListener listener = getSendMessageActionListener();
                if (listener != null) {
                    listener.onSendMessageActionListener(msg);
                }
                MessageTextField.setText("");
            }
        });
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat App");

        jButton1.setText("Logowanie");
        jButton1.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleLoginPress(evt);
            }
        });

        jButton2.setText("Zmień Login");
        jButton2.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleChangeLoginPress(evt);
            }
        });

        jButton3.setText("Rejestracja");
        jButton3.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleRegisterPress(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(117, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jScrollPane1.setViewportView(MessageWrapper);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MessageTextField, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MessageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void handleRegisterPress(ActionEvent evt) {
        Register window = new Register(getUser());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleRegisterUserChange();
            }
        });
        window.setVisible(true);
    }

    private void handleRegisterUserChange() {
        RegisterActionListener listener = getRegisterActionListener();
        if (listener != null) {
            listener.onRegisterActionListener(getUser());
        }
    }

    private void handleChangeLoginPress(ActionEvent evt) {
        ChangeLogin window = new ChangeLogin(getUser());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleChangeLoginUserChange();
            }
        });
        window.setVisible(true);
    }

    private void handleChangeLoginUserChange() {
        LoginChangeActionListener listener = getLoginChangeActionListener();
        if (listener != null) {
            listener.onLoginChangeActionListener(getUser());
        }
    }

    private void handleLoginPress(ActionEvent evt) {
        Login window = new Login(getUser());
        //window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            System.out.println("====================================");
            handleLoginUserChange();
            }
        });
        window.setVisible(true);
    }

    private void handleLoginUserChange() {
        LoginActionListener listener = getLoginActionListener();
        if (listener != null) {
            listener.onLoginActionListener(getUser());
        }
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<Message> MessageWrapper;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField MessageTextField;

    public String getTextInput() {
        return textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DefaultListModel<Message> getMessageListModel() {
        return messageListModel;
    }

    public void setNewMessage(Message msg) {
        messageListModel.addElement(msg);
        MessageWrapper.repaint();
        MessageWrapper.ensureIndexIsVisible(messageListModel.size() - 1);
    }

    public void setLoginActionListener(LoginActionListener loginActionListener) {
        this.loginActionListener = loginActionListener;
    }

    public void setLoginChangeActionListener(LoginChangeActionListener loginChangeActionListener) {
        this.loginChangeActionListener = loginChangeActionListener;
    }

    public void setRegisterActionListener(RegisterActionListener registerActionListener) {
        this.registerActionListener = registerActionListener;
    }

    public void setSendMessageActionListener(SendMessageActionListener sendMessageActionListener) {
        this.sendMessageActionListener = sendMessageActionListener;
    }

    public LoginActionListener getLoginActionListener() {
        return loginActionListener;
    }

    public LoginChangeActionListener getLoginChangeActionListener() {
        return loginChangeActionListener;
    }

    public RegisterActionListener getRegisterActionListener() {
        return registerActionListener;
    }

    public SendMessageActionListener getSendMessageActionListener() {
        return sendMessageActionListener;
    }
    // End of variables declaration//GEN-END:variables
}

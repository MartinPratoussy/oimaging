/*******************************************************************************
 * JMMC project ( http://www.jmmc.fr ) - Copyright (C) CNRS.
 ******************************************************************************/
package fr.jmmc.oimaging.gui;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author martin
 */
public class TableEditor extends javax.swing.JFrame {

    private final List<String> availableKeywords = new ArrayList<>();

    /**
     * Creates new form TableEditor
     */
    public TableEditor() {
        initComponents();
    }

    public TableEditor(List<String> availableKeywordsKeywords) {
        initComponents();

        this.availableKeywords.addAll(availableKeywordsKeywords);
        for (String keyword : this.availableKeywords) {
            listAvailable.add(keyword);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jLabelAvailable = new javax.swing.JLabel();
        jLabelDisplayed = new javax.swing.JLabel();
        jButtonCancel = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jPanelAvailable = new javax.swing.JPanel();
        jScrollPaneAvailable = new javax.swing.JScrollPane();
        listAvailable = new java.awt.List();
        jPanelDisplayed = new javax.swing.JPanel();
        jScrollPaneDisplayed = new javax.swing.JScrollPane();
        listDisplayed = new java.awt.List();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        getContentPane().add(jButtonAdd, gridBagConstraints);

        jButtonRemove.setText("Remove");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        getContentPane().add(jButtonRemove, gridBagConstraints);

        jLabelAvailable.setText("Available keywords");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jLabelAvailable, gridBagConstraints);

        jLabelDisplayed.setText("Keywords displayed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        getContentPane().add(jLabelDisplayed, gridBagConstraints);

        jButtonCancel.setText("Cancel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jButtonCancel, gridBagConstraints);

        jButtonOk.setText("Ok");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        getContentPane().add(jButtonOk, gridBagConstraints);

        jPanelAvailable.setLayout(new java.awt.GridBagLayout());

        jScrollPaneAvailable.setViewportView(listAvailable);

        jPanelAvailable.add(jScrollPaneAvailable, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        getContentPane().add(jPanelAvailable, gridBagConstraints);

        jPanelDisplayed.setLayout(new java.awt.GridBagLayout());

        jScrollPaneDisplayed.setViewportView(listDisplayed);

        jPanelDisplayed.add(jScrollPaneDisplayed, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        getContentPane().add(jPanelDisplayed, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAddActionPerformed

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
            java.util.logging.Logger.getLogger(TableEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TableEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TableEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TableEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TableEditor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JLabel jLabelAvailable;
    private javax.swing.JLabel jLabelDisplayed;
    private javax.swing.JPanel jPanelAvailable;
    private javax.swing.JPanel jPanelDisplayed;
    private javax.swing.JScrollPane jScrollPaneAvailable;
    private javax.swing.JScrollPane jScrollPaneDisplayed;
    private java.awt.List listAvailable;
    private java.awt.List listDisplayed;
    // End of variables declaration//GEN-END:variables
}

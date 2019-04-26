/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Crawler.java
 *
 * Created on Oct 22, 2009, 7:15:06 PM
 */

package searchengine.main;

import java.awt.BorderLayout;  
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import searchengine.spider.CrawlerDriver;

/**
 *
 */
public class CrawlerInterface extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Creates new form Crawler */
	
    public CrawlerInterface() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

    	c_panel = new JPanel();
    	jLabel1 = new javax.swing.JLabel();
        urlbox = new javax.swing.JTextField("",30);
        ds = new javax.swing.JComboBox();
        limit = new javax.swing.JTextField("",10);
        jLabel2 = new javax.swing.JLabel();
        crawl = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        panel1 = new javax.swing.JPanel();
        cen_panel = new javax.swing.JPanel();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        setTitle("Search Engine-CrawlerInterface");
        setAlwaysOnTop(true);
        
        cen_panel.add(urlbox);
        cen_panel.add(ds);
        c_panel.add(jLabel2);
        c_panel.add(limit);
        c_panel.add(crawl);
        
        
        jLabel1.setBackground(new Color(200, 23, 100));
        jLabel1.setFont(new java.awt.Font("Algerian", Font.BOLD, 20)); // NOI18N
        jLabel1.setForeground(new Color(200,200 , 200));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Mini Google Crawler");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 204), null));
        output.setText("        Results will be displayed here");
        urlbox.setText("Enter the url");
        panel1.setLayout(new BorderLayout());
        panel1.add(jLabel1, BorderLayout.PAGE_START);
        panel1.add(cen_panel, BorderLayout.CENTER);
        panel1.add(c_panel, BorderLayout.PAGE_END);
        panel1.setBackground(new Color(43, 55, 105));
        ds.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "List", "Hash", "Myhash", "BST", "AVL" }));

        jLabel2.setText("Enter the Max Limit");

        crawl.setText("Crawl");
        crawl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crawlActionPerformed(evt);
            }
        });

        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panel1,BorderLayout.PAGE_START);
        output.setColumns(200);
        output.setFont(new java.awt.Font("Monospaced", 1, 16)); // NOI18N
        output.setRows(29);
        jScrollPane1.setBackground(new Color(153, 55, 15));
        jScrollPane1.setViewportView(output);
        container.add(jScrollPane1,BorderLayout.PAGE_END);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crawlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crawlActionPerformed
        // TODO add your handling code here:

        String url = urlbox.getText();
        int i = ds.getSelectedIndex();
        String l=limit.getText();
        //int lim = Integer.parseInt(l);
        String []dsType={"list","hash","myhash","bst","avl"};
        String a[] = new String[4];
        a[0]=url;
        a[1]="foo.save2";
        a[2]=dsType[i];
        a[3]=l;
        CrawlerDriver cd= new CrawlerDriver();
        String[] s = cd.crawls(a);
        
        int ii=0;
        output.setText("Crawling for the web sites:\n");
        for(int k=0;k<s.length;k++)
        {
            output.append("\t"+(k+1)+"--->"+s[k]+"\n");
           
            ii++;
        }

        output.append("Crawling complited\t"+ii+"urls found");

    }//GEN-LAST:event_crawlActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrawlerInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton crawl;
    private javax.swing.JComboBox ds;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField limit;
    private javax.swing.JTextArea output;
    private javax.swing.JTextField urlbox;
    private JPanel panel1, cen_panel;
    private JPanel c_panel;
    private java.awt.Container container;
    // End of variables declaration//GEN-END:variables

}

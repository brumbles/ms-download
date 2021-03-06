/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddManga.java
 *
 * Created on 22.7.2010, 17:57:04
 */

package mangastreamdl.gui;

import mangastreamdl.business.*;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sirDarts
 */
public class AddManga extends JFrame implements MangaWindow
{

    Gui gui;

    /**
     * Creates new form AddManga
     */
    public AddManga()
    {
        init();
    }

    AddManga(Gui gui)
    {
        this();
        this.gui = gui;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        bg_sites = new javax.swing.ButtonGroup();
        jt_name = new javax.swing.JTextField();
        jt_dir = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jb_add = new javax.swing.JButton();
        jb_res = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jrb_site_MF = new javax.swing.JRadioButton();
        jrb_site_MS = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Name:");

        jLabel3.setText("Directory:");

        jb_add.setText("Add");
        jb_add.setActionCommand("ADD");

        jb_res.setText("Reset");
        jb_res.setActionCommand("RES");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Add Manga");

        bg_sites.add(jrb_site_MF);
        jrb_site_MF.setText("MangaFox");

        bg_sites.add(jrb_site_MS);
        jrb_site_MS.setSelected(true);
        jrb_site_MS.setText("MangaStream");
        jrb_site_MS.setNextFocusableComponent(jrb_site_MS);

        jLabel2.setText("Site:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(92, 92, 92)
                                                .addComponent(jLabel4))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jb_add, javax.swing.GroupLayout.PREFERRED_SIZE, 89,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(144, 144, 144)
                                                .addComponent(jb_res, javax.swing.GroupLayout.DEFAULT_SIZE, 89,
                                                        Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel3)
                                                        .addComponent(jLabel2))
                                                .addGap(75, 75, 75)
                                                .addGroup(layout.createParallelGroup(
                                                        javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jrb_site_MF)
                                                        .addComponent(jrb_site_MS)
                                                        .addComponent(jt_name, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                199, Short.MAX_VALUE)
                                                        .addComponent(jt_dir,
                                                                javax.swing.GroupLayout.Alignment.TRAILING))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jt_name, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jt_dir, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jrb_site_MS)
                                        .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jrb_site_MF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jb_add)
                                        .addComponent(jb_res))
                                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup  bg_sites;
    private javax.swing.JLabel       jLabel1;
    private javax.swing.JLabel       jLabel2;
    private javax.swing.JLabel       jLabel3;
    private javax.swing.JLabel       jLabel4;
    private javax.swing.JButton      jb_add;
    private javax.swing.JButton      jb_res;
    private javax.swing.JRadioButton jrb_site_MF;
    private javax.swing.JRadioButton jrb_site_MS;
    private javax.swing.JTextField   jt_dir;
    private javax.swing.JTextField   jt_name;
    // End of variables declaration//GEN-END:variables

    private void init()
    {
        initComponents();
        ActionListener al = new AddMangaButtonListener(this);
        jb_add.addActionListener(al);
        jb_res.addActionListener(al);
    }

    @Override
    public void buttonPressed(action action)
    {
        switch (action)
        {
            case OK:
                if (!allFilled())
                {
                    JOptionPane.showMessageDialog(this, "All fields have to be filled.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
                try
                {
                    Manga manga = new Manga(jt_name.getText(), jt_dir.getText(), "0");
                    if (jrb_site_MF.isSelected())
                    {
                        manga.setSite(Sites.MF);
                    }
                    else
                    {
                        manga.setSite(Sites.MS);
                    }
                    MangaParser parser = MangaParserFactory.getParser(manga.getSite());
                    if (!parser.checkManga(manga.getName()))
                    {
                        JOptionPane.showMessageDialog(this, "The manga " + manga.getName() +
                                " was not found. It was probably dropped by mangastream or you mistyped the name.",
                                "Manga not found.", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    gui.addManga(manga);
                    this.dispose();
                }
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(this, "The field number has to contain only numbers.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                catch (Exception e)
                {
                    Logger.getLogger(Downloader.class.getName()).log(Level.SEVERE, null, e);
                }
                break;
            case RESET:
                jt_dir.setText("");
                jt_name.setText("");
                break;
            default:
        }
    }

    private boolean allFilled()
    {
        return (jt_dir.getText().length() >= 1 && jt_name.getText().length() >= 1);
    }

}

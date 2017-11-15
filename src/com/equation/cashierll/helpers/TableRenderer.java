/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.equation.cashierll.helpers;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author Wellington
 */
public class TableRenderer {
    
    
    public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double... percentages){
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++){
            total += percentages[i];
        } 
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++){
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)(tablePreferredWidth * (percentages[i] / total)));
        }
    }
}

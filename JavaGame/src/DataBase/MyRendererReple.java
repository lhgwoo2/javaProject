package DataBase;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyRendererReple extends DefaultTableCellRenderer {
	   Color color;
	    int rowAtMouse;
	    int colAtMouse;
	         
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column) 
	    { 
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
	       
	                if(row==rowAtMouse &&column == colAtMouse){
	                	c.setBackground(color);
	                }else c.setBackground(new Color(255,255,255));
	       
	            return c;
	    } 
}

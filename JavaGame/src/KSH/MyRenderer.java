package KSH;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyRenderer extends DefaultTableCellRenderer  
{ 
    Color color;
    int rowAtMouse;
         
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column) 
    { 
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
            if (! table.isRowSelected(row)) //���� ���õ� ���� ������ �������� �ʰ� ���� ������ ��쿡�� �������� �����Ѵ�
            {
                if(row==rowAtMouse) c.setBackground(color);
                else c.setBackground(Color.WHITE);
            }
            return c;
    } 
}
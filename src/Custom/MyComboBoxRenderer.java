/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Custom;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Nham Ngo
 */
public class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
    private String _title;

  public MyComboBoxRenderer(String title) {
    _title = title;
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean hasFocus) {
    if (index == -1 && value == null)
      setText(_title);
    else
      setText(value.toString());
    return this;
  }
}

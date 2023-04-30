/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

/**
 *
 * @author Nham Ngo
 */
public class MyComboBox {
    Object value;
    Object Text;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getText() {
        return Text;
    }

    public void setText(Object Text) {
        this.Text = Text;
    }

    public MyComboBox(Object value, Object Text) {
        this.value = value;
        this.Text = Text;
    }

    public MyComboBox() {
    }

    @Override
    public String toString() {
        return (String) Text;
    }
    public int MaInt()
    {
        return Integer.parseInt(getValue().toString());
    }
}

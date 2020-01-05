import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class ColorChooser extends JFrame implements ActionListener {
	JButton b;
	Container c;
	private Color color;
	
	public ColorChooser() {
	}
	
	public ColorChooser(ActionEvent e) {
		actionPerformed(e);
	}
	public void actionPerformed(ActionEvent e) {
		Color initialcolor = Color.BLACK;
		color = JColorChooser.showDialog(this, "Select a color", initialcolor);
	}
	
	public Color getColor() {
		if (color == null)
			return Color.BLACK;
		else
			return color;
	}
}
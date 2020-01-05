import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

public class Triangle {

	private JFrame frame;
	private int recursion = 1;
	private JComboBox<Integer> recursionLevel;
	private JButton colorChooser;
	private Color color;
	private ColorChooser chooser;
	private Graphics g;
	private Point p1;
	private Point p2;
	private Point p3;
	private JPanel contentPane;
	private DrawingPanel drawingPanel;
	private JRadioButton overlay;
	private JRadioButton redraw;
	private boolean over = false;
	private boolean re = true;

	public Triangle() {
		makeFrame();
		int[] deminsion = getResolution();
		drawingPanel = new DrawingPanel(deminsion[0], deminsion[1]);
		g = drawingPanel.getGraphics();

		int triangleHeight = (int) Math.round(drawingPanel.getHeight() * Math.sqrt(3.0) / 2.0);
		p1 = new Point(0, triangleHeight);
		p2 = new Point((drawingPanel.getHeight() / 2), 0);
		p3 = new Point(drawingPanel.getHeight(), triangleHeight);
	}

	public static void main(String[] args) {
		Triangle t = new Triangle();
		t.draw(t.recursion, t.g, t.p1, t.p2, t.p3);
	}

	private void makeFrame() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = (JPanel) frame.getContentPane();
		
		overlay = new JRadioButton("overlay", false);
		redraw = new JRadioButton("redraw", true);
		ButtonGroup group = new ButtonGroup();
		group.add(overlay);
		group.add(redraw);
		
		colorChooser = new JButton("Choose color");
		chooser = new ColorChooser();
		colorChooser.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooser = new ColorChooser(e);
			}
		});
		
		Integer[] level = new Integer[] { 1,2,3,4, 5,6,7,8,9, 10, 15 };
		recursionLevel = new JComboBox<>(level);
		recursionLevel.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					recursion = (int) recursionLevel.getSelectedItem();
					if (redraw.isSelected())
						drawingPanel.clear();
					color = chooser.getColor();
					draw(recursion, g, p1, p2, p3);
				}
			}
		});

		contentPane.add(recursionLevel, BorderLayout.PAGE_START);
		contentPane.add(overlay, BorderLayout.EAST);
		contentPane.add(redraw, BorderLayout.WEST);
		contentPane.add(colorChooser, BorderLayout.PAGE_END);
		frame.pack();
	}

	public int[] getResolution() {
		int[] deminsion = new int[2];
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		deminsion[0] = gd.getDisplayMode().getWidth();
		deminsion[1] = gd.getDisplayMode().getHeight();
		return deminsion;
	}

	public void draw(int level, Graphics g, Point p1, Point p2, Point p3) {
		if (level == 1) {

			Polygon poly = new Polygon();
			poly.addPoint(p1.x, p1.y);
			poly.addPoint(p2.x, p2.y);
			poly.addPoint(p3.x, p3.y);
			g.setColor(color);
			g.fillPolygon(poly);
		} else {

			Point p4 = distance(p1, p2);
			Point p5 = distance(p2, p3);
			Point p6 = distance(p1, p3);

			draw(level - 1, g, p1, p4, p6);
			draw(level - 1, g, p4, p2, p5);
			draw(level - 1, g, p6, p5, p3);
		}
	}

	public Point distance(Point p1, Point p2) {
		return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
	}
}
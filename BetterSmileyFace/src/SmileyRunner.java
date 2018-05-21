import javax.swing.JFrame;

public class SmileyRunner extends JFrame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	public SmileyRunner() {
		super(":)");
		setVisible(true);
		setSize(WIDTH, HEIGHT);
		getContentPane().add(new SmileyPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SmileyRunner cat = new SmileyRunner();
	}

}
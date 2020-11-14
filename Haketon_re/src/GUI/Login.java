package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Character.Hero;
import Information.DB;
import Play.Maple;

public class Login extends JFrame implements ActionListener, KeyListener {
	
	/**
	 * 초기 배경 Y좌표
	 */
	private final int CHAR_Y = 392;
	
	/**
	 * ID입력 칸
	 */
	private JTextField field1;
	/**
	 * PASSWORD 입력 칸
	 */
	private JPasswordField field2;
	
	/**
	 * 로그인 버튼
	 */
	private JButton b1;
	/**
	 * 회원가입 버튼
	 */
	private JButton b2;
	
	/**
	 * 화면 가운데에 창을 띄우게 해주는 좌표 반환
	 * @param width 창의 너비
	 * @param height 창의 높이
	 * @return 화면 가운데에 창을 띄우게 해주는 좌표
	 */
	public static Point center(int width, int height) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screen = tk.getScreenSize();
		
		int xpos = (int) ((screen.getWidth()-width)/2);
		int ypos = (int) ((screen.getHeight()-height)/2);
		
		return new Point(xpos, ypos);
	}
	
	/**
	 * 로그인 창 생성 공간
	 */
	public Login() {
		init();
		view();
		setVisible(true);
	}
	
	
	/**
	 * 로그인 화면 설정(크기, 위치, 창 이름)
	 */
	private void init() {
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final int WIDTH = 1000;
		final int HEIGHT = 700;
		
		setLocation(center(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);
	}
	
	/**
	 * 로그인 화면 그래픽 설정
	 */
	private void view() {
		JPanel background = background();
		
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		background.add(p1);
		
		background.add(ID());
		background.add(PASSWORD());
		background.add(button());
		
		JScrollPane scrollPane = new JScrollPane(background);
		setContentPane(scrollPane);
	}

	/**
	 * 배경화면 그리기
	 * @return 배경화면이 그려진 JPanel
	 */
	private JPanel background() {
		JPanel back = new JPanel() {
			protected void paintComponent(java.awt.Graphics arg0) {
				Dimension d = getSize();
				ImageIcon icon = new ImageIcon("Image\\back.png");
				arg0.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
				
				setOpaque(false);
				super.paintComponent(arg0);
			}
		};
		
		back.setLayout(new GridLayout(4,1));
		
		return back;
	}
	
	/**
	 * ID 입력칸 그래픽설정
	 * @return ID 입력하는 JPanel
	 */
	private JPanel ID() {
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new GridLayout(2,1));
		
		JPanel p_u = new JPanel();
		p_u.setOpaque(false);
		p_u.setLayout(new GridLayout(2,1));
		
		p_u.add(write("ID"));
		
		p_u.add(ID_field());
		
		p.add(p_u);
		
		return p;
	}
	
	/**
	 * ID를 입력하는 칸
	 * @return ID를 입력하는 JTextField를 포함하는 JPanel
	 */
	private JPanel ID_field() { 
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new GridLayout(1,3));
		JLabel label1 = new JLabel();
		p.add(label1);
		field1 = new JTextField();
		field1.setFont(new Font("Default", Font.BOLD, 20));
		p.add(field1);
		JLabel label2 = new JLabel();
		p.add(label2);
		
		return p;
	}

	/**
	 * 패스워드 입력칸 그래픽 설정
	 * @return Password 입력하는 JPanel
	 */
	private JPanel PASSWORD() {
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new GridLayout(2,1));
		
		JPanel p_u = new JPanel();
		p_u.setOpaque(false);
		p_u.setLayout(new GridLayout(2,1));
		
		p_u.add(write("PASSWORD"));
		
		p_u.add(PW_field());
		
		p.add(p_u);
		
		return p;
	}
	
	/**
	 * 패스워드를 입력하는 칸
	 * @return Password를 입력하는 JPasswordField를 포함하는 JPanel
	 */
	private JPanel PW_field() { 
		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new GridLayout(1,3));
		JLabel label1 = new JLabel();
		p.add(label1);
		field2 = new JPasswordField();
		field2.setFont(new Font("Default", Font.BOLD, 20));
		field2.addKeyListener(this);
		p.add(field2);
		JLabel label2 = new JLabel();
		p.add(label2);
		
		return p;
	}
	
	/**
	 * ID와 비밀번호 입력 칸을 구분해주는 JPanel
	 * @param s ID 혹은 PASSWORD 중 하나만 입력값으로 들어감
	 * @return s에 입력된 문자를 포함하는 JPanel
	 */
	private JPanel write(String s) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1,3));
		p.setOpaque(false);
		JLabel label1 = new JLabel(s);
		label1.setHorizontalAlignment(JLabel.RIGHT);
		label1.setFont(new Font("Default", Font.BOLD, 20));
		label1.setForeground(Color.white);
		p.add(label1);
		JLabel label2 = new JLabel();
		p.add(label2);
		JLabel label3 = new JLabel();
		p.add(label3);
		
		return p;
	}
	
	/**
	 * 로그인버튼과 회원가입 버튼 생성
	 * @return 로그인 혹은 회원가입을 할 수 있게 하는 버튼이 달린 JPanel
	 */
	private JPanel button() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		p.setOpaque(false);
		
		b1 = new JButton("LOG IN");
		b1.setPreferredSize(new Dimension(250,70));
		
		b2 = new JButton("회원가입");
		b2.setPreferredSize(new Dimension(250,70));
		
		b1.addActionListener(this);
		b2.addActionListener(this);

		p.add(b1);
		p.add(b2);
		
		return p;
	}
	
	/**
	 * 로그인 시도 메서드
	 * 1. 실패하면 아이디와 비밀번호 입력 칸을 초기화시키고 커서를 아이디 JTextField로 이동시킴
	 * 2. 로그인은 성공하였으나, 해당 계정이 캐릭터를 생성하지 않았을 경우 캐릭터 생성 메서드를 실행
	 * 3. 아이디가 입력되지 않았을 경우 로그인을 시도하지 않는다
	 */
	private void info() {
		if(!field1.getText().equals("")) {
			String s = String.valueOf(field2.getPassword());
			int no = DB.execute(field1.getText(), s);
			
			if(no!=0) {
				Hero h = DB.execute(no);
				
				if(h!=null) {
					new Maple(h, no);
				}
				else {
					new Check(no);
				}
				setVisible(false);
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "아이디를 입력해주세요!");
		}
		field1.setText("");
		field2.setText("");
		field1.requestFocusInWindow();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			info();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		
		if(b==b1) {
			info();
		}
		else {
			new Sign();
		}
	}
}
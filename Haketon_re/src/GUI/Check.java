package GUI;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Character.Hero;
import Play.Maple;

/**
 * 회원가입은 했지만, 캐릭터를 생성하지 않았을 경우 캐릭터를 생성 창을 띄우는 클래스
 */
public class Check extends JFrame implements ActionListener {
	/**
	 * 유저 일련번호
	 */
	private int no;

	private JButton warrior = new JButton("전사");
	private JButton archor = new JButton("궁수");

	/**
	 * 처음 생성하는 캐릭터의 직업을 고르는 창을 띄움
	 * 
	 * @param no 해당 ID의 일련번호
	 */
	public Check(int no) {
		this.no = no;
		option();
		init();
		setVisible(true);
	}

	/**
	 * 캐릭터 생성 창의 설정(크기, 위치 등)
	 */
	private void option() {
		setTitle("직업 선택");
		setLayout(new GridLayout(1, 2));

		final int SMALL_WIDTH = 500;
		final int SMALL_HEIGHT = 350;

		Point tmp_p = Login.center(SMALL_WIDTH, SMALL_HEIGHT);
		setLocation(tmp_p);
		setSize(SMALL_WIDTH, SMALL_HEIGHT);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 여러 직종군의 버튼 추가
	 */
	private void init() {
		warrior.addActionListener(this);
		archor.addActionListener(this);

		add(warrior);
		add(archor);
	}

	/**
	 * 선택한 직업으로 캐릭터를 생성하고 게임 창으로 넘어감
	 * @param s 선택한 직업
	 */
	private void start(String s) {
		Hero h = new Hero(s);
		new Maple(h,no);
		setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton b_1 = (JButton) arg0.getSource();

		String s = b_1.getText().trim();

		start(s);
	}
}
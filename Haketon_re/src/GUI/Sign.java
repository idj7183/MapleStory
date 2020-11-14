package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Information.DB;

/**
 * 회원가입 창 생성 클래스
 */
public class Sign extends JFrame implements KeyListener, ActionListener {
	
	/**
	 * 아이디를 입력받을 공간
	 */
	private JTextField field_id = new JTextField();
	/**
	 * 패스워드를 입력받을 공간
	 */
	private JTextField field_pw = new JTextField();

	/**
	 * 비밀번호 조건을 제약시키는 패턴
	 */
	private final String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{6,20}$";

	/**
	 * 회원가입 창 생성
	 */
	public Sign() {
		setting();
		running();
		setVisible(true);
	}

	/**
	 * 회원가입 창 설정(크기, 이름, 위치)
	 */
	private void setting() {
		final int width = 500;
		final int height = 250;

		setTitle("회원가입");
		setLayout(new GridLayout(3, 1));

		setSize(width, height);
		setLocation(Login.center(width, height));
	}

	/**
	 * 회원가입 창 그래픽 설정
	 */
	private void running() {
		add(condition());

		add(center_pan());

		add(checking());
	}

	/**
	 * 비밀번호 제약을 설명
	 * @return 비밀번호 제약을 설명한 JLabel
	 */
	private JLabel condition() {
		JLabel label = new JLabel("대문자, 소문자, 숫자가 모두 포함된 6~20자의 비밀번호를 입력해주세요");
		label.setFont(new Font("맑은 바탕", Font.BOLD, 15));
		label.setHorizontalAlignment(JLabel.CENTER);

		return label;
	}

	/**
	 * 아이디와 비밀번호를 입력하고 엔터를 치면 적절한 아이디와 비밀번호인지 확인
	 * @return ID와 PW를 입력하는 JPanel.
	 */
	private JPanel center_pan() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		JPanel p_l = new JPanel();
		p_l.setLayout(new GridLayout(2, 1));
		p_l.setPreferredSize(new Dimension(150, 100));

		JLabel label1 = new JLabel("ID");
		JLabel label2 = new JLabel("Password");
		label1.setHorizontalAlignment(JLabel.CENTER);
		label2.setHorizontalAlignment(JLabel.CENTER);

		p_l.add(label1);
		p_l.add(label2);

		JPanel p_r = new JPanel();
		p_r.setLayout(new GridLayout(2, 1));
		field_pw.addKeyListener(this);
		p_r.add(field_id);
		p_r.add(field_pw);

		p.add(p_l, BorderLayout.WEST);
		p.add(p_r, BorderLayout.CENTER);
		p.add(new JPanel(), BorderLayout.EAST);

		return p;
	}

	/**
	 * 조회 버튼을 누르면 적절한 아이디와 비밀번호인지 확인
	 * @return 조회 버튼이 포함된 JPanel
	 */
	private JPanel checking() {
		JPanel p = new JPanel();

		JButton b = new JButton("조회");
		b.addActionListener(this);
		b.setPreferredSize(new Dimension(100, 40));

		p.add(b);

		return p;
	}

	/**
	 * 적절한 아이디와 비밀번호인지 확인
	 */
	private void id_pw() {
		String s1 = field_id.getText().trim();
		String s2 = field_pw.getText().trim();

		if (!s1.equals("")) {
			if (!s2.matches(pattern)) {
				JOptionPane.showMessageDialog(this, "적절한 비밀번호 형식을 입력해주세요");
			} else {
				setVisible(false);
				DB.execute(s1, s2, true);
			}
		} else {
			JOptionPane.showMessageDialog(this, "ID를 입력해주세요");
		}
		field_id.setText("");
		field_pw.setText("");
		field_id.requestFocusInWindow();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		id_pw();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			id_pw();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}

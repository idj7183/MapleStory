package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Character.Hero;
import Play.Maple;

/**
 * 무기 강화 창 생성 클래스
 */
public class Reinforce extends JFrame implements ActionListener {
	
	/**
	 * 강화 버튼
	 */
	private JButton rein;
	/**
	 * 게임으로 돌아가는 버튼
	 */
	private JButton exit;
	/**
	 * 강화 이전 무기 레벨
	 */
	private JButton prev_we;
	/**
	 * 강화 후 무기 레벨
	 */
	private JButton we;
	/**
	 * 무기를 가지고 있는 캐릭터
	 */
	private Hero hero;
	/**
	 * stop = false가 되는 순간 강화 창이 닫힘
	 */
	private static boolean stop = true;
	
	public static void clear() {
		stop = true;
	}
	
	public Reinforce(Hero hero) {
		this.hero = hero;
		init();
		setVisible(true);
		
		while(stop) {
			System.out.println(stop);
		}
	}
	/**
	 * 강화 창 그래픽 설정
	 */
	private void init() {
		setLayout(new BorderLayout());

		add(level(), BorderLayout.CENTER);

		add(button(), BorderLayout.SOUTH);

		setLocationRelativeTo(Maple.loc());
	}
	
	/**
	 * 무기 레벨 표현 공간
	 * @return 무기 레벨을 표현하는 JPanel
	 */
	private JPanel level() {
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 2));

		setSize(500, 200);

		int t_level = hero.getWeapon_level();
		prev_we = new JButton(t_level + "");
		we = new JButton((t_level + 1) + "");

		prev_we.setEnabled(false);
		we.setEnabled(false);
		prev_we.setFont(new Font("Default", Font.BOLD, 50));
		we.setFont(new Font("Default", Font.BOLD, 50));

		p1.add(prev_we);
		p1.add(we);
		
		return p1;
	}
	
	/**
	 * 강화 시도, 혹은 게임으로 돌아가기
	 * @return 강화 시도 버튼과 게임으로 돌아가기 버튼이 포함된 JPanel
	 */
	private JPanel button() {
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.setPreferredSize(new Dimension(500, 50));

		rein = new JButton("강화");
		exit = new JButton("게임으로 돌아가기");
		rein.setPreferredSize(new Dimension(300, 40));

		rein.addActionListener(this);
		exit.addActionListener(this);

		p2.add(rein);
		p2.add(exit);
		
		return p2;
	}
	
	/**
	 * 강화버튼을 눌러 강화를 시도하고, 강화성공이면 스펙 상승, 실패하면 실패 창 생성
	 */
	private void rein() {
		int t_level = hero.getWeapon_level();
		double percentage = 1.0 / t_level;

		if (Math.random() < percentage) {
			t_level++;
			
			prev_we.setText(t_level + "");
			we.setText((t_level + 1) + "");
			
			hero.setWeapon_level();
			hero.setAp((int) (10 / percentage));
		} else {
			JOptionPane.showMessageDialog(this, "실패");
		}
	}
	
	/**
	 * 게임으로 돌아가는 버튼
	 */
	private void exit() {
		setVisible(false);
		stop = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton tmp = (JButton) arg0.getSource();
		if (tmp == rein) {
			rein();
		} else {
			exit();
		}
	}
}

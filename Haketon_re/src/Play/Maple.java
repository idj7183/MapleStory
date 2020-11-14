package Play;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Character.Hero;
import Character.Mop;
import Character.Arrow;
import GUI.Exit;
import GUI.Login;
import GUI.Reinforce;

/**
 * 게임 동작 및 그래픽 통합 클래스
 *
 */
public class Maple extends JFrame implements Runnable, KeyListener {
	/**
	 * 캐릭터 정보
	 */
	private static Hero hero;
	
	/**
	 * 유저 일련번호
	 */
	private int no;

	private final int BACK_LOWER_Y = -100;
	private final int BACK_LOWER_X = -444;
	private final int BACK_UPPER_X = 0;

	private final int CHAR_LOWER_X = 10;
	private final int CHAR_UPPER_X = 690;
	private final int CHAR_UPPER_Y = 392;

	/**
	 * 배경화면 x좌표
	 */
	private int bx;
	/**
	 * 배경화면 y좌표
	 */
	private int by = -105;

	private final int WIDTH = 800;
	private final int HEIGHT = 600;

	Toolkit tk = Toolkit.getDefaultToolkit();
	private java.awt.Image left_archer = tk.getImage("Image\\Left_Archer.png");
	private java.awt.Image right_archer = tk.getImage("Image\\\\Right_Archer.png");
	private java.awt.Image background_img = tk.getImage("Image\\\\Background.png");
	private java.awt.Image shooting = tk.getImage("Image\\\\right_arrow.png");
	private java.awt.Image left_shooting = tk.getImage("Image\\left_arrow.png");
	private java.awt.Image enemy = tk.getImage("Image\\enemy.png");

	/**
	 * false인 경우 : 오른쪽 보고 있음 true인 경우 : 왼쪽 보고 있음
	 */
	private boolean turn = false;
	/**
	 * 오른쪽을 보고 쏜 화살 공격들
	 */
	private ArrayList<Arrow> right_arrow = new ArrayList<Arrow>();
	/**
	 * 왼쪽을 보고 쏜 화살 공격들
	 */
	private ArrayList<Arrow> left_arrow = new ArrayList<Arrow>();
	/**
	 * 살아있는 몬스터들
	 */

	private ArrayList<Mop> m = new ArrayList<Mop>();
	/**
	 * 몬스터 공격 쓰레드
	 */
	private ArrayList<Thread> th_list = new ArrayList<Thread>();

	/**
	 * true : 왼쪽으로 감
	 */
	private boolean left = false;
	/**
	 * true : 오른쪽으로 감
	 */
	private boolean right = false;
	/**
	 * true : 화살 ArrayList에 공격이 추가됨
	 */
	private boolean attack = false;
	/**
	 * ture : jump함
	 */
	private boolean jump = false;
	/**
	 * true : jump를 시행한 후 다시 땅으로 돌아옴
	 */
	private boolean gravity = false;

	/**
	 * true : esc키를 누르거나 X창을 누른 경우
	 */
	public static boolean esc = false;
	/**
	 * true : 강화버튼 키(A)를 누른 경우
	 */
	public static boolean reinforce = false;

	/**
	 * 써주십쇼
	 */
	private boolean visible = false;
	/**
	 * 써주십쇼
	 */
	private boolean one_time = true;

	/**
	 * 공격속도 제한 때 사용되는 멤버변수 1
	 */
	private int prev = -10;
	/**
	 * 공격속도 제한 때 사용되는 멤버변수 2
	 */
	private int cnt = 0;
	/**
	 * 공격속도
	 */
	private int firespeed = 10;

	/**
	 * 캐릭터가 점프할 때 몬스터도 점프하면 안되므로, 이를 처리하기 위한 멤버변수
	 */
	private int mop = 0;
	/**
	 * 부드러운 점프를 위해 사용한 멤버 변수
	 */
	private int count = 0;
	/**
	 * 처음 소환할 몬스터 숫자
	 */
	private int monster_num = 5;

	java.awt.Image buffImage;
	Graphics buffg;
	
	/**
	 * 강화창을 화면 중앙에 띄우기 위한 멤버 변수
	 */
	private static Maple maple = null;
	
	/**
	 * 현재 메이플 창의 위치 반환
	 * @return 현재 메이플 창
	 */
	public static JFrame loc() {
		return maple;
	}

	/**
	 * DB에서 받아온 캐릭터 정보를 게임에 적용
	 * 
	 * @param hero DB에 저장된 캐릭터 정보
	 * @param no   유저의 일련번호
	 */
	public Maple(Hero hero, int no) {
		maple = this;
		this.bx = hero.getBx();
		Maple.hero = hero;
		this.no = no;
		init();
		start();

		setVisible(true);
	}

	/**
	 * 띄워질 창 설정(크기, 이름, 위치) - X키를 눌렀을 때 바로 종료되지 않고 정말 종료할 것인지 물어보는 과정 추가
	 */
	private void init() {
		setTitle("메이플스토리");

		addKeyListener(this);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				visible = true;
				esc = true;
			}

		});
		Point tmp_p = Login.center(WIDTH, HEIGHT);
		setLocation(tmp_p);
		setSize(WIDTH, HEIGHT);

		maple = this;
		setResizable(false);
	}

	/**
	 * 쓰레드를 실행시키는 메서드
	 */
	private void start() {
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	public void run() {
		while (true) {
			if(reinforce) {
				rein();
			}
			System.out.println(reinforce);
			
			if(visible) {
				setVisible(true);
			}
			
			if(esc) {
				out();
			}
			
			System.out.println(Mop.once);
			if (!Mop.once) {
				left = false;
				right = false;
				attack = false;
				continue;
			}

			if (m.isEmpty()) {
				add();
			}

			KeyProcess();
			speed();
			re();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
			cnt = (cnt + 1) % 100000;
		}
	}
	
	/**
	 * 강화버튼을 눌렀을 때 강화 창이 생성됨
	 */
	private void rein() {
		new Reinforce(hero);
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reinforce.clear();
		reinforce = false;
		left = false;
		right = false;
		attack = false;
	}
	
	/**
	 * ESC버튼이나 X버튼을 눌렀을 때 종료 의사를 물어보는 창 띄우기
	 */
	private void out() {
		left = false;
		right = false;
		attack = false;
		
		Exit.exit(hero,no,bx);
		
		esc = false;
		visible = false;
	}
	
	/**
	 * 몬스터가 모두 죽거나 게임을 시작한 경우 몬스터 레벨을 입력받고 몬스터를 생성
	 * 이전 공격들과 이동 명령들은 모두  초기화됨
	 */
	private void add() {
		left_arrow.clear();
		right_arrow.clear();
		left = false;
		right = false;
		attack = false;
		
		int tmp = CHAR_UPPER_X - CHAR_LOWER_X;
		int level = 0;

		while (true) {
			try {
				String s = JOptionPane.showInputDialog(this, "원하는 적의 레벨 입력").trim();
				level = Integer.parseInt(s);
				break;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "숫자를 입력해주세요!");
			}
		}

		for (int i = 0; i < monster_num; i++) {
			int a = (int) ((Math.random() * tmp) + CHAR_LOWER_X);

			Point f_pos = new Point(a, CHAR_UPPER_Y);

			Mop tmp_m = new Mop(level);
			tmp_m.setPos(f_pos);

			m.add(tmp_m);
		}

		Collections.sort(m);

		for (int i = 0; i < monster_num; i++) {
			Thread tmp_th = new Thread(m.get(i));
			th_list.add(tmp_th);
			tmp_th.start();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 키보드 입력에 따른 캐릭터 움직임 시행 1. 위 : 점프 후 다시 지상으로 돌아오기(이단 점프 안됨) 2. 왼쪽, 오른쪽 : 해당 방향으로
	 * 이동
	 */
	private void KeyProcess() {
		Point tmp = hero.getPos();

		if (jump) {
			tmp.y -= 10;
			count++;
			if (count >= 10) {
				jump = false;
				gravity = true;
				count = 0;
			}
			by += 2;
			mop = mop + 2;
		}

		if (left) {
			if (tmp.x > CHAR_LOWER_X) {
				tmp.x -= 4;
			}
			if (bx < BACK_UPPER_X) {
				bx += 2;
				for (int i = 0; i < m.size(); i++) {
					m.get(i).getPos().x += 4;
				}
			}
			turn = true;
		}
		if (right) {
			if (tmp.x < CHAR_UPPER_X) {
				tmp.x += 4;
			}
			if (bx > BACK_LOWER_X) {
				bx -= 2;
				for (int i = 0; i < m.size(); i++) {
					m.get(i).getPos().x -= 4;
				}
			}
			turn = left;
		}
	}

	/**
	 * 공격속도보다 빠르게 공격 명령이 입력되면 해당 입력은 무시하고 공격속도보다 긴 시간 후 공격 명령이 입력되면 공격한다.
	 */
	private void speed() {
		if (attack && cnt > (prev + firespeed) % 100000) {
			Point tmp = hero.getPos();
			prev = cnt;
			if (!turn) {
				right_arrow.add(new Arrow(tmp.x + 100, tmp.y + 15));
			} else {
				left_arrow.add(new Arrow(tmp.x - 100, tmp.y + 15));
			}
		}
	}

	/**
	 * 점프를 했을 때 다시 지상으로 돌아오는데, 이를 구현해주는 메서드
	 */
	private void re() {
		if (gravity) {
			if (by > BACK_LOWER_Y) {
				by -= 2;
				mop = mop - 2;
			}
			if (hero.getPos().y < CHAR_UPPER_Y) {
				hero.getPos().y += 10;
			} else {
				gravity = false;
				jump = false;
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		buffImage = createImage(WIDTH, HEIGHT);
		buffg = buffImage.getGraphics();

		update(g);
	}

	@Override
	public void update(Graphics g) {
		Draw_Background();
		Draw_Char();
		Draw_enemy();

		Draw_attack();

		record();

		g.drawImage(buffImage, 0, 0, this);
	}

	/**
	 * 배경화면 그리는 메서드
	 */
	private void Draw_Background() {
		buffg.clearRect(0, 0, WIDTH, HEIGHT);

		buffg.drawImage(background_img, bx, by, this);
	}

	/**
	 * 캐릭터 그리는 메서드
	 */
	private void Draw_Char() {
		if (turn) {
			buffg.drawImage(left_archer, hero.getPos().x, hero.getPos().y, this);
		} else {
			buffg.drawImage(right_archer, hero.getPos().x, hero.getPos().y, this);
		}
	}

	/**
	 * 몬스터 그리는 메서드
	 */
	private void Draw_enemy() {
		for (int i = 0; i < m.size(); i++) {
			Mop mon = m.get(i);
			Point pos = mon.getPos();
			buffg.drawImage(enemy, pos.x, pos.y + mop, this);
		}
	}

	/**
	 * 게임창 좌상단에 캐릭터의 스펙을 나타냄
	 */
	private void record() {
		buffg.setFont(new Font("바탕체", Font.BOLD, 20));
		buffg.setColor(Color.white);
		buffg.drawString("HP : " + hero.getHp(), 30, 70);
		buffg.drawString("Level : " + hero.getLevel(), 30, 90);
		buffg.drawString("EX : " + hero.getEx(), 30, 110);
		buffg.drawString("AP : " + hero.getAp(), 30, 130);
		buffg.drawString("CRI : " + (int) (hero.getCri() * 100), 30, 150);
	}

	/**
	 * Spacebar를 통해 실행된 공격들을 그림
	 */
	private void Draw_attack() {
		try {
			for (int i = 0; i < left_arrow.size(); i++) {
				Arrow ar = left_arrow.get(i);
				Point pos = ar.getPos();

				buffg.drawImage(left_shooting, pos.x, pos.y + 30, this);

				ar.left_move();

				if (pos.x < -100) {
					left_arrow.remove(i);
				}

				for (int j = m.size() - 1; j >= 0; j--) {
					Mop tmp = m.get(j);
					if (left_Crash(pos.x, pos.y, tmp.getPos().x, tmp.getPos().y + mop, left_shooting, enemy)) {

						boolean crash = attack(tmp);
						if (crash) {
							th_list.get(j).interrupt();
							try {
								Thread.sleep(50);
							} catch (Exception e) {

							}
							m.remove(j);
							th_list.remove(j);
							try {
								Thread.sleep(50);
							} catch (Exception e) {

							}
						}
						left_arrow.remove(i);
						break;
					}
				}
			}

			for (int i = 0; i < right_arrow.size(); i++) {
				Arrow ar = right_arrow.get(i);
				Point pos = ar.getPos();

				buffg.drawImage(shooting, pos.x, pos.y + 30, this);

				ar.right_move();

				if (pos.x > WIDTH) {
					right_arrow.remove(i);
				}

				for (int j = 0; j < m.size(); j++) {
					Mop tmp = m.get(j);
					if (right_Crash(pos.x, pos.y, tmp.getPos().x, tmp.getPos().y + mop, shooting, enemy)) {

						boolean crash = attack(tmp);

						if (crash) {
							th_list.get(j).interrupt();
							try {
								Thread.sleep(50);
							} catch (Exception e) {

							}
							m.remove(j);
							th_list.remove(j);
							try {
								Thread.sleep(50);
							} catch (Exception e) {

							}
						}
						right_arrow.remove(i);
						break;
					}
				}
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 몬스터를 공격하고, 생존 여부를 판단
	 * @param m 공격한 몬스터
	 * @return 몬스터 체력이 0이하라면 true, 몬스터가 살아있다면 false를 도출
	 */
	private boolean attack(Mop m) {
		int attack = hero.getAp();
		double per = Math.random();

		if (per < hero.getCri()) {
			attack = (int) (attack * 1.5);
		}

		int m_hp = m.getHp() - attack;
		if (m_hp <= 0) {
			int ex = 20 - ((hero.getLevel() - m.getLevel()) * 5);
			if (ex <= 0) {
				ex = 1;
			}
			hero.setEx(ex);
			return true;
		} else {
			m.setHp(m_hp);
			return false;
		}
	}
	
	/**
	 * 몬스터에게 공격받을 당시 캐릭터의 체력을 반환
	 * @return 현재 캐릭터 체력 반환
	 */
	public static int hp() {
		return hero.getHp();
	}

	/**
	 * 캐릭터가 죽었을 때 실행됨
	 */
	public static void die() {
		hero.setEx(-(hero.getEx() / 2));
		hero.setHp(hero.getMaxHP());
	}
	
	/**
	 * 캐릭터가 몬스터에게 공격받았지만 죽지 않았을 경우 실행됨
	 * @param hp 남아있는 캐릭터 체력
	 */
	public static void live(int hp) {
		hero.setHp(hp);
	}

	/**
	 * @param x 몬스터의 x좌표
	 * @param y 몬스터의 y좌표
	 * @return 몬스터와 (좌측으로 발사된)화살 사이의 거리
	 */
	public static double getDistance(int x, int y) {
		return Math.sqrt(Math.pow(Math.abs(hero.getPos().x - x), 2) + Math.pow(Math.abs(hero.getPos().y - y), 2));
	}

	/**
	 * (오른쪽으로 움직이는)화살과 몬스터 충돌 여부
	 * 
	 * @param x1 화살 x좌표
	 * @param y1 화살 x좌표
	 * @param x2   몬스터 x좌표
	 * @param y2   몬스터 y좌표
	 * @param img1 화살 이미지
	 * @param img2 몬스터 이미지
	 * @return 화살과 몬스터가 충돌하면 true, 충돌하지 않았으면 false 도출
	 */
	private boolean right_Crash(int x1, int y1, int x2, int y2, Image img1, Image img2) {
		Rectangle re = new Rectangle(x1, y1, img1.getWidth(null), img1.getHeight(null));
		Rectangle two = new Rectangle(x2 + 25, y2 - 20, img2.getWidth(null), img2.getHeight(null));

		return re.intersects(two);
	}

	/**
	 * (왼쪽으로 움직이는)화살과 몬스터 충돌 여부
	 * 
	 * @param x1 화살 x좌표
	 * @param y1 화살 x좌표
	 * @param x2   몬스터 x좌표
	 * @param y2   몬스터 y좌표
	 * @param img1 화살 이미지
	 * @param img2 몬스터 이미지
	 * @return 화살과 몬스터가 충돌하면 true, 충돌하지 않았으면 false 도출
	 */
	private boolean left_Crash(int x1, int y1, int x2, int y2, Image img1, Image img2) {
		Rectangle re = new Rectangle(x1, y1, img1.getWidth(null), img1.getHeight(null));
		Rectangle two = new Rectangle(x2, y2, img2.getWidth(null), img2.getHeight(null));

		return re.intersects(two);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_UP:
			if (!gravity) {
				jump = true;
			}
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_SPACE:
			attack = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_SPACE:
			attack = false;
			break;
		case KeyEvent.VK_ESCAPE:
			esc = true;
			break;
		case KeyEvent.VK_A:
			reinforce = true;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}

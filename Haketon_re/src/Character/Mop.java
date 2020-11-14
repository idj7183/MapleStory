package Character;

import java.awt.Point;

import javax.swing.JOptionPane;

import GUI.Reinforce;
import Play.Maple;

/**
 * 몬스터 생성 클래스
 */
public class Mop implements Comparable<Mop>, Runnable {

	/**
	 * 여러 몬스터에게 공격받아 죽었을 때, 한 개의 몬스터에게로 죽은 것으로 처리하기 위한 멤버변수
	 * false : 이미 죽어있는 상태. 다른 몬스터로부터 공격받지 않는다.
	 */
	public static boolean once = true;
	/**
	 * 몬스터 체력
	 */
	private int hp;
	/**
	 * 몬스터 공격력
	 */
	private int ap;
	/**
	 * 몬스터 레벨
	 */
	private int level;

	/**
	 * 몬스터 현재 위치
	 */
	private Point pos;

	/**
	 * 몬스터 레벨에 따라 체력과 공격력을 설정
	 * 
	 * @param level 몬스터의 설정 레벨
	 */
	public Mop(int level) {
		setHp(level * 50);
		this.level = level;
		ap = level * 10;
	}

	/**
	 * 몬스터 현재 체력 반환
	 * 
	 * @return 몬스터 현재 체력
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * 몬스터 남아있는 체력 설정
	 * 
	 * @param hp 몬스터 체력
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * 몬스터 현재 공격력 반환
	 * 
	 * @return 몬스터 현재 공격력
	 */
	public int getAp() {
		return ap;
	}

	/**
	 * 몬스터 현재 레벨 반환
	 * 
	 * @return 몬스터 현재 레벨
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 몬스터 현재 위치 반환
	 * 
	 * @return 몬스터 현재 위치
	 */
	public Point getPos() {
		return pos;
	}

	/**
	 * 몬스터 위치 설정
	 * 
	 * @param pos 몬스터 현재 위치
	 */
	public void setPos(Point pos) {
		this.pos = pos;
	}

	/**
	 * 캐릭터가 몬스터에게 공격을 받을 때 실행되는 메서드
	 * 캐릭터와 몬스터가 일정 거리 내에 있어야지 공격이 실행된다.
	 */
	private void damage() {
		int attack = ap;

		if(once) {
			if (Maple.getDistance(getPos().x, getPos().y) < 100) {
				int hp = Maple.hp() - attack;
				if (hp <= 0) {
					once = false;
					JOptionPane.showMessageDialog(null, "죽었습니다 부활하시면 가지고 계신 경험치의 절반을 잃습니다");
	
					Maple.die();
					once = true;
				} else {
					Maple.live(hp);
				}
			}
		}
	}
	
	@Override
	public int compareTo(Mop o) {
		return (this.getPos().x - o.getPos().x);
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				if(Maple.esc||Maple.reinforce) {
					Thread.sleep(100);
					continue;
				}
				damage();
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			return;
		}
	}

}

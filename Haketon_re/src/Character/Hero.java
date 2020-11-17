package Character;

import java.awt.Point;

/**
 * 캐릭터 생성 클래스
 */
public class Hero {
	/**
	 * 캐릭터 초기 X좌표
	 */
	private final int CHAR_X = 10;
	
	/**
	 * 캐릭터 초기 Y좌표
	 */
	private final int CHAR_Y = 392;
	
	/**
	 * 캐릭터 체력
	 */
	private int hp;
	
	/**
	 * 캐릭터 공격력
	 */
	private int ap;
	
	/**
	 * 캐릭터 치명타
	 */
	private int cri;
	
	/**
	 * 캐릭터 경험치
	 */
	private int ex = 0;
	
	/**
	 * 캐릭터 레벨
	 */
	private int level = 1;
	
	/**
	 * 캐릭터 직업
	 */
	private String job;
	
	/**
	 * 캐릭터 레벨에 해당하는 최대 체력
	 */
	private int MaxHP;
	
	/**
	 * 캐릭터 무기 레벨
	 */
	private int weapon_level = 1;
	
	/**
	 * 게임 배경 x좌표
	 */
	private int bx = 0;
	
	/**
	 * 캐릭터 좌표
	 */
	private Point pos = new Point(CHAR_X, CHAR_Y);
	
	/**
	 * 이미 생성된 계정 캐릭터 정보 받아오기
	 * @param job 직업
	 * @param hp 체력
	 * @param ex 경험치
	 * @param level 레벨
	 * @param weapon_level 무기 레벨
	 * @param bx 배경 x좌표
	 * @param pos 캐릭터 위치
	 */
	public Hero(String job, int hp, int ex, int level, int weapon_level, int bx, Point pos) {
		this.hp = hp;
		this.ex = ex;
		this.level = level;
		this.weapon_level = weapon_level;
		this.bx = bx;
		this.pos = pos;
		this.job = job;
		
		if(job.equals("전사")) {
			warrior();
		}
		else {
			archer();
		}
	}
	
	/**
	 * 새로운 계정 캐릭터 생성
	 * @param job 직업
	 */
	public Hero(String job) {
		this.job = job;
		
		if(job.equals("전사")) {
			warrior();
		}
		else {
			archer();
		}
		hp = MaxHP;
	}
	
	/**
	 * 전사 직업 스펙 설정
	 */
	private void warrior() {
		MaxHP = 100+(level-1)*50;
		cri = 1+(level-1)*2;
		ap = 10+(level-1)*30;
		
		for(int i =1;i<weapon_level;i++) {
			double tmp = 1.0/weapon_level;
			ap += (int) (10/tmp);
		}
	}
	
	/**
	 * 궁수 직업 스펙 설정
	 */
	private void archer() {
		MaxHP = 70+(level-1)*50;
		cri = 5+(level-1)*2;
		ap = 20+(level-1)*30;
		
		for(int i =1;i<weapon_level;i++) {
			double tmp = 1.0/weapon_level;
			ap += (int) (10/tmp);
		}
	}
	
	/**
	 * 캐릭터 현재 체력 반환
	 * @return 캐릭터의 현재 체력
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * 캐릭터 체력 설정
	 * @param hp 캐릭터 체력
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * 캐릭터 현재 공격력 반환
	 * @return 캐릭터의 현재 공격력
	 */
	public int getAp() {
		return ap;
	}

	/**
	 * 추가되는 공격력을 현재 공격력에 더함
	 * @param ap 추가되는 공격력
	 */
	public void setAp(int ap) {
		this.ap += ap;
	}

	/**
	 * cri확률을 소수로 반환
	 * @return cri확률(할푼리)
	 */
	public double getCri() {
		return cri/100.0;
	}

	/**
	 * 캐릭터 치명타 확률 반환
	 * @param cri 캐릭터의 치명타 확률
	 */
	public void setCri(int cri) {
		this.cri = cri;
	}

	/**
	 * 추가되는 경험치를 현재 공격력에 더함
	 * @param ex 추가되는 경험치
	 */
	public void setEx(int ex) {
		int tmp = this.ex + ex;
		
		this.ex = levelup(tmp);
	}
	
	/**
	 * 경험치가 100을 넘기면 레벨업을 시켜주고, 레벨 업에 따른 스펙업
	 * @param b_ex 경험치
	 * @return 레벨업 조정을 마친 경험치 값
	 */
	private int levelup(int b_ex){
		while(b_ex>=100) {
			level++;
			b_ex = b_ex-100;
			MaxHP += 50;
			hp = MaxHP;
			ap += 30;
			cri += 2;
		}
		
		return b_ex;
	}
	
	/**
	 * 캐릭터 현재 경험치 반환
	 * @return 캐릭터의 현재 경험치
	 */
	public int getEx() {
		return ex;
	}
	
	/**
	 * 캐릭터 현재 레벨 반환
	 * @return 캐릭터의 현재 레벨
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * 캐릭터 현재 위치 반환
	 * @return 캐릭터의 현재 위치
	 */
	public Point getPos() {
		return pos;
	}

	/**
	 * 캐릭터 현재 위치 설정
	 * @param pos 캐릭터의 위치
	 */
	public void setPos(Point pos) {
		this.pos = pos;
	}

	/**
	 * 캐릭터 직업명 반환
	 * @return 캐릭터의 직업
	 */
	public String getJob() {
		return job;
	}

	/**
	 * 게임 배경의 x좌표 반환
	 * @return 게임 배경의 x좌표
	 */
	public int getBx() {
		return bx;
	}

	/**
	 * 게임 배경의 x좌표 설정
	 * @param bx 게임 배경의 x좌표
	 */
	public void setBx(int bx) {
		this.bx = bx;
	}

	/**
	 * 캐릭터 최대 체력 반환
	 * @return 해당 직업이 해당 레벨에 가지는 최대 체력
	 */
	public int getMaxHP() {
		return MaxHP;
	}

	/**
	 * 캐릭터 현재 무기 레벨 반환
	 * @return 현재 캐릭터가 가지고 있는 무기 레벨
	 */
	public int getWeapon_level() {
		return weapon_level;
	}

	/**
	 * 무기 강화 성공 시 무기 레벨 1증가
	 */
	public void setWeapon_level() {
		this.weapon_level++;
	}
}

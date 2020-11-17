package Character;

import java.awt.Point;

/**
 * 화살 생성 및 움직임 구현
 */
public class Arrow {
	/**
	 * 화살 좌표
	 */
	private Point pos;
	
	/**
	 * 초기 화살 위치 지정
	 * @param x 화살의 x축 위치
	 * @param y 화살의 y축 위치
	 */
	public Arrow(int x, int y) {
		pos = new Point(x, y);
	}
	
	/**
	 * @return 화살의 현재 위치
	 */
	public Point getPos() {
		return pos;
	}

	/**
	 * 오른쪽으로 화살이 이동하는 메서드
	 */
	public void right_move() {
		this.pos.x = this.pos.x + 10;
	}

	/**
	 * 왼쪽으로 화살이 이동하는 메서드
	 */
	public void left_move() {
		this.pos.x = this.pos.x - 10;
	}
}

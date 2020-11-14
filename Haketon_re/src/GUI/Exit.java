package GUI;

import javax.swing.JOptionPane;

import Character.Hero;
import Information.DB;

/**
 * 종료 창 생성 클래스
 */
public class Exit {
	/**
	 * 종료 의사를 물어보고, 확인을 누르면 정보를 저장하고 종료
	 * @param hero 저장할 캐릭터 정보
	 * @param no 유저 일련번호
	 * @param bx 배경의 x좌표
	 */
	private static void process(Hero hero, int no, int bx) {
		int check = JOptionPane.showConfirmDialog(null, "종료하시겠습니까", "종료", JOptionPane.YES_NO_CANCEL_OPTION);
		if(check==0) {
			DB.execute(hero, no);
			System.exit(0);
		}
	}
	
	public static void exit(Hero hero,int no, int bx){
		process(hero, no, bx);
	}
}

package Information;

import java.awt.Point;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import Character.Hero;
import GUI.Sign;

/**
 * DB에 유저와 캐릭터 조회 및 저장
 */
public class DB {
	private final static int CHAR_Y = 392;

	/**
	 * DB에서 아이디가 중복되는지 검사하고, 중복되지 않으면 의사 재확인 후 회원가입
	 * @param id        회원가입 할 ID
	 * @param password  회원가입 할 비밀번호
	 * @param b search_db메서드 실행과 구분해주기 위한 추가 입력값
	 */
	public static void execute(String id, String password, boolean b) {
		sign_in(id, password);
	}
	
	/**
	 * DB 유저의 일련번호를 통해 계정 캐릭터 정보를 불러옴
	 * @param no DB에 저장된 계정의 일련번호
	 * @return DB에 저장된 계정 캐릭터의 정보(저장된 캐릭터 정보가 없으면 null 반환)
	 */
	public static Hero execute(int no) {
		return loading(no);
	}
	
	/**
	 * DB에 존재하는 유저의 일련번호를 DB에서 검색
	 * @param id        로그인을 시도한 ID
	 * @param password  로그인을 시도한 Password
	 * @return          DB에 존재하면 해당 user의 일련번호를, 존재하지 않으면 0 출력
	 */
	public static int execute(String id, String password) {
		return search_db(id, password);
	}

	/**
	 * 유저의 캐릭터 정보를 해당 유저의 DB 공간에 저장
	 * @param h	캐릭터 정보
	 * @param no 유저 일련번호
	 */
	public static void execute(Hero h, int no){
		r_store(h, no);
	}
	
	private static void sign_in(String id, String password) {
		final String query = "INSERT INTO MapleStory.user VALUES(null, ?, ?)";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String ur1 = "jdbc:mysql://127.0.0.1/MapleStory";
			
			String username = "sign_in";
			
			conn = DriverManager.getConnection(ur1,username,username);
			
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, id);
			ps.setString(2, password);

			int a = JOptionPane.showConfirmDialog(null, "이 아이디로 회원가입하시겠습니까?", "확인창", JOptionPane.YES_NO_OPTION);
			
			if(a==1) {
				new Sign();
			}
			else {
				ps.execute();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "이미 존재하는 아이디입니다");
			new Sign();
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static Hero loading(int no){
		Hero h = null;
		final String query = "SELECT * FROM MapleStory.status WHERE no=?";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String ur1 = "jdbc:mysql://127.0.0.1/MapleStory";
			
			String username = "loading";
			
			conn = DriverManager.getConnection(ur1,username,username);
			
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			String job = rs.getString("job");
			int hp = rs.getInt("hp");
			int ex = rs.getInt("ex");
			int level = rs.getInt("level");
			int w_l = rs.getInt("weapon_level");
			int bx = rs.getInt("bx");
			int x = rs.getInt("x");
			
			h = new Hero(job, hp, ex, level, w_l, bx, new Point(x,CHAR_Y));
		} catch (Exception e) {
			
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return h;
	}
	
	private static int search_db(String id, String password){
		final String query = "SELECT no FROM MapleStory.user WHERE id=?&&password=?";
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String ur1 = "jdbc:mysql://127.0.0.1/MapleStory";
			
			String username = "search";
			
			conn = DriverManager.getConnection(ur1,username,username);
			
			PreparedStatement ps = conn.prepareStatement(query);

			ps.setString(1, id);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			return rs.getInt("no");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"존재하지 않는 아이디, 혹은 맞지 않는 비밀번호입니다");
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}
	
	private static void r_store(Hero h, int no) {

		final String update = "UPDATE MapleStory.status SET job=?, hp=?, "
				+ "ex=?, level=?, weapon_level=?,bx=?,x=? WHERE no=?";
		
		final String query = "INSERT INTO MapleStory.status VALUES (?,?,?,?,?,?,?,?)";
		
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String ur1 = "jdbc:mysql://127.0.0.1/MapleStory";
			
			String username = "store";
			
			conn = DriverManager.getConnection(ur1,username,username);
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setInt(1, no);
			ps.setInt(2, h.getLevel());
			ps.setInt(3, h.getHp());
			ps.setInt(4, h.getEx());
			ps.setInt(5, h.getPos().x);
			ps.setString(6, h.getJob());
			ps.setInt(7, h.getBx());
			ps.setInt(8, h.getWeapon_level());
			
			ps.execute();
		} catch (Exception e) {
			PreparedStatement ps;
			try {
				ps = conn.prepareStatement(update);
				
				ps.setString(1, h.getJob());
				ps.setInt(2, h.getHp());
				ps.setInt(3, h.getEx());
				ps.setInt(4, h.getLevel());
				ps.setInt(5, h.getWeapon_level());
				ps.setInt(6, h.getBx());
				ps.setInt(7, h.getPos().x);
				ps.setInt(8, no);
				
				ps.execute();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally{
			if(conn!=null) {
				try {
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

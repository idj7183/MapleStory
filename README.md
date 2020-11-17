# MapleStory

## 프로그램 주제
메이플스토리 로그인 화면과 메이플스토리 게임을 구현한다
* * *
## 핵심 기능
### 로그인 화면
  * 로그인 시도하는 방법
  
    \* 제약 조건 : 아이디는 무조건 입력되어야 함
  
    (1-1) 아이디와 비밀번호를 입력한 후 LOG IN 버튼 클릭
    
    (1-1) 아이디와 비밀번호를 입력한 후 PASSWORD 입력란에 키보드 커서가 존재하는 상태에서 엔터 입력
    
    (2) 만약 아이디와 비밀번호가 DB에 존재하지 않는다면 존재하지 않는다는 문구를 출력한다.
    
    (3) 존재하는 아이디와 비밀번호일 경우 2개 중 하나의 경우를 실행한다.
    
        1. 미리 생성되어 있던 캐릭터가 있으면 캐릭터 정보를 토대로 게임을 실행한다
        2. 미리 생성된 캐릭터가 없으면 캐릭터를 새로 생성하는 창을 띄운다.
  * 회원가입 시도하는 방법
  
    \* 비밀번호 제약 조건 : 대문자, 소문자, 숫자가 모두 포함된 6~20자로 입력해야한다.(특수문자는 포함하지 않음)
    
    \* 아이디는 무조건 입력되어야 함. 아이디는 중복될 수 없음
    
    (1-1) 아이디와 비밀번호를 입력한 후 조회 버튼 클릭
    
    (1-2) 아이디와 비밀번호를 입력한 후 PASSWORDS 입력란에 키보드 커서가 존재하는 상태에서 엔터 입력
    
    (2) 제약 조건을 지키지 않은 상황
    
        아이디를 입력하지 않았을 경우 : 아이디를 입력하라는 문구 출력
        비밀번호가 제약 조건을 만족시키지 않았을 경우 : 적절한 비밀번호 형식을 입력하라는 문구 출력
        ID가 중복되는 경우 : 중복되는 ID라는 문구 출력
        
    (3) 제약 조건을 모두 충족시킨 경우 해당 아이디로 회원가입할지 물어보고 확인하면 DB에 해당 계정의 정보를 저장
       
### 캐릭터 생성 창
  * 전사 버튼을 클릭하면 전사 클래스의 캐릭터가, 궁수 버튼을 클릭하면 궁수 클래스의 캐릭터가 생성되고 게임이 실행됨

### 게임 실행 창
  * 적 레벨 입력 창 : 적 레벨에 해당하는 몬스터가 맵에 출현
    * 게임을 처음 시작할 때 생성
    * 적이 모두 처치되었을 경우 생성. 이 때 이전 공격들은 모두 없어진다.
  * 공격 키 : SPACE BAR
  * 이동 키 : 키보드 왼쪽 키(왼쪽 이동), 키보드 오른쪽 키(오른쪽 이동), 키보드 위쪽 키(점프)
  * 강화 창 : 무기를 무기 레벨에 따라 강화확률을 조정하고 무기 강화를 시도한다
    * A키를 누르면 강화 창 생성
    * 강화 버튼 클릭 : 강화 시도
    * 게임으로 돌아가기 버튼 클릭 : 강화를 종료하고 강화된 만큼 공격력과 무기 레벨을 변경한다.
  * 몬스터가 캐릭터 일정 거리 안에 존재하면 캐릭터는 데미지를 입는다
    * 만약 HP가 0이하가 되면 경험치가 반이된다는 메시지를 출력시키고 경험치는 반이되며, 레벨에 해당하는 최대 체력으로 부활한다.
  * 종료 창 띄우기 : ESC 혹은 JFrame의 X버튼 클릭
    * 종료 창을 띄우는 동안은 몬스터는 공격을 하지 않고, 캐릭터도 공격할 수 없다.
    * 예 클릭 : 캐릭터의 좌표와 스텟을 저장한 후 종료
    * 아니오 혹은 취소 클릭 : 다시 게임으로 돌아감
* * *
## 내가 생각하는 잘한 점
1. 로그인 화면에 배경 그림 넣기
2. 게임 실행 화면 부드럽게 넘어가도록 설정
3. private을 사용한 캡슐화의 최대화
4. 기능을 최대한 쪼개서 하나의 메서드는 하나의 기능만을 하도록 최대한 구현
5. 종료 버튼, 강화 버튼을 눌렀을 때 모든 메서드 일시 정지
6. 점프 키를 누르면 계속 올라가는 것이 아닌 일반 게임처럼 다시 땅으로 돌아오는 메서드 구현
* * *
## 힘들었던점과 해결 방법
1. 로그인 화면에 배경 넣기
* 해결 방법 : 큰 JPanel 생성 후 이미지를 첨부시킨 다음 paintComponent를 오버라이드 하여 이미지를 추가하고 setOpaque(false)를 통해 이미지를 제외한 Panel 투명화

2. 쓰레드 중단
* 종료 버튼이 호출되거나 강화 창이 호출될 때 공격 쓰레드, 몬스터에게 데미지 받는 쓰레드, repaint()(배경화면 update 쓰레드)를 모두 중단시켜야 했다.
* 사실 repaint()나 공격 쓰레드 같은 경우는 간단했는데, 몬스터 쓰레드는 해당 메서드에 존재하는 것이 아니라 다른 공간 클래스 쓰레드여서 해결하기 어려웠다.

* 해결 방법 : A버튼을 눌렀을 때 boolean A = true;로 변하고, 종료 버튼이 호출되면 boolean es = true;로 변하도록 했다.

  몬스터에게 데미지 받는 쓰레드를 중지시키기 위해 A와 esc를 public static으로 지정하고, 만약 둘 중 하나라도 true로 변한다면 Thread.sleep과 continue문을 사용하여
  
  몬스터에게 데미지 받는 메서드를 실행하지 않도록 했다.

3. X버튼을 누른 후 아니오를 눌렀을 때 창이 사라지고 다시 생성되는 문제
* 해결 방법 : addWindowListener에 존재하는 windowClosing 메서드를 오버라이드할 때, boolean visible을 true로 변경시켰다

  run() 메서드 내에 만약 visible이 true라면 setvisible(true)로 생성하여 사라지는 시간을 매우 짧게 설정하였다.

4. 종료 창이나 강화 창을 띄우는 버튼을 눌렀을 때 창이 무한으로 로딩되는 현상
* 해결 방법 1. boolean one_time을 true로 설정했다가 만약 창이 한 번 생성되면 one_time = false;로 설정한 후 해당 창이 종료될 때 다시 one_time = true;로 변경시킴
* 해결 방법 2. 해당 메서드에 while(true)문과 Thread.sleep을 사용하여 해당 메서드가 종료될 때까지 쓰레드의 아래 내용을 도달할 수 없도록 함

5. 몬스터가 죽었는데 죽은 몬스터가 계속 공격하는 현상
* 불완전한 해결 방법 : 완벽히 해결하지는 못했다. 하지만, interrupt명령어를 사용하여 몬스터와 딱 붙어서 죽이는 경우만 아니면 해결할 수 있었다.
* 해결 방법 : 불완전한 해결방법을 수정하기 위해 코딩을 갈아엎었다. 원래 Fight 클래스에 싸움 관련 메서드를 생성했는데, 이를 Mop과 Hero객체에 분배하였다.

  Mop에 Runnable을 주고, 만약 몬스터가 죽으면 interrupt와 while명령문, try~catch명령문을 이용하여 몬스터가 죽음->interrupt명령 수행->interrupt오류가 발생하므로 catch문에 return; 수행 함으로써 쓰레드를 죽였다. 이런 식으로 짰을 때 죽은 몬스터가 공격하는 경우가 없어졌다.

6. 강화창이나 종료창을 닫았을 때 쓰레드가 실행되지 않는 문제
* 해결방법 : system.out.println(A)를 통해 boolean A값을 출력시킨 후 if(A)문을 실행시키자 쓰레드가 정상적으로 실행되었다. 내가 생각할 땐 A가 갑작스럽게 바뀌는 것을 처리하지 못해서라고 예상은 했지만, 정확한 이유는 모르겠다.
* 왜 해결되는지 모르겠는 해결방법이다.
* 코딩을 갈아엎으면서 이러한 해결방법을 총 3군데에 사용했고, 이런 방법을 사용하지 않고 구현할 수 있었던 메서드와 비교해봤다. 이 때, 이러한 해결방법을 사용해야하는 장소는 while문 속에 while문이 들어가 있는 경우였다. 사용하지 않아도 되는 경우는 while문을 실행하다 그 메서드에 도달하면 입력 받을 때까지 바깥쪽 while문이 실행되지 않았지만, 이러한 해결방법을 쓴 메서드는 밖의 while문이 계속해서 실행되었다. 아마 System.out을 쓰면 내부 while문 속 조건식의 true false여부가 바뀐 후 메서드가 실행되기 때문에 조건식 충돌이 일어나지 않아 이러한 오류가 발생하지 않을 것 같다라고 추정해보았다.


* * *
## 보완해야하는 점
1. 맵을 내가 생성할 수 있었으면 y좌표까지 이용해서 메이플 스토리와 더 유사하게 할 수 있었을 것 같다.
2. Inventory를 구현해서 여러 방어구나 무기, 포션 등도 구현할 수 있으면 좋을 것 같다.
3. 밸런스가 하나도 안맞는다. 레벨 10인 몬스터하테 레벨 12인 캐릭터가 순삭당한다 반대로 무기 강화만 조금 시키면 레벨 1짜리가 레벨 20몬스터를 순삭시킨다.
4. 근접 공격 구현은 시도해보지 못했다. 아마 화살 대신 캐릭터 대검 휘두르는 이미지를 계속 변경시켜주면 될 것 같다.
5. 일반 공격이 아닌 스킬 구현도 시킬 수 있으면 좋았을 것 같다.
6. 몬스터 또한 이동할 수 있게 구현하면 좋았을 것 같다.
7. 편의를 위해 스펙을 항상 창 왼쪽 위에 항상 써놨는데, 나중에는 TAB 키 등을 이용해서 보고 싶을 때만 창을 띄워서 볼 수 있게 구현할 수 있을 것 같다.
* * *
## 자바독
[JAVADOC](https://idj7183.github.io/MapleStory/MapleStory/Haketon_re/doc/index.html)
* * *
[![IU(아이유) _ Into the I-LAND](http://img.youtube.com/vi/QYNwbZHmh8g/0.jpg)](https://youtu.be/QYNwbZHmh8g?t=0s) 

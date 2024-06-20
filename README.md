<img src="https://capsule-render.vercel.app/api?type=wave&color=auto&height=200&section=header&width=1000&text=BoardV2%20&fontSize=90" />
<div>  
  <h3>개발 목적</h3>
  jdbd 이용 및 프로시저 공부
  <h3>사용 언어</h3>
  <img src="https://img.shields.io/badge/Java-FF7800?style=flat&logo=Java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Javascript-F7DF1E?style=flat&logo=Javascript&logoColor=white"/>
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat&logo=HTML-5&logoColor=white"/>
  
  <h3>사용 툴</h3>
  <img src="https://img.shields.io/badge/intellijidea-000000?style=flat&logo=intellijidea&logoColor=white"/>
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=flat&logo=springboot&logoColor=white"/>
  
  <h3>메인 개발 환경</h3>
  <img src="https://img.shields.io/badge/intellijidea-000000?style=flat&logo=intellijidea&logoColor=white"/>
  
  <h3>부트 의존성</h3>
  <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=flat&logo=thymeleaf&logoColor=white"/>
  <img src="https://img.shields.io/badge/jdbc-83B81A?style=flat&logo=jpa&logoColor=white"/>
  <img src="https://img.shields.io/badge/web-0085CA?style=flat&logo=web&logoColor=white"/>
  <img src="https://img.shields.io/badge/lombok-FF9A00?style=flat&logo=lombok&logoColor=white"/>
  <img src="https://img.shields.io/badge/validation-EF2D5E?style=flat&logo=validation&logoColor=white"/>
  <img src="https://img.shields.io/badge/devtools-0099E5?style=flat&logo=devtools&logoColor=white"/>
  <img src="https://img.shields.io/badge/mysql-4479A1?style=flat&logo=mysql&logoColor=white"/>

  <h3>ERD</h3>
  추후 업데이트
  <hr>
</div>
<div>

<h3>기능</h3>
<ul>
<li>회원 가입, 로그인</li>
<li>게시글 작성(이미지 업로드 X), 수정(이미지 수정X), 삭제</li>
<li>댓글 작성, 삭제(권한 작성자, 게시글 주인)</li>
</ul>
    <hr>

<h3>이슈</h3>
<ul>
<li>기본 적인 프로시져 crud만 구현 상태</li>
</ul>
<hr>

<h3>추후 업데이트, 수정 계획</h3>
<ul>
<li>mysql 프로시저 가상 테이블 이용 계획(현 작성한 프로시저가 디비에 많은 부하를 줄 수 있다는 피드백)</li>
<li>이미지 업로드 재 추가</li>
<li>페이징 처리</li>
</ul>

<pre>
sql query
--기본 테이블 생성 쿼리
create database Apiboard2;
use Apiboard2;
create table member (
id int auto_increment primary key,
email varchar(50) unique not null,
password varchar(30) not null,
username varchar(30) not null,
nickname varchar(30) unique not null);

create table roles (
id int auto_increment primary key,
role_name varchar(20) unique not null);

create table member_roles(
member_id int,
role_id int,
primary key (member_id, role_id),
foreign key (member_id) references member(id) on delete cascade,
foreign key (role_id) references roles(id) on delete cascade);

create table board (
	id int auto_increment primary key,
    member_id int,
    title varchar(255),
    content varchar(2000),
    foreign key (member_id) references member(id) on delete cascade
);

create table comment(
	id int auto_increment primary key,
    board_id int,
    member_id int,
    comments varchar(255),
    foreign key (board_id) references board(id) on delete cascade,
    foreign key (member_id) references member(id) on delete cascade
);
  
-- 로그인 프로시저
DROP PROCEDURE IF EXISTS signIn; 
DELIMITER $$

CREATE PROCEDURE signIn(
    IN p_email VARCHAR(50),
    IN p_password VARCHAR(30),
    OUT p_id INT,
    OUT p_user_email VARCHAR(50),
    OUT p_username VARCHAR(30),
    OUT p_nickname VARCHAR(30),
    OUT p_role_type VARCHAR(20)
)
BEGIN
    DECLARE v_id INT;
    DECLARE v_email VARCHAR(50);
    DECLARE v_username VARCHAR(30);
    DECLARE v_nickname VARCHAR(30);
    DECLARE v_role_type VARCHAR(20);

    SELECT m.id, m.email, m.username, m.nickname, r.role_name
    INTO v_id, v_email, v_username, v_nickname, v_role_type
    FROM member m
    INNER JOIN member_roles mr ON m.id = mr.member_id
    INNER JOIN roles r ON mr.role_id = r.id
    WHERE m.email = p_email AND m.password = p_password;

    IF v_id IS NULL THEN
        SET p_id = NULL;
        SET p_user_email = NULL;
        SET p_username = NULL;
        SET p_nickname = NULL;
        SET p_role_type = NULL;
    ELSE
        SET p_id = v_id;
        SET p_user_email = v_email;
        SET p_username = v_username;
        SET p_nickname = v_nickname;
        SET p_role_type = v_role_type;
    END IF;
END $$

DELIMITER ;

-- 테스트 구문

CALL signIn('admin@admin.com', 'admin1234!', @p_id, @p_user_email, @p_username, @p_nickname, @p_role_type);
SELECT @p_id, @p_user_email, @p_username, @p_nickname, @p_role_type;

  -- 회원가입 프로시저

DROP PROCEDURE IF EXISTS signUp;
DROP PROCEDURE IF EXISTS checkEmail;
DROP PROCEDURE IF EXISTS checkNickname;

DELIMITER $$

create procedure signUp(
	in p_email varchar(50),
    in p_password varchar(30),
    in p_username varchar(30),
    in p_nickname varchar(30)
)

begin
	declare v_member_id int;
	declare v_default_roly int default 2;
    
    set v_default_roly = 2;
    
    insert into member (email, password, username, nickname) 
    values (p_email, p_password, p_username, p_nickname);
    
    set v_member_id = last_insert_id();
    
    insert into member_roles (member_id, role_id)
    values (v_member_id, v_default_roly);
    
end $$

-- 이메일 체크 프로시저
create procedure checkEmail(
    in p_email VARCHAR(50),
    out p_exists BOOLEAN
)
begin
    declare v_count INT;
    
    select COUNT(*)
    into v_count
    from member
    where email = p_email;
    
    if v_count > 0 then
        set p_exists = true;
    else
        set p_exists = false;
    end if;
end $$

-- 닉네임 체크 프로시저
create procedure checkNickname(
    in p_nickname VARCHAR(30),
    out p_exists BOOLEAN
)
begin
    declare v_count INT;
    
    select COUNT(*)
    into v_count
    from member
    where nickname = p_nickname;
    
    if v_count > 0 then
        set p_exists = true;
    else
        set p_exists = false;
    end if;
end $$

DELIMITER ;

-- 게시판 프로시저

drop procedure if exists createBoard;
drop procedure if exists getAllBoard;
drop procedure if exists updateBoard;
drop procedure if exists deleteBoard
DELIMITER $$

create procedure createBoard(
	in p_member_id int,
    in p_title varchar(255),
    in p_content varchar(2000)
)

begin
	insert into board (member_id, title, content)
    values (p_member_id, p_title, p_content);
end $$

-- 모든 게시판 조회

create procedure getAllBoard ()
begin
    select b.id, b.title, m.nickname
    from board b
    join member m on b.member_id = m.id;
end $$

create procedure getByBoardId(
	in p_id int,
    out p_board_id int,
    out p_title varchar(255),
    out p_content varchar(2000),
    out p_member_id int,
    out p_nickname varchar(30)
)
begin
select b.id, b.title, b.content, m.id, m.nickname
into p_board_id, p_title, p_content, p_member_id, p_nickname
from board b
join member m on m.id = b.member_id
where b.id = p_id;

end $$
 
 create procedure updateBoard(
	in p_board_id int,
    in p_member_id int,
    in p_content varchar(2000)
 )
begin
	update board set content = p_content
    where id = p_board_id and member_id = p_member_id;
end $$

	create procedure deleteBoard(
    in p_board_id int,
    in p_member_id int
    )
    begin
		delete from board
		where id = p_board_id and member_id = p_member_id;
	end $$
    
DELIMITER ;

 -- 댓글
 drop procedure if exists newComments;
 drop procedure if exists getByboardIdcomment;
 drop procedure if exists deleteComment;
 
 DELIMITER $$
 
create procedure newComments(
	in p_board_id int,
    in p_member_id int,
    in p_comments varchar(255)
)
begin
	insert into comment(board_id, member_id, comments)
    values(p_board_id, p_member_id, p_comments);
end $$
 
create procedure getByboardIdcomment(
	in p_board_id int
)
begin
	select c.id, m.id, m.nickname, c.comments
    from comment c 
    join member m on m.id = c.member_id
    where c.board_id = p_board_id;
end $$

create procedure deleteComment(
	in p_comment_id int,
    in p_board_id int,
    in p_member_id int
)
begin
	delete from comment
    where id = p_comment_id and board_id = p_board_id and member_id = p_member_id;
end $$

DELIMITER ;
</pre>
</div>

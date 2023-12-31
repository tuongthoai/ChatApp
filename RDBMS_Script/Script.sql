CREATE DATABASE chat_app
-- DROP DATABASE CHAT_APP

create table roles (
                       role_id int primary key,
                       role_name varchar(20)
)

CREATE TABLE USER_METADATA (
                               user_id SERIAL PRIMARY KEY,
                               USERNAME VARCHAR UNIQUE,
                               USER_PASSWORD VARCHAR,
                               USER_ROLE INT references roles(role_id),
                               FULLNAME VARCHAR,
                               EMAIL VARCHAR,
                               SEX VARCHAR(3),
                               USER_ADDRESS VARCHAR,
                               BIRTHDAY BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000,
                               CREATEDTIME BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000,
                               ISONLINE BOOLEAN DEFAULT FALSE,
                               ISBLOCKED BOOLEAN DEFAULT FALSE
);

CREATE TABLE USER_FRIEND (
                             USER_ID INTEGER REFERENCES USER_METADATA (USER_ID),
                             FRIEND_ID INTEGER REFERENCES USER_METADATA (USER_ID),
                             PRIMARY KEY (USER_ID, FRIEND_ID)
);

CREATE TABLE LOGIN_HISTORY (
                               USER_ID INTEGER REFERENCES USER_METADATA (USER_ID),
                               LOGINTIME BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000,
                               DCTIME BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000,
                               PRIMARY KEY (USER_ID, LOGINTIME)
);

CREATE TABLE GCHAT_METADATA (
                                GROUP_ID SERIAL PRIMARY KEY,
                                GROUPNAME VARCHAR,
                                CREATEDTIME BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000,
                                ISGROUP BOOLEAN DEFAULT FALSE
);

CREATE TABLE USER_GCHAT_LIST (
                                 USER_ID INTEGER REFERENCES USER_METADATA (USER_ID),
                                 GROUP_ID INTEGER REFERENCES GCHAT_METADATA (GROUP_ID),
                                 PRIMARY KEY (USER_ID, GROUP_ID)
);

CREATE TABLE USER_BLOCK_LIST (
                                 USER_ID INTEGER REFERENCES USER_METADATA (USER_ID),
                                 USERISBLOCKED INTEGER REFERENCES USER_METADATA (USER_ID),
                                 PRIMARY KEY (USER_ID, USERISBLOCKED)
);

CREATE TABLE SPAM_REPORT (
                             REPORT_ID SERIAL PRIMARY KEY,
                             USERSENT INTEGER REFERENCES USER_METADATA (USER_ID),
                             USERISREPORTED INTEGER REFERENCES USER_METADATA (USER_ID),
                             CREATEDTIME BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000,
                             STATUS VARCHAR(10),
                             CONTENT VARCHAR,
                             CONSTRAINT USER_CHECK CHECK (USERSENT <> USERISREPORTED)
);

CREATE TABLE GCHAT_MEMBER (
                              GROUPCHAT_ID INTEGER REFERENCES GCHAT_METADATA (GROUP_ID),
                              MEMBER_ID INTEGER REFERENCES USER_METADATA (USER_ID),
                              PRIMARY KEY (GROUPCHAT_ID, MEMBER_ID)
);

CREATE TABLE GCHAT_CONTENT (
                               GROUP_ID INTEGER,
                               USERSENT INTEGER,
                               MSG VARCHAR,
                               MSG_OFFSET INTEGER,
                               SENTTIME BIGINT DEFAULT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000,
                               PRIMARY KEY (GROUP_ID, SENTTIME),
                               FOREIGN KEY (GROUP_ID, USERSENT) REFERENCES GCHAT_MEMBER (GROUPCHAT_ID, MEMBER_ID) ON DELETE CASCADE
);

CREATE TABLE GCHAT_ADMINS (
                                GROUP_ID INTEGER,
                                ADMIN_ID INTEGER,
                                PRIMARY KEY (GROUP_ID, ADMIN_ID),
                                FOREIGN KEY (GROUP_ID, ADMIN_ID) REFERENCES GCHAT_MEMBER (GROUPCHAT_ID, MEMBER_ID) ON DELETE CASCADE
);

CREATE TABLE GCHAT_OFFSET (
                                GROUP_ID INTEGER,
                                USER_ID INTEGER,
                                LASTSEENOFFSET INTEGER,
                                LASTRECEIVEDOFFSET INTEGER,
                                PRIMARY KEY (GROUP_ID, USER_ID),
                                FOREIGN KEY (GROUP_ID, USER_ID) REFERENCES GCHAT_MEMBER (GROUPCHAT_ID, MEMBER_ID) ON DELETE CASCADE
);

insert into ROLES(role_id, role_name) values
                                          (1, 'ROLE_ADMIN'),
                                          (2, 'ROLE_USER');

INSERT INTO USER_METADATA (USER_ROLE, USERNAME, USER_PASSWORD, FULLNAME, USER_ADDRESS, BIRTHDAY, SEX, EMAIL, CREATEDTIME) VALUES
                                                                                                                              (2,'usn1', '1234', N'Nguyễn Xuân Huy', N'Đà Nẵng', 943266359, N'Nam', 'usn1@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-12-25 20:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                                                              (2,'usn2', '1234', N'Lê Ngọc Nhi', N'An Giang', 911730359, N'Nữ', 'usn2@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-07-09 09:45:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                                                              (2,'usn3', '1234', N'Nguyễn Thị Thanh Thảo', N'Sóc Trăng', 814357559, N'Nữ', 'usn3@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-01-24 12:36:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                                                              (2,'usn4', '1234', N'Nghiêm Xuân Huy', N'Cần Thơ', 876911159, N'Nam', 'usn4@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-12-05 22:20:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                                                              (2,'usn5', '1234', N'Lương Xuân Trường', N'Tuyên Quang', 861099959, N'Nam', 'usn5@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-01-22 08:27:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                                                              (2,'usn6', '1234', N'Nguyễn Văn Bình', N'Quảng Nam', 703333559, N'Nam', 'usn6@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-04-28 15:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                                                              (2,'usn7', '1234', N'Phạm Băng Băng', N'Bến Tre', 356178359, N'Nữ', 'usn7@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-12-05 20:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                                                              (2,'usn8', '1234', N'Chu Thị Trà', N'Hải Phòng', 387714359, N'Nữ', 'usn8@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-03-15 06:10:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                                                              (2,'usn9', '1234', N'Đinh Bá Tiến', N'Nasa', 577103159, N'Nam', 'usn9@gmail.com', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-04-29 12:35:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000);

INSERT INTO USER_METADATA (USER_ROLE, USER_ID, USERNAME, USER_PASSWORD) VALUES (1, 0,'admin','admin');

INSERT INTO USER_FRIEND (USER_ID, FRIEND_ID) VALUES
                                                 (1, 3), (3, 1),
                                                 (1, 5), (5, 1),
                                                 (1, 7), (7, 1),
                                                 (2, 3), (3, 2),
                                                 (2, 4), (4, 2),
                                                 (3, 7), (7, 3),
                                                 (3, 9), (9, 3),
                                                 (4, 6), (6, 4),
                                                 (5, 6), (6, 5),
                                                 (5, 7), (7, 5),
                                                 (6, 9), (9, 6),
                                                 (7, 8), (8, 7);

INSERT INTO LOGIN_HISTORY (USER_ID, LOGINTIME) VALUES
                                                   (1, EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-12-25 20:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                   (2, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-07-09 09:45:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                   (1, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-01-24 12:36:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                   (1, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-05 22:20:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                   (5, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-02-22 08:27:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                   (8, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-05-28 15:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                   (7, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-12-05 20:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                   (8, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-10-15 06:10:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                   (2, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-09-29 12:35:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000);

INSERT INTO GCHAT_METADATA (GROUPNAME, CREATEDTIME, ISGROUP) VALUES
                                                                 ('Group 1', EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-02-25 20:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000, TRUE),
                                                                 ('Group 2', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-07-19 09:45:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000, FALSE),
                                                                 ('Group 3', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-14 12:36:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000, TRUE),
                                                                 ('Group 4', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-12-05 22:20:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000, FALSE);

INSERT INTO USER_GCHAT_LIST (USER_ID, GROUP_ID) VALUES
                                                    (1, 1), (2, 1), (3, 1), (4, 1),
                                                    (2, 2), (3, 2),
                                                    (2, 3), (3, 3), (4, 3),
                                                    (1, 4), (4, 4);

INSERT INTO USER_BLOCK_LIST (USER_ID, USERISBLOCKED) VALUES
                                                         (1, 2), (2, 4), (3, 4);

INSERT INTO SPAM_REPORT (USERSENT, USERISREPORTED, STATUS, CONTENT, CREATEDTIME) VALUES
                                                                                     (1, 2, 'PENDING', 'Spam', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-01 20:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                     (1, 4, 'PENDING', 'Spam', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-06 12:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                     (2, 4, 'PROCESSED', 'Spam', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-10-30 19:31:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                                     (3, 4, 'REJECTED', 'Spam', EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-10-31 15:28:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000);

INSERT INTO GCHAT_MEMBER (GROUPCHAT_ID, MEMBER_ID) VALUES
                                                       (1, 1), (1, 2), (1, 3), (1, 4),
                                                       (2, 2), (2, 3),
                                                       (3, 2), (3, 3), (3, 4),
                                                       (4, 1), (4, 4);

INSERT INTO GCHAT_CONTENT (GROUP_ID, USERSENT, MSG, MSG_OFFSET, SENTTIME) VALUES
                                                                              (1, 1, 'Hello', 1, EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-02-25 20:30:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (1, 2, 'Hi', 2,EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-02-25 20:31:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (1, 3, 'How are you?', 3, EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-02-25 20:32:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (1, 4, 'I am fine', 4, EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-02-25 20:33:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (1, 1, 'What are you doing?', 5, EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-02-25 20:34:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (1, 2, 'I am doing homework', 6, EXTRACT(EPOCH FROM TO_TIMESTAMP('2022-02-25 20:35:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (2, 2, 'Hello', 1, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-07-19 09:45:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (2, 3, 'Hi', 2, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-07-19 09:46:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (2, 2, 'How are you?', 3, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-07-19 09:47:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (2, 3, 'I am fine', 4, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-07-19 09:48:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (2, 2, 'What are you doing?', 5, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-07-19 09:49:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (2, 3, 'I am doing homework', 6, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-07-19 09:50:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (3, 2, 'Hello', 1, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-14 12:36:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (3, 3, 'Hi', 2, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-14 12:37:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (3, 2, 'How are you?', 3, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-14 12:38:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (3, 3, 'I am fine', 4, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-14 12:39:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (3, 2, 'What are you doing?', 5, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-14 12:40:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (3, 3, 'I am doing homework', 6, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-11-14 12:41:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (4, 1, 'Hello', 1, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-12-05 22:20:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (4, 4, 'Hi', 2, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-12-05 22:21:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (4, 1, 'How are you?', 3, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-12-05 22:22:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (4, 4, 'I am fine', 4, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-12-05 22:23:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (4, 1, 'What are you doing?', 5, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-12-05 22:24:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000),
                                                                              (4, 4, 'I am doing homework', 6, EXTRACT(EPOCH FROM TO_TIMESTAMP('2023-12-05 22:25:00', 'YYYY-MM-DD HH24:MI:SS')) * 1000);

INSERT INTO GCHAT_ADMINS (GROUP_ID, ADMIN_ID) VALUES
                                                  (1, 1), (1, 2), (3, 2), (3, 4);

INSERT INTO GCHAT_OFFSET (GROUP_ID, USER_ID, LASTSEENOFFSET, LASTRECEIVEDOFFSET) VALUES
                                                                                     (1, 1, 5, 6), (1, 2, 6, 6), (1, 3, 4, 6), (1, 4, 5, 6),
                                                                                     (2, 2, 5, 6), (2, 3, 6, 6),
                                                                                     (3, 2, 6, 6), (3, 3, 6, 6), (3, 4, 0, 6),
                                                                                     (4, 1, 6, 6), (4, 4, 6, 6);

----------------------------------------------------------------------------------------------------------


select * from gchat_member gm where gm.member_id = 1

select * from gchat_member gm where gm.groupchat_id = 1


select
    (select count(*) from gchat_member gm where gm.member_id = 1) as no_group_chat,
    (select count(*) from user_friend uf where uf.user_id = 1 ) as no_friends,
    (MAX(lh.logintime)) as last_login
from user_metadata um
         join login_history lh on lh.user_id = um.user_id
where um.user_id = 1

select COALESCE(MAX(lh.logintime),0)
from login_history lh where lh.user_id = 2

select count(*) from user_friend uf where uf.user_id = 1

select * from login_history lh

update user_metadata set isonline = ? where user_metadata.user_id = ?


select * from spam_report sr
insert into spam_report (usersent, userisreported, "content", status, createdtime)
values (?, ?, ?, 'PENDING', SELECT EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000)


update gchat_metadata
set groupname = 'TEST'
where group_id = 1


select * from gchat_metadata gm
-- SET DEFAULT VALUE cua isgroup thanh FALSE

-- trigger de tu dong set isGroup cua gchat_metadata
CREATE OR REPLACE FUNCTION update_isgroup()
    RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'DELETE' THEN
        IF (SELECT count(*) FROM gchat_member gmb WHERE gmb.groupchat_id = OLD.groupchat_id) > 2 THEN
            UPDATE gchat_metadata SET isgroup = true WHERE group_id = OLD.groupchat_id;
        ELSE
            UPDATE gchat_metadata SET isgroup = false WHERE group_id = OLD.groupchat_id;
        END IF;
    ELSE
        IF (SELECT count(*) FROM gchat_member gmb WHERE gmb.groupchat_id = NEW.groupchat_id) > 2 THEN
            UPDATE gchat_metadata SET isgroup = true WHERE group_id = NEW.groupchat_id;
        ELSE
            UPDATE gchat_metadata SET isgroup = false WHERE group_id = NEW.groupchat_id;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_isgroup_trigger
    AFTER INSERT OR UPDATE OR DELETE ON gchat_member
    FOR EACH ROW
EXECUTE FUNCTION update_isgroup();


-- them user vao gr chat
insert into gchat_member values(groupchat_id, member_id)

-- them gr chat moi
insert into gchat_metadata (groupname) values ('Nhom moi')

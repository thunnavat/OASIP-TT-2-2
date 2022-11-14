CREATE DATABASE oasip DEFAULT CHARACTER SET utf8;
USE oasip;
CREATE USER 'test221'@'%' IDENTIFIED by 'oasiptt2';
GRANT ALL PRIVILEGES ON *.* TO 'test221'@'%';
FLUSH PRIVILEGES;
CREATE TABLE eventcategories (
 eventCategoryId INT NOT NULL AUTO_INCREMENT,
 eventCategoryName VARCHAR(100) NOT NULL,
 eventCategoryDescription VARCHAR(500) NULL,
 eventDuration INT NOT NULL,
 PRIMARY KEY (eventCategoryId),
 UNIQUE INDEX eventCategoryName_UNIQUE (eventCategoryName ASC) VISIBLE
);
CREATE TABLE events (
 eventId INT NOT NULL AUTO_INCREMENT,
 bookingName VARCHAR(100) NOT NULL,
 bookingEmail VARCHAR(100) NOT NULL,
 eventStartTime DATETIME NOT NULL,
 eventDuration INT NOT NULL,
 eventNotes VARCHAR(500) NULL,
 eventCategoryId INT NOT NULL,
 PRIMARY KEY (eventId),
 INDEX fk_events_eventcategories_idx (eventCategoryId ASC) VISIBLE,
 CONSTRAINT fk_events_eventcategories
   FOREIGN KEY (eventCategoryId)
   REFERENCES eventcategories (eventCategoryId)
   ON DELETE NO ACTION
   ON UPDATE NO ACTION
);
CREATE TABLE users (
  userId INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  role ENUM('ADMIN', 'LECTURER', 'STUDENT') NOT NULL DEFAULT 'STUDENT',
  createdOn TIMESTAMP NOT NULL DEFAULT current_timestamp,
  updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp on update current_timestamp,
  PRIMARY KEY (userId),
  UNIQUE INDEX name_UNIQUE (name ASC) VISIBLE,
  UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE
);
CREATE TABLE eventcategoryowner (
  eventCategoryOwnerId INT NOT NULL AUTO_INCREMENT,
  eventCategoryId INT NOT NULL,
  userId INT NOT NULL,
  PRIMARY KEY (eventcategoryownerId, eventCategoryId, userId),
  INDEX fk_eventcategory_has_user_user1_idx (userId ASC) VISIBLE,
  INDEX fk_eventcategory_has_user_eventcategory_idx (eventCategoryId ASC) VISIBLE,
  CONSTRAINT fk_eventcategory_has_user_eventcategory
    FOREIGN KEY (eventCategoryId)
    REFERENCES eventcategories (eventCategoryId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT fk_eventcategory_has_user_user1
    FOREIGN KEY (userId)
    REFERENCES users (userId)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

INSERT INTO eventcategories (eventCategoryName,eventCategoryDescription,eventDuration)
VALUES ('Project Management Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย Project Management Clinic ในวิชา INT221 integrated project I', 30);
INSERT INTO eventcategories (eventCategoryName,eventCategoryDescription,eventDuration)
VALUES ('DevOps/Infra Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย DevOps/Infra Clinic ในวิชา INT221 integrated project I', 20);
INSERT INTO eventcategories (eventCategoryName,eventCategoryDescription,eventDuration)
VALUES ('Database Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย Database Clinic ในวิชา INT221 integrated project I', 30);
INSERT INTO eventcategories (eventCategoryName,eventCategoryDescription,eventDuration)
VALUES ('Client-side Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย  Client-side Clinic ในวิชา INT221 integrated project I', 60);
INSERT INTO eventcategories (eventCategoryName,eventCategoryDescription,eventDuration)
VALUES ('Server-side Clinic', 'ตารางนัดหมายนี้ใช้สำหรับนัดหมาย Server-side Clinic ในวิชา INT221 integrated project I', 45);

INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventCategoryId)
values ('สมเกียรติ ขยันเรียน กลุ่ม TT-2','somkiat.k@gmail.com','2022-10-12 10:10:10',30,'1');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventCategoryId)
values ('Harry Maguire กลุ่ม SSI-2','staymanu_forever@gmail.com','2022-10-10 12:30:00',45,'5');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventNotes,eventCategoryId)
values ('Tawan Thanyaphon กลุ่ม SSA-5','tawan_than@gmail.com','2022-10-02 16:45:00',30,'ผมงงตรงที่อาจารย์พูดตอนต้นคาบครับ','1');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventCategoryId)
values ('Todsawat Somtua กลุ่ม AT-3','todsawat.st@gmail.com','2022-10-30 09:20:00',20,'2');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventCategoryId)
values ('Supaporn Parkwarn กลุ่ม PL-3','supapornpw@gmail.com','2022-10-18 11:00:00',60,'4');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventCategoryId)
values ('Danny Welbeck กลุ่ม KW-2','godofthegod@gmail.com','2022-10-14 08:50:00',60,'4');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventCategoryId)
values ('Pronpitcha Pimpa กลุ่ม OR-4','pronpitcha@gmail.com','2022-10-29 14:30:00',45,'5');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventNotes,eventCategoryId)
values ('Wallapa Tosap กลุ่ม SY-1','wallapak@gmail.com','2022-10-06 13:00:00',30,'งงเรื่อง database ใน week นี้ค่ะ  ว่าทำยังไง','3');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventCategoryId)
values ('Nattapon Chusriwan กลุ่ม US-5','nattapon_champ@gmail.com','2022-10-02 15:10:00',30,'2');
INSERT INTO events (bookingName,bookingEmail,eventStartTime,eventDuration,eventCategoryId)
values ('ไชยา ชูเจริญ กลุ่ม KP-5','chaitya_chu@gmail.com','2022-10-04 12:20:00',30,'1');

INSERT INTO users (name, email, password, role)
values ('OASIP ADMIN','oasip.admin@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$sYXzbUOqBoHY1NfhJ8cjnw$H6+adWySiFPgcUogJK3hEhcF6Y4fusy7tcXYEL+f0cQ','ADMIN');
INSERT INTO users (name, email, password, role)
values ('Olarn Rojanapornpun','olarn.roj@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$Sx7y2jxKZSjpWUV4srd8eg$AMH09iFiPQgAZ00cAdN3Gucqfhx2kRo3tQbHeLSR0RE','LECTURER');
INSERT INTO users (name, email, password, role)
values ('Pichet Limvachiranan','pichet.limv@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$46EB43gQ46Z1/EmdqxtKNA$7m6cWGO2iDlFl/ETDYuYf+ArnSjRnsNwXLIP18DTYQY','LECTURER');
INSERT INTO users (name, email, password, role)
values ('Umaporn Supasitthimethee','umaporn.sup@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$1Z2UK1zC76FIQeLH54GVAQ$qfXcHF31LnuWpt37QAcWyNp8PdbOQ+jjaV1xWXixS0M','LECTURER');
INSERT INTO users (name, email, password, role)
values ('Siam Yamsaengsung','siam.yam@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$C4pPaNWKTnZQX2mPs14jlg$rQ5W5NYKqGOu1B4GkUWq8cFbcg2peFWGjpUMr9Nkm8g','LECTURER');
INSERT INTO users (name, email, password, role)
values ('Sunisa Sathapornvajana','sunisa.sat@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$29/ffaszvjvi3CZO45bSCg$kKpfq5WEswoqa/LfyIZzQaQ6AFdjhyiYjXRCfMiTnwg','LECTURER');
INSERT INTO users (name, email, password, role)
values ('Somchai Jaidee','somchai.jai@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$dmsOy7LPTjmooPu+P2oTZA$NZFTFd3f0K1Sp19aaUwyn3jgiy15yFcXhp8E4/1yXoI','STUDENT');
INSERT INTO users (name, email, password, role)
values ('Komkrid Rakdee','komkrid.rak@mail.kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$8W61ZOC5RU7sJP5kKRbSqg$OLwZNPeMqxp+g0Vbn+odcA47XMClFN+IswTueVah7F0','STUDENT');
INSERT INTO users (name, email, password, role)
values ('สมเกียรติ ขยันเรียน','somkiat.kay@kmutt.ac.th','$argon2id$v=19$m=4096,t=3,p=1$gBqgjspF45FcIKQEw8GmaQ$alrOCZ0YrDqOu8/aZiLDMGZo4vFkSEAXA0YoHhY0BDQ','STUDENT');

INSERT INTO eventcategoryowner (eventCategoryId, userId)
values (1, 2);
INSERT INTO eventcategoryowner (eventCategoryId, userId)
values (2, 5);
INSERT INTO eventcategoryowner (eventCategoryId, userId)
values (2, 2);
INSERT INTO eventcategoryowner (eventCategoryId, userId)
values (3, 6);
INSERT INTO eventcategoryowner (eventCategoryId, userId)
values (4, 4);
INSERT INTO eventcategoryowner (eventCategoryId, userId)
values (5, 3);
INSERT INTO eventcategoryowner (eventCategoryId, userId)
values (5, 2);
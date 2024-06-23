

ALTER TABLE QuizQuestion MODIFY content LONGTEXT;
ALTER TABLE News MODIFY content LONGTEXT;

INSERT IGNORE INTO  field (id, name) 
VALUES 
    (1, 'Biology'),
    (2, 'Economic'),
    (3, 'History'),
    (4, 'Information Technology');
    
INSERT IGNORE INTO semeter (name, year, day_end, day_start, id,startRegisDate,closeRegisDate) VALUES
    (1, 2024, '2024-6-30', '2024-01-01', 1,'2024-05-01','2024-05-11'),
    (2, 2024, '2024-12-01', '2024-07-01', 2,'2024-06-01','2024-06-28');

    
INSERT IGNORE INTO  room (id, name ,capacity) 
VALUES 
    (1, 'A101',40),
    (2, 'A102',40),
    (3, 'A103',20),
    (4, 'A104',60);
    
    
    INSERT IGNORE INTO  subjectlevel (id, name) 
VALUES 
    (1, 'Core'),
    (2, 'Level 100'),
    (3, 'Level 200'),
    (4, 'Level 300');

   
INSERT IGNORE INTO  user (id, address, avatar, email, infomation, name, password, phone, role,status,code) 
VALUES 
    (2, '279 Kinh Duong Vuong Street, An Lac Ward', 'nobita.jpg', 'admin@gmail.com', 'admin information', 'admin', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '095 119 5229', 'ADMIN',0,'AD240001'),
    (3, '256 Hung Vuong, Phuc Yen', 'nobita.jpg', 'student@gmail.com', 'st@gmail.com', 'Nguyễn Văn A', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '098 518 5230', 'STUDENT',0,'ST240001'),
    (4, '20B Phan Chu Trinh St., Tan Thanh Ward', 'nobita.jpg', 'student1@gmail.com', 'Nguyễn Văn B', 'Student Nguyen', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '090 035 1405', 'STUDENT',0,'ST240002'),
    (5, '187B Giang Vo Street', 'nobita.jpg', 'teacher@gmail.com', 'teacher information', 'professor David', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '092 198 8791', 'TEACHER',0,'TC240001'),
    (6, '32 Nguyen An Ninh Street', 'nobita.jpg', 'employee@gmail.com', 'Nguyễn Thái Công', 'employee', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '098 518 5230', 'EMPLOYEE',0,'EM240001'),
    (7, '97B Giang Vo Street', 'nobita.jpg', 'st2@gmail.com', 'student1 information', 'student Hien', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '090 035 1405', 'STUDENT',0,'ST240003'),
    (8, '12A Phan Chu Trinh St., Tan Thanh Ward', 'nobita.jpg', 'teacher2@gmail.com', 'teacher2 information', 'professor LY', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '092 198 8791', 'TEACHER',0,'TC240001'),
    (9, '128 Nguyen An Ninh Street', 'nobita.jpg', 'nguyenthaithanh101104@gmail.com', 'good student', 'Lee', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '095 119 5229', 'STUDENT',0,'ST240004'),
    (10, '128 Nguyen An Ninh Street', 'nobita.jpg', 'student3@gmail.com', 'good student', 'Hoang', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '095 119 5229', 'STUDENT',0,'ST240005'),
	(11, '128 Phan Chu Trinh Street', 'nobita.jpg', 'hoang1231@gmail.com', 'good student', 'Hoang', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '095 119 5229', 'STUDENT',0,'ST240006');
  


INSERT IGNORE INTO  subject (id, name, credit, field_id, subjectlevel_id,type,creditAction) 
VALUES 
    (1, 'Computer Science', '2', '4', '1',0,1),
    (2, 'Software Engineering', '3', '4', '1',1,2),
    (3, 'Network Security', '3', '4', '2',2,1),
    (4, 'Database Management', '1', '4', '2',0,0),
    (5, 'Web Development', '2', '4', '2',2,1),
    (6, 'Artificial Intelligence', '3', '4', '2',0,0),
    (7, 'Cybersecurity', '3', '4', '3',0,0),
    (8, 'Cloud Computing', '4', '4', '3',0,0),
    (9, 'Mobile Application Development', '3', '4', '4',0,0),
    (10, 'Operating Systems', '2', '4', '4',2,1), 
    (11, 'Macroeconomics', '2', '2', '1',0,0),
    (12, 'Microeconomics', '3', '2', '1',0,0),
    (13, 'International Economics', '4', '2', '2',0,0),
    (14, 'Development Economics', '1', '2', '2',0,0),
    (15, 'Econometrics', '2', '2', '2',0,0),
    (16, 'Behavioral Economics', '3', '2', '2',0,0),
    (17, 'Game Theory', '3', '2', '3',0,0),
    (18, 'Financial Economics', '4', '2', '3',0,0),
    (19, 'Labor Economics', '3', '2', '4',0,0),
    (20, 'Environmental Economics', '2', '2', '4',0,0),
    
    (21, 'Ancient History', '2', '3', '1',0,0),
    (22, 'Medieval History', '3', '3', '1',0,0),
    (23, 'Modern History', '4', '3', '2',0,0),
    (24, 'European History', '1', '3', '2',0,0),
    (25, 'American History', '2', '3', '2',0,0),
    (26, 'Asian History', '3', '3', '2',0,0),
    (27, 'African History', '3', '3', '3',0,0),
    (28, 'Art History', '4', '3', '3',0,0),
    (29, 'Military History', '3', '3', '4',0,0),
    (30, 'Political History', '2', '3', '4',0,0),
    
    
    (31, 'Genetics', '2', '1', '1',1,2),
    (32, 'Microbiology', '3', '1', '1',2,1),
    (33, 'Ecology', '4', '1', '2',2,2),
    (34, 'Anatomy', '1', '1', '2',1,1),
    (35, 'Biochemistry', '2', '1', '2',1,2),
    (36, 'Botany', '3', '1', '2',1,3),
    (37, 'Zoology', '3', '1', '3',0,0),
    (38, 'Immunology', '4', '1', '3',0,0),
    (39, 'Evolutionary Biology', '3', '1', '4',0,0),
    (40, 'Physiology', '2', '1', '4',0,0);
    
    
        INSERT IGNORE INTO  user_field (field_id,users_id) 
VALUES 
     (1,2),
     (1,3),
     (1,4),
     (1,5),
     (1,6),
     (1,7);
              
    
    

        INSERT IGNORE INTO  teacher_subject (id, subject_id,teacher_id) 
VALUES 
    (1,36,5),
    (2,1,5),
    (3,11,5),
    (4,12,5),
    (5,31,5),
    (6,34,5),
    (7,39,5),
    (8,38,5),
    (9,37,5),
    (10,35,5),
    (11,40,5);
    

    
    
            INSERT IGNORE INTO  class_subject (currentQuantity, minQuantity, quantity, slotEnd, slotStart, status, weekDay, dateEnd, dateStart, id, room_id, semeter_id, subject_id, teacher_id, description, name, type) 
VALUES 
    (0,5,50,3,1,2,2,'2024-06-30 12:31:29.000000','2024-01-1 12:31:29.000000',1,1,1,36,5,'Class for ...','CPKH24',2),
    (0,5,50,12,10,2,1,'2024-06-30 12:31:29.000000','2024-01-1 12:31:29.000000',2,2,1,1,5,'Class for ...','CPKH4',2),
    (0,5,50,6,4,2,3,'2024-06-30 12:31:29.000000','2024-01-1 12:31:29.000000',3,3,1,2,5,'Class for ...','CPKH3',2),
    (0,5,50,12,7,2,5,'2024-06-30 12:31:29.000000','2024-01-1 12:31:29.000000',4,4,1,11,5,'Class for ...','CPKH2',2),
    (0,5,50,6,1,2,6,'2024-06-30 12:31:29.000000','2024-01-1 12:31:29.000000',5,1,1,12,5,'Class for ...','CPHK1',2);
         
    
    
        INSERT IGNORE INTO  studentclass (status,class_id,id,student_id) 
VALUES 
      (4,1,1,3),
      (4,1,2,4),
      (4,1,3,7),
      (4,1,4,9),
      (4,1,5,10),
      (4,4,6,11),
      (4,2,7,3),
      (4,2,8,4),
      (4,2,9,7),
      (4,3,7,3),
      (4,3,8,4),
      (4,3,9,7);

      
       INSERT IGNORE INTO  quiz (duration, totalMark,createDate,id,subject_id,teacher_id,name,status, type) 
VALUES 
      (60,100,NULL,2,36,5,'Test','Finished',1);
      
      
             INSERT IGNORE INTO  quizquestion (id,mark,quiz_id,type,content) 
VALUES 
  (11,20,2,'Single','<p>What kind of structure is the molecule shown in the diagram?</p>\r\n\r\n<p><img alt=\"\" src=\"https://dru69sbqnarp.cloudfront.net/imgs/Biology/600x600/DNA.png\" style=\"height:300px; width:300px\" /></p>\r\n'),
  (12,20,2,'Single','<p>A mitochondrion is labeled by letter _____________ &nbsp;.</p><p><img alt="" src="https://dru69sbqnarp.cloudfront.net/imgs/Biology/600x300/animal-cell.png" style="height:150px; width:300px" /></p>'),
  (13,20,2,'Single','<p><img alt="" src="https://dru69sbqnarp.cloudfront.net/imgs/Biology/400x200/bird-beak-buzzard.png" style="height:150px; width:300px" /></p><p>Different types of bird beaks are an example of</p>'),
  (14,20,2,'Multi','<p>In the diagram below, which is a decomposer?</p><p><img alt="" src="https://dru69sbqnarp.cloudfront.net/imgs/Biology/600x300/food_web.png" style="height:150px; width:300px" /></p>'),
  (15,20,2,'Multi','<p>Which image below represents the state of the cell after cytokinesis?</p><p><img alt="" src="Which image below represents the state of the cell after cytokinesis?" /><img alt="" src="https://dru69sbqnarp.cloudfront.net/imgs/Biology/600x300/cell-division.png" style="height:150px; width:300px" /></p>');
  
  
         INSERT IGNORE INTO  quizanswer (id,isTrue,quiz_question_id,content) 
VALUES 
      (1,1,11,'double helix'),
      (2,0,11,'multi-strand'),
      (3,0,11,'ladder'),
      (4,0,11,'double vertex'),
      
      (5,1,12,'J'),
      (6,0,12,'K'),
      (7,0,12,'L'),
      (8,0,12,'M'),
      
      (9,1,13,'food webs'),
      (10,0,13,'weathering.'),
      (11,0,13,'learned behaviors.'),
      (12,0,13,'physical adaptations.'),
      
      (13,1,14,'grass'),
      (14,1,14,'mushroom'),
      (15,0,14,'bird'),
      (16,0,14,'snake'),
      
      (17,1,15,'A'),
      (18,1,15,'B'),
      (19,0,15,'C'),
      (20,0,15,'D'),
      (21,0,15,'E'),
      (22,0,15,'F');
      
      
      
      
             INSERT IGNORE INTO  lessonsubject (lesson,class_id,day,id,type) 
VALUES 
 
      ('',1,'2024-06-23 00:00:00',NULL,1),
      ('',2,'2024-06-24 00:00:00',NULL,1),
      ('',3,'2024-06-24 00:00:00',NULL,1),
      ('',4,'2024-06-22 00:00:00',NULL,1),
      ('',5,'2024-06-25 00:00:00',NULL,1);
      
      
      
      INSERT IGNORE INTO  requiredsubject (id, required_subject_id,subject_id,status) 
VALUES 
 
      (1,36,40,'PASS'),
      (2,36,39,'PASS'),
      (3,33,39,'PASS'),
      (4,34,39,'OPTIONAL'),
      (5,35,39,'OPTIONAL');
      
      
      
      
            INSERT IGNORE INTO  marksubject (id,mark, class_id, student_id,subject_id,style) 
VALUES 
 
      (1,95,1,3,36,'normalMark'),
      (2,85,1,3,36,'middleMark'),
      (3,85,1,3,36,'finalMark'),
      (4,90,1,3,36,'final'),
      
      (5,60,1,4,36,'normalMark'),
      (6,70,1,4,36,'middleMark'),
      (7,85,1,4,36,'finalMark'),
      (8,70,1,4,36,'final'),
     
      
      (9,60,2,9,1,'normalMark'),
      (10,60,2,9,1,'middleMark'),
      (11,50,2,9,1,'finalMark'),
      (12,55,2,9,1,'final'),
      
      (13,60,2,4,1,'normalMark'),
      (14,70,2,4,1,'middleMark'),
      (15,80,2,4,1,'finalMark'),
      (16,70,2,4,1,'final'),
      (17,70,5,11,12,'middleMark'),
      (18,80,5,10,12,'middleMark');
      
      
      
--                  INSERT IGNORE INTO  lessonsubject (id,lesson,class_id, day) 
--VALUES 
-- 
--
--      (1,1,1,'2024-01-02'),
--      (2,2,1,'2024-01-09'),
--      (3,3,1,'2024-01-16'),
--      (4,4,1,'2024-01-23'),
--      (5,5,1,'2024-01-30'),
--      (6,6,1,'2024-02-06'),
--      (7,7,1,'2024-02-13'),
--      (8,8,1,'2024-02-20'),
--      (9,9,1,'2024-02-27'),
--      (10,10,1,'2024-03-05'),
--      (11,11,1,'2024-03-12'),
--      (12,12,1,'2024-03-19'),
--      (13,13,1,'2024-03-26'),
--      (14,14,1,'2024-04-02'),
--      (15,15,1,'2024-04-09'),
--      (16,16,1,'2024-04-16'),
--      (17,17,1,'2024-04-23'),
--      (18,18,1,'2024-05-07'),
--      (19,19,1,'2024-05-14'),
--      (20,20,1,'2024-05-21'),
--      (21,21,1,'2024-05-28'),
--      (22,22,1,'2024-06-04'),
--      (23,23,1,'2024-06-11'),
--      (24,24,1,'2024-06-18'),
--      (25,25,1,'2024-06-25');
--
--    
   
    

    
    






    
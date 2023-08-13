-- 휘발성 h2 데이터베이스의 특성상 편의를 위해 data.sql을 사용하여 서버 재기동시 데이터가 자동으로 INSERT되도록 하였다.
-- Spring Boot 2.5 버전 부터 data.sql을 사용한 데이터 초기화를 권장하지 않는다.
-- application.properties에 spring.jpa.defer-datasource-initialization=true 옵션을 주어야 에러가 발생하지 않는다.
INSERT INTO article(title, content) VALUES ('가가가가', '1111');
INSERT INTO article(title, content) VALUES ('나나나나', '2222');
INSERT INTO article(title, content) VALUES ('다다다다', '3333');
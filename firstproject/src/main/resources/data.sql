-- 휘발성 h2 데이터베이스의 특성상 편의를 위해 data.sql을 사용하여 서버 재기동시 데이터가 자동으로 INSERT되도록 하였다.
-- Spring Boot 2.5 버전 부터 data.sql을 사용한 데이터 초기화를 권장하지 않는다.
-- application.properties에 spring.jpa.defer-datasource-initialization=true 옵션을 주어야 에러가 발생하지 않는다.
INSERT INTO article(title, content) VALUES ('가가가가', '1111');
INSERT INTO article(title, content) VALUES ('나나나나', '2222');
INSERT INTO article(title, content) VALUES ('다다다다', '3333');

INSERT INTO article(title, content) VALUES ('당신의 인생 영화는?', '댓글 고');
INSERT INTO article(title, content) VALUES ('당신의 소울 푸드는?', '댓글 고고');
INSERT INTO article(title, content) VALUES ('당신의 취미는?', '댓글 고고고');

INSERT INTO comment(article_id, nickname, body) VALUES (4, 'park', '굿 윌 헌팅');
INSERT INTO comment(article_id, nickname, body) VALUES (4, 'Kim', '아이 엠 샘');
INSERT INTO comment(article_id, nickname, body) VALUES (4, 'Choi', '쇼생크 탈출');

INSERT INTO comment(article_id, nickname, body) VALUES (5, 'park', '치킨');
INSERT INTO comment(article_id, nickname, body) VALUES (5, 'Kim', '샤브샤브');
INSERT INTO comment(article_id, nickname, body) VALUES (5, 'Choi', '초밥');

INSERT INTO comment(article_id, nickname, body) VALUES (6, 'park', '조깅');
INSERT INTO comment(article_id, nickname, body) VALUES (6, 'Kim', '유튜브 시청');
INSERT INTO comment(article_id, nickname, body) VALUES (6, 'Choi', '독서');
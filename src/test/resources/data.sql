DELETE FROM phone_number;
DELETE FROM account;
INSERT INTO account (id, auth_id, username) VALUES
(1,'20S0KPNOIM','azr1'),
(2,'54P2EOKQ47','azr2'),
(3,'9LLV6I4ZWI','azr3'),
(4,'YHWE3HDLPQ','azr4'),
(5,'6DLH8A25XZ','azr5');

INSERT INTO phone_number (id, number, account_id) VALUES
(1,'4924195509198',1),
(2,'4924195509196',1),
(3,'4924195509197',1),
(24,'441224980094',2),
(25,'441224459426',2),
(26,'13605917249',2),
(45,'441873440017',3),
(46,'441970450009',3),
(47,'441235330075',3),
(61,'61881666926',4),
(62,'61871705936',4),
(63,'61871112920',4),
(72,'61871112916',5),
(73,'61881666921',5),
(74,'61871112905',5);
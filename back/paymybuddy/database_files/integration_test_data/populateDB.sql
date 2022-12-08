DELETE FROM `user`;
DELETE FROM `friends`;
DELETE FROM `transaction`;
DELETE FROM `unverified_user`;


INSERT INTO `user` VALUES(1, 0, "user1@example.com", "user", "pwd");
INSERT INTO `user` VALUES(2, 100, "user2@example.com", "john", "pwd1");
INSERT INTO `user` VALUES(3, 1000, "user3@example.com", "jack", "pwd2");
INSERT INTO `user` VALUES(4, 2000, "user4@example.com", "bob", "pwd3");
INSERT INTO `user` VALUES(5, 3000, "user5@example.com", "angus", "pwd4");
INSERT INTO `user` VALUES(6, 4000, "user6@example.com", "ian", "pwd5");
INSERT INTO `user` VALUES(7, 5000, "user7@example.com", "roger", "pwd6");
INSERT INTO `user` VALUES(8, 6000, "user8@example.com", "richie", "pwd7");


-- 初始化用户表
DELETE FROM `lms_user`;
INSERT INTO `lms_user` (id, user_name, password, create_by, create_time, last_modified_by, last_modified_time) VALUES
('U2054420754468048896', 'test1', '$2a$10$HjsHOX7eBuLrpZuVHNEoRerxV18nuH7GFikjIfkvUcgHcvlTMpODG', 'admin', '2026-05-13 12:37:32', 'admin', '2026-05-13 12:37:32'),
('U2054433422587334656', 'test2', '$2a$10$QlSujC2H6U7t1BRlAnwpLe8p4eLutWJEQuOC1NSTrqUATOz8QX28i', 'admin', '2026-05-13 12:37:32', 'admin', '2026-05-13 12:37:32'),
('U2054433453193170944', 'test3', '$2a$10$D8ZuVxdiGq7Vk66SejVUwOP/TseBtBg/cF46.6Mnb8fQqG10gPluq', 'admin', '2026-05-13 12:37:32', 'admin', '2026-05-13 12:37:32'),
('U2054495336747110400', 'test4', '$2a$10$O.ept84jA.mId46hxAT5X.vw9tmmTb3yWDGlDxRhKyAARXBOImWFu', 'admin', '2026-05-13 12:37:32', 'admin', '2026-05-13 12:37:32'),
('U2054744191858249728', 'test5', '$2a$10$iyLMZLIaijKYnVqMlNHbvOIMrQr288ryPi6ZN.Rpo0iczpYSX8ZFm', 'admin', '2026-05-13 12:37:32', 'admin', '2026-05-13 12:37:32'),
('U2054744230764613632', 'admin', '$2a$10$QhSdQ6pRaps7TW43zVr8bO0A6YSZ2Nz9JBJwWxjzzJ5WLxj/p3tQS', 'admin', '2026-05-13 12:37:32', 'admin', '2026-05-13 12:37:32');

-- 初始化权限组表
DELETE FROM `lms_permission_group`;
INSERT INTO `lms_permission_group` (id, group_name, group_code, remark) VALUES
('G2054819434236874752', '用户组', 'USER', '只有查询权限'),
('G2054819434236874753', '管理组', 'MANAGE', '具有增删改查权限');

-- 初始化用户&权限组关系表
DELETE FROM `lms_user_group`;
INSERT INTO `lms_user_group` (id, user_id, group_code) VALUES
('2054819434236874754', 'U2054420754468048896', 'USER'),
('2054819434236874755', 'U2054433422587334656', 'USER'),
('2054819434241069056', 'U2054433453193170944', 'USER'),
('2054819434241069057', 'U2054744230764613632', 'MANAGE');


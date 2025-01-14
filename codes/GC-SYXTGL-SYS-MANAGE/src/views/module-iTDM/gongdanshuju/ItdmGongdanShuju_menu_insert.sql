-- 注意：该页面对应的前台目录为views/itdmgongdanshuju文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('2023052508209910100', NULL, '工单数据', '/itdmgongdanshuju/itdmGongdanShujuList', 'module-iTDM/itdmgongdanshuju/ItdmGongdanShujuList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 1, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2023-05-25 20:20:10', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023052508209910101', '2023052508209910100', '新增', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_gongdan_shuju:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-25 20:20:10', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023052508209910102', '2023052508209910100', '编辑', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_gongdan_shuju:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-25 20:20:10', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023052508209910103', '2023052508209910100', '删除', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_gongdan_shuju:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-25 20:20:10', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023052508209910104', '2023052508209910100', '批量删除', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_gongdan_shuju:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-25 20:20:10', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023052508209910105', '2023052508209910100', '导出excel', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_gongdan_shuju:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-25 20:20:10', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023052508209910106', '2023052508209910100', '导入excel', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_gongdan_shuju:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-25 20:20:10', NULL, NULL, 0, 0, '1', 0);

-- 注意：该页面对应的前台目录为views/btCust文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('2023051502166860000', NULL, '客户表', '/btCust/btCustList', 'btCust/BtCustList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 1, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2023-05-15 14:16:00', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023051502166860001', '2023051502166860000', '添加客户表', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:bt_cust:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-15 14:16:00', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023051502166860002', '2023051502166860000', '编辑客户表', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:bt_cust:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-15 14:16:00', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023051502166860003', '2023051502166860000', '删除客户表', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:bt_cust:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-15 14:16:00', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023051502166860004', '2023051502166860000', '批量删除客户表', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:bt_cust:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-15 14:16:00', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023051502166860005', '2023051502166860000', '导出excel_客户表', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:bt_cust:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-15 14:16:00', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023051502166860006', '2023051502166860000', '导入excel_客户表', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:bt_cust:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-15 14:16:00', NULL, NULL, 0, 0, '1', 0);
-- 注意：该页面对应的前台目录为views/itdmShiYanXiangMu文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external)
VALUES ('2023053010346830390', NULL, '试验项目输入', '/itdmShiYanXiangMu/itdmShiyanxiangmuList', 'module-iTDM/itdmShiYanXiangMu/ItdmShiyanxiangmuList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 1, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2023-05-30 10:34:39', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023053010346840391', '2023053010346830390', '新增', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_shiyanxiangmu:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-30 10:34:39', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023053010346840392', '2023053010346830390', '编辑', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_shiyanxiangmu:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-30 10:34:39', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023053010346840393', '2023053010346830390', '删除', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_shiyanxiangmu:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-30 10:34:39', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023053010346840394', '2023053010346830390', '批量删除', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_shiyanxiangmu:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-30 10:34:39', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023053010346840395', '2023053010346830390', '导出excel', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_shiyanxiangmu:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-30 10:34:39', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023053010346840396', '2023053010346830390', '导入excel', NULL, NULL, 0, NULL, NULL, 2, 'org.jeecg.modules:itdm_shiyanxiangmu:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-05-30 10:34:39', NULL, NULL, 0, 0, '1', 0);

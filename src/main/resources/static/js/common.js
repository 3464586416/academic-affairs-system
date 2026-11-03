/**
 * 公共脚本：登录状态检查、侧边栏权限控制
 */
(function() {
    // 全局拦截 401/403 响应，弹出提示框
    var originalFetch = window.fetch;
    window.fetch = function() {
        return originalFetch.apply(this, arguments).then(function(response) {
            if (response.status === 401) {
                alert('请先登录');
                window.location.href = '/login';
                return response;
            }
            if (response.status === 403) {
                return response.json().then(function(data) {
                    alert(data.message || '权限不足');
                    return response;
                });
            }
            return response;
        });
    };

    // 菜单路径到角色的映射
    var roleMenuMap = {
        '/system/user': ['ADMIN'],
        '/system/dept': ['ADMIN', 'TEACHER'],
        '/system/class': ['ADMIN', 'TEACHER'],
        '/system/semester': ['ADMIN', 'TEACHER'],
        '/system/config': ['ADMIN'],
        '/system/log': ['ADMIN'],
        '/student': ['ADMIN', 'TEACHER'],
        '/teacher': ['ADMIN', 'TEACHER'],
        '/course': ['ADMIN', 'TEACHER'],
        '/classroom': ['ADMIN', 'TEACHER'],
        '/textbook': ['ADMIN', 'TEACHER'],
        '/selection': ['ADMIN', 'TEACHER'],
        '/scheduling': ['ADMIN', 'TEACHER'],
        '/grade': ['ADMIN', 'TEACHER'],
        '/exam': ['ADMIN', 'TEACHER']
    };

    function applyRoleFilter(roles) {
        var isAdmin = roles.indexOf('ADMIN') >= 0;
        if (isAdmin) return; // 管理员全部显示

        // 遍历所有菜单项，根据角色隐藏
        var allMenus = document.querySelectorAll('.menu-group');
        allMenus.forEach(function(group) {
            var items = group.querySelectorAll('.menu-item');
            var hasVisible = false;
            items.forEach(function(item) {
                var url = item.getAttribute('onclick');
                if (!url) return;
                var match = url.match(/'([^']+)'/);
                if (!match) return;
                var path = match[1];

                var allowedRoles = roleMenuMap[path];
                if (allowedRoles) {
                    var hasAccess = false;
                    for (var i = 0; i < allowedRoles.length; i++) {
                        if (roles.indexOf(allowedRoles[i]) >= 0) {
                            hasAccess = true;
                            break;
                        }
                    }
                    if (!hasAccess) {
                        item.style.display = 'none';
                    } else {
                        hasVisible = true;
                    }
                }
            });
            // 隐藏整个组（如果组内没有可见项）
            var groupTitle = group.querySelector('.menu-group-title');
            var visibleCount = 0;
            items.forEach(function(item) {
                if (item.style.display !== 'none') visibleCount++;
            });
            if (visibleCount === 0) {
                group.style.display = 'none';
            }
        });

        // 隐藏dashboard中不可访问的卡片
        var cards = document.querySelectorAll('.dashboard-card');
        cards.forEach(function(card) {
            var link = card.querySelector('a');
            if (!link) return;
            var href = link.getAttribute('href');
            var allowedRoles = roleMenuMap[href];
            if (allowedRoles) {
                var hasAccess = false;
                for (var i = 0; i < allowedRoles.length; i++) {
                    if (roles.indexOf(allowedRoles[i]) >= 0) {
                        hasAccess = true;
                        break;
                    }
                }
                if (!hasAccess) {
                    card.style.display = 'none';
                }
            }
        });
    }

    // 获取角色信息并应用
    if (typeof window.getUserRoles === 'function') {
        window.getUserRoles(function(roles) {
            applyRoleFilter(roles);
        });
    } else {
        // 如果页面没有 getUserRoles，自己获取
        fetch('/api/currentUser').then(function(res) { return res.json(); }).then(function(data) {
            if (data.code === 200 && data.data.roles) {
                applyRoleFilter(data.data.roles);
            }
        });
    }

    // 暴露到全局
    window.applyRoleFilter = applyRoleFilter;
})();

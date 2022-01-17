---
title: MongoDB 创建账户
---

## 创建账户

```
use admin
db.createUser(
  {
    user: "Admin",
    pwd: "admin_password",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ] # 指定数据库
  }
)
```

集群模式使用`root`权限, 避免权限引起功能不可用

```
use admin
db.grantRolesToUser("Admin", ["root"])
```

或者直接创建root用户

```
use admin
db.createUser(
  {
    user: "Admin",
    pwd: "admin_password",
    roles: [ { role: "root", db: "admin" } ]
  }
)
```

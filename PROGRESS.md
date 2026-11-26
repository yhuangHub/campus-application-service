# Verified Progress

## Aufgabe 01 – Studiengang nach ID abfragen

Status: **abgenommen / 验收通过**

- `GET /api/v1/study-programs/{id}` 已按约定实现。
- 响应使用独立 DTO，调用链为 Controller → Service → Repository。
- ID 不存在时返回 `404`、错误码 `STUDY_PROGRAM_NOT_FOUND` 和精确错误消息。
- Service 单元测试、Controller 测试和真实 H2 集成测试均已覆盖成功与失败路径。
- H2 初始数据使用固定 ID，并将 Identity 的下一个值同步为 `4`。
- 本地执行 `mvnw.cmd clean test`：7 个测试全部通过。
- [Pull Request #1](https://github.com/yhuangHub/campus-application-service/pull/1) 的 GitHub Actions `Verify / test` 已通过，并已合并到 `main`（merge commit `a0e5fcb`）。

## Aufgabe 02 – Kapazität ändern

Status: **已发布 / in Bearbeitung**

- 已创建中德文题面：`docs/aufgaben/aufgabe-02-capacity-update`。
- 当前仅发布需求与既有累计代码；容量修改的业务实现和业务测试尚未验收。
- 分支：`aufgabe/02-capacity-update`。

| Aufgabe | Status | Verified evidence |
|---|---|---|
| 01 Study Program Lookup | 验收通过 | 7 个本地测试通过；PR #1 CI 通过并已合并 |
| 02 Capacity Update | 已发布 / 进行中 | 题面已创建；业务实现尚未验收 |
| 03+ | 未创建 | 仅存在于路线图 |

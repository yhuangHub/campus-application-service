# Aufgabe 01：按 ID 查询招生专业

## 建议时间

30–40 分钟，不包含后续 Code Review。

## 业务背景

申请人需要在提交申请前查看一个招生专业的详细信息。系统已经保存三条虚构专业数据，但尚未提供 REST API。

## User Story

> 作为申请人，我希望通过专业 ID 查询招生专业，以便确认专业代码、名称、大学、容量和开放状态。

## 已提供内容

- Java 21 / Spring Boot 项目骨架
- `StudyProgram` JPA Entity
- `StudyProgramRepository`
- 三条 H2 初始数据
- Swagger/OpenAPI 依赖
- 基础启动和种子数据测试

当前没有提供 DTO、Service、Controller、API 异常处理或业务测试。

## REST 接口

```http
GET /api/v1/study-programs/{id}
```

### 成功示例

```http
GET /api/v1/study-programs/1
```

```json
{
  "id": 1,
  "code": "CS-BER-01",
  "name": "Computer Science",
  "university": "Campus University Berlin",
  "capacity": 120,
  "active": true
}
```

状态码：`200 OK`

### 不存在示例

```http
GET /api/v1/study-programs/999
```

```json
{
  "code": "STUDY_PROGRAM_NOT_FOUND",
  "message": "Study program not found: 999"
}
```

状态码：`404 Not Found`

错误 JSON 可以额外包含 `timestamp` 和 `path`，但 `code` 与 `message` 必须精确匹配。

## 验收标准

1. 接口路径和 HTTP 方法与题目一致。
2. ID `1` 返回 `200` 及六个规定字段。
3. 响应使用独立 DTO，不直接序列化 JPA Entity。
4. 数据必须通过 Repository 从真实 H2 数据库读取，不能硬编码。
5. 不存在的 ID 返回 `404`。
6. 404 的 `code` 是 `STUDY_PROGRAM_NOT_FOUND`。
7. 404 的 `message` 是 `Study program not found: {id}`。
8. 调用链为 Controller → Service → Repository。
9. 使用构造器注入，不使用字段注入。
10. 不直接调用 `Optional.get()`。
11. Service 单元测试至少覆盖找到与未找到。
12. Controller 测试至少覆盖 `200` 与 `404`，并断言响应内容。
13. 至少一个不 Mock Service 的集成测试验证真实数据库查询。
14. 现有基础测试继续通过。

## Java 基础考点

- 类与对象的职责
- 构造器注入与 `final` 依赖
- `Optional` 和 `orElseThrow`
- 自定义 Runtime Exception
- Entity 到 DTO 的映射
- Java `record` 是否适合作为响应 DTO
- `==` 与 `equals()` 在对象比较中的区别

## 测试要求

建议至少编写：

- `StudyProgramServiceTest`
  - 找到 ID 1 并正确映射全部字段
  - ID 999 抛出正确异常及消息
- `StudyProgramControllerTest`
  - 200 响应
  - 404 响应及错误字段
- `StudyProgramLookupIntegrationTest`
  - 使用真实 Spring Context、MockMvc 和 H2
  - 不 Mock Service 或 Repository

测试名称应描述行为和结果，而不是只写 `test1`。

## 非目标

本题不要求：

- POST、PUT、DELETE
- 搜索或分页
- Spring Security
- Flyway、PostgreSQL 或 Docker
- 修改 Entity 字段
- 提前实现后续 Aufgabe

## 交卷检查

- [ ] `mvnw.cmd clean test` 全部通过
- [ ] 没有返回 Entity
- [ ] 没有字段注入
- [ ] 没有硬编码成功响应
- [ ] 404 code/message 精确匹配
- [ ] 成功和失败路径都有测试
- [ ] 没有实现 Aufgabe 02 之后的功能

完成后提交当前分支并说“交卷”。


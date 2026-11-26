# Aufgabe 02：修改招生专业容量

## 建议时间

45–60 分钟，不包含后续 Code Review。

## 业务背景

招生办公室需要在招生计划调整后修改某个专业的可用名额。Aufgabe 01 的查询接口已经验收，本题在同一项目中增加一个写操作，并保持现有查询功能不变。

## User Story

> 作为招生办公室工作人员，我希望修改一个已有招生专业的容量，以便查询接口立即返回最新的招生名额。

## 已提供内容

- Aufgabe 01 已验收的完整功能和测试
- `StudyProgram` Entity 与 `StudyProgramRepository`
- 三条 H2 初始数据
- Spring Validation、Spring Data JPA、MockMvc 和测试依赖
- 统一的 404 错误合同

本题不提供容量修改的 DTO、Controller 方法、Service 业务实现、Entity 修改方法或业务测试。这些内容由学习者完成。

## REST 接口

```http
PUT /api/v1/study-programs/{id}/capacity
Content-Type: application/json
```

### 成功示例

```http
PUT /api/v1/study-programs/1/capacity
```

```json
{
  "capacity": 150
}
```

响应状态：`200 OK`

```json
{
  "id": 1,
  "capacity": 150
}
```

随后调用 `GET /api/v1/study-programs/1` 时，`capacity` 必须为 `150`，其他字段保持不变。

### 边界规则

允许的容量范围是闭区间 `1..1000`：

- `1` 和 `1000` 是有效值。
- `0`、负数和 `1001` 是无效值。
- `capacity` 缺失或为 `null` 也是无效请求。

无效请求返回 `400 Bad Request`：

```json
{
  "status": 400,
  "code": "INVALID_CAPACITY",
  "message": "Capacity must be between 1 and 1000"
}
```

`code` 与 `message` 必须精确匹配。验证失败时，数据库中的原容量不能改变。

### 专业不存在

对不存在的 ID，例如 `999`，返回 Aufgabe 01 已有的 `404 Not Found` 合同：

```json
{
  "code": "STUDY_PROGRAM_NOT_FOUND",
  "message": "Study program not found: 999"
}
```

错误 JSON 可以继续包含既有的 `status` 字段。

## 验收标准

1. HTTP 方法和路径精确为 `PUT /api/v1/study-programs/{id}/capacity`。
2. 请求与响应使用独立、不可变的 DTO；不能把 JPA Entity 作为请求或响应对象。
3. 成功响应只包含 `id` 和更新后的 `capacity`，状态为 `200`。
4. 允许范围为 `1..1000`，两个边界值都能成功保存。
5. `0`、负数、`1001`、缺失值和 `null` 返回规定的 `400` 错误。
6. 无效请求不修改数据库。
7. 不存在的专业继续返回规定的 `404` 错误。
8. 调用链为 Controller → Service → Repository；Controller 不直接访问 Repository。
9. 写事务边界位于 Service 层；Controller 不使用 `@Transactional`。
10. Entity 通过具有业务含义的方法修改容量，不新增公开的通用 `setCapacity`。
11. 更新必须持久化，并能通过 Aufgabe 01 的 GET 接口读到。
12. 使用构造器注入；新依赖声明为 `final`。
13. PUT 对相同请求具有幂等效果：重复设置相同容量不会产生不同业务结果。
14. Aufgabe 01 的全部测试继续通过。
15. 新增 Service、Controller 和真实数据库集成测试，覆盖成功路径与重要失败路径。

## Java 考点

- `int` 与 `Integer` 的差异，以及为什么请求字段需要表达 `null`
- Java `record` 或等价不可变类作为 Request/Response DTO
- Bean Validation 的 `@NotNull`、`@Min`、`@Max`
- 封装：用业务方法维护 Entity 状态，而不是暴露通用 Setter
- `@Transactional` 的事务边界与 JPA Dirty Checking
- PUT 的幂等语义
- 边界值分析：最小值、最大值和边界外值

## 测试要求

至少覆盖：

- Service 单元测试
  - 已有 ID 成功更新并返回新容量
  - 不存在的 ID 抛出既有 `StudyProgramNotFoundException`
  - 验证 Repository 交互和 Entity 状态变化
- MockMvc Controller 测试
  - 有效请求返回 `200` 及精确 JSON
  - `0`、`1001` 和 `null` 返回 `400` 及精确错误合同
  - 不存在的 ID 返回既有 `404` 合同
- Spring Boot 集成测试
  - 不 Mock Service 或 Repository
  - 执行 PUT 后再执行 GET，确认真实 H2 数据已更新
  - 验证至少一个有效边界值
  - 验证无效请求后数据库值保持不变

测试之间必须隔离。可以使用测试事务回滚或在每个测试前恢复数据，但不能依赖测试执行顺序。

## 非目标

本题不要求：

- 修改专业代码、名称、大学或开放状态
- 创建、删除或搜索专业
- 批量更新容量
- 乐观锁、并发冲突处理或审计日志
- Spring Security、外部数据库、Flyway、Docker 或 Kafka
- 提前实现 Aufgabe 03 之后的内容
- 为格式错误的 JSON 或非数字 JSON 值设计新的错误合同

## 交卷清单

- [ ] `mvnw.cmd clean test` 全部通过
- [ ] PUT 路径、状态码和 JSON 合同精确匹配
- [ ] DTO 不可变，且没有直接暴露 Entity
- [ ] `1`、`1000`、`0`、`1001` 和 `null` 均有明确测试
- [ ] 写事务位于 Service 层
- [ ] Entity 没有新增公开的 `setCapacity`
- [ ] PUT 后的 GET 能读到新容量
- [ ] 无效请求不会改变数据库
- [ ] Aufgabe 01 的功能与测试没有被破坏
- [ ] 没有实现后续 Aufgabe

完成后提交当前分支并说“交卷”。

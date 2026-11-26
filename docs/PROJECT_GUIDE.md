# 项目架构、练习路线与设计说明

本文档记录 `campus-application-service` 的整体练习构想、架构选择和教学理由。它不是某一道 Aufgabe 的答案，也不会提前给出后续题目的业务实现。

## 1. 项目目标

这是一个长期演进的 Java 后端面试练习项目。业务背景模拟高校申请、审核、排名、名额分配和录取流程，但所有学校、人员和数据均为虚构。

项目同时追求三个目标：

1. 重新练熟 `GET`、`PUT`、`POST`、`DELETE` 等 REST 基础。
2. 在同一个 Spring Boot 项目中逐步进入更真实的申请与录取业务。
3. 把 Java 基础知识自然地放进业务代码、测试和面试问答中，而不是另外堆放没有业务意义的算法题。

最终希望形成的能力不是“记住几个注解”，而是能够理解需求、设计接口、划分职责、实现业务、处理异常、编写测试并解释自己的技术选择。

## 2. 为什么采用一个持续演进的项目

所有 Aufgabe 都放在同一个 Repository 和同一个 Spring Boot 应用中。每完成一题，已经验收的功能会成为下一题的基础。

这样设计有几个原因：

- 可以反复练习真实开发流程：阅读需求、建分支、实现、测试、Commit、Push、Pull Request 和 Code Review。
- 后续修改必须保持旧功能继续工作，从而练习回归测试和兼容性。
- 可以看到项目如何从一个很小的 CRUD 服务逐渐发展成包含业务规则、事务和多个模块的应用。
- Java 基础不再是孤立知识点，而是为解决具体业务问题服务。
- 每次只面对一个小 Aufgabe，认知负担可控，同时又不会失去大项目的连续性。

路线图只描述方向。除当前 Aufgabe 外，后续题面、骨架和测试不会提前创建；只有当前题验收后并且学习者明确要求，才发布下一题。

## 3. 两阶段演进路线

### Phase 1：REST 基础巩固

第一阶段围绕 `StudyProgram`（招生专业）逐步完成一组相关接口：

| Aufgabe | 主要目标 | REST 能力 | Java 能力 |
|---|---|---|---|
| 01 | 按 ID 查询专业 | GET、路径参数、200/404 | Optional、Record、异常、Entity 到 DTO 映射 |
| 02 | 修改专业容量 | PUT、请求体、校验 | 数值边界、不可变 DTO、事务 |
| 03 | 查询和筛选专业 | GET、查询参数 | List、String、Stream 映射、Comparator |
| 04 | 创建专业 | POST、201、输入校验 | 字符串规范化、equals/hashCode、Validation |
| 05 | 删除专业 | DELETE、204、冲突处理 | 状态判断、异常流程、测试隔离 |

这些题彼此关联，但每一题都有独立、清晰的验收范围。Aufgabe 01 建立查询链路；后续题继续使用相同领域对象和架构约定，逐步补全 CRUD。学习者不需要每题重新创建项目，也不应该提前实现尚未发布的接口。

第一阶段结束时，应能够比较熟练地解释并实现：

- Controller、Service、Repository 的职责。
- DTO 与 Entity 为什么需要分开。
- 常用 HTTP 方法、状态码和错误响应。
- Bean Validation、异常映射和输入边界。
- 单元测试、Controller 测试、Repository 测试和集成测试的区别。

### Phase 2：申请、审核与录取业务

第二阶段在已有 REST 基础上引入更真实的业务能力：

1. 创建申请草稿。
2. 提交和撤回申请。
3. 搜索申请并更新申请人信息。
4. 管理材料状态。
5. 审核申请。
6. 生成排名。
7. 根据名额生成录取结果。
8. 接受或拒绝录取。
9. 记录审计事件。
10. 最后再引入 PostgreSQL、Flyway、Testcontainers、Docker、Actuator 和 CI 的扩展。

这一阶段的重点不再只是 CRUD，而是状态转换、业务不变量、事务边界、幂等性、并发和模块协作。

## 4. 架构思路

项目采用“面向功能的模块化单体”：只有一个可以部署的 Spring Boot 应用，但代码按业务能力分包。

```text
HTTP / Swagger
      |
      v
Controller + DTO + Validation
      |
      v
Application Service + Transaction
      |
      +----> Domain model
      |
      v
Repository -> Database
```

计划中的功能模块包括：

```text
de.yhuang.campusapplication
├── studyprogram
├── applicant       (later)
├── application     (later)
├── document        (later)
├── review          (later)
├── ranking         (later)
├── admission       (later)
├── audit           (later)
└── shared          (only when truly shared)
```

当前只创建实际需要的包。不会为了看起来像“大项目”而提前建立大量空目录、抽象层或微服务。

核心依赖规则：

- Controller 负责 HTTP 合同和参数接收，通过 Service 完成用例，不直接调用 Repository。
- REST 接口返回 DTO，不直接序列化 JPA Entity。
- Service 负责编排业务用例和事务边界。
- Repository 负责持久化和数据库查询。
- 存在业务状态变化时，应优先用有业务含义的方法表达，而不是在外部随意修改字段。
- 一个功能模块不能调用另一个模块的 Controller。
- `shared` 只保存真正共享的基础设施，不能成为杂物目录。

选择模块化单体是为了在当前学习阶段保留清晰边界，同时避免微服务带来的网络、部署和分布式事务复杂度。Security、Kafka、Keycloak 和微服务都不是当前阶段目标。

## 5. 每一道 Aufgabe 的结构

每道正式题目使用中文和德文两份 Markdown，包含：

- 业务背景和 User Story。
- 明确的 REST 接口和示例。
- 可逐项检查的验收标准。
- 本题自然涉及的 Java 基础考点。
- 测试要求。
- 明确的非目标，防止范围不断扩大。
- 交卷检查清单。

必要骨架只提供启动项目所需的结构、初始数据以及极少量骨架测试。业务 DTO、Service、Controller、异常处理和业务测试原则上由学习者完成。

默认协作方式是“考官和 Reviewer”：

- 不主动实现学习者负责的业务代码。
- 只有学习者提出要求时才给提示。
- 提示分三级：概念方向、代码位置/机制、局部示例。
- 除非明确要求完整答案，否则不提供完整业务实现。
- 学习者说“交卷”后，才按验收标准检查代码、运行测试、给出具体问题，并提出几道德语面试问题。

## 6. Aufgabe 之间如何衔接

每完成一道题，流程如下：

1. 学习者完成业务代码和测试。
2. 运行完整测试，确认旧功能没有被破坏。
3. Commit 并 Push 当前分支。
4. 创建 Pull Request 或提交 Review。
5. 按验收标准检查，而不是只看测试是否为绿色。
6. 修正问题并再次验证。
7. 验收后合并到 `main`，更新 `PROGRESS.md`。
8. 学习者明确要求后，再创建下一道 Aufgabe 的题面和必要骨架。

因此，Aufgabe 01 完成后不会重新建立项目。下一题从已验收的代码继续演进，并且 Aufgabe 01 的测试必须一直保持通过。

## 7. Java 基础如何融入业务

Java 基础不会集中在一章里一次考完，而是随业务复杂度逐步出现：

| Java 主题 | 在项目中的应用 |
|---|---|
| 类、对象、封装、构造器 | Entity、Service、业务对象和依赖注入 |
| 基本类型与包装类型 | ID、容量、空值语义和数据库映射 |
| String | 代码和名称的校验、规范化、搜索 |
| Optional | Repository 查询结果和不存在分支 |
| Exception | 404、409 和业务规则失败 |
| Record 与不可变对象 | Request/Response DTO、审计事件 |
| List、Set、Map | 搜索结果、材料、分组和名额分配 |
| equals/hashCode | 去重、集合行为和业务身份 |
| Stream | DTO 映射、适当的分组和聚合 |
| Comparator | 搜索排序和录取排名 |
| Enum | 申请、材料和录取状态 |
| Interface 与多态 | 审核规则和 Strategy |
| BigDecimal | 分数或权重计算 |
| Java Time、Instant、Clock | 提交时间、截止时间和可测试时间逻辑 |
| 泛型 | Repository、集合和通用返回结构的理解 |
| 事务 | 多步骤业务操作的一致性 |
| 并发与乐观锁 | 名额竞争和重复操作 |

原则是：能由数据库完成的过滤，不为了练 Stream 而先 `findAll()`；与业务无关的算法题不塞进生产代码。必要时可以在面试问答或单独的小测试中补充纯 Java 讨论。

## 8. 测试策略

测试是验收证据的一部分，但“现有测试通过”不等于题目已经完成。

项目根据风险选择不同层次的测试：

- Unit Test：验证纯 Java 逻辑和 Service 行为。
- MockMvc Controller Test：验证路径、状态码、校验和 JSON 合同。
- Repository Test：验证真实 JPA 查询。
- Integration Test：验证 Spring Context、数据库、事务和完整调用链。

每道 Aufgabe 都必须覆盖成功路径和重要失败路径。后续 Aufgabe 还必须保持已经验收的旧测试继续通过。

Reviewer 会同时检查：

- 验收标准是否全部满足。
- 是否存在硬编码、错误分层或不必要复杂度。
- 异常和边界条件是否明确。
- 测试是否真正验证行为，而不是只为了覆盖行数。
- 学习者能否解释关键技术选择。

## 9. 文档职责与跨设备继续

- `README.md`：项目入口、启动方式和当前 Aufgabe。
- `ROADMAP.md`：长期演进方向，不代表后续题目已经发布。
- `docs/architecture/ARCHITECTURE.md`：正式架构约定。
- 本文档：整体学习构想、题目关系和设计理由。
- `CONTEXT.md`：供新对话快速理解稳定背景。
- `PROGRESS.md`：只记录经过验证的完成情况和当前题目。
- `AGENTS.md`：约束后续考官/Reviewer 的工作方式。

切换电脑或开启新的 Codex 对话时，应先 Clone/Pull GitHub 仓库，然后要求读取上述文件、当前 Aufgabe 和当前代码。聊天记录可以作为补充，但 GitHub 中的代码与文档才是项目的长期事实来源。

## 10. 当前状态

Aufgabe 01：按 ID 查询招生专业，已经验收并通过 Pull Request #1 合并到 `main`。它的查询接口、DTO、Service、异常处理和测试会继续作为回归基线。

当前正式发布的是 Aufgabe 02：修改招生专业容量。题目要求实现 `PUT /api/v1/study-programs/{id}/capacity`，重点是数值边界、不可变 DTO、校验和 Service 事务。容量修改的业务代码与业务测试仍由学习者实现；Aufgabe 03 之后的内容只存在于 `ROADMAP.md` 中。

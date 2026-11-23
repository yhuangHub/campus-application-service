# Project Roadmap

路线图用于说明项目将如何演进。除当前 Aufgabe 外，后续内容只是方向，不是已经发布的题目；实际范围可以根据学习进度拆分或调整。

## Phase 1 – REST 基础巩固

| Aufgabe | Thema | REST | Java-Schwerpunkt |
|---|---|---|---|
| 01 | Studiengang nach ID abfragen | `GET /api/v1/study-programs/{id}` | Optional、Record、异常、映射 |
| 02 | Kapazität ändern | `PUT /api/v1/study-programs/{id}/capacity` | 数值边界、不可变 DTO、事务 |
| 03 | Studiengänge suchen | `GET /api/v1/study-programs` | List、String、Stream 映射、Comparator |
| 04 | Studiengang anlegen | `POST /api/v1/study-programs` | String 规范化、equals/hashCode、校验 |
| 05 | Studiengang löschen | `DELETE /api/v1/study-programs/{id}` | 异常流程、状态验证、测试隔离 |

## Phase 2 – Bewerbung und Zulassung

| Aufgabe | Thema | Schwerpunkt |
|---|---|---|
| 06 | Bewerbungsentwurf anlegen | OOP、构造器、Enum、多实体关系 |
| 07 | Bewerbung einreichen | 状态转换、Instant、Clock、事务 |
| 08 | Bewerbung zurückziehen | 状态机、幂等性、业务异常 |
| 09 | Bewerbungen suchen | 分页、组合筛选、结果映射 |
| 10 | Bewerberdaten ändern | PATCH、不可变 DTO、Optional 字段 |
| 11 | Dokumentenstatus verwalten | List、Set、equals/hashCode、一对多 |
| 12 | Bewerbung prüfen | 接口、多态、Strategy |
| 13 | Rangliste erstellen | BigDecimal、Comparator、分组、复杂度 |
| 14 | Zulassungsangebote erzeugen | Map、Top N、多实体事务 |
| 15 | Angebot entscheiden | 乐观锁、竞态条件、幂等性 |
| 16 | Änderungen protokollieren | 不可变事件、Java Time、审计 |
| 17 | Datenbank produktionsnah machen | PostgreSQL、Flyway、Testcontainers |
| 18 | Betrieb und CI erweitern | Docker、Actuator、Profiles、CI |

Security、Keycloak、Kafka 和微服务不是当前路线的一部分，只有在明确请求后才会作为后续阶段设计。


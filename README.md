# Campus Application Service

一个持续演进的 Java 21 / Spring Boot 面试练习项目，用虚构的高校申请与录取流程练习 REST、JPA、事务、测试和 Java 基础。

## 当前练习

- [Aufgabe 01 中文题面](docs/aufgaben/aufgabe-01-study-program-lookup/AUFGABE_ZH.md)
- [Aufgabe 01 Deutsche Aufgabenstellung](docs/aufgaben/aufgabe-01-study-program-lookup/AUFGABE_DE.md)

当前只提供 `StudyProgram` Entity、Repository、H2 初始数据和基础测试。业务 DTO、Service、Controller、异常处理及业务测试由学习者完成。

## 启动

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

应用启动后：

- Swagger UI: <http://localhost:8080/swagger-ui.html>
- H2 Console: <http://localhost:8080/h2-console>

基础测试通过只表示项目骨架和初始数据可用，不表示当前 Aufgabe 已完成。

## 工作方式

1. 从 `main` 创建当前 Aufgabe 分支，例如 `aufgabe/01-study-program-lookup`。
2. 独立完成业务实现和测试。
3. Push 并创建 Pull Request。
4. 交卷后按验收标准进行 Code Review。
5. 验收通过后 Merge，并建立完成标签。

完整路线见 [ROADMAP.md](ROADMAP.md)，架构约定见 [ARCHITECTURE.md](docs/architecture/ARCHITECTURE.md)。所有学校、人员及数据均为虚构。


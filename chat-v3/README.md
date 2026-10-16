# ChatGPT Snapshot Pipeline - V2 (Multithreaded)

Java 17 + Spring Boot + Jackson implementation of the Node.js snapshot processing pipeline.

## V2 improvements

- Split the monolithic pipeline into configuration, repository, and service classes.
- Externalized paths and execution options in `application.properties`.
- Reused one Spring-managed `ObjectMapper`.
- Supports both `coversationNames` (existing misspelling) and `conversationNames`.
- Uses `Path.resolve()` rather than manual path concatenation.
- Validates JSON input and required snapshot fields.
- Supports one conversation JSON file or a directory containing `conversations-*.json` shards.
- Writes all outputs for each snapshot in one pass.
- Adds unit tests for conversation traversal, system-message filtering, QnA mapping, and UTC date grouping.
- Processes independent snapshots concurrently using a bounded fixed thread pool.
- Configurable worker count through `snapshot.threads`.
- Reports the worker thread and processing time for each completed snapshot.
- Validates duplicate output slugs before starting workers to prevent output collisions.
- Continues processing other snapshots when one fails (configurable).

## Requirements

- JDK 17+
- Maven 3.9+ (or use the included Maven Wrapper if available)

## Configuration

Edit `src/main/resources/application.properties`:

```properties
snapshot.config-path=D:/path/to/snapshots.json
snapshot.output-root=D:/v-dir-v2
snapshot.created-by=Prem
snapshot.run-on-startup=true
snapshot.continue-on-error=true\nsnapshot.threads=4
```

The input `snapshots.json` is expected to contain:

```json
{
  "LATEST_CONVERSATION_FILE": "conversations-001.json",
  "baseDir": "D:/chatgpt-export/",
  "coversationNames": {
    "snapshot-id-1": "conversations-001.json",
    "snapshot-id-2": "some-conversation-directory"
  }
}
```

The property `coversationNames` is intentionally supported for compatibility with the existing input. The corrected `conversationNames` spelling is also accepted.

Each `coversationNames` value is resolved against `baseDir`. The generated `base.json` contains absolute normalized source paths.

## Multithreading

The pipeline parallelizes at the **snapshot level**: each worker reads one snapshot,
builds its outputs, and writes into that snapshot's own `itr2/<slug>` directory.

Configure the maximum number of concurrent workers:

```properties
snapshot.threads=4
```

The effective worker count is `min(snapshot.threads, number of snapshots)`.
Use `1` to disable parallel processing. Avoid setting this higher than your machine
can comfortably support; JSON parsing and file I/O can use significant memory and disk bandwidth.

Snapshots must have unique `slug` values because each slug identifies an output directory.
Duplicate slugs are rejected before any worker starts.

When multiple snapshot rows point to the same source directory, access to that
directory's `merged-conversations.json` is synchronized to prevent concurrent writes.

## Generated output

```text
D:/v-dir-v2/
├── base.json
└── itr2/
    ├── v1/
    │   ├── index.json
    │   ├── conversations.json
    │   ├── message.json
    │   ├── message.contents.json
    │   ├── messageContentsMap.json
    │   ├── qNa.json
    │   └── datewiseMessages.json
    └── v2/
        └── ...
```

When a source location is a directory, files matching `conversations-*.json` are merged into `merged-conversations.json` inside that source directory.

## Run

```bash
mvn clean test
mvn spring-boot:run
```

Or package and run:

```bash
mvn clean package
java -jar target/chat-v2-2.0.0.jar
```

## Disable automatic processing

```properties
snapshot.run-on-startup=false
```

## Output compatibility notes

- Message traversal follows `current_node` through `parent` links and reverses the result.
- Only text messages are included.
- System messages are excluded unless `metadata.is_user_system_message` is true.
- Unix timestamps are written as ISO-8601 UTC strings. A zero timestamp is treated as null, matching the original JavaScript truthy check.
- Date-wise grouping uses UTC dates to match `new Date(value).toISOString().split('T')[0]`.

## Package structure

```text
com.p.chat
├── ChatApplication.java
├── config
│   ├── JacksonConfiguration.java
│   └── SnapshotProperties.java
├── model
│   └── SnapshotOutput.java
├── repository
│   └── JsonFileRepository.java
└── service
    ├── ConversationService.java
    ├── DatewiseMessageService.java
    ├── MessageService.java
    ├── QuestionAnswerService.java
    ├── SnapshotConfigService.java
    └── SnapshotService.java
```

package com.p.chat;

import com.p.chat.config.SnapshotProperties;
import com.p.chat.service.SnapshotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SnapshotProperties.class)
public class ChatApplication implements CommandLineRunner {

    private final SnapshotService snapshotService;
    private final SnapshotProperties properties;

    public ChatApplication(SnapshotService snapshotService, SnapshotProperties properties) {
        this.snapshotService = snapshotService;
        this.properties = properties;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (!properties.isRunOnStartup()) {
            System.out.println("Snapshot pipeline is disabled. Set snapshot.run-on-startup=true to enable it.");
            return;
        }

        snapshotService.run();
    }
}

package com.example.login.util;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IpLocationUtils {

    private static Searcher searcher;

    static {
        try {
            // Load xdb from classpath
            ClassPathResource resource = new ClassPathResource("ip2region.xdb");
            if (resource.exists()) {
                InputStream inputStream = resource.getInputStream();
                // Load whole xdb into memory, it's about 11MB
                byte[] cBuff = FileCopyUtils.copyToByteArray(inputStream);
                searcher = Searcher.newWithBuffer(cBuff);
                log.info("Ip2region xdb loaded successfully");
            } else {
                log.warn("Ip2region xdb file not found in classpath");
            }
        } catch (Exception e) {
            log.error("Failed to load ip2region xdb file", e);
        }
    }

    public static String getRegion(String ip) {
        if (searcher == null) {
            return "Unknown";
        }
        // Handle localhost explicitly just in case
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            return "本地局域网";
        }

        try {
            long startTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - startTime);
            // Result format: Country|Region|Province|City|ISP
            return region;
        } catch (Exception e) {
            log.warn("Failed to search IP region for {}: {}", ip, e.getMessage());
            return "Unknown";
        }
    }
}

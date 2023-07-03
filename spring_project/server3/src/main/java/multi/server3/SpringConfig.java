package multi.server3;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class SpringConfig {
    @Value("${curator.connection-string}")
    private String connectionString;

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("end");
    }

    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework() {
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectionString, new ExponentialBackoffRetry(1000, 3));
        client.start();
        return client;
    }

    @Bean
    public InterProcessMutex interProcessMutex(CuratorFramework client) {
        String lockPath = "/lock";
        try {
            client.checkExists().creatingParentsIfNeeded().forPath(lockPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new InterProcessMutex(client, lockPath);
    }
}

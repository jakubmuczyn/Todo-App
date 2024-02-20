package pl.jakubmuczyn;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
    
    }
}

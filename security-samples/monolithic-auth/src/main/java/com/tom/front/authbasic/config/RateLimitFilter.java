package com.tom.front.authbasic.config;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class RateLimitFilter extends OncePerRequestFilter {

	@Value("${application.rate.limit:10}")
	private int rateLimit;
	
	@Value("${application.rate.refil.time:30s}")
	private Duration timeLimit;

	private final Cache rateLimitCache;

	public RateLimitFilter(CacheManager cacheManager,
            @Value("${spring.cache.cache-names}") String cacheName) {
		
		this.rateLimitCache = cacheManager.getCache(cacheName);
		
		if(this.rateLimitCache == null) {
			throw new IllegalArgumentException("Cache not exists: " + cacheName);
		}
	}
	
    private Bucket createNewBucket() {
    	Bandwidth limit = Bandwidth.builder()
    			.capacity(rateLimit)
    			.refillGreedy(rateLimit, timeLimit)
    			.build();
    	
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
    								@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();
        Bucket bucket = this.rateLimitCache.get(ip, this::createNewBucket);
        
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
        	response.setContentType("text/plain");
            response.setStatus(429);
            response.getWriter().write("Too many requests - try again later.");
        }
    }
	
}

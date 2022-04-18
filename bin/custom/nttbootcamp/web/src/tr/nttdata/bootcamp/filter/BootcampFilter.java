package tr.nttdata.bootcamp.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BootcampFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(BootcampFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOG.info("Entered filter for request {} [{}]", request.getRequestURI(), request.getMethod());
        filterChain.doFilter(request, response);
    }

}
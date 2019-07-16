package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CharacterEncodingFilter", urlPatterns = "/*")
public class CharacterEncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req ;
        HttpServletResponse response = (HttpServletResponse) resp;
        request.setCharacterEncoding("utf-8");
        String requestURI =request.getRequestURI();
        if(! isTextHtml(requestURI)){
            response.setContentType("text/html ; charset = utf-8");
        }
        //是否包含静态资源文件  图片 js css
        chain.doFilter(request, response);
    }

    private boolean isTextHtml(String requestURI) {
        return requestURI.contains(".jpg") || requestURI.contains(".jpeg")
                || requestURI.contains(".png") || requestURI.contains(".js") || requestURI.contains(".css");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

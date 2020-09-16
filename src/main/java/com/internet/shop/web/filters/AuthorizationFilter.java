package com.internet.shop.web.filters;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final String USER_ID = "userId";
    private static final Injector injector = Injector.getInstance("com.internet.shop");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private Map<String, List<Role.RoleName>> protectedUrl = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrl.put("/users/all", List.of(Role.RoleName.ADMIN));
        protectedUrl.put("/users/delete", List.of(Role.RoleName.ADMIN));
        protectedUrl.put("/admin/orders", List.of(Role.RoleName.ADMIN));
        protectedUrl.put("/orders/admin/delete", List.of(Role.RoleName.ADMIN));
        protectedUrl.put("/admin/products", List.of(Role.RoleName.ADMIN));
        protectedUrl.put("/products/admin/delete", List.of(Role.RoleName.ADMIN));
        protectedUrl.put("/products/add", List.of(Role.RoleName.ADMIN));
        protectedUrl.put("/shopping-carts/products", List.of(Role.RoleName.USER));
        protectedUrl.put("/shopping-carts/products/add", List.of(Role.RoleName.USER));
        protectedUrl.put("/shopping-carts/products/delete", List.of(Role.RoleName.USER));
        protectedUrl.put("/user/orders", List.of(Role.RoleName.USER));
        protectedUrl.put("/orders/complete-order", List.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getServletPath();
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.get(userId);
        if (!protectedUrl.containsKey(url) || isAuthorized(user, protectedUrl.get(url))) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, List<Role.RoleName> protectedRoleNames) {
        for (Role.RoleName protectedRoleName : protectedRoleNames) {
            for (Role userRole : user.getRoles()) {
                if (protectedRoleName.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}

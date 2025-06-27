package br.com.avaliacaogames.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);

        // Define as rotas e recursos que são sempre públicos
        boolean isLoginPage = uri.endsWith("index.jsp") || uri.endsWith("/login");
        boolean isPublicRegisterPage = uri.endsWith("/formCadastro.jsp");
        boolean isRegisterAction = uri.endsWith("/register"); // Nova rota pública
        boolean isVerificaUser = uri.endsWith("/verifica-username");
        boolean isResource = uri.contains("/css/") || uri.contains("/js/") || uri.contains("/images/");

        if (isLoggedIn || isLoginPage || isPublicRegisterPage || isRegisterAction || isVerificaUser || isResource) {
            // Permite o acesso
            chain.doFilter(request, response);
        } else {
            // Bloqueia e redireciona para o login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
        }
    }
}

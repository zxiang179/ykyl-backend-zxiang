package edu.ecnu.ykyl.web.websocket.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.replyentity.ReplyPKUser;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

	private ReplyPKUser pkUser = null;
	private User user = null;
	private Map<String, Object> pkResultMap2WS = null;

	/**
	 * 在创建websocket连接时，获取登录的session对象
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {

		System.out.println("beforeHandshake start======");
		System.out.println(request.getClass().getName());
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest()
					.getSession(false);
			if (session != null) {
				
				// 拦截该用户的信息存入PKUser中
				pkUser = new ReplyPKUser();
				pkUser = (ReplyPKUser) session
						.getAttribute(Constants.SESSION_USER);
				attributes.put(Constants.SESSION_USER, pkUser);
				
				// 拦截该用户的信息存入User中
				user = new User();
				user = (User)session.getAttribute(Constants.LOGIN_USER);
				attributes.put(Constants.LOGIN_USER, user);

				// 拦截该用户的PK结果并存入wssession
				pkResultMap2WS = new HashMap<String, Object>();
				pkResultMap2WS = (Map<String, Object>) session
						.getAttribute(Constants.PKResultMap2WS);
				if (pkResultMap2WS != null) {
					attributes.put(Constants.PKResultMap2WS, pkResultMap2WS);
				}

				System.out.println("httpSession is not null");
			} else {
				System.out.println("httpSession is null");
			}
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		System.out.println("After Handshake");
	}

}

package edu.ecnu.ykyl.web.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import edu.ecnu.ykyl.web.websocket.session.WebSocketHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements
		WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemWebSocketHandler2(), "/webSocketServer")
        		//SystemWebSocketHandler为后端WebSocket的API，
		        //"/webSocketServer"表示前端页面需要请求WebSocket时所需的url
				.addInterceptors(new WebSocketHandshakeInterceptor())
				//设置websocket拦截器，WebSocketHandshakeInterceptor为WebSocket建立连接时的拦截规则
				//连接102wifi，使用webstorm本地测试
//				.setAllowedOrigins("http://localhost:8081");//设置跨域
				//阿里云部署
		        .setAllowedOrigins("http://101.132.75.130:8080");
//				.setAllowedOrigins("http://localhost:63342");
				//本地测试
//				.setAllowedOrigins("http://localhost:8888");//设置跨域
				//连接102wifi，使用webstorm异地测试
//				.setAllowedOrigins("http://192.168.1.100:8081");
//		 .setAllowedOrigins("http://192.168.1.132:8080");
//				.setAllowedOrigins("http://192.168.1.132:8081");
		//http://192.168.1.132:8081/ykyl-ui-yu/src/app/login/login-home.html#/user
	}

	@Bean
	//创建WebSocket对象后WebSocket服务所提供的API操作
	public SystemWebSocketHandler systemWebSocketHandler() {
		return new SystemWebSocketHandler();
	}
	
	@Bean
	//创建WebSocket对象后WebSocket服务所提供的API操作
	public SystemWebSocketHandler2 systemWebSocketHandler2() {
		return new SystemWebSocketHandler2();
	}


}

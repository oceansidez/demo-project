# demo-project
本平台SpringBoot搭建

标准的SpringMVC + Spring + Mybatis结构
包含：
base                各层基类
controller          视图层
entity              实体类（对应Mysql表结构）
service             Service服务层
mapper              Dao数据层（使用tk.mybatis通用mapper）
bean                素材模型（封装自定义属性）
filter              过滤器
interceptor         拦截器（包含Aspect切面和传统Interceptor拦截器）
listener			监听器
init                初始化处理
job                 定时任务（包含Scheduled定时器和Quartz任务组件）
security            Spring-Security组件（权限控制）
websocket           WebSocket（客户端、服务端双向通讯）
official       	            微信公众号
mini				微信小程序
smsgate				短信网关
mongo				MongoDB完整基础类
thread              自定义线程池
util                常用工具

资源：
resources文件夹
---static           静态资源
---static/template  模板文件（用于按模板内容生成指定文件）
---templates        标准页面（freemarker模板，ftl文件后缀）
---messages         i18n国际化翻译

配置文件：
application.properties    			   Springboot配置文件
application-myconfig.properties        自定义配置参数
application-payconfig.properties       第三方支付配置参数
application-mysecurity.properties      Security配置参数
application-official.properties        Official微信公众号配置参数
application-elasticsearch.properties   ElasticSearch配置参数
application-code.properties			        代码自动生成配置参数
application-systemconfig.properties	        系统配置参数
authorize-role-config.yml              权限集合

config ***重要***:		各类配置
---FreemarkerConfig    	Freemarker配置
---GlobalValue         	可动态变化的全局变量（例如存储所有websocket所有连接的set集合）
---RedisConfig         	Redis缓存配置
---SwaggerConfig        Swagger配置
---WebSocketConfig      Websocket协议配置
---WebMvcConfig         Mvc自定义配置（含默认跳转页，拦截器，文件路径映射）
---WebConfig           	myconfig文件变量配置
---SecurityConfig       mysecurity文件变量配置
---PayConfig            payconfig文件变量配置
---OfficialConfig       officialconfig文件变量配置
---ElasticSearchConfig  elasticsearch文件变量配置
---CodeGenerateConfig   codeconfig文件变量配置


bean
---CommonConstant	 	常用字典最终静态变量（不可改变）

pay                    	第三方支付相关
---PayBean              第三方支付通用入参
---PayResult            第三方支付通用结果
---PayUtil              第三方支付核心工具
---wechat文件夹                      	微信支付
---alipay文件夹                      	支付宝
---bestpay文件夹                   	    翼支付

official				微信公众号
---WechatOfficialCore   核心类
---WechatUploadFile     核心上传类

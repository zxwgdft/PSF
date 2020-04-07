# PSF

<h3>组件</h3>
<ul>   
    <li><b>Alibaba Nacos：</b>一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台，相较Eureka功能更强大</li>
    <li><b>Alibaba Sentinel：</b>把流量作为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性，相较Hystrix功能更强大</li>
    <li><b>Spring Gateway：</b>Spring自主开发的微服务网关，应对Zuul不在更新的问题</li>
    <li><b>RabbitMQ：</b>消息中间件</li>
    <li><b>MongoDB：</b>NoSql数据库</li>
    <li><b>MySql：</b>关系型数据库</li>
    <li><b>Redis：</b>缓存数据库</li>   
</ul>

<h3>应用</h3>
<ul>   
    <li><b>Common：</b>存放一些公用代码，并且这些代码不涉及业务</li>
    <li><b>Common-Web：</b>在Common代码的基础上增加了Web相关公共代码，作为业务项目的基础代码框架</li>
    <li><b>Eureka：</b>服务发现与注册中心，已被Alibaba Nacos取代</li>
    <li><b>Gateway：</b>网关，进行地址转发和过滤，网关与Organization应用共同完成统一认证功能，存在与Organization耦合</li>
    <li><b>Organization：</b>主要业务应用，完成人员管理、组织管理、权限管理、应用管理4个大业务模块</li>
    <li><b>Web：</b>空，日后可能作为前端使用</li>   
</ul>

<h3>如何开始</h3>
<p>开发环境使用的Nacos、数据库等服务都在172.16.16.120上已经部署了（可通过application-dev.properties文件查看），
可直接启动GateWay与Organization应用，但是由于还没有前端页面，暂时只能用Postman发送请求测试。</p>


<h3>还在犹豫中的技术</h3>
<ul>   
    <li><b>Liquibase：</b>Liquibase是一个用于跟踪，管理和应用数据库变化的开源的数据库重构工具。它将所有数据库的
    变化(包括结构和数据) 都保存在XML文件中，便于版本控制。但是对于我们来说还未发现很大的使用价值</li>
    <li><b>JHipster：</b>强大的脚手架工具与框架，可生成最佳实践的项目与代码。但是对于我们现在的技术选型与国内情况
    来看暂时并不是非常适用</li>    
</ul>
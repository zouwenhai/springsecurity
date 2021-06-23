#### jwt由三部分组成

header (base64加密后)
payload (base64加密后)
secret (盐值)
jwt的第三部分就是是sha256（header.payload，secret）

jwt = header.payload.sha256(header.secret)

### 任务

###### 1 需要写一个全局异常器，统一返回异常给前端。比如说未登录,或者token校验失效 [完成]

##### 2 写一个拦截器，拦截所有的请求。除了免登录接口以外度都需要进行token校验。 [完成]



# spring-cloud-gateway-nacos

相信大家都会使用SpringSecurity oauth2.0的四种授权模式：

其中密码模式跟客户端模式会用到cleintid和cleintsecret会使得前端很不友好而且不安全。针对这个问题可以这样解决

1、将 cleintid、cleintsecret 组合成  cleintid:cleintsecret

2、访问：https://base64.us/；将组合好的字符串粘贴到加密成Base64就可以了

3、访问时在 Header头信息加入  'Basic  '+生成的Base64  就可以了

Authorization: Basic Y2xpZW50OjEyMzQ1Ng==
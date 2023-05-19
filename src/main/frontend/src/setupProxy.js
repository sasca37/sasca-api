const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
    app.use(
        '/api',
        createProxyMiddleware({
            target: 'http://localhost:8080', // 프록시할 API 서버의 주소
            changeOrigin: true
        })
    );
};

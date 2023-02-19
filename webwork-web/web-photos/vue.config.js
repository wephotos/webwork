// vue.config.js
module.exports = {
    publicPath: '/web-photos',
    assetsDir: 'resources',
    lintOnSave: true,
    devServer: {
        proxy: 'http://127.0.0.1:8090'
    }
}

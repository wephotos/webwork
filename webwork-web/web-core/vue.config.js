// vue.config.js
module.exports = {
    publicPath: '/',
    assetsDir: 'resources',
    lintOnSave: true,
    devServer: {
        proxy: 'http://127.0.0.1:8090'
    }
}
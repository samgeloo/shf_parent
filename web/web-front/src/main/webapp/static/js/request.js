
var request = axios.create({
    baseURL:'http://139.198.152.148:8002',
    timeout:100000
});

//添加一个响应拦截器
request.interceptors.response.use(function(response){
    //在这里对返回的数据进行处理
    // debugger
    console.log(JSON.stringify(response))

    if (response.data.code == 208) {
        window.location.href = 'login.html?originUrl='+window.location.href
    } else {
        // debugger
        if (response.data.code == 200) {
            return response.data
        } else {
            return Promise.reject(response)
        }
    }
},function(error){
    //Do something with response error
    return Promise.reject(error);
})

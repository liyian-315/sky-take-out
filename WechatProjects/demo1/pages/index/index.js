// index.js
Page({
  data:{
    msg:'hello world',
    nickName:'',
    url:'',
    code:'',
  },

  // 定义JS方法，获取wx用户头像昵称
  getUserInfo(){
    wx.getUserProfile({
      desc: '获取用户信息',
      // res代表获取返回的数据
      success: (res) =>{
        // 控制台打印
        console.log(res.userInfo)
        //为数据赋值
        this.setData({
          nickName: res.userInfo.nickName,
          url: res.userInfo.avatarUrl
        })
      }
    })
  },

  //微信登陆，获取微信用户的授权码
  wxLogin(){
    wx.login({
      success: (res)=>{
        console.log(res.code)
        this.setData({
          code: res.code
        })
      }
    })
  },

  // 发送请求
  sendRequest(){
    wx.request({
      url: 'http://localhost:8080/user/shop/status',
      method:'GET',
      success: (res)=>{
        console.log(res.data)
      }
    })
  }

})

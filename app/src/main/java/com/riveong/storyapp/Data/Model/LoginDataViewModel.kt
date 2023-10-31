// package com.riveong.storyapp.Model
//
// import androidx.lifecycle.ViewModel
// import com.riveong.storyapp.Data.Retrofit.ApiConfig
// import com.riveong.storyapp.Data.Retrofit.LoginResponse
// import retrofit2.Call
// import retrofit2.Callback
// import retrofit2.Response
//
// class LoginDataViewModel : ViewModel() {
//
// private fun postLogins(email: String, password: String) {
// val client = ApiConfig.getApiService().postLogin(email, password)
// client.enqueue(object : Callback<LoginResponse> {
// override fun onResponse(
// call: Call<LoginResponse>,
// response: Response<LoginResponse>
// ) {
//
// val responseBody = response.body()
// if (response.isSuccessful && responseBody != null) {
// print(response)
// } else {
// print("gagal")
// }
// }
// override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//
//
// }
// })
// }
//
//
// }
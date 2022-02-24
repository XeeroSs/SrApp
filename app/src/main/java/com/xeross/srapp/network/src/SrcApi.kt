package com.xeross.srapp.network.src

import com.xeross.srapp.BuildConfig
import com.xeross.srapp.network.src.responses.leaderboard.SrcLeaderBoard
import com.xeross.srapp.network.src.responses.notifications.SrcNotifications
import com.xeross.srapp.network.src.responses.users.SrcUser
import com.xeross.srapp.network.src.responses.users.pb.SrcUserPB
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Only Read.
 *
 * @author XeroSs
 */
interface SrcApi {

    @GET("users/{id}")
    fun getProfile(@Path("id") name: String/*, @Query("key") api_key: String*/): Call<SrcUser>

    @GET("users/{id}/personal-bests")
    fun getPB(@Path("id") name: String/*, @Query("key") api_key: String*/): Call<SrcUserPB>

    @GET("users/{id}/personal-bests")
    fun getPBByGame(@Path("id") name: String, @Query("embed") game: String/*, @Query("key") api_key: String*/): Call<SrcUserPB>

    @GET("leaderboards/{game}/category/{category}")
    fun getLeaderBoards(@Path("game") game: String, @Path("category") category: String): Call<SrcLeaderBoard>

    @GET("leaderboards/{game}/category/{category}")
    fun getILLeaderBoards(@Path("game") game: String, @Path("level") level: String, @Path("category") category: String): Call<SrcLeaderBoard>

    @Headers("Host: www.speedrun.com",
            "Accept: application/json",
            "X-API-Key: ${BuildConfig.SRC_KEY}")
    @GET("notifications")
    fun getNotifications(/*@Header("X-API-Key") key: String*/): Call<SrcNotifications>

}
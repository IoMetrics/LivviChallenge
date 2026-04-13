package com.cristiano.livvichallenge.core.network

import com.cristiano.livvichallenge.data.dto.DoorDto
import com.cristiano.livvichallenge.data.dto.DoorEventsPageDto
import com.cristiano.livvichallenge.data.dto.DoorsPageDto
import com.cristiano.livvichallenge.data.dto.SignInRequestDto
import com.cristiano.livvichallenge.data.dto.SignInResponseDto
import com.cristiano.livvichallenge.data.dto.SignUpRequestDto
import com.cristiano.livvichallenge.data.dto.SignUpResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("users/signin")
    suspend fun signIn(
        @Body body: SignInRequestDto
    ): SignInResponseDto

    @POST("users/signup")
    suspend fun signUp(
        @Body body: SignUpRequestDto
    ): SignUpResponseDto

    @GET("doors")
    suspend fun getDoors(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): DoorsPageDto

    @GET("doors/{id}")
    suspend fun getDoorById(
        @Path("id") doorId: Int
    ): DoorDto

    @GET("doors/find")
    suspend fun findDoors(
        @Query("name") name: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): DoorsPageDto

    @GET("doors/{id}/events")
    suspend fun getDoorEvents(
        @Path("id") doorId: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): DoorEventsPageDto
}

package com.gmail.sbrunet102.smack.services

import android.content.Context
import android.util.Log
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.gmail.sbrunet102.smack.controller.App
import com.gmail.sbrunet102.smack.model.Channel
import com.gmail.sbrunet102.smack.utilities.URL_GET_CHANNELS
import org.json.JSONException

object MessageService {

    val channels = ArrayList<Channel>()

    fun getChannels(complete:(Boolean)->Unit){

        val channelRequest = object :  JsonArrayRequest(Method.GET, URL_GET_CHANNELS,null,Response.Listener { response ->

            try {

                for(x in 0 until response.length()){
                    val channel = response.getJSONObject(x)
                    val channelName = channel.getString("name")
                    val channelDesc = channel.getString("description")
                    val channelId = channel.getString("_id")

                    val newChannel = Channel(channelName,channelDesc,channelId)
                    this.channels.add(newChannel)
                }
                complete(true)
            }catch (e:JSONException){
                Log.d("JSON","EXC"+e.localizedMessage)
                complete(false)
            }

        },Response.ErrorListener {error ->
            Log.d("ERROR","Could not retreive channels")
            complete(false)
        }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Authorization", "Bearer ${App.prefs.authToken}")
                return headers
             }
        }
        App.prefs.requestQueue.add(channelRequest)
    }
}
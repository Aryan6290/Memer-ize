package com.example.memesharer


import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var ImageUrl:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()

    }

    private fun loadMeme(){
        progressbar.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val memeRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                // Display the first 500 characters of the response string.
                val url = response.getString("url")
                ImageUrl=url
                Glide.with(this).load(url).listener(object : RequestListener<Drawable>{
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility=View.GONE
                        return false
                    }
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        TODO("Not yet implemented")
                        progressbar.visibility=View.GONE

                        return false

                    }

                }).into(memeImageView)

            },
            Response.ErrorListener {
                Toast.makeText(this,"No INternet",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        queue.add(memeRequest)

    }

    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout this meme! $ImageUrl ")
        val chooser=Intent.createChooser(intent,"Share this text using...")
        startActivity(intent)
    }
    fun NextMeme(view: View) {
        loadMeme()
    }
}
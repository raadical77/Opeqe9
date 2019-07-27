package ir.app.opeqe

 import android.graphics.Bitmap
 import android.graphics.Color
 import android.graphics.Typeface
 import android.graphics.drawable.GradientDrawable
 import android.os.Bundle
 import android.support.v7.app.AppCompatActivity
 import android.view.Gravity
 import android.view.View
 import android.view.ViewGroup
 import com.android.volley.Request
 import com.android.volley.Response
 import com.android.volley.toolbox.ImageRequest
 import com.android.volley.toolbox.JsonArrayRequest
 import org.jetbrains.anko.*
 import org.json.JSONArray
 import java.util.*


class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         val buttonCaption = "..."
        var items: MutableList<String> = mutableListOf<String>()
        var rate: MutableList<Int> = mutableListOf<Int>()
        var preparation: MutableList<String> = mutableListOf<String>()
        var imageurl: MutableList<String> = mutableListOf<String>()
        var region: MutableList<String> = mutableListOf<String>()
        var mealtype: MutableList<String> = mutableListOf<String>()

        val url = "http://imagetranslator.ir/menu.json"
        val arrayListener = Response.Listener<JSONArray> { response ->
            for (i in 0 until response.length()) {
                try {
                    val `object` = response.getJSONObject(i)
                    items.add(`object`.getString("title"))
                    rate.add(`object`.getInt("rate"))
                    preparation.add(`object`.getString("preparation"))
                    imageurl.add(`object`.getString("image"))
                    val c = `object`.getJSONObject("cuisineType")
                    region.add(c.getString("title"))
                    val meal = `object`.getJSONObject("mealType")
                    mealtype.add(meal.getString("title"))




                } catch (e: Exception) {

                }


            }
            verticalLayout{
                backgroundColor = Color.parseColor("#ffffff")
                linearLayout{
                    textView("Restaurants") {
                        textSize = 22f
                        gravity = Gravity.CENTER
                        setTypeface(null , Typeface.BOLD)

                    }.lparams(matchParent , wrapContent){
                        topMargin = dip(30)
                        bottomMargin = dip(16)
                    }
                }

                linearLayout {
                    gravity = Gravity.CENTER
                    searchView() {
                        background = s()
                        queryHint = "Restaurants & dishes"
                        backgroundColor = Color.parseColor("#ebebeb")

                    }.lparams{
                        width=0
                        weight=0.55f
                        height = dip(60)
                        margin = dip(8)
                    }
                    imageView(R.mipmap.first) {
                    }.lparams{
                        width=0
                        weight =0.2f
                        height = dip(60)

                    }
                    imageView(R.mipmap.dash) {
                    }.lparams{
                        width= 0
                        weight=0.05f
                        height = dip(48)
                    }
                    imageView(R.mipmap.two) {
                    }.lparams{
                        width=0
                        weight =0.2f
                        height = dip(60)
                    }
                }

                val list =  listView {
                    backgroundColor = Color.parseColor("#f3f3f3")
                }.lparams{
                    width = matchParent
                    height = 0
                    weight =1f
                    topMargin = dip(7)

                }

                list.divider = null

                list.adapter = AnkoAdapter({items as AbstractList<Any> }) { index: Int, items: AbstractList<Any>, view: View?, viewGroup: ViewGroup? ->
                    with(viewGroup!!.context) {
                        verticalLayout {
                            backgroundColor = Color.parseColor("#f3f3f3")

                            linearLayout {
                                background = buttonBg()
                                val img = imageView() {

                                }.lparams {
                                    width = 0
                                    weight = 0.3f
                                    margin = dip(4)

                                }
                                val bitmapListener = Response.Listener<Bitmap> { response ->
                                    img.setImageBitmap(response)

                                }
                                val er = Response.ErrorListener {

                                }
                                val rr = ImageRequest(imageurl[index], bitmapListener, 0, 0, null, null, er)
                                AppController.getInstance().addToRequestQueue(rr)


                                verticalLayout {
                                    textView(items[index].toString()) {
                                        textSize = 25f
                                        textColor = Color.parseColor("#0070eb")
                                        setTypeface(null , Typeface.BOLD)
                                    }
                                    textView("${region[index]}, ${mealtype[index]}") {
                                        textColor = Color.parseColor("#c1c1c1")
                                        textSize = 18f
                                    }
                                    linearLayout {

                                        textView("${preparation[index]} mins") {
                                            textSize = 20f
                                            textColor = Color.BLACK
                                        }.lparams {
                                            topMargin = dip(20)
                                            leftMargin = dip(8)


                                        }
                                        button {
                                            visibility = View.INVISIBLE
                                        }.lparams {
                                            width = 0
                                            weight = 1f
                                        }

                                        verticalLayout {
                                            val r = ratingBar {
                                                numStars = 5
                                                rating = rate[index].toFloat()
                                                scaleY = 0.5f
                                                scaleX = 0.7f


                                            }

                                             r.setIsIndicator(true)

                                            textView("${rate[index]} rating")
                                        }.lparams {
                                            //rightMargin = dip(30)
                                            gravity = Gravity.LEFT
                                        }
                                    }.lparams{
                                        gravity = Gravity.RIGHT
                                    }
                                }.lparams {
                                    width = 0
                                    weight = 0.7f
                                    leftMargin = dip(10)
                                    gravity = Gravity.CENTER
                                }

                            }.lparams {

                                width = matchParent
                                gravity = Gravity.CENTER
                                margin = dip(16)


                            }
                        }.lparams{
                            width = matchParent
                        }
                    }
                }


            }
        }
        val errorListener = Response.ErrorListener {

        }
        val request = JsonArrayRequest(Request.Method.GET, url, null, arrayListener, errorListener)
        AppController.getInstance().addToRequestQueue(request)




    }

}
fun buttonBg() = GradientDrawable().apply {
    cornerRadius = 8f
    setColor(Color.parseColor("#ffffff"))

}

fun s() = GradientDrawable().apply {
    cornerRadius = 8f


}

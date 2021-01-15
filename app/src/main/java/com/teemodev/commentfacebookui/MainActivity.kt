package com.teemodev.commentfacebookui

import CommentResponse
import Comments
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.teemodev.commentfacebookui.adapter.CommentAdapter
import com.teemodev.commentfacebookui.data.ItemsGroup
import com.teemodev.commentfacebookui.extention.*
import com.teemodev.commentfacebookui.listener.CommentNavigator
import com.teemodev.commentfacebookui.utils.DeviceUtil
import com.teemodev.commentfacebookui.utils.Utils.getJsonFromAssets
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type

class MainActivity : AppCompatActivity(), CommentNavigator {

    var itemsGroupList: List<ItemsGroup> = ArrayList()

    lateinit var adapters: List<CommentAdapter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonFileString = getJsonFromAssets(
            applicationContext,
            "comments.json"
        )
        Log.i("data", jsonFileString.toString())

        val gson = Gson()
        val commentResponseType: Type = object : TypeToken<CommentResponse?>() {}.type

        val commentResponse: CommentResponse =
            gson.fromJson<CommentResponse>(jsonFileString, commentResponseType)

        for (comment: Comments in commentResponse.data.comments) {
            itemsGroupList += ItemsGroup(comment, comment.replies)
        }

        adapters = itemsGroupList.map {
            CommentAdapter(this, it)
        }

        val concatAdapterConfig = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        val concatAdapter = ConcatAdapter(concatAdapterConfig, adapters)

        with(comment_list) {
            layoutManager = LinearLayoutManager(context)
//            itemAnimator =
//                ExpandableItemAnimator()
            adapter = concatAdapter
        }

        tag_cancel.setOnClickListener {
            onCancelComment()
        }
    }

    private fun getData() {

    }

    override fun onCommentBoxClicked(comments: Comments) {
        tag_layout.visibility = View.VISIBLE

        val spanned = spannable{ normal("Đang trả lời ") + color(Color.BLACK, "${comments.user.fullname}") }

        tag_description.text = spanned
        DeviceUtil.showKeyboard(this)
    }

    override fun onCommentLoadMoreClicked(comments: Comments) {
        // TODO("Not yet implemented")
    }

    private fun onCancelComment() {
        tag_layout.visibility = View.GONE
        DeviceUtil.hideKeyboard(this)
    }
}
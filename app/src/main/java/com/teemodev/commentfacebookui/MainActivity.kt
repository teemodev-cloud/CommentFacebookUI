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

    var adapters: List<CommentAdapter> = ArrayList()

    private lateinit var concatAdapter: ConcatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val concatAdapterConfig = ConcatAdapter.Config.Builder()
            .setIsolateViewTypes(false)
            .build()
        concatAdapter = ConcatAdapter(concatAdapterConfig, adapters)

        with(comment_list) {
            layoutManager = LinearLayoutManager(context)
//            itemAnimator =
//                ExpandableItemAnimator()
            adapter = concatAdapter
        }

        tag_cancel.setOnClickListener {
            onCancelComment()
        }

        getCommentData()
    }

    private fun getCommentData() {
        val jsonFileString = getJsonFromAssets(
            applicationContext,
            "comments.json"
        )
        Log.i("data", jsonFileString.toString())

        val commentResponseType: Type = object : TypeToken<CommentResponse?>() {}.type

        val commentResponse: CommentResponse =
            Gson().fromJson<CommentResponse>(jsonFileString, commentResponseType)

        for ((index: Int, comment: Comments) in commentResponse.data.comments.withIndex()) {
            itemsGroupList += ItemsGroup(index, comment, comment.replies)
        }

        adapters = itemsGroupList.map {
            CommentAdapter(this, it)
        }

        for (commentAdapter: CommentAdapter in adapters) {
            concatAdapter.addAdapter(commentAdapter)
        }
    }

    override fun onCommentBoxClicked(comments: Comments) {
        tag_layout.visibility = View.VISIBLE

        val spanned = spannable{ normal("Đang trả lời ") + color(Color.BLACK, "${comments.user.fullname}") }

        tag_description.text = spanned
        DeviceUtil.showKeyboard(this)
    }

    override fun onCommentLoadMoreClicked(position: Int, comments: Comments) {

        val jsonFileString = getJsonFromAssets(
            applicationContext,
            "replies.json"
        )
        Log.i("data", jsonFileString.toString())

        val commentResponseType: Type = object : TypeToken<CommentResponse?>() {}.type

        val commentResponse: CommentResponse =
            Gson().fromJson<CommentResponse>(jsonFileString, commentResponseType)

        for (reply: Comments in commentResponse.data.comments) {
            itemsGroupList[position].replies += reply
        }

        adapters[position].notifyDataSetChanged()

    }

    private fun onCancelComment() {
        tag_layout.visibility = View.GONE
        DeviceUtil.hideKeyboard(this)
    }
}
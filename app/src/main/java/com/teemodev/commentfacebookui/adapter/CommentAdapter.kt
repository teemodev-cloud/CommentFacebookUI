package com.teemodev.commentfacebookui.adapter

import Comments
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teemodev.commentfacebookui.R
import com.teemodev.commentfacebookui.data.ItemsGroup
import com.teemodev.commentfacebookui.listener.CommentNavigator
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_comment_box.view.*
import kotlinx.android.synthetic.main.item_loadmore.view.*

class CommentAdapter(private val commentNavigator: CommentNavigator, private val itemsGroup: ItemsGroup) :
    RecyclerView.Adapter<CommentAdapter.CommentAdapterVH>() {

    companion object {
        private const val VIEW_TYPE_COMMENT = 1
        private const val VIEW_TYPE_REPLY = 2
        private const val VIEW_TYPE_LOAD_MORE = 3
        private const val VIEW_TYPE_COMMENT_BOX = 4
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_COMMENT
            1 -> VIEW_TYPE_LOAD_MORE
            itemsGroup.replies.size + 2 -> VIEW_TYPE_COMMENT_BOX
            else -> VIEW_TYPE_REPLY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapterVH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_COMMENT -> CommentAdapterVH.CommentVH(
                inflater.inflate(
                    R.layout.item_comment,
                    parent,
                    false
                )
            )
            VIEW_TYPE_REPLY -> CommentAdapterVH.ReplyVH(
                inflater.inflate(
                    R.layout.item_reply,
                    parent,
                    false
                )
            )
            VIEW_TYPE_LOAD_MORE -> CommentAdapterVH.LoadMoreVH(
                inflater.inflate(
                    R.layout.item_loadmore,
                    parent,
                    false
                )
            )
            else -> CommentAdapterVH.CommentBoxVH(
                inflater.inflate(
                    R.layout.item_comment_box,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return itemsGroup.replies.size + 3
    }

    override fun onBindViewHolder(holder: CommentAdapterVH, position: Int) {
        when (holder) {
            is CommentAdapterVH.CommentVH -> holder.bind(itemsGroup.comment)
            is CommentAdapterVH.ReplyVH -> {
                holder.bind(itemsGroup.replies[position - 2])
            }
            is CommentAdapterVH.LoadMoreVH -> {
                holder.bind(itemsGroup.index, itemsGroup.comment, commentNavigator)
            }
            is CommentAdapterVH.CommentBoxVH -> {
                holder.bind(itemsGroup.comment, commentNavigator)
            }
        }
    }

    sealed class CommentAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class CommentVH(itemView: View) : CommentAdapterVH(itemView) {
            fun bind(comment: Comments) {
                itemView.apply {
                    itemView.newFeedAuthor.text = comment.user.fullname
                    itemView.newFeedContent.text = comment.content
                }
            }
        }

        class ReplyVH(itemView: View) : CommentAdapterVH(itemView) {
            fun bind(replies: Comments) {
                itemView.apply {
                    itemView.newFeedAuthor.text = replies.user.fullname
                    itemView.newFeedContent.text = replies.content
                }
            }
        }

        class LoadMoreVH(itemView: View) : CommentAdapterVH(itemView) {
            fun bind(position: Int, comment: Comments, commentNavigator: CommentNavigator) {
                itemView.apply {
                    itemView.load_more.setOnClickListener {
                        commentNavigator.onCommentLoadMoreClicked(position, comment)
                    }
                }
            }
        }

        class CommentBoxVH(itemView: View) : CommentAdapterVH(itemView) {
            fun bind(comment: Comments, commentNavigator: CommentNavigator) {
                itemView.apply {
                    itemView.comment_box.setOnClickListener {
                        commentNavigator.onCommentBoxClicked(comment)
                    }
                }
            }
        }
    }
}
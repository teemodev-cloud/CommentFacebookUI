package com.teemodev.commentfacebookui.listener

import Comments

interface CommentNavigator {

    fun onCommentBoxClicked(comments: Comments)

    fun onCommentLoadMoreClicked(position: Int, comments: Comments)
}
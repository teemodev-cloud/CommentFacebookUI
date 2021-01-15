package com.teemodev.commentfacebookui.data

import Comments
import Replies

data class ItemsGroup(val comment: Comments, var replies: List<Replies>)
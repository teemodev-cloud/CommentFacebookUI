package com.teemodev.commentfacebookui.data

import Comments

data class ItemsGroup(val index: Int, val comment: Comments, var replies: List<Comments>)
package com.jchanghong.model

import com.jchanghong.GlobalApplication
import java.io.Serializable

data class Category(var id: Long = 0,
                    var name: String=GlobalApplication.db.cat_name[0],
                    var color: String=GlobalApplication.db.cat_color[0],
                    var icon: String=GlobalApplication.db.cat_icon[0],
                    var note_count: Int = 0) : Serializable
{
    fun exit():Boolean =
        GlobalApplication.db.allCategory.firstOrNull { it.name==name }!=null
    fun create() {
        GlobalApplication.db.insertCategory(this)
    }

    override fun equals(other: Any?) =
        if (other !is Category) {
             false
        }
        else{
            other.name==name
        }

}

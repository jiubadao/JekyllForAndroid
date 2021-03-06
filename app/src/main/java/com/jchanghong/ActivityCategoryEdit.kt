package com.jchanghong

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.jchanghong.adapter.ListAdapterCategoryIcon
import com.jchanghong.data.DatabaseManager
import com.jchanghong.model.Category

/**
 * \* Created with IntelliJ IDEA.
 * \* User: jchanghong
 * \* Date: 12/06/2016
 * \* Time: 10:00
 * \ */
class ActivityCategoryEdit : AppCompatActivity() {

    lateinit private var parent_view: View
    private var ext_cat: Category? = null
    lateinit private var btnSave: Button
    lateinit private var txtTittle: EditText
    private var radioIcon: RadioButton? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_add)
        parent_view = findViewById(android.R.id.content)

        //init component
        initComponent()

        // init DatabaseManager

        hideKeyboard()

        // get extra object
        ext_cat = intent.getSerializableExtra(EXTRA_OBJCT) as? Category

        val recyclerView = findViewById(R.id.category_icon_list) as RecyclerView
        recyclerView.setHasFixedSize(true)

        //grid layout
        val lLayout = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = lLayout
        val icons = DatabaseManager.categoryIcon
        val ai = ListAdapterCategoryIcon(applicationContext, icons)
        recyclerView.adapter = ai

        //define field from object
        if (ext_cat != null) {
            txtTittle.setText(ext_cat!!.name)
            ai.setSelectedRadio(ext_cat!!.icon)
        }

        btnSave.setOnClickListener {
            if (txtTittle.text.toString() == "" || ai.selectedCategoryIcon == null) {
                Toast.makeText(applicationContext, getString(R.string.categorynamecanno_empty), Toast.LENGTH_SHORT).show()
            } else {
                if (ext_cat != null) {
                    if (ext_cat!!.name == txtTittle.text.toString() && ext_cat!!.icon == ai.selectedCategoryIcon?.icon) {
                        finish()
                    } else {
                        ext_cat!!.name = txtTittle.text.toString()
                        ext_cat!!.icon = ai.selectedCategoryIcon!!.icon
                        ext_cat!!.color = ai.selectedCategoryIcon!!.color
                        DatabaseManager.updateCategory(ext_cat!!)
                        finish()
                        Toast.makeText(applicationContext, getString(R.string.category_updated), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val category = Category()
                    category.name = txtTittle.text.toString()
                    category.color = ai.selectedCategoryIcon!!.color
                    category.icon = ai.selectedCategoryIcon!!.icon
                    DatabaseManager.insertCategory(category)
                    finish()
                    Toast.makeText(applicationContext, getString(R.string.categorysaved), Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun initComponent() {
        txtTittle = findViewById(R.id.cat_tittle) as EditText
        btnSave = findViewById(R.id.btn_save) as Button
        radioIcon = findViewById(R.id.radioSelected) as? RadioButton
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    companion object {
        val EXTRA_OBJCT = "com.jchanghong.EXTRA_OBJECT_CATEGORY"

    }
}

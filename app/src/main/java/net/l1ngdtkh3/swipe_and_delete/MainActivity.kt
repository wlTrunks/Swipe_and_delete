package net.l1ngdtkh3.swipe_and_delete

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        initSwipe()
    }

    private fun initSwipe() {
        val mRecyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.addItemDecoration(SpacesItemDecoration(20))
        val myCallback = SwipeItemCallback()
        val itemTouchHelper = ItemTouchHelper(myCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
        val itemAdapter = ItemAdapter(object : ItemCallBack {
            override fun onCancel(item: ItemList, viewHolder: RecyclerView.ViewHolder) {
                myCallback.clearView(mRecyclerView, viewHolder)
            }

            override fun onDelete(item: ItemList) {
                viewModel.deleteItem(item)
            }
        })
        mRecyclerView.adapter = itemAdapter
        lifecycleScope.launchWhenCreated {
            viewModel.itemFlow.collect {
                itemAdapter.submitList(it)
            }
        }
    }
}

interface ItemCallBack {
    fun onCancel(item: ItemList, viewHolder: RecyclerView.ViewHolder)
    fun onDelete(item: ItemList)
}

class MainViewModel : ViewModel() {
    private val itemList: MutableList<ItemList> = mutableListOf()

    init {
        for (i in 1..19) {
            itemList.add(ItemList("ITEM $i"))
        }
    }

    val itemFlow = MutableStateFlow(itemList.toList())

    fun deleteItem(item: ItemList) {
        viewModelScope.launch(Dispatchers.Default) {
            itemFlow.emit(itemList.filter { it.data != item.data })
            itemList.remove(item)
        }
    }
}

data class ItemList(val data: String, var swiped: Boolean = false)

private class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        if (parent.getChildAdapterPosition(view) == 0) outRect.top = space
    }
}